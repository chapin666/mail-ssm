package adm.bean;

import java.io.Serializable;

public class Groups implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
			
	private String name;
	
	private int state;
	
	private String member;
	
	private String unid; 
	
	private String gid;
	
	private int start;
	
	private int end;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getState() {
		return state;
	}

	public String getMember() {
		return member;
	}

	public String getUnid() {
		return unid;
	}

	public String getGid() {
		return gid;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
