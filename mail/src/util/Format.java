package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Format {

	public static String DateFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		return time;
	}

	public static String timeFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(date);
		return time;
	}
	
	public static String DateFormat1(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String time = sdf.format(date);
		return time;
	}
	
	public static String TimeFormat(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		return time;
	}
	
	public static String IdFormat(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format(date);
		return time;
	}
	
	public static Date StringToDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(date);
		return date1;
	}
	
	public static Date StringToTime(String time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time1 = sdf.parse(time);
		return time1;
	}
	
	public static String getRandomUploadFileName()
	{
		StringBuffer result = new StringBuffer();
		Random random = new Random();
		int randomNum = Math.abs(random.nextInt() % 100);
		result.append(System.currentTimeMillis());
		result.append(randomNum);
		return result.toString();
	}
	
	public static boolean isNumber(String str) throws ParseException{
		boolean result = str.matches("^[1-9]\\d*|0$");
		return result;
	}

	public static boolean isNum(String str) throws ParseException{
		boolean result = str.matches("^[1-9]\\d*$");
		return result;
	}

	public static boolean isEmail(String str) throws ParseException{
		boolean result = str.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		return result;
	}

	public static boolean isMobile(String str) throws ParseException{
		boolean result = str.matches("^[1]+[0-9]+[0-9]{9}$");
		return result;
	}

	public static boolean isQQ(String str) throws ParseException{
		boolean result = str.matches("^[1-9]+[0-9]{4,}");
		return result;
	}

	public static boolean isTell(String str) throws ParseException{
		boolean result = str.matches("^\\d{3}-?\\d{8}|\\d{4}-?\\d{8}$");
		return result;
	}

	public static int isLen(String str) throws ParseException{
        int len = 0; 
    	for(int i=0; i<str.length(); i++){
            if(str.charAt(i) > '~'){
            	len+=2;
            }else{
            	len+=1;
            }
        }
		return len;
	}
	
}
