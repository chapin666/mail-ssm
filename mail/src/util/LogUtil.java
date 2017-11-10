package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import adm.bean.Member;

public class LogUtil {
	
	public static void addLog(HttpServletRequest request, Log log,
			String types, String title, String fmail, String tmail,String state, String odata, String ips )
	{
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
		StringBuffer sb = new StringBuffer();
		sb.append(types).append("#");
		sb.append(title).append("#");
		sb.append(fmail).append("#");
		sb.append(tmail).append("#");
		sb.append(df.format(new Date())).append("#");
		sb.append(state).append("#");
		sb.append(member.getId()).append("#");
		sb.append(member.getUsername()).append("#");
		sb.append(odata).append("#");
		sb.append(ips);
		log.info(sb.toString());
	}
}
