package mail.bean;

import java.io.Serializable;

public class Units implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int pid;
			
	private String name;
	
	private int start;
	
	private int end;
		
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
