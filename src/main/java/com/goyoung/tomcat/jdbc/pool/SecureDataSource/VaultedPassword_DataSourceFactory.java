package com.goyoung.tomcat.jdbc.pool.SecureDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.XADataSource;

import com.goyoung.util.ERPM.RestAPI.client.Get_Auth_Token;
import com.goyoung.util.ERPM.RestAPI.client.SharedCredential;

public class VaultedPassword_DataSourceFactory extends DataSourceFactory {

	@Override
	public DataSource createDataSource(Properties properties, Context context, boolean XA)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SQLException,
			NoSuchAlgorithmException, NoSuchPaddingException, IOException {
		
		// Load RestAPI username & password from config file
		File configDir = new File(System.getProperty("catalina.base"), "conf");
		File configFile = new File(configDir, "config.properties");
		InputStream in = new FileInputStream(configFile);
		Properties prop = new Properties();


		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String shared_pw_list = prop.getProperty("shared_pw_list");
		String system_name = prop.getProperty("system_name");
		String co_comment = prop.getProperty("co_comment");

		// get the present JDBC connection pool propeties
		PoolConfiguration poolProperties = VaultedPassword_DataSourceFactory.parsePoolProperties(properties);

		// get the ERPM Vault Password
		String auth_token = Get_Auth_Token.go();

		// get the database password from the secret vault
		String PoolPassword = SharedCredential.CheckOut(auth_token, co_comment, poolProperties.getUsername(), shared_pw_list, system_name);

		// set the connection pool password
		poolProperties.setPassword(PoolPassword);

		// The rest of this code is copied from Tomcat's DataSourceFactory.
		if (poolProperties.getDataSourceJNDI() != null && poolProperties.getDataSource() == null) {
			performJNDILookup(context, poolProperties);
		}
		org.apache.tomcat.jdbc.pool.DataSource dataSource = XA ? new XADataSource(poolProperties)
				: new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
		dataSource.createPool();

		return dataSource;
	}

}