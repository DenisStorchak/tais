TAIS
====

TEES Automatic Interaction System

Installation
============
####Build needs:
1. <b>jdk</b> >= 1.8<br />
2. <b>maven</b> >= 3<br />
3. internet connection (other dependencies will fetch from external repositories)

Build and run
===
To run you need apache tomcat >= 7.0.52. Installation described <a href="http://tomcat.apache.org/tomcat-7.0-doc/appdev/installation.html">here</a>. Next you need to deploy tais *.war package to tomcat. Deployment described <a href="https://tomcat.apache.org/tomcat-7.0-doc/appdev/deployment.html">here</a>. Also you need postgresql >= 9.3 and redis-server >= 2.6.13 (optional).<br />
1. Go to tais root directory (that contains <b>pom.xml</b> and <b>tais.properties</b>)<br />
2. type <b>mvn clean install</b><br />
2. Place tais configuration file (<b>tais.properties</b>) into tomcat <b>conf</b> directory<br />
3. Edit and save <b>tais.properties</b> file<br />
4. Open tomcat <b>conf/context.xml</b> and insert line <i><Environment name="tais.config" value="file:///${catalina.home}/conf/tais.properties" type="java.net.URI"/></i> between <Context> and </Context> tags. Note: <i>file:///${catalina.home}/conf/tais.properties</i> — URL to tais configuration file. Save <b>context.xml</b><br />
5. Configure user access to tomcat manager (described <a href=http://tomcat.apache.org/tomcat-7.0-doc/manager-howto.html>here</a>)<br />
6. Start tomcat<br />
7. Open tomcat manager (<a href=http://localhost:8080/manager>http://localhost:8080/manager</a> default url) and deploy <b>tais.war</b> with context path <b>/</b><br />
8. Open <a href=localhost:8080>localhost:8080</a> — new tais url<br />

Configuration file
==================
<pre>
hibernate.hbm2ddl.auto=create-drop #do not use 'create-drop' value in production. It deletes all the database!!! Recommended value is 'validate'
hibernate.show_sql=true
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

jdbc.driver=org.postgresql.Driver
jdbc.url=jdbc:postgresql:tais
jdbc.username=postgres
jdbc.password=postgres

mail.host=smtp.yandex.ru
mail.port=465
mail.username=username
mail.password=pass
mail.smtp.auth=true
mail.smtp.ssl.enable=true

cache.enable=true
redis.enable=true
redis.host=localhost
redis.port=6379
redis.pool=true
redis.password=

user.login=admin
user.password=admin
user.role=Administrator #possible values: Administrator / Teacher / Student
</pre>

####Notes:
<b>hibernate</b> — Hibernate ORM properties. Do not change them if you don't know anything about ORM and Hibernate.<br />
<b>hibernate.hbm2ddl.auto=create-drop</b> means that database will be deleted after redeploy and should use only for development.
Change this value to <b>validate</b>.<br>
<b>jdbc</b> — database connection settings. By default configured for local postgresql with database name <b>tais</b> and credentials <i>postgres</i><br />
<b>mail</b> — mail server settings
<b>cache.enable</b> you can use cache to optimize database requests. <b>true</b> value is strong recommended if application do not use all of your's server RAM.<br />
<b>redis</b> — you can use <a href=http://redis.io/>redis</a> as cache server. You have to install redis-server and configure redis.* properties if <i>redis.enable</i> and <i>cache.enable</i> values set to <b>true</b>. However I <b>recommend to use redis for cache.</b><br />
<b>user</b> — default user for application. Stored only in RAM and don't shown next to other registrants. Used only for first authorization. Administrator has to clear this properties' values and restart tomcat after his own profile registration will be completed.