package mail.mapper;

import java.util.List;

import mail.bean.Email;
import mail.bean.MailData;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MailDataMapper {
    
    @Select("select * from emaildata_recv where mailid=#{mailId} limit 0,1")
    MailData getRecvData(String mailId);
    
    @Select("select * from emaildata_send where mailid=#{mailId} limit 0,1")
    MailData getSendDataByMailId(String mailId);

    @Select("select * from emaildata_send where id=#{mailId} limit 0,1")
    MailData getSendDataById(int mailId);
    
    @Insert("INSERT INTO emaildata_send(id, mailid, subject, file, filename, data) VALUES(#{id}, '0',#{title},#{filename},#{file},#{content})")
    void saveDraftData(Email data);

    @Update("update emaildata_send set subject=#{title}, file=#{filename}, filename=#{file}, data=#{content} where id=#{id}")
    void updateDraftData(Email data);
    
    @Update("update emaildata_recv set subject=#{title}, file=#{filename}, filename=#{file}, data=#{content} where id=#{id}")
    void updateDraftData1(Email data);
}
