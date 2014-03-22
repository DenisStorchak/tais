tais
====

TEES Automatic Interaction System

build
=====
####Build needs:
1. <b>jdk</b> >= 1.8<br />
2. <b>maven</b> >= 3<br />
3. internet connection (other dependencies will fetch from external repositories)

####Steps:
1. <b>mvn</b> clean<br />
if you want to run test suites type<br />
2. <b>mvn</b> test<br />
last phase<br />
3. <b>mvn</b> package -Dmaven.test.skip=true<br />

run
===
To run you need apache tomcat >= 7.0.52. Installation described <a href="http://tomcat.apache.org/tomcat-7.0-doc/appdev/installation.html">here</a>. Next you need to deploy tais *.war package to tomcat. Deployment described <a href="https://tomcat.apache.org/tomcat-7.0-doc/appdev/deployment.html">here</a>. Also you need postgresql >= 9.3.
