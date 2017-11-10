package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Upload {

	public static void UploadFile(String path,File file){
		FileChannel out = null ;
		MappedByteBuffer buffer = null ;
		try {
			out=new FileOutputStream(new File(path)).getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        try {
			buffer=new FileInputStream(file).getChannel().map(FileChannel.MapMode.READ_ONLY,0,file.length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        buffer.load();
        try {
			out.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        buffer=null;
        try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
