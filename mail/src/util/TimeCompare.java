package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCompare {

	public static int CompareTime(String time1,String time2,String type){
		
		int s = 0 ;
		int m = 0 ;
		int d = 0 ;

		Pattern DatePattern = Pattern.compile("\\s*\\d{4}\\s*");
		Matcher Year1matcher = DatePattern.matcher(time1);
		Matcher Year2matcher = DatePattern.matcher(time2);
		DatePattern = Pattern.compile("\\s*-\\s*\\d{2}\\s*-\\s*");
		Matcher Month1matcher = DatePattern.matcher(time1);
		Matcher Month2matcher = DatePattern.matcher(time2);
		DatePattern = Pattern.compile("\\s*-\\s*\\d{2}\\s*-\\s*\\d{2}\\s*");
		Matcher Day1matcher = DatePattern.matcher(time1);
		Matcher Day2matcher = DatePattern.matcher(time2);
		DatePattern = Pattern.compile("\\s*\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*");
		Matcher hour1matcher = DatePattern.matcher(time1);
		Matcher hour2matcher = DatePattern.matcher(time2);
		DatePattern = Pattern.compile("\\s*:\\s*\\d{2}\\s*:\\s*");
		Matcher minute1matcher = DatePattern.matcher(time1);
		Matcher minute2matcher = DatePattern.matcher(time2);
		DatePattern = Pattern.compile("\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}\\s*");
		Matcher second1matcher = DatePattern.matcher(time1);
		Matcher second2matcher = DatePattern.matcher(time2);
		
		if(Year1matcher.find()&&Year2matcher.find()&&Month1matcher.find()&&Month2matcher.find()&&Day1matcher.find()&&Day2matcher.find()){
			int year1 = Integer.parseInt(Year1matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int year2 = Integer.parseInt(Year2matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int month1 = Integer.parseInt(Month1matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int month2 = Integer.parseInt(Month2matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			m = month1 ;
			int day1 = Integer.parseInt(Day1matcher.group().replaceAll("\\s*-\\s*\\d{2}\\s*-\\s*|\\s*",""));
			int day2 = Integer.parseInt(Day2matcher.group().replaceAll("\\s*-\\s*\\d{2}\\s*-\\s*|\\s*",""));

			if((year2-year1==0&&month2-month1>0)||(year2-year1==0&&month2-month1==0&&day2-day1>=0)){
									
				for(int a=month1;a<month2;a++){
					
					if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
						d = d+31 ;
					}else if(a==4||a==6||a==9||a==11){
						d = d+30 ;
					}else if(year1%4==0&&year1%100!=0){		
						d = d+29 ;
					}else {		
						d= d+28 ;
					}
				}
				d = d+day2-day1; 
			}else {
			
				while(year2-year1>0){
						
					for(int a=m;a<=12;a++){
						
						if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
							d = d+31 ;
						}else if(a==4||a==6||a==9||a==11){							
							d = d+30 ;
						}else if(year1%4==0&&year1%100!=0){		
							d = d+29 ;
						}else {		
							d= d+28 ;
						}
					}
					year1++;
					m = 1 ;
				}
				if(year2-year1==0){
											
					for(int a=1;a<month2;a++){
						
						if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
							d = d+31 ;
						}else if(a==4||a==6||a==9||a==11){
							d = d+30 ;
						}else if(year1%4==0&&year1%100!=0){
							d = d+29 ;
						}else {
							d= d+28 ;
						}
					}
					d = d+day2-day1;
				}
			}
			if(hour1matcher.find()&&hour2matcher.find()&&minute1matcher.find()&&minute2matcher.find()&&second1matcher.find()&&second2matcher.find()){
				String tag = "";
				int hour1 = 0 ;
				int hour2 = 0 ;
				int minute1 = 0 ;
				int minute2 = 0 ;
				int second1 = 0 ;
				int second2 = 0 ;
				tag = hour1matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					hour1 = 0 ;
				}else {
					hour1 = Integer.parseInt(hour1matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*",""));
				}
				tag = hour2matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					hour2 = 0 ;
				}else {
					hour2 = Integer.parseInt(hour2matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*",""));
				}
				tag = minute1matcher.group().replaceAll("\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					minute1 = 0 ;
				}else {
					minute1 = Integer.parseInt(minute1matcher.group().replaceAll("\\s*:\\s*|\\s*",""));
				}
				tag = minute2matcher.group().replaceAll("\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					minute2 = 0 ;
				}else {
					minute2 = Integer.parseInt(minute2matcher.group().replaceAll("\\s*:\\s*|\\s*",""));
				}
				tag = second1matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					second1 = 0 ;
				}else {
					second1 = Integer.parseInt(second1matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*",""));
				}
				tag = second2matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*","");
				if(tag.trim().equals("00")){
					second2 = 0 ;
				}else {
					second2 = Integer.parseInt(second2matcher.group().replaceAll("\\s*:\\s*\\d{2}\\s*:\\s*|\\s*",""));
				}
				s = (hour2-hour1)*3600+(minute2-minute1)*60+(second2-second1);
			}
			s = s + d*24*3600 ;
			//System.out.println("两个日期的相隔秒数是："+s);
		}
		return s ;
	}

	public static int CompareDate(String date1,String date2){
		
		int m = 0 ;
		int d = 0 ;

		Pattern DatePattern = Pattern.compile("\\s*\\d{4}\\s*");
		Matcher Year1matcher = DatePattern.matcher(date1);
		Matcher Year2matcher = DatePattern.matcher(date2);
		DatePattern = Pattern.compile("\\s*-\\s*\\d{2}\\s*-\\s*");
		Matcher Month1matcher = DatePattern.matcher(date1);
		Matcher Month2matcher = DatePattern.matcher(date2);
		DatePattern = Pattern.compile("\\s*-\\s*\\d{2}\\s*-\\s*\\d{2}\\s*");
		Matcher Day1matcher = DatePattern.matcher(date1);
		Matcher Day2matcher = DatePattern.matcher(date2);
		if(Year1matcher.find()&&Year2matcher.find()&&Month1matcher.find()&&Month2matcher.find()&&Day1matcher.find()&&Day2matcher.find()){
			int year1 = Integer.parseInt(Year1matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int year2 = Integer.parseInt(Year2matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int month1 = Integer.parseInt(Month1matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			int month2 = Integer.parseInt(Month2matcher.group().replaceAll("\\s*-\\s*|\\s*",""));
			m = month1 ;
			int day1 = Integer.parseInt(Day1matcher.group().replaceAll("\\s*-\\s*\\d{2}\\s*-\\s*|\\s*",""));
			int day2 = Integer.parseInt(Day2matcher.group().replaceAll("\\s*-\\s*\\d{2}\\s*-\\s*|\\s*",""));

			if((year2-year1==0&&month2-month1>0)||(year2-year1==0&&month2-month1==0&&day2-day1>=0)){
									
				for(int a=month1;a<month2;a++){
					
					if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
						d = d+31 ;
					}else if(a==4||a==6||a==9||a==11){
						d = d+30 ;
					}else if(year1%4==0&&year1%100!=0){
						d = d+29 ;
					}else {		
						d= d+28 ;
					}
				}
				d = d+day2-day1; 
			}else {
			
				while(year2-year1>0){
						
					for(int a=m;a<=12;a++){
						
						if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
							d = d+31 ;
						}else if(a==4||a==6||a==9||a==11){							
							d = d+30 ;
						}else if(year1%4==0&&year1%100!=0){		
							d = d+29 ;
						}else {		
							d= d+28 ;
						}
					}
					year1++;
					m = 1 ;
				}
				if(year2-year1==0){
											
					for(int a=1;a<month2;a++){
						
						if(a==1||a==3||a==5||a==7||a==8||a==10||a==12){
							d = d+31 ;
						}else if(a==4||a==6||a==9||a==11){
							d = d+30 ;
						}else if(year1%4==0&&year1%100!=0){
							d = d+29 ;
						}else {
							d= d+28 ;
						}
					}
					d = d+day2-day1;
				}
			}
			System.out.println("两个日期的相隔天数是："+d);
		}
		return d ;
	}
}
