
# README #

This is the un-jenkins repository, which contains the source for un-jenkins. un-jenkins is a rest-based web server application written in Java that provides a simple api for retrieving dashboard like metrics from Jenkins.  

### Whats so great about un-jenkins? ###

* un-jenkins provides a rest response containing the recent build outcomes of all the jobs within a given view, enabling batch consumption of real-time Jenkins metrics.
* It takes many api calls to talk to Jenkins, which we all surely want to do less of to do a quick checkup on the health of our pipelines.
* Data is real time and only includes up to the last 5 (value is configurable) builds retained within Jenkins.
* It enables anyone to create a simple rest client that can visualize or consume the LIVE data, such as the [unjenkins-client](https://bitbucket.org/kdwaechter/unjenkins-client)
* Caches data according to a configurable timeout, alleviating concerns about overburdening Jenkins. 
* Fast! un-jenkins uses Jersey, Jackson, and embedded Jetty to handle all http/json. Calls to Jenkins are multi-threaded so you don't have to wait to get to that sweet sweet candy.

### How do I get set up? ###

* Build instructions: Run mvn package on the repository and a single jar file will be created in the target folder. This is an UBER jar! It contains all of the dependencies needed to run, including the web server! 
* Distribution is easy. Place this jar file wherever you would like to run un-jenkins and fire off the command "java -jar unjenkins-1.0-SNAPSHOT.jar". The exact file name of the jar will depend on the version that was built. The server will start, the default port is 8080, although this can be changed by passing in an argument for port number "java -jar unjenkins-1.0-SNAPSHOT.jar 8888"
* Verify the server is running by accessing the test dashboard http://localhost:8080/api/dashboard. It should return a set of metrics related to the Content Management API & UI Tests.
* Access any Jenkins view you like by appending the view/Viewname1/view/Viewname2 portion of a Jenkins url. 


![Build Status](https://travis-ci.org/tsu-denim/unjenkins.svg?branch=master)
