package org.druwido.hygrosens.d1820;

/**
 * Data object that represents all available
 * information from the D1820 temperature sensor
 * (wired with the hygrosens rs232 thermostat).
 */
public class D1820Sensor
{
	private String id;
	private int bus;
	private float value;
	private String hardware;
	private String model;
	private long time;

	/**
	* Empty constructor
	*/
	public D1820Sensor()
	{}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getBus()
	{
		return bus;
	}

	public void setBus(int bus)
	{
		this.bus = bus;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

	public String getHardware()
	{
		return hardware;
	}

	public void setHardware(String hardware)
	{
		this.hardware = hardware;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}
}