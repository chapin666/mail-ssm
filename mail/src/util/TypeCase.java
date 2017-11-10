package util;

public class TypeCase {

	public static String ArrayCase(Object[] o){
		
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<o.length; i++){
			
			sb. append(o[i]+"#_#");
			
		}
		sb.delete(sb.length()-3,sb.length());
		
		return sb.toString();
	}
}
