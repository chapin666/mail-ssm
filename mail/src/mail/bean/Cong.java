package mail.bean;

import java.io.Serializable;

public class Cong implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int ugid;
			
	private int cid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUgid() {
		return ugid;
	}

	public void setUgid(int ugid) {
		this.ugid = ugid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
}
