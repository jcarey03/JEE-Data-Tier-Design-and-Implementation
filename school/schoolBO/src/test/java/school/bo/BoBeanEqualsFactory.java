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
		
//		if(c1.getId() != c2.getId()) {
//			return false;
//		}
//		
//		if(c1.getFirstName() == null && c2.getFirstName() != null) {
//			return false;
//		} else if(!c1.getFirstName().equals(c2.getLastName())) {
//			return false;
//		}
//		
//		if(c1.getLastName() == null && c2.getLastName() != null) {
//			return false;
//		} else if(!c1.getLastName().equals(c2.getLastName())) {
//			return false;
//		}
//		
//		if(c1.getMiddleInitial() == null && c2.getMiddleInitial() != null) {
//			return false;
//		} else if(!c1.getMiddleInitial().equals(c2.getLastName())) {
//			return false;
//		}
//		
//		return true;
		
	}
	
}
