# TicTacToe
A simple java console application to implement the game tic-tac-toe


Before you start running this application make sure you have Java development kit (JDK) and maven installed on your machine : 
[Maven installation](https://maven.apache.org/install.html)

You can make sure that everything is fine by running : 

```
java -version
```
```
mvn -v
```

and checking your java and maven version.
Also make sure that your environment vairable `JAVA_HOME` is pointing to the path of a java development kit (JDK) not a java running environment (JRE).


## Building the application : 
after cloning the repository go to the main folder which contains `pom.xml` file and run 
```
mvn clean install
```

## Running the JAR :
* After having a successful build you should have a `target` folder containg the JAR file `tic-tac-toe-0.0.1.jar`
make sure the JAR file is executable 
if it is not executable you can use the command 
```
chmod +x tic-tac-toe-0.0.1.jar
``` 

* Download a sample `config.properties` file from [here](https://github.com/mister0/TicTacToe/blob/master/src/main/java/resources/config.properties) and put it in the same folder of the JAR file `target` 


* Run the JAR file using the command : 
```
java -jar tic-tac-toe-0.0.1.jar ./config.properties
```

and play !!!!


**Note** : If you don't pass a configuration file path as an argument or an invalid configuration file you will get an error according to what went wrong and the application will not run.


## Running the tests : 
to run the tests use 
```
mvn test
``` 
