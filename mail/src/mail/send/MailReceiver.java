package mail.send;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.struts2.ServletActionContext;

import util.Cache;

public class MailReceiver {

    private static Properties props = new Properties();
    static {
        // 存储接收邮件服务器使用的协议
        props.setProperty("mail.store.protocol", "pop3");
        // 设置接收邮件服务器的地址
        props.setProperty("mail.pop3.host", Cache.cache.get("pop3"));
    }

    public static void receive() throws MessagingException, IOException {
        // 根据属性新建一个邮件会话.
        Session session = Session.getInstance(props);
        // 从会话对象中获得POP3协议的Store对象
        Store store = session.getStore("pop3");
        // 如果需要查看接收邮件的详细信息，需要设置Debug标志
        session.setDebug(true);

        // User user = SessionManager.getUser();
        // if (user == null) {
        // throw new RuntimeException("登录超时，请重新登录");
        // }
        // store.connect("pop3.163.com", user.getUsername(), Blowfish.decrypt(user.getPass()));
        store.connect("pop3."+ServletActionContext.getRequest().getSession().getAttribute("host"), "qilin-dong", "dql-fc");

        // 获取邮件服务器的收件箱
        Folder folder = store.getFolder("INBOX");
        // 以只读权限打开收件箱
        folder.open(Folder.READ_ONLY);

        // 获取收件箱中的邮件，也可以使用getMessage(int 邮件的编号)来获取具体某一封邮件
        for (Message message : folder.getMessages()) {
            Address[] recipients = message.getAllRecipients();
            message.getContent();
        }
        // 关闭连接
        folder.close(false);
        store.close();
    }

    public static void main(String[] args) {

    }
}
