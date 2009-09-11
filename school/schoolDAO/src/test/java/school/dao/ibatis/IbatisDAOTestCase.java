package school.dao.ibatis;

import java.io.Reader;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.dao.DAOTestCase;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class IbatisDAOTestCase extends DAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(IbatisDAOTestCase.class);
	
	protected SqlMapClient sqlMapClient;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		String resource = "META-INF/sqlMap-config.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
		
	}
	
}
