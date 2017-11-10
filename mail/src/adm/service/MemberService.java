package adm.service;

import java.util.List;
import adm.bean.Member;

public interface MemberService {

	public Member checkUserNameExist(String username) throws RuntimeException;
		
	public Member getMember(Member member);
	
	public List<Member> getMemberList(Member member) throws RuntimeException;

	public int getSize(Member member);
	
	public void deleteMember(String[] ids);

	public void editMember(Member member);

	public void addMember(Member member);

	public void updatePassword(Member member);
	
}