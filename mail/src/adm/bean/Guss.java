package adm.bean;

import java.io.Serializable;

public class Guss implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int gid;
			
	private int userid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
