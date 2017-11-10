package util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Para {
	
	/**
	 * 
	 * @param name 要获取的值的参数
	 * @param file 从哪个properties文件获取，即文件名
	 * @param param 如果路径不够可以继续加
	 * @param tag 判断是否创建你指定的路径
	 * @return
	 * @throws Exception
	 */
	public String getPara(String name,String file,String param,boolean tag) throws Exception {
		
		Properties prop = new Properties();
		InputStream fis= getClass().getClassLoader().getResourceAsStream(file);
		prop.load(fis);
	    fis.close();
	    name = prop.getProperty(name)+param;
	    if(tag){
	    	File files = new File(name);
	    	if(!files.exists()){
	    		files.mkdirs();
	    	}
	    }
	    return name ;
	}
}
