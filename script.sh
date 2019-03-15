#!/bin/bash

javac -d classes -classpath .:classes src/Accounting_itf.java
cd classes
jar cvf ../lib/Accounting_itf.jar Accounting_itf.class
cd ..
javac -d classes -classpath .:classes src/Accounting_impl.java
cd classes
jar cvf ../lib/Accounting_impl.jar Accounting_impl.class
cd ..
javac -d classes -classpath .:classes src/Registry_itf.java
cd classes
jar cvf ../lib/Registry_itf.jar Registry_itf.class
cd ..
javac -d classes -classpath .:classes src/Registry_impl.java
cd classes
jar cvf ../lib/Registry_impl.jar Registry_impl.class
cd ..
javac -d classes -classpath .:classes src/Communication_itf.java
cd classes
jar cvf ../lib/Communication_itf.jar Communication_itf.class
cd ..
javac -d classes -classpath .:classes src/Communication_impl.java
cd classes
jar cvf ../lib/Communication_impl.jar Communication_impl.class
cd ..
javac -d classes -cp .:classes:lib/Hello.jar:lib/HelloImpl.jar src/HelloServer.java
javac -d classes -cp .:classes:lib/Hello.jar src/HelloClient.java
killall rmiregistry
export CLASSPATH="lib/Accounting_itf.jar:lib/Registry_itf.jar:lib/Communication_itf.jar"
echo $CLASSPATH
