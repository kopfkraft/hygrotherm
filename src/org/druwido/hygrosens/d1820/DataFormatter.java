package org.druwido.hygrosens.d1820;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to format the log output. Change or implement your own
 * custom DataFormatter class for your purposes.
 */
public class DataFormatter
{
	public static final String DEFAULT_DATE_PATTERN = "yyyy.MM.dd kk:mm:ss";
	public static final String DEFAULT_TEMP_PATTERN = "#0.00;-#0.00";
	public static final String DEFAULT_DELIMITER = ";";

	protected SimpleDateFormat dateFormat;
	protected DecimalFormat tempFormat;
	protected String delimiter;

	public DataFormatter()
	{
		this(DEFAULT_DATE_PATTERN, DEFAULT_TEMP_PATTERN, DEFAULT_DELIMITER);
	}

	public DataFormatter(String datePattern, String tempPattern, String delimiter)
	{
		this.dateFormat = new SimpleDateFormat(datePattern);
		this.tempFormat = new DecimalFormat(tempPattern);
		this.delimiter = delimiter;
	}

	public String format(D1820Sensor sensor)
	{
		return sensor.getId()
				+ delimiter + sensor.getBus()
				+ delimiter + sensor.getHardware()
				+ delimiter + sensor.getModel()
				+ delimiter + dateFormat.format(new Date(sensor.getTime()))
				+ delimiter + tempFormat.format(sensor.getValue());
	}
}
