# Secure Connection Pool
remove passwords from JDBC connection pool properties files

must do:
1. need to reference the custom DataSourceFactory "VaultedPassword_DataSourceFactory" class 
   in the your tomcat server's context.xml

<!-- example JDBC resource-->
<Resource
	name="jdbc/postgres"
	type="javax.sql.DataSource" 
	factory="com.goyoung.tomcat.jdbc.pool.SecureDataSource.VaultedPassword_DataSourceFactory" 
	auth="Container" 
	driverClassName="org.postgresql.Driver" 
	maxIdle="10" 
	maxTotal="20" 
	maxWaitMillis="-1" 
	username="tomcat"
	url="jdbc:postgresql://localhost:5432/test_app_db" 
	/>

2. edit the provided config.properties file to contain the correct settings

3. copy the provided config.properties into your tomcat server's the $catalina_home/conf directory

4. add Google's Gson 2.8.0 library https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.0
into the $catalina_home/lib directory
