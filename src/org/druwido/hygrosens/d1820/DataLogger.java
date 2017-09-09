package org.druwido.hygrosens.d1820;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Handles the input coming from the serial port.
 * The data is parsed according to the hygrosens
 * reference manual.
 */
public class DataLogger implements SerialPortEventListener
{
	public static final char START_CHAR = '@';
	public static final char END_CHAR   = '$';
	public static final String NEWLINE  = "\r";

	private long time;
	private int previous;
	private StringBuffer buffer;
	private InputStream in;
	private PrintWriter out;
	private DataFormatter formatter;

	public DataLogger(InputStream in, PrintWriter out, DataFormatter formatter)
	{
		this.time = System.currentTimeMillis();
		this.previous = -1;
		this.buffer = new StringBuffer();
		this.in = in;
		this.out = out;
		this.formatter = formatter;
	}

	public void serialEvent(SerialPortEvent arg0)
	{
		int data;

		try
		{
			while (in.available() > 0)
			{
				data = in.read();

				if (data == START_CHAR)
				{
					buffer.delete(0, buffer.length());
					time = System.currentTimeMillis();
				}
				else if (data == END_CHAR)
				{
					parseData(buffer.toString());
				}
				else if (previous != START_CHAR && previous != END_CHAR)
				{
					buffer.append((char) data);
				}

				previous = data;
			}
		}
		catch (IOException e)
		{
			HygroMain.error("IOException in SerialPortEventListener.", e);
		}
	}

	private void parseData(String input)
	{
		String[] lines = input.split(NEWLINE);

		for (int i = 0; i < lines.length; i += 2)
		{
			D1820Sensor data = new D1820Sensor();

			try
			{
				data.setBus(Integer.parseInt(lines[i].substring(1, 3),16));
				data.setModel(lines[i].substring(3, 5));
				data.setHardware(lines[i].substring(5, 7));
				data.setId(lines[i].substring(7, 19));
				data.setTime(time);
				data.setValue(parseValue(lines[i + 1]));

			} catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}


			// print parsed result to given PrintWriter
			out.println(formatter.format(data));
		}

		out.flush();
	}

	private float parseValue(String dataLine)
	{
		int value = Integer.parseInt(dataLine.substring(3, 7), 16);

		// correct data for negative values
		if (value > 0x7FFF)
			value -= 0xFFFF;

		return (float)value / 100;
	}
}
