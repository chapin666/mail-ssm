package adm.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import adm.bean.Member;
import adm.mapper.MemberMapper;
import adm.service.MemberService;

@Component("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper ;

	public Member checkUserNameExist(String username) throws RuntimeException{
		
		return memberMapper.checkUserNameExist(username) ;
	}

	public Member getMember(Member member) {
		
		return memberMapper.getMember(member) ;
	}

	public List<Member> getMemberList(Member member) throws RuntimeException {

		return memberMapper.getMemberList(member) ;
	}

	public int getSize(Member member) {

		return memberMapper.getSize(member) ;
	}
	
	public void deleteMember(String[] ids){
		
		memberMapper.deleteMember(ids);
	}
	
	public void editMember(Member member) {

		memberMapper.editMember(member) ;
	}
	
	public void addMember(Member member) {

		memberMapper.addMember(member) ;
	}

	public void updatePassword(Member member) {
		
		memberMapper.updatePassword(member);
	}
}
