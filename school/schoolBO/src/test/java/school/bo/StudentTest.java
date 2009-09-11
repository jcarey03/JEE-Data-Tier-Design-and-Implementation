package school.bo;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	private Student student;
	
	@Before
	public void initialize() {
		student = new Student(1);
		student.setFirstName("firstName");
		student.setLastName("lastName");
		student.setMiddleInitial("m");
		student.setMajor("major");
	}

	@Test
	public void testBeanProperties() {
	
		Student newStudent = new Student(1);
		newStudent.setFirstName("firstName");
		newStudent.setLastName("lastName");
		newStudent.setMiddleInitial("m");
		newStudent.setMajor("major");
		
		Assert.assertTrue(EqualsBuilder.reflectionEquals(student, newStudent));
	}
	
}
