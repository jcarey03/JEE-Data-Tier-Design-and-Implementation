package school.bo;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {

	private Contact contact;
	
	@Before
	public void initialize() {
		contact = new Contact(1);
		contact.setFirstName("firstName");
		contact.setLastName("lastName");
		contact.setMiddleInitial("m");
	}

	@Test
	public void testBeanProperties() {
	
		Contact newContact = contact = new Contact(1);
		contact.setFirstName("firstName");
		contact.setLastName("lastName");
		contact.setMiddleInitial("m");
		
		Assert.assertTrue(EqualsBuilder.reflectionEquals(contact, newContact));
	}
	
}
