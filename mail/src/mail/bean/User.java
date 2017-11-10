package mail.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private int end;
	private int id;
	private String name;
	private String pass;
	private String phone; 
	private int sex;
	private int start;
	private int state;
	private int unid;
	private String username;	
	private int domain;
	private String domainname;
	private String orby;//排序
	private String sortorder;//排序方式	
	private String stat;
	private String secondpass;//迁移其它邮件系统原用户的密码	
	
	
	public String getDomainname() {
		return domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}
	
	public String getSecondpass() {
		return secondpass;
	}

	public void setSecondpass(String secondpass) {
		this.secondpass = secondpass;
	}

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public String getOrby() {
		return orby;
	}

	public void setOrby(String orby) {		
		this.orby = orby;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public int getEnd() {
		return end;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPass() {
		return pass;
	}

	public String getPhone() {
		return phone;
	}

	public int getSex() {
		return sex;
	}

	public int getStart() {
		return start;
	}

	public int getState() {
		return state;
	}

	public int getUnid() {
		return unid;
	}

	public String getUsername() {
		return username;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setUnid(int unid) {
		this.unid = unid;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
