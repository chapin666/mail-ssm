package adm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import adm.bean.Member;

public interface MemberMapper {
	
    @Select("select * from member where username=#{username}")
	public Member checkUserNameExist(String username) throws RuntimeException;
	
	public List<Member> getMemberList(Member member);
	
	//@Select("select * from member where id=#{id}")
	public Member getMember(Member member);
	
	public void deleteMember(String[] ids);
	
	public void editMember(Member member);

	public void addMember(Member member);

	public int getSize(Member member);
	
	public void updatePassword(Member member);
	
}
