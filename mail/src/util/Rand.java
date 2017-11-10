package util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;  

public class Rand {  
  
    public static int rondom4(){
    	
    	Random rand = new Random();
    	int result = rand.nextInt(10000);
    	if(result<1000){
    		result = result+1000;
    	}else if(result>10000){
    		result = result-10000+1000;
    	}
    	return result ;
    }
    
    public static int rondom6(){
    	
    	Random rand = new Random();
    	int result = rand.nextInt(1000000);
    	return result ;
    }
    
    public static String getMacAddr () {
        try  
        {  
            Enumeration <NetworkInterface> em = NetworkInterface.getNetworkInterfaces ();  
            while (em.hasMoreElements ())  
            {  
                NetworkInterface nic = em.nextElement ();  
                byte[] b = nic.getHardwareAddress ();  
                if (b == null || b.length<1)  
                continue;  
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < b.length; i++)  
                {  
                	builder.append(byteHEX(b[i]));
                	builder.append("-");
                }  
                builder.deleteCharAt(builder.length() - 1);
                return builder.toString();
            }  
        }  
        catch (SocketException e)  
        {  
            e.printStackTrace ();  
            System.exit (-1);  
        }  
        return null;  
    }
    
    /* 一个将字节转化为十六进制ASSIC码的函数 */  
    public static String byteHEX (byte ib)  
    {
        char[] Digit =  
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
        char[] ob = new char[2];  
        ob[0] = Digit[(ib >>> 4) & 0X0F];  
        ob[1] = Digit[ib & 0X0F];  
        String s = new String (ob);  
        return s;  
    }
    
    public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}  
