package mail.bean;

import java.io.Serializable;

public class UGroup implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int userid;
			
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
