package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import mail.bean.User;

public class LoggerUtil {
	
	public static void addLog(HttpServletRequest request, Log log,
			String types, String title, String fmail, String tmail,String state, String odata, String ips )
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
		StringBuffer sb = new StringBuffer();
		sb.append(types).append("#");
		sb.append(title).append("#");
		sb.append(fmail).append("#");
		sb.append(tmail).append("#");
		sb.append(df.format(new Date())).append("#");
		sb.append(state).append("#");
		sb.append(user.getId()).append("#");
		sb.append(user.getUsername()).append("#");
		sb.append(odata).append("#");
		sb.append(ips);
		log.info(sb.toString());
	}

	public static void addDSLog(User user, Log log,
			String types, String title, String fmail, String tmail,String state, String odata, String ips )
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
		StringBuffer sb = new StringBuffer();
		sb.append(types).append("#");
		sb.append(title).append("#");
		sb.append(fmail).append("#");
		sb.append(tmail).append("#");
		sb.append(df.format(new Date())).append("#");
		sb.append(state).append("#");
		sb.append(user.getId()).append("#");
		sb.append(user.getUsername()).append("#");
		sb.append(odata).append("#");
		sb.append(ips);
		log.info(sb.toString());
	}
}
