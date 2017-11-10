package util;

public class DataSource extends org.logicalcobwebs.proxool.ProxoolDataSource {
	
	@Override
	public void setDriverUrl(String url) {
		super.setDriverUrl(url);
	}
	
	@Override
	public void setUser(String user) {
		super.setUser(user);
	}
	
	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}
}
