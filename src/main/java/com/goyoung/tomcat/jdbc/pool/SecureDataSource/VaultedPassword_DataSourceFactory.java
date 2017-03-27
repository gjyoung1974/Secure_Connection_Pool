package com.goyoung.tomcat.jdbc.pool.SecureDataSource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

		// Here we decrypt our password.
		PoolConfiguration poolProperties = VaultedPassword_DataSourceFactory.parsePoolProperties(properties);

		// get the ERPM Vault Password
		String auth_token = Get_Auth_Token.go();

		// get the connection pool password
		// TODO move the list and servet name to the SharedCredential method
		String PoolPassword = SharedCredential.CheckOut(auth_token, "testing", poolProperties.getUsername(),"tomcat_passwords", "tomcat02");

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