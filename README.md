# MDM-CVMA

The CVMA staging processor extracts XMP metadata from CVMA images.

## How to build

In order to build the MDM-CVMA you'll need:

* Java SE Development Kit 8 or higher
* Apache Maven 3

Change to the folder where the sources are located, e.g.: /home/user/MDM-CVMA/. 
Afterwards, just call:

```
user@localhost:/home/user/MDM-CVMA/$ bash buildMDM-CVMA.sh
[...]
[INFO] Building zip: /home/user/MDM-CVMA/zip/MDM-CVMA-1.1-release.zip
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 24.444 s
[INFO] Finished at: 2017-12-12T16:21:46+01:00
[INFO] Final Memory: 59M/403M
[INFO] ------------------------------------------------------------------------
user@localhost:/home/user/MDM-CVMA/$
```

As soon as the assembly process has finished there will be a file named `MDM-CVMA-1.1-release.zip` 
located at /home/user/MDM-CVMA/zip, which is the distribution package of the client 
containing everything you need to install the tool. Extract the zip file to a directory of your
choice and refer to the contained manual for further instructions.

## More Information

* [Bugtracker](http://datamanager.kit.edu/bugtracker/thebuggenie/)

## License

The MDM-CVMA is licensed under the Apache License, Version 2.0.


