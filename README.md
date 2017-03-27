# Secure JDBC Connection Pool
Secure JDBC connection pool removes stored, plaintext passwords from JDBC connection pool properties files such as context.xml
for tomcat & related servers.

**Today:**
This library supports Lieberman Software's ERPM platform.

**Future:**
Going forward I would like to support some additional secret managers like HashiCorp's Vault, or Square's KeyWhiz.
HashCorp Vault: https://www.vaultproject.io/intro/index.html
Sqaure KeyWhiz: https://square.github.io/keywhiz/

must do:
1. need to reference the custom DataSourceFactory "VaultedPassword_DataSourceFactory" class 
   in the your tomcat server's context.xml
```xml
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
```
2. edit the provided config.properties file to contain the correct settings

3. copy the provided config.properties into your tomcat server's the $catalina_home/conf directory

4. add Google's Gson 2.8.0 library https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.0
into the $catalina_home/lib directory
