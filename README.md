# Sgrite-project-app
Project area: Data mining, big data and data analysis (AI) The goal of this project is the efficient extraction of gradual pattern.
#Organization of the folder 
- This project is implemented in Java language. The IDE used is Netbeans.
- the most important directories are src, datasets, runnable, and nbproject. 
- ``src`` contains the source code
- ``datasets`` contains all the datasets (Synthetic,meteorogical, economic and heath)
- ``runnable`` contains the executable code of SGOPt, SG1, SGOpt, SGB1, SGB2 and Grite.
- ``nbproject`` contains the configuration files which indicate the main classes of the different methods. This configuration files are:
nbproject / configs / VersionRBIGrite.properties,
nbproject / configs / VersionRBISG1.properties,
nbproject / configs / VersionRBISGB1.properties,
nbproject / configs / VersionRBISGB2.properties, and
nbproject / configs / VersionRBISGOpt.properties.
- 
# Description of Runtime
## Environment
The java support environment is the version 8 (JDK 8) 
BUILD OUTPUT DESCRIPTION
========================
When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and
type the following:

java -jar "sgrite-project.jar" 

To distribute this project, zip up the dist folder (including the lib folder)
and distribute the ZIP file.
Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
### Execution of algorithms
- The execution can be started using the following syntaxes.
* ``java -jar executable filename threshold``
* ``java -jar executable filename min max step1``
The meaning of the arguments of these commands is described as follow:
* executable: the java exucatable file
* filename: the file name of the dataset
* threshold: the minimum gradual support threshold
* min: the lower bound of the minimum gradual support threshold
* max: the upper bound of the minimum gradual support threshold
* step1: the step of varying the minimum gradual support threshold between the lower and upper bounds.
In the first syntax the output is made up of (1) the list of frequent gradual patterns, (2) the number of frequent gradual patterns, (3) the execution time, and (4) the memory usage. In the second syntax the minimum support threshold varies from min to max, with the step of step1 and operations (1), (2), (3) and (4) are performed for each minimum support threshold value.

- For example, the following commands execute SGOPt, SG1, SGOpt, SGB1, SGB2 and Grite on dataset  `test.dat` with 0.05 as the minimum gradual support threshold:
``java -jar SGOpt.jar test.dat 0.05``
``java -jar SG1.jar test.dat 0.05``
``java -jar SGB1.jar test.dat 0.05``
``java -jar SGB2.jar test.dat 0.05``
