package Tool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @清空目录
 */
public class ClearDir {
	/**
     *根据路径删除指定的目录里的所有文件及文件夹（不包含当天的）
     *@param sPath  要删除的目录或文件
     */
	private static String fs;
	
    public static Boolean DeleteFolder(String sPath) {
    	File file = new File(sPath);
		fs = file.getAbsolutePath();
    	return deleteDirectory(file);
    }
	
    public static Boolean deleteDirectory(File file) {
    	long modifyDate = file.lastModified(); //得到最后修改日期		
    	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	Date date = new Date();//获得系统当前日期    
    	Calendar cd = Calendar.getInstance();//获得系统当前日期   
    	cd.setTimeInMillis(modifyDate);     	
    	
		if(file.exists()) { // 判断文件是否存在			
			if (file.isFile()) { // 判断是否是文件
				if(modifyDate!=0){ 
            		if(sd.format(date).equals(sd.format(cd.getTime()))){//今天的不删除            			
            			return false;
	                }else{
	                	file.delete(); //删除;
	                	return true;
	                }
				}else{
					return false;
				}
			}else if(file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteDirectory(files[i]); // 把每个文件 用这个方法进行迭代
					
					if(files[i].isDirectory()){//目录中有子目录不删除
						File childfiles[] = files[i].listFiles();
						if(childfiles.length>0){						
							return false;
						}else{
							file.delete();
							return true;
						}						
					}
				}
				
				if(file.getAbsolutePath().equals(fs)){//顶级目录不删除
					return false;
				}else{				
					file.delete();
					return true;
				}										
							
			}else{
				return false;
			}			
		}else {
			return false;
		}
    }
}
