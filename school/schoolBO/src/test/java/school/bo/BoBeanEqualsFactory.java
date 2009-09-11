package school.bo;

import org.apache.commons.lang.builder.EqualsBuilder;

public final class BoBeanEqualsFactory {

	public static boolean equals(Contact c1, Contact c2) {
		
		if(c1 == null || c2 == null) {
			return false;
		}
		
		return new EqualsBuilder()
			.append(c1.getId(), c2.getId())
			.append(c1.getFirstName(), c2.getFirstName())
			.append(c1.getLastName(), c2.getLastName())
			.append(c2.getMiddleInitial(), c2.getMiddleInitial()).isEquals();
		
	}
	
}
