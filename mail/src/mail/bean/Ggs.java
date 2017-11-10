package mail.bean;

import java.io.Serializable;

public class Ggs implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int gid;
			
	private int ogid;

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

	public int getOgid() {
		return ogid;
	}

	public void setOgid(int ogid) {
		this.ogid = ogid;
	}
}
