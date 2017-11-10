package adm.bean;

import java.io.Serializable;

public class Member implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
			
	private String name;
	
	private String username;
	
	private String pass;
		
	private String phone;
	
	private String type;
	
	private String units;
	
	private int start;
	
	private int end;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public String getPhone() {
		return phone;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
