package mail.bean;
/**
 * @签名
 */
public class Sign {
	private int id;
	/**用户id*/
	private int userid;  
	/**签名名称*/
	private String name;
	/**签名内容*/
	private String content;
	/**默认签名*/
	private String defaultsign;
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDefaultsign() {
		return defaultsign;
	}
	public void setDefaultsign(String defaultsign) {
		this.defaultsign = defaultsign;
	}
}
