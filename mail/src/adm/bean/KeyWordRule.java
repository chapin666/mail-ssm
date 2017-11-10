package adm.bean;

import java.io.Serializable;
/**关键字规则 */
public class KeyWordRule implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	/**	规则名称 */
	private String name;
	/**	优先级 */	
	private int grade;
	/**	状态 */
	private int state;
	/**	发件人 */
	private String addresser;
	/**	收件人 */
	private String addressee;
	/**	主题 */
	private String title;
	/**	正文 */
	private String mainbody;
	/**	最后修改时间 */
	private String lasttime;
	/**	开始记录 */
	private int start; 
	/**	结束记录 */
	private int end;

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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddresser() {
		return addresser;
	}

	public void setAddresser(String addresser) {
		this.addresser = addresser;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainbody() {
		return mainbody;
	}

	public void setMainbody(String mainbody) {
		this.mainbody = mainbody;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
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

	@Override
	public String toString() {
		return "KeyWordRule [id=" + id + ", name=" + name + ", grade=" + grade
				+ ", state=" + state + ", addresser=" + addresser
				+ ", addressee=" + addressee + ", title=" + title
				+ ", mainbody=" + mainbody + ", lasttime=" + lasttime
				+ ", start=" + start + ", end=" + end + "]";
	}	
}
