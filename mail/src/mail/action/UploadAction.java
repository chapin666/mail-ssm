package mail.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import mail.bean.User;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.CreateUUID;

import com.opensymphony.xwork2.ActionSupport;

@Component("uploadAction")
@Scope("prototype")
public class UploadAction extends ActionSupport implements ServletContextAware {
	private static final long serialVersionUID = 1000000L;
	private boolean success;//删除成功标志

	// 上传文件属性
	private File file;//文件
	private String fileFileName;//文件名
	private String number;
	
	private String fileDir;//文件URL
	private ServletContext context;

	/**
	 * ajax异步上传
	 * 
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public String fileUpload() throws IOException {
		number = ServletActionContext.getRequest().getParameter("number");
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		String relativePath = "upload/"+user.getId();
		//File directory = new File(relativePath);
		String dir = ServletActionContext.getRequest().getRealPath("/").replace("\\", "/") + relativePath + "/" ;
		if (!isNull(fileFileName)) {
			String newName = fileName(fileFileName, dir, file);
			relativePath += "/" + newName;
			relativePath = "/" + relativePath.replace("\\", "/");
			fileDir = dir + newName;
			fileDir = fileDir.replace("\\", "/");
		}

		return tohtml(fileFileName, fileDir, relativePath, number);
	}
	
	/**
	 * 删除文件
	 * 
	 * @param filename
	 * @param fileUrl
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public void deleteUpFile() throws IOException {
		String dir = ServletActionContext.getRequest().getRealPath("/").replace("\\", "/");
		if(fileDir.compareTo("error") == 0)
		{
			ServletActionContext.getResponse().getWriter().print("1");
			return;
		}
		if (fileDir != null) {
			File file = new File(dir+fileDir);
			if (file.exists()) {
				try{
					file.delete();
				}catch (Exception e) {
					e.printStackTrace();
				}
				ServletActionContext.getResponse().getWriter().print("1");
			}else{
				ServletActionContext.getResponse().getWriter().print("2");
			}
		}else{
			ServletActionContext.getResponse().getWriter().print("2");
		}
	}

	/************************************************************************ 私有方法 ********************************************************************/

	private String tohtml(String filename, String fileUrl, String fileUri, String uuid) {
		HttpServletResponse r = ServletActionContext.getResponse();
		r.setHeader("Cache-Control", "no-cache, must-revalidate");
		r.setHeader("Pragma", "no-cache");
		r.setContentType("text/html;charset=utf-8");
		try {
			r.getOutputStream().write(filename.getBytes("utf-8"));
			r.getOutputStream().write("@".getBytes());
			r.getOutputStream().write(fileUrl.getBytes("utf-8"));
			r.getOutputStream().write("@".getBytes());
			r.getOutputStream().write(fileUri.getBytes("utf-8"));
			r.getOutputStream().write("@".getBytes());
			r.getOutputStream().write(uuid.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/******************************** 验证函数 *********************************/
	/**
	 * 验证输入是否为空
	 * 若输入字符串value为空或空字符(包括中文空字符)，那么返回true
	 * 反之返回false
	 * @param value
	 * @return boolean
	 */
	public static boolean isNull(String value) {
		if (value == null || trim(value).length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	private static String trim(String value) {
		return value.replaceAll("[　]", "").trim();
	}

	public static String  fileName(String filename ,String realPath,File doc){
		int BUFFER_SIZE = 16 * 1024 ;
		String targetFileName  = uuidFileName(filename);//成长随机新文件名
		String dir = targetFileName;
		File destFile = new File(realPath);//判断要路径是否存在
		if(!destFile.exists()) {//若路径不存在，则创建路径
			destFile.mkdirs();
		}
		File dst = new File(realPath,targetFileName);
		
		try  {   
			InputStream in = null ;   
            OutputStream out = null ;   
            try  {                   
                in = new BufferedInputStream( new FileInputStream(doc), BUFFER_SIZE);   
                out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);   
                byte [] buffer = new byte [BUFFER_SIZE];   
              	while (in.read(buffer) > 0 )  {   
                 	out.write(buffer);   
               	}    
        	} finally  {   
            	if ( null != in)  {   
            		in.close();   
             	}    
                if ( null != out)  {   
                	out.close();   
                }    
        	}
		} catch (Exception e)  {   
			e.printStackTrace();   
			return null;
		}    
		return dir;
	}

	public static String uuidFileName(String fileName) {    
        String uuid = CreateUUID.getuuid();    
        int position = fileName.lastIndexOf(".");
        String extension="";
        if(position != -1)
        {
        	extension = fileName.substring(position);
        }
        
        return uuid + extension;    
    }
	
	/************************************************************************ set get方法 ********************************************************************/

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setServletContext(ServletContext arg0) {
		context = arg0;
	}

	@JSON(serialize = false)
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
