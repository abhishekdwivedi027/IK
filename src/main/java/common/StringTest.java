package common;

public class StringTest {
	
	public static void main(String[] args) {
		StringTest stringTest = new StringTest();
		int location = stringTest.findLocation("mississippi", "issip");
		System.out.println(location);
	}
	
	public int findLocation(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        if (haystack.length() == 0) {
            return -1;
        }
        if (needle.length() > haystack.length()) {
        	return -1;
        }

        
        // return haystack.indexOf(needle);
        
        int fromIndex = -1;
        for (int pinLength=1; pinLength<=needle.length(); pinLength++) {
            String pin = needle.substring(0, pinLength);
            fromIndex = haystack.indexOf(pin, fromIndex != -1 ? fromIndex : 0);
            // no match - pin not present in haystack => needle not present in haystack
            if (fromIndex == -1) {
                return -1;
            }
            while (fromIndex < haystack.length()) {
                boolean found = isFound(haystack, pin, fromIndex);
                if (found) {
                    break;
                } else {
                    fromIndex = haystack.indexOf(pin, fromIndex);
                }    
            }
        }  
        
        return fromIndex;
    }
    
    private boolean isFound(String haystack, String pin, int index) {
        for (int i=0; i<pin.length(); i++) {
            if (haystack.charAt(index+i) != pin.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}
