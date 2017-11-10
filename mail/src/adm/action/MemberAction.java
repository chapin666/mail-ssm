package adm.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import mail.bean.Config;
import mail.bean.Units;
import mail.service.UnitsService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import adm.bean.Member;
import adm.bean.Unit;
import adm.service.MemberService;
import adm.service.UnitService;
import util.IpData;
import util.LogUtil;
import util.Page;
import util.encry.Blowfish;

@Component("memberAction")
@Scope("prototype")
public class MemberAction {
	private static final Log logger = LogFactory.getLog(MemberAction.class);
	@Autowired
	private MemberService memberService ;
	
	@Autowired
	private UnitService unitService ;

	@Autowired
	private UnitsService unitsService ;
	
	private Member member ;
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	//判断管理员名称是否存在
	public String checkUserNameExist() throws Exception{
		
		String username = ServletActionContext.getRequest().getParameter("username").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		member = memberService.checkUserNameExist(username);
		
		if(member!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	//检测密码是否正确
	public String checkPass() throws Exception{
		
		Blowfish crypt = new Blowfish();
		String username = ServletActionContext.getRequest().getParameter("username").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		String password = ServletActionContext.getRequest().getParameter("password").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		member = memberService.checkUserNameExist(username) ;
		String password1 = crypt.decryptString(member.getPass());
		
		if(password.equals(password1)){
			ServletActionContext.getResponse().getWriter().print(1);
			ActionContext.getContext().getSession().put("member", member);
//			MailSet ms = new MailSet();
//			ms.getmail();
			/*Config config = unitService.getDoMain("domain");
			if(config!=null){
				ServletActionContext.getRequest().getSession().setAttribute("host",config.getValue());
			}*/
			LogUtil.addLog(ServletActionContext.getRequest(), logger, "管理员登录", "", "", "","",member.getName(),IpData.getIp(ServletActionContext.getRequest()));
		}else {
			ServletActionContext.getResponse().getWriter().print(3);
		}
		return null;
	}

	public void checkPassword() throws Exception{
		
		Blowfish crypt = new Blowfish();
		String password = ServletActionContext.getRequest().getParameter("password").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		member = (Member) ServletActionContext.getRequest().getSession().getAttribute("member");
		String password1 = crypt.decryptString(member.getPass());
		
		if(password.equals(password1)){
			ServletActionContext.getResponse().getWriter().print(1);
		}else {
			ServletActionContext.getResponse().getWriter().print(3);
		}
	}
	//进入修改密码页面
	public String toApdatePassword()
	{
		return "success";
	}
	
	public void updatePassword() throws Exception
	{
		String password = ServletActionContext.getRequest().getParameter("password").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		member = (Member) ServletActionContext.getRequest().getSession().getAttribute("member");
		Blowfish crypt = new Blowfish();
		member.setPass(crypt.encryptString(password));
		memberService.updatePassword(member);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "修改密码", "", "", "","",member.getUsername(),"");
		ServletActionContext.getResponse().getWriter().print(1);
	}

	public String addMember(){
		return "success";
	}
	
	public String doAddMember(){
		Blowfish crypt = new Blowfish();
		member.setPass(crypt.encryptString(member.getPass()));
		memberService.addMember(member);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "添加管理员", "", "", "","",member.getName(),"");
		return "success";
	}
	
	public String editMember(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		Member m = new Member();
		m.setId(iid);
		m.setUsername(username);
		member = memberService.getMember(m);
		String unitsn = member.getUnits();
		List<Unit> unitl = new ArrayList<Unit>();
		if(null != unitsn && !"".equals(unitsn)){
			String[] unitss = unitsn.split(",");
			for(int i=0; i<unitss.length; i++){
				Unit unit = unitService.getUnit(Integer.parseInt(unitss[i]));
				unitl.add(unit);
			}
		}
		request.setAttribute("unitl", unitl);
		
		List<Units> unitlist = new ArrayList<Units>();
		Units units = new Units();
		unitlist = unitsService.getUnitsList(units);
		
		if(unitlist!=null&&unitlist.size()>0){
			for(Units u:unitlist){
				if(u.getId()==1){
					u.setPid(0);
				}
			}
		}
		request.setAttribute("unitlist",unitlist);
		
		request.setAttribute("member", member);
		return "success" ;
	}

	public String editMemberPass(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return "success" ;
	}
	
	public String doEditMember(){
		if(null != member.getPass() && !"".equals(member.getPass())){
			Blowfish crypt = new Blowfish();
			member.setPass(crypt.encryptString(member.getPass()));
		}
		memberService.editMember(member);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "编辑管理员", "", "", "","",member.getName(),"");
		return "success";
	}
	
	public String deleteMember(){
		String[] ids = ServletActionContext.getRequest().getParameterValues("id");
		memberService.deleteMember(ids);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "删除管理员", "", "", "","","删除管理员","");
		return "success";
	}
	
	//后台查询管理员列表
	public String getMemberList()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Member> memberlist = new ArrayList<Member>();
		int totalsize = memberService.getSize(member);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
			if(currentpage != null){
				pagenow = Integer.parseInt(currentpage);
			}else{
				pagenow = 1;
			}
			if(pagenow == 0){
				pagenow = 1;
			}
			int pagenum = 0 ;
			if(totalsize%pagesize==0){
				pagenum = totalsize/pagesize;
			}else{
				pagenum = totalsize/pagesize+1;
			}
			if(member == null){
				member = new Member();
			}
			member.setStart(pagesize*pagenow-pagesize);
			member.setEnd(pagesize);
			memberlist = memberService.getMemberList(member);
			request.setAttribute("member", member);
			
			if(memberlist != null){
				request.setAttribute("memberlist", memberlist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagesize",pagesize);
				request.setAttribute("totalsize",totalsize);
				request.setAttribute("pagenum",pagenum);
			}
		}
		return "success";
	}
}