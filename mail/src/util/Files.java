package util;

import java.io.*;

public class Files {
	public static void delAll(File f) throws IOException {
		if (f.exists()) {
			File subs[] = f.listFiles();
			for (int i=0; i<subs.length; i++) {
				if (subs[i].isDirectory())
					delAll(subs[i]);
					subs[i].delete();
			}
		}
	}
	
	public static void delAlls(File f) throws IOException {
		if (f.exists()) {
			File subs[] = f.listFiles();
			for (int i=0; i<subs.length; i++) {
				if (subs[i].isDirectory())
					delAlls(subs[i]);
					subs[i].delete();
			}
			f.delete();
		}
	}
	
	public static void del(String path){
		File file = new File(path);
		file.delete();	
	}
	
	public static void dels(String path){
		String[] paths = path.split("\\/");
		if(paths.length>0){
			File file = new File(path);
			file.delete();
			path = path.replace(paths[paths.length-1],"");
			File files = new File(path);
			if(files.exists()){
				String [] list = files.list();
				if(list!=null){
					if(list.length==0){
						files.delete();
					}
				}
			}
		}
	}
	
	public static String[] filelist(String path){
		
		File file = new File(path);
		String [] list = {};
		if(file.exists()){
			list = file.list();
		}
		if(list.length>0){
			return list ;
		}
		return null ;
	}
}