package school.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="STUDENT")
@SuppressWarnings("serial")
public class Student extends Contact implements Serializable {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(Student.class);
	
	private String major;
	
	// needed by Hibernate because it uses Javassist, which requires non-private
	// default constructor
	Student() {}
	
	public Student(long id) {
		super(id);
	}

	public Student(String firstName, String lastName, String major) {
		super(firstName, lastName);
		setMajor(major);
	}
	
	@Column(name="MAJOR", nullable=true, length=50)
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
