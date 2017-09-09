Hygrosens RS232 Thermometer Java DataLogger
-------------------------------------------

This software builds on the platform independent
RXTX java library. This enables you to use
several operating system with the same codebase.

The software logs the data to console or file.

Prequisities
------------

You need a Java JDK and Apache Ant installed as
well as JAVA_HOME and ANT_HOME set and A PATH entry
to the executables (java, javac, ant).

Setup
-----
Modify the build.properties file to fit your
need (os, outfile, com port).

Run
---
To run the logger, use

ant run

KNOWN ISSUES
------------
I did not create the libraries myself and do not have
the possibility to test on every platform. Maybe some
modifications to version numbers, build paths, etc
are required.

TIPPS
-----
It is possible to "native compile/link" the project with
gcc. It did this on a linux machine and it worked well.
Unfortunately i did not have time yet to write a proper
makefile/build.xml.

Best Regards
kopfkraft@hotmail.com

