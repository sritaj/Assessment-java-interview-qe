FROM openjdk:8-jre-slim

#WORKSPACE
WORKDIR /usr/share/tag

#ADD .jars under Target from Host
#into this image
ADD target/qe-interview.jar               qe-interview.jar
ADD target/qe-interview-tests.jar         qe-interview-tests.jar
ADD target/libs                           libs
ADD src/test/resources/testng.xml         testng.xml

ENTRYPOINT java -cp qe-interview.jar:qe-interview-tests.jar:libs/* org.testng.TestNG testng.xml