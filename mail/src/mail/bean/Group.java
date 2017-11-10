package mail.bean;

import java.io.Serializable;

public class Group implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
				
	private String name;
	
	private int state;
	
	private int start;
	
	private int end=10000;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
