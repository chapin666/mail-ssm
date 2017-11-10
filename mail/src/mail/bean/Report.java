package mail.bean;

/** 邮件举报 */
public class Report {
	private int id;
	/** 举报人 */
	private String reportname;
	/**举报邮件ID*/
	private String mailid;
	///** 举报邮件 */
	//private String reportmail;
	/** 举报时间 */
	private String reporttime;
	/** 举报类型 */
	private String reporttype;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

//	public String getReportmail() {
//		return reportmail;
//	}
//
//	public void setReportmail(String reportmail) {
//		this.reportmail = reportmail;
//	}

	public String getReporttime() {
		return reporttime;
	}

	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	
}
