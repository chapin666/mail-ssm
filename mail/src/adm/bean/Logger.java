package adm.bean;

public class Logger {
	
	private String id;	           //记录事务日志的id
	
	private String searchType;     //搜索类型
	
	private String types;	       //日志操作类型    发信 收信、登录、其它
	
	private String title;	       //邮件主题
	private String fmail;	       //发送者
	private String tmail;	       //接收者
	private String ftmail;         //搜索帐号
	
	private String time;	       //日志操作时间
	private String times;	       //操作时间
	private String timex;	       //操作时间
	private String day;            //搜索天数

	private String state;	       //邮箱发送状态
	
	private String userid;	       //用户id/管理员id

	private String username;	   //用户名称/管理员名称
	
	private String odata;	       //相关数据
	
	private String ips;           //登录ip

	/*分页字段 -- Start*/
	private int start ;
	private int end ;
	/*分页字段 -- End*/
	
	private String domain;//域名
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFmail() {
		return fmail;
	}
	public void setFmail(String fmail) {
		this.fmail = fmail;
	}
	public String getTmail() {
		return tmail;
	}
	public void setTmail(String tmail) {
		this.tmail = tmail;
	}
	public String getFtmail() {
		return ftmail;
	}
	public void setFtmail(String ftmail) {
		this.ftmail = ftmail;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getTimex() {
		return timex;
	}
	public void setTimex(String timex) {
		this.timex = timex;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOdata() {
		return odata;
	}
	public void setOdata(String odata) {
		this.odata = odata;
	}
	public String getIps() {
		return ips;
	}
	public void setIps(String ips) {
		this.ips = ips;
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
