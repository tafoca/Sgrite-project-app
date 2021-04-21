# Sgrite-project-app
Project area: Data mining, big data and data analysis (AI) The goal of this project is the efficient application of gradual pattern extraction methods.
#Organization of folder 
- This project is implemented in Java language. The IDE used is Netbeans.
- the most important directories are src, datasets, runnable, and nbproject. 
- The nbproject directory contains respectively the configuration files which indicate the main classes of the different methods. So we find there:
nbproject / configs / VersionRBIGrite.properties,
nbproject / configs / VersionRBISG1.properties,
nbproject / configs / VersionRBISGB1.properties,
nbproject / configs / VersionRBISGB2.properties, and
nbproject / configs / VersionRBISGOpt.properties.
# Description of Runtime
This file of name, `runnable` contains the dataset test, and the methods to be executed.

## Environment
The java support environment is the version 8 (JDK 8)
## Step follow for the execution
- For the execution of the code it is necessary to generate the executable java of each method.
- then to execute the proce de command line as follows:
``java -jar executable filename threshold``
- for example to execute SGOPt, SG1, SGOpt, SGB1, SGB2 and Grite for a minimum support threshold value 0.05 we will write the following command:
* ``java -jar SGOpt.jar test.dat 0.05``
* ``java -jar SG1.jar test.dat 0.05``
* ``java -jar Grite.jar test.dat 0.05``
* ``java -jar SGB1.jar test.dat 0.05``
* `java -jar SGB2.jar test.dat 0.05``
