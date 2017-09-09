package org.druwido.hygrosens.d1820;

import gnu.io.*;

import java.io.*;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * The main class. Load native libraries and connects the
 * DataLogger to the giver serial port. The serial port
 * is passed as first command line argument.
 */
public class HygroMain
{
	/**
	 * Loads the native libraries. Make sure that you defined
	 * the proper -Dnative.library.path=/path/to/library
	 * argument at vm startup. The native libraries are
	 * found in the lib/ folder of the project.
	 */
	static
	{
		RXTXNativeLoader.load();
	}

	/**
	 * Constructor of HygroMain.
	 */
	public HygroMain()
	{}

	/**
	 * The main method lists all accessible ports on the
	 * system and tries to connect to the port passed with
	 * arg[0]. On windows it is something like COM15, where
	 * on unixes maybe something like /dev/tty...
	 *
	 * @param args 	first string should point to serial port
	 * 				the optional second arg defines the output file
	 */
	public static void main(String[] args)
	{
		try
		{
			// to find out what ports are available
			listSerialPorts();

			HygroMain main = new HygroMain();

			String port = args.length > 0 ? args[0] : null;
			String file = args.length > 1 ? args[1] : null;

			if(port != null && !port.equals(""))
			{
				main.connect(port, file);
			}
			else
			{
				error("you did not specify a serial port at command line. " +
						"first argument should be the port (eg COM1).", null);
			}
		}
		catch (PortInUseException e)
		{
			error("port already in use. check for already running instances.", e);
		}
		catch (IOException e)
		{
			error("general IOException occurred.", e);
		}
		catch (NoSuchPortException e)
		{
			error("the port you defined does not exist. check the list above.", e);
		}
		catch (TooManyListenersException e)
		{
			error("too many listeners assigned to rxtx.", e);
		}
		catch (UnsupportedCommOperationException e)
		{
			error("comm operation is not supported.", e);
		}
	}

	/**
	 * A helper method that list all ports accessible via
	 * rxtx and prints them on System.out.
	 */
	public static void listSerialPorts()
	{
		println("available ports on system");
		println("-------------------------");

		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements())
			println(((CommPortIdentifier)ports.nextElement()).getName());

		println("-------------------------");
	}

	public static void println(String message)
	{
		System.out.println(message);
	}

	public static void error(String message, Exception e)
	{
		if(e != null)
			println(e.getMessage());
	
		println("ERROR: " + message);
		System.exit(-1);
	}

	/**
	 * Connects to the hygrosens rs232 thermometer box. The
	 * connection settings (baud, parity, ...) are hardcoded,
	 * as you cant change them anyway.
	 *
	 * @param portName the serial port the sensor(s) are connected to
	 */
	public void connect(String portName, String filename) throws NoSuchPortException
												, PortInUseException
												, UnsupportedCommOperationException
												, IOException
												, TooManyListenersException
	{

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		CommPort commPort = portIdentifier.open(this.getClass().getName(),100);

		if (commPort instanceof SerialPort)
		{
			println("connecting to port: " + commPort.getName());

			SerialPort serialPort = (SerialPort) commPort;
			serialPort.setSerialPortParams(	4800
											, SerialPort.DATABITS_8
											, SerialPort.STOPBITS_1
											, SerialPort.PARITY_NONE);

			InputStream in = serialPort.getInputStream();

			PrintWriter writer = filename != null 	? new PrintWriter(filename)
													: new PrintWriter(System.out);

			DataLogger logger = new DataLogger(in, writer, new DataFormatter());

			serialPort.addEventListener(logger);
			serialPort.notifyOnDataAvailable(true);

		}
		else
		{
			error("the defined port is not a serial port.", null);
		}
	}
}