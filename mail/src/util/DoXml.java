package util;

public class DoXml {

//	public void UploadImageToXml(){
//
//		string strFilePathName = loFile.PostedFile.FileName;
//		string strFileName = Path.GetFileName(strFilePathName);
//		int FileLength = loFile.PostedFile.ContentLength;
//		if(FileLength<=0)
//		return;
//		
//		try {
//		
//			Byte[] FileByteArray = new Byte[FileLength];
//			Stream StreamObject = loFile.PostedFile.InputStream;
//			StreamObject.Read(FileByteArray,0,FileLength);
//			string fileName = Server.MapPath(".\\WriteXml.xml");
//			XmlDocument xmlDoc = new XmlDocument();
//			xmlDoc.Load(fileName);
//			XmlNode root=xmlDoc.SelectSingleNode("dbImage");
//			XmlNodeList xnl=xmlDoc.SelectSingleNode("dbImage").ChildNodes;
//			int nIndex = xnl.Count;
//			XmlElement xe1=xmlDoc.CreateElement("Image");// 创建一个<User>节点
//			XmlElement xesub1=xmlDoc.CreateElement("ImageID");
//			xesub1.InnerText=nIndex.ToString();
//			xe1.AppendChild(xesub1);
//			XmlElement xesub2=xmlDoc.CreateElement("ImageContentType");
//			xesub2.InnerText=loFile.PostedFile.ContentType;
//			xe1.AppendChild(xesub2);
//			XmlElement xesub3=xmlDoc.CreateElement("ImageSize");
//			xesub3.InnerText=FileLength.ToString();
//			xe1.AppendChild(xesub3);
//			XmlElement xesub4=xmlDoc.CreateElement("ImageDescription");
//			xesub4.InnerText=tbDescription.Text;
//			xe1.AppendChild(xesub4);
//			XmlElement xesub5=xmlDoc.CreateElement("ImageData");
//			xesub5.InnerText= Convert.ToBase64String(FileByteArray);
//			xe1.AppendChild(xesub5);
//			root.AppendChild(xe1);
//			xmlDoc.Save(fileName);
//			Response.Redirect("ShowAllImg.aspx");
//		}catch(Exception ex){
//				throw ex;
//		}
//	}
//	
//	　　private void ReadImageFromXml(string ImageID)
//
//	　　{
//
//	　　///ID为图片ID
//
//	　　int ImgID = Convert.ToInt32(ImageID);
//
//	　　///要打开的文件
//
//	　　string fileName = Server.MapPath(".\WriteXml.xml");
//
//	　　XmlDocument xmlDoc = new XmlDocument();
//
//	　　xmlDoc.Load(fileName);
//
//	　　XmlNodeList node =  xmlDoc.SelectSingleNode("//Image[ImageID='"+ImgID.ToString()+"']").ChildNodes;
//
//	　　if(node!=null)
//
//	　　{
//
//	　　string strType = node.Item(1).InnerText;
//
//	　　string strData =node.Item(4).InnerText;
//
//	　　int nSize = int.Parse(node.Item(2).InnerText);
//
//	　　///设定输出文件类型
//
//	　　Response.ContentType = strType;
//
//	　　///输出图象文件二进制数制
//
//	　　Response.OutputStream.Write(Convert.FromBase64String(strData), 0, nSize);
//
//	　　Response.End();
//
//	　　//也可以保存为图像
//
//	　　//FileStream fs = new FileStream(@"C:aa.BMP", FileMode.OpenOrCreate, FileAccess.Write);
//
//	　　//fs.Write((Convert.FromBase64String(strData), 0,nSize);
//
//	　　//fs.Close();
//
//	　　}
//
//	　　}
}  
