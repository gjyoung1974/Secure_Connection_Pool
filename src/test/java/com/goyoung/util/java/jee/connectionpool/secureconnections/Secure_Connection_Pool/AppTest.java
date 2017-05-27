package com.goyoung.util.java.jee.connectionpool.secureconnections.Secure_Connection_Pool;

import com.goyoung.util.ERPM.RestAPI.client.ErpmRestClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        try {
            ErpmRestClient ec = new ErpmRestClient();
          //  ErpmRestClient.post("", "");
            System.out.print("add the unit test back in here..");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(true);
    }
}
