package util;

import java.io.InputStream;
import java.util.Properties;
import util.encry.Blowfish;

public class Prop {
	
	private String url ;
	private String user ;
	private String password ;
	private String key ;

	public Prop() throws Exception {
		
		Properties prop = new Properties();
		InputStream fis= getClass().getClassLoader().getResourceAsStream("proxool.properties");
		prop.load(fis);
	    fis.close();
	    Blowfish bf = new Blowfish();
	    String key = bf.decryptString(prop.getProperty("key"));
	    this.key = key ;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
