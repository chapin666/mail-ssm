package util;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import mail.bean.User;

public class SessionManager {
    
    private static Map<String, User> session = new HashMap<String, User>();
    
    public synchronized static void addUser(User user){
        session.put(getCurrentSessionId(), user);
    }
    
    private static String getCurrentSessionId(){
        return ServletActionContext.getRequest().getSession().getId();
    }
    
    public static User getUser(){
        User user = session.get(getCurrentSessionId());
        return user;
    }

}
