package adm.bean;

import java.io.Serializable;

public class Unit implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int pid;
			
	private String name;

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
