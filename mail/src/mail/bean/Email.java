package mail.bean;

import java.io.Serializable;

public class Email implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private String bcc;
	
	private String content;
	
	private String copyto;

    private int end;

    private String file;
	
	private String filename;
	
	private String frommail;
	
	private String fromname;
	
	private int id;
	
	private int ismain;
	
	private String mailid;
	
	private String realsend;
	
	private int receipt;
	
	private int see;
	
	private int start;
	
	private int state;
	
	// 邮件的发送状态
	private int state2;
	
	private String time;
	
	private String title;
	
	private String tomail;
	
	private String toname;
	
	private String type;
	
	private String subject;
	
	// 联表查询时，标志属于哪个邮箱的,1-收件箱，2-发件箱 3-草稿箱
	private String boxtype;

	// 邮件数据保存的文件路径
	private String mailfile;
	

	// 用作用户举报的参数
	private int checkboxs;
	// 是否加入黑名单
	private int bInsertSpamList;

	private String times;	       //开始时间 
	private String timex;	       //结束时间
	
	public int getState2() {
		return state2;
	}

	public void setState2(int state2) {
		this.state2 = state2;
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

	public String getBoxtype() {
		return boxtype;
	}

	public void setBoxtype(String boxtype) {
		this.boxtype = boxtype;
	}

	
	public String getMailfile() {
		return mailfile;
	}

	public void setMailfile(String mailfile) {
		this.mailfile = mailfile;
	}

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getbInsertSpamList() {
		return bInsertSpamList;
	}

	public void setbInsertSpamList(int bInsertSpamList) {
		this.bInsertSpamList = bInsertSpamList;
	}

	public int getCheckboxs() {
		return checkboxs;
	}

	public void setCheckboxs(int checkboxs) {
		this.checkboxs = checkboxs;
	}

	public String getBcc() {
		return bcc;
	}
	
	public String getContent() {
		return content;
	}

	public String getCopyto() {
		return copyto;
	}

	public int getEnd() {
		return end;
	}

	public String getFile() {
		return file;
	}

	public String getFilename() {
		return filename;
	}

	public String getFrommail() {
		return frommail;
	}

	public String getFromname() {
		return fromname;
	}

	public int getId() {
		return id;
	}

	public int getIsmain() {
		return ismain;
	}

	public String getMailid() {
        return mailid;
    }

	public String getRealsend() {
		return realsend;
	}

	public int getReceipt() {
		return receipt;
	}

	public int getSee() {
		return see;
	}

	public int getStart() {
		return start;
	}

	public int getState() {
		return state;
	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public String getTomail() {
		return tomail;
	}

	public String getToname() {
		return toname;
	}

	public String getType() {
		return type;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFrommail(String frommail) {
		this.frommail = frommail;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIsmain(int ismain) {
		this.ismain = ismain;
	}

	public void setMailid(String mailid) {
        this.mailid = mailid;
    }

	public void setRealsend(String realsend) {
		this.realsend = realsend;
	}

	public void setReceipt(int receipt) {
		this.receipt = receipt;
	}

	public void setSee(int see) {
		this.see = see;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTomail(String tomail) {
		this.tomail = tomail;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public void setType(String type) {
		this.type = type;
	}
}
