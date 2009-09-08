package school.bo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="CONTACT")
@Inheritance(strategy=InheritanceType.JOINED)
@SuppressWarnings("serial")
public class Contact implements Serializable {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(Contact.class);
	
	private long id;
	
	private String firstName;
	
	private String middleInitial;
	
	private String lastName;
	
	// needed by Hibernate because it uses Javassist, which requires non-private
	// default constructor
	Contact() {}
	
	public Contact(long id) {
		setId(id);
	}

	public Contact(String firstName, String lastName) {
		this(firstName, lastName, null);
	}

	public Contact(String firstName, String lastName, String middleInitial) {
		setFirstName(firstName);
		setLastName(lastName);
		setMiddleInitial(middleInitial);
	}
	
	@Id
	@GeneratedValue(generator="CONTACT_SEQ")
	@SequenceGenerator(name="CONTACT_SEQ", sequenceName="CONTACT_SEQ")
	@Column(name="ID", nullable=false)
	public long getId() {
		return id;
	}
	
	private void setId(long id) {
		this.id = id;
	}
	
	@Column(name="FIRST_NAME", nullable=false, length=50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="MIDDLE_INITIAL", nullable=true, length=1)
	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	@Column(name="LAST_NAME", nullable=false, length=50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
