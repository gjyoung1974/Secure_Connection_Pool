# Secure Connection Pool
remove passwords from JDBC connection pool properties files

must do:
1. need to reference the custom DataSourceFactory class in the context.xml

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

2. copy the config.properties into the $catalina_home/conf directory

3. add Google's Gson 2.8.0 https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.0
into the $catalina_home/lib directory