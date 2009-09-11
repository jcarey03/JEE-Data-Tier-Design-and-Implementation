package school.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.dao.DAOTestCase;

public abstract class HibernateDAOTestCase extends DAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(HibernateStudentDAOTest.class);
	
	protected SessionFactory sessionFactory;
	
	protected Session session;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		/*
		* Build a SessionFactory object from session-factory configuration
		* defined in the hibernate.cfg.xml file. In this file we register
		* the JDBC connection information, connection pool, the hibernate
		* dialect that we used and the mapping to our hbm.xml file for each
		* POJO (Plain Old Java Object).
		*
		*/
		sessionFactory = new AnnotationConfiguration()
			.configure("/META-INF/hibernate.cfg.xml")
			.buildSessionFactory();
		session = sessionFactory.getCurrentSession();

	}
	
	@After
	public void tearDown() throws Exception {
		
		if(session != null && session.isOpen()) {
			session.close();
		}
		
		if(sessionFactory != null) {
			sessionFactory.close();
		}
		
		super.tearDown();
		
	}
	
}
