package org.druwido.hygrosens.d1820;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * This class is for the means of loading the native library.
 * As the project may be used on several platforms, this class
 * is workaround to have the right native library loaded.
 *
 * You might also have a look at the build.xml to how the
 * packaging is involved.
 */
public class RXTXNativeLoader
{
	public static final String RXTX_SERIAL = "rxtxSerial";

	public static void load()
	{
		try
		{
			//find native library in jar file
			JarFile jarFile = new JarFile("hygrotherm.jar");

			Enumeration entries = jarFile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if(entry.getName().contains(RXTX_SERIAL))
				{
					InputStream in = jarFile.getInputStream(entry);
					OutputStream out = new FileOutputStream(entry.getName());

                    //TODO implement BufferedReader/Writer
                    //TODO check if the folder is already created.
					int c;
					while ((c = in.read()) != -1)
						out.write(c);

					in.close();
					out.close();
					jarFile.close();

					break;
				}
			}

			System.setProperty("java.library.path", ".");
			System.loadLibrary(RXTX_SERIAL);

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
