package mail.bean;

import java.io.Serializable;

public class Contact implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int id;
	
	private int userid;
	
	private String ids;
	
	private String names;
				
	private String name;
	
	private String addr;
	
	private String gnames;
		
	private int start;
	
	private int end;

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getGnames() {
		return gnames;
	}

	public void setGnames(String gnames) {
		this.gnames = gnames;
	}

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
}
