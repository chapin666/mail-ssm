package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
	private static final long serialVersionUID = 4250497006668560546L;
	private static final int BUFFER_SIZE = 10 * 1024 * 1024;//缓冲区10Mb
	private static final String DEFAULT_SAVE_PATH = "/website/m6699.com/bmeasy/inoutmall/uploadfiles/default/";//存放路径
	private static final String DEFAULT_MAXSIZE = "300000"; //默认最大300Kb  
	private static final String DOC_DEFAULT_MAXSIZE = "1000000"; //文档默认最大2001M 
	private static final String DEFAULT_SUFFIX = ".JPG,.GIF,.BMP,.JPEG,.DOC,.DOCX,.TXT";//文件默认扩展名
	private static final String TEMP_SAVE_PATH = "uploadfiles/temp/";//文件临时存放路径
	
	File tmpDir = null;//临时存储目录
    File saveDir = null;//存放目录
 
    public UploadServlet() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request,response);
	}

    @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    	String result = "{success:false}";

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
    	try{
    	
        if(ServletFileUpload.isMultipartContent(request)){
          DiskFileItemFactory dff = new DiskFileItemFactory();
          dff.setRepository(tmpDir);
          dff.setSizeThreshold(BUFFER_SIZE);
          ServletFileUpload sfu = new ServletFileUpload(dff);
          sfu.setFileSizeMax(2 * 1024 * 1024);
          sfu.setSizeMax(-1);
           
          String file_name = null;
          String save_path = null;
          String suffix = null;
          
          FileItem relFileItem = null;
          
          List<FileItem> files = null;
          
          try{
        	  files = sfu.parseRequest(request);    
          }catch (FileUploadBase.SizeLimitExceededException e)   
          {   
          
        	    result = "{success:{maxfile:1}}";
				response.getWriter().println(result);
				response.getWriter().close();
           }   
           catch (FileUploadBase.FileSizeLimitExceededException e)   
           {   
        	    result = "{success:{maxfile:1}}";
				response.getWriter().println(result);
				response.getWriter().close();
				return ;
          }   

          
          
          for(FileItem fileItme : files){
            if(fileItme.isFormField()){
             
            	if(fileItme.getFieldName().equals("file_name"))
            	{
            		file_name=fileItme.getString("UTF-8");
            	}
            	if(fileItme.getFieldName().equals("save_path"))
            	{
            		save_path=fileItme.getString("UTF-8");
            	}
            	if(fileItme.getFieldName().equals("suffix"))
            	{
            		suffix=fileItme.getString();
            	}
            	
            }else
            {
            	relFileItem = fileItme;
            }
          }
          
        
         String path = save_path == null ? DEFAULT_SAVE_PATH
  				: "/website/m6699.com/bmeasy/inoutmall/uploadfiles/"+request.getParameter("mid")+save_path.substring(save_path.indexOf("/"));
  		  String name = file_name;
	  	  File f = new File( path + "/");
		  if (!f.exists()) {
				System.out.println("enter mkdirs!!!!!!!!!!!!!!!!!!!");
				f.mkdirs();
				System.out.println("end mkdirs!!!!!!!!!!!!!!!!!!!!!!1");
		  }
           
		  name = "n"+this.generate() + suffix;
		  
		  String tempsuffix = suffix.toUpperCase();
			try {
				File newFile = new File(path + "/", name);
				if(tempsuffix.equals(".doc") || tempsuffix.equals(".DOC") || tempsuffix.equals(".docx") || tempsuffix.equals(".DOCX")){
					if(relFileItem.getSize() > Long.valueOf(DOC_DEFAULT_MAXSIZE)){
						result = "{success:{maxfile:1}}";
						response.getWriter().println(result);
						response.getWriter().close();
						return ;
						
					}
				}else{
					if(relFileItem.getSize() > Long.valueOf(DEFAULT_MAXSIZE)){
						result = "{success:{maxfile:1}}";
						response.getWriter().println(result);
						response.getWriter().close();
						return ;
					}
				}
				if(DEFAULT_SUFFIX.indexOf(tempsuffix) == -1 || tempsuffix.equals("nosuffix")){
					result = "{success:{maxfile:2}}";
					response.getWriter().println(result);
					response.getWriter().close();
					return ;
				}else{
					if (!newFile.exists()) {
						relFileItem.write(newFile);
						if(null != request.getParameter("pictyle") && "1".equals(request.getParameter("pictyle"))){
							ImageUtil.createThumbnail(path + "/" + name, path + "/s"+name, 78, 78);
							ImageUtil.createThumbnail(path + "/" + name, path + "/b"+name, 415, 415);
						}else if(null != request.getParameter("pictyle") && "2".equals(request.getParameter("pictyle"))){
							ImageUtil.createThumbnail(path + "/" + name, path + "/s"+name, 98, 98);
						}
					}
					path = path.replaceAll("/website/m6699.com/bmeasy/inoutmall/", "");
					result = "{success:{maxfile:0},file:{name:'" + name + "',path:'" + path +"',suffix:'" + suffix +"',fileLength:'" + relFileItem.getSize()+"',fName:'" + name
							+ "'}}";
				}
				
			}catch (IOException e) {
			
				e.printStackTrace();
			}
			try {
				response.getWriter().println(result);
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  
        }
	   }catch(Exception e){
	        e.printStackTrace();
	   }
  }           

  public void init() throws ServletException {

    super.init();
  
    tmpDir = new File(TEMP_SAVE_PATH);
    if(!tmpDir.isDirectory())
        tmpDir.mkdir();
	
    
  }
  
	public String generate() {
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		buffer.append(sf.format(new Date()));
		Random ran = new Random();
		for (int i = 0; i < 6; i++) {
			buffer.append(ran.nextInt(10));
		}
		return new String(buffer);
	}
}
