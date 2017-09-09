package org.druwido.hygrosens.d1820;

/**
 * A dummy class to simulate the hygrosens device.
 * Essentially it delivers a random datafeed on a
 * given time interval.
 */
public class DummyMain implements Runnable
{
	private DataFormatter formatter;
	private String port;

	public DummyMain(String port)
	{
		formatter = new DataFormatter();
		this.port = port;
	}

	public void run()
	{

		try
		{
			print("available ports on system");
			print("-------------------------");
			Thread.currentThread().sleep(2000);
			print("COM7");
			print("-------------------------");
			print("connecting to port: //./COM7");

			while(true)
			{
				Thread.currentThread().sleep(5000);
				int count = 6;
				while(count-- > 0)
					print(randomTemp(count));
			}

		} catch (InterruptedException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}


	}

	public String randomTemp(int count)
	{
		D1820Sensor sensor = new D1820Sensor();
		sensor.setBus(count + 1);
		sensor.setHardware(5 + "");
		sensor.setId(Integer.toHexString((count + 10) * 999999));
		sensor.setModel(10 + "");
		sensor.setTime(System.currentTimeMillis());
		sensor.setValue((float) (Math.random() * 60 + 20));

//		String tmp = "65EFDC010800;5;10;01;2011.02.20 17:21:55;38,00";

		return formatter.format(sensor);
	}

	public static void print(String message)
	{
		System.out.println(message);
	}

	public static void main(String[] args)
	{
		Runnable dummy = new DummyMain(args[0]);
		Thread thread = new Thread(dummy);
		thread.start();
	}
}
