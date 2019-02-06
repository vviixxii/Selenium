I do not add the driver .exe's into the repo

Either add them to your path on the build system or during CI pass in the path to the driver as a property

Or have them in a C://webdrivers folder you can see the paths used in the Driver.java file

Controlling it on your build machine with path or system property is probably the best bet, but we have hardcoded defaults to look for in the Driver.java if they work for you.

