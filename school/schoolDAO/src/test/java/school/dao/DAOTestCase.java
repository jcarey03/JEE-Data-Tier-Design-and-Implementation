package school.dao;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAOTestCase extends DBTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(DAOTestCase.class);
	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty( 
			PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, 
			System.getProperty("jdbc.driver")
		);
        System.setProperty( 
        	PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
        	System.getProperty("jdbc.url")
        );
        System.setProperty( 
        	PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, 
        	System.getProperty("jdbc.username")
        );
        System.setProperty( 
        	PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
        	System.getProperty("jdbc.password")
        );
        System.setProperty( 
        	PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA,
            System.getProperty("jdbc.schema")
        );
		
		super.setUp();
		
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Override
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        
		config.setProperty(
        	DatabaseConfig.PROPERTY_DATATYPE_FACTORY, 
        	new org.dbunit.ext.oracle.OracleDataTypeFactory()
        );
        config.setFeature(
        	DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, 
            true
        );
        
    }
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		
		ReplacementDataSet dataSet = new ReplacementDataSet(
			new FlatXmlDataSet(
				new FileInputStream(System.getProperty("data.file")))); 
		dataSet.addReplacementObject("[NULL]", null);
		return dataSet;
		
    }
	
}
