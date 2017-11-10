package mail.bean;

import java.io.Serializable;

public class Gns implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int gid;
			
	private int unid;

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

	public int getUnid() {
		return unid;
	}

	public void setUnid(int unid) {
		this.unid = unid;
	}
}
