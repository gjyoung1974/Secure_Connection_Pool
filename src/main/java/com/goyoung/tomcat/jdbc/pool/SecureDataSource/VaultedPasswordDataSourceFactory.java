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

import com.goyoung.util.ERPM.RestAPI.client.GetAuthToken;
import com.goyoung.util.ERPM.RestAPI.client.SharedCredential;

import java.util.logging.*;


public class VaultedPasswordDataSourceFactory extends DataSourceFactory {

    private static Logger logger = Logger.getLogger(VaultedPasswordDataSourceFactory.class.getName());

    @Override
    public DataSource createDataSource(Properties properties, Context context, boolean xa)
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
            logger.log(Level.INFO, "prop.load() threw an exception.", e);
        }

        String sharedPwList = prop.getProperty("shared_pw_list");
        String systemName = prop.getProperty("system_name");
        String coComment = prop.getProperty("co_comment");

        // get the present JDBC connection pool propeties
        PoolConfiguration poolProperties = VaultedPasswordDataSourceFactory.parsePoolProperties(properties);

        // get the ERPM Vault Password
        String authToken = GetAuthToken.go();

        // get the database password from the secret vault
        String poolPassword = SharedCredential.checkOut(authToken, coComment, poolProperties.getUsername(), sharedPwList, systemName);

        // set the connection pool password
        poolProperties.setPassword(poolPassword);

        // The rest of this code is copied from Tomcat's DataSourceFactory.
        if (poolProperties.getDataSourceJNDI() != null && poolProperties.getDataSource() == null) {
            performJNDILookup(context, poolProperties);
        }
        org.apache.tomcat.jdbc.pool.DataSource dataSource = xa ? new XADataSource(poolProperties)
                : new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        dataSource.createPool();

        return dataSource;
    }

}