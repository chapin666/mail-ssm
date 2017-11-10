package mail.bean;

import java.io.Serializable;

public class Tag implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int userid;
			
	private String name;

	private int start;//开始页
	private int end;//结束页

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

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
