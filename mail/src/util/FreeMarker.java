package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarker {
	
	public static void MakeTemplate(String path,String name,String filename,Map<?,?> root){
		
		try{
			Configuration config = new Configuration();
			config.setDirectoryForTemplateLoading(new File(path));
			config.setObjectWrapper(new DefaultObjectWrapper());
			Template template = config.getTemplate(name,"utf-8");
			FileOutputStream fos = new FileOutputStream(filename);
			Writer out = new OutputStreamWriter(fos,"utf-8");
			template.process(root, out);
			out.flush();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}
	}
}