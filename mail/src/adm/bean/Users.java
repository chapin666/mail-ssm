package adm.bean;

import java.io.Serializable;

public class Users implements Serializable {

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
    private String unitname;
    private String username;
    private String domainname;

	public String getDomainname() {
		return domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
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

    public String getUnitname() {
        return unitname;
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

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
