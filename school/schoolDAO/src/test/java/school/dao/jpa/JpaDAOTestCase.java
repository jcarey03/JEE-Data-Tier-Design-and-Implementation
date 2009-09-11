package school.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.dao.DAOTestCase;

public abstract class JpaDAOTestCase extends DAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JpaDAOTestCase.class);
	
	protected EntityManagerFactory emf;
	
	protected EntityManager em;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		emf = Persistence.createEntityManagerFactory("schoolPU");
		em = emf.createEntityManager();

	}
	
	@After
	public void tearDown() throws Exception {
		
		if(em != null) {
			em.close();
		}
		
		if(emf != null) {
			emf.close();
		}
		
		super.tearDown();
		
	}
	
}
