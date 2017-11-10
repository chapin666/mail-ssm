package mail.bean;

import java.io.Serializable;

public class Files implements Serializable{

	private static final long serialVersionUID = 7809262355115046883L;

	private String file ;
	
	private String filename;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
