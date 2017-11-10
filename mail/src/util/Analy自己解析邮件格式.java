//package util;
//
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//
//
//import mail.bean.Email;
//import mail.bean.MailData;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.net.QuotedPrintableCodec;
//
//
//
//import sun.misc.BASE64Decoder;
//
//class PairFile {
//
//	public String filename = "";
//	public String path = "";
//
//	PairFile(String name, String path) {
//		filename = name;
//		this.path = path;
//	}
//}
//
//public class Analy自己解析邮件格式 {
//
//	private static String dir = Cache.cache.get("filedir"); //目录以"/"结束
//	
//	private String filename = "MaileFile/2013-04-15/516C4E27.8010303@dm569263708.eicp.net1366052445";
//	private String messageID = ""; // 邮件ID
//	private String subject = ""; // 邮件标题
//	private String from = ""; // 发件人帐号和名称
//	private String to = ""; // 邮件收件人
//	private String cc = ""; // 邮件收件人
//	private String bcc = ""; // 邮件收件人
//	private String date = ""; // 日期
//	private boolean hasAttachment = false; // 是否包含附件
//	private String content = ""; // 邮件正文
//	private String boundary = ""; // 邮件每部分的分隔符号(假如存在附件才会有值)
//    List<String> part = new ArrayList<String>();
//
//    private StringBuffer bodytext = new StringBuffer();//存放邮件内容   
//    String saveAttachPath=dir+"/temp/";
//    
//	// private static OutputStream outputStream=null;
//	// 装载各个部分的数据
//	private Vector<String> vecPartData = new Vector<String>();
//	// 装载附件
//	private Vector<PairFile> vecAttach = new Vector<PairFile>();
//
//	public static void main(String[] args) throws Exception {
//		Email email = new Email();
//		new Analy自己解析邮件格式().parse(email);
//	}
//
//	/**
//	 * 外部调用接口
//	 * @param email
//	 * @param data
//	 * @throws Exception 
//	 */
//	public static void parse(Email email, MailData data) throws Exception{
//		Analy自己解析邮件格式 analy = new Analy自己解析邮件格式();
//		analy.filename = dir + data.getData();
//		//System.out.println("邮件文件的路径为："+analy.filename);
//		analy.parse(email);
//	}
//	
//	private static String DecodeString(String str)
//	{
//	 	if(str !=null){
//	        String baseStr = str.trim();
//	        baseStr=baseStr.replace("\"", "");
//	        try{
//	            if(baseStr.indexOf("=?") >= 0){
//	                String[] tmp = baseStr.substring(2, baseStr.length() - 1).split("\\?");
//	                if("B".equalsIgnoreCase(tmp[1])){ 
//	                	str = new String(Base64.decodeBase64(tmp[2]), tmp[0]);
//	                } else if("Q".equalsIgnoreCase(tmp[1])){
//	                	str = new QuotedPrintableCodec().decode(tmp[2], tmp[0]);
//	                }
//	            }
//	        } catch(Exception e){
//	            e.printStackTrace();
//	        }
//    	}
//	 	
//	 	return str;
//	}
//	
//	public void parse(Email email) throws Exception {
//		
//		// 解析邮件文件
//		AnalyMailFile();
//
//		// 解析邮件正文部分各个分段
//		AnalyMailData();
//
//		// 获取正文和附件
//		GetContentAttach();
//
//		// 解析标题编码
//	   	if(subject !=null){
//	        String baseStr = subject.trim();
//	        try{
//	            if(baseStr.indexOf("=?") >= 0){
//	                String[] tmp = baseStr.substring(2, baseStr.length() - 1).split("\\?");
//	                if("B".equalsIgnoreCase(tmp[1])){ 
//	                	subject = new String(Base64.decodeBase64(tmp[2]), tmp[0]);
//	                } else if("Q".equalsIgnoreCase(tmp[1])){
//	                	subject = new QuotedPrintableCodec().decode(tmp[2], tmp[0]);
//	                }
//	            }
//	        } catch(Exception e){
//	            e.printStackTrace();
//	        }
//    	}
//	   	
//	   	// 邮件内容的换行符号转换
//	   	content=content.replace("\r\n", "</br>");
//		
//	   	// 解码发件人字符串
//	   	int index1=from.indexOf('<');
//	   	if(index1 != -1)
//	   	{
//	   		String tempStr=from.substring(0,index1-1);
//	   		tempStr=DecodeString(tempStr);
//	   		
//	   		from=tempStr+from.substring(index1);
//	   	}
//	   	
//	   	// 解码收件人字符串
//	   	String StrTempTo=to;
//	   	String[] tempTo= to.split(",");
//	   	int i=0;
//	   	for(String str :tempTo)
//	   	{
//	   		index1=str.indexOf('<');
//		   	if(index1 != -1)
//		   	{
//		   		String tempStr=str.substring(0,index1-1);
//		   		str=DecodeString(tempStr)+str.substring(index1);
//		   		if(i > 0) str=";"+str;
//		   		StrTempTo+=str;
//		   	}
//		   	i++;
//	   	}
//	   	
//	   	to=StrTempTo;
//	   	
//		email.setFrommail(from);
//		email.setToname(to);
//		email.setCopyto(cc);
//		email.setBcc(bcc);
//		email.setTitle(subject);
//		email.setTime(date);
//		email.setContent(content);
//		
//		// 将附件信息拼成字符串
//		String TotailFile="";
//		for(PairFile file:vecAttach)
//		{
//			TotailFile+=file.filename+"*";
//			TotailFile+=file.path+"|";
//		}
//		email.setFile(TotailFile);
//	}
//
//	public boolean GetContentAttach() throws Exception {
//
//		for (int i = 0; i < vecPartData.size(); i++) {
//			String TempContent="";
//			StringBuffer stringBuffer = new StringBuffer(vecPartData.get(i));
//
//			String flag = "";
//			int index1 = 0, index2 = 0;
//			String splitBase = "Content-Transfer-Encoding: base64";
//	        String splitQP = "Content-Transfer-Encoding: quoted-printable";
//			
//			// 有附件时，第一部分是正文，第二部分是附件
//			// 没有附件时，第一部分是纯文本内容，第二部分是html内容
//			if (hasAttachment == false) {
//				String ContentType = "";
//				flag = "Content-Type:";
//				index1 = stringBuffer.indexOf(flag);
//				index2 = stringBuffer.indexOf(";", index1 + 1);
//
//				if (index2 != -1) {
//					ContentType = stringBuffer.substring(index1 + flag.length(), index2).trim();
//				}
//
//				if (ContentType.startsWith("text/html")|| ContentType.startsWith("text/plain")) {
//					String character = "", code = "";
//					String[] temp = stringBuffer.toString().split("\n");
//					for (String strLine : temp) {
//						if (strLine.startsWith("."))
//							continue;
//						if (code.length() > 1 && character.length() > 1) {
//							TempContent += strLine;
//							continue;
//						}
//
//						index1 = strLine.indexOf("charset=");
//						if (index1 != -1) {
//							character = strLine.substring(index1+ "charset=".length());
//							if (character.indexOf("\"") >= 0&& character.lastIndexOf("\"") >= 0) {
//								character = character.substring(1,character.length() - 1);
//								character = character.trim();
//							}
//						}
//
//						index1 = strLine.indexOf("Content-Transfer-Encoding:");
//						if (index1 != -1) {
//							code = strLine.substring(index1+ "Content-Transfer-Encoding:".length());
//							code = code.trim();
//						}
//					}
//
//					 // 半编码的内容解码
//					 if(content.indexOf('<') != -1)
//					 {
//						 String TempContetn="";
//						 int index3=-1;
//						 while(true)
//						 {
//							 index1=content.indexOf("<",index3);
//							 index2=content.indexOf(">",index1+1);
//							 
//							 if(index1 == -1 || index2 == -1) break;
//							 
//							 String strPart1=content.substring(index3+1,index1);
//							 String strPart2=content.substring(index1,index2+1);
//							 index3=index2;
//							 
//							 // 进行解码
//							 String strPart3="";
//							 if(strPart1.length() > 0)
//							 {
//								 strPart1=strPart1.replace("==", "=");
//								 try {
//									if(code.equals("quoted-printable"))
//									 {
//										 strPart3 = new String(new QuotedPrintableCodec().decode(strPart1, character)); 
//									 }else if(code.equals("base64"))
//									 {
//										 strPart3 = new String(Base64.decodeBase64(strPart1), character);
//									 }
//								} catch (Exception e) {
//									System.out.print("解码失败:"+strPart1);
//								}
//							 }
//							 
//							 TempContetn+=strPart2+strPart3;
//						 }
//						 
//						 content=TempContetn;
//						 
//					 }// 全编码的解码
//					 else
//					 {
//						 if(code.equals("quoted-printable"))
//						 {
//							 content = new String(new QuotedPrintableCodec().decode(content, character)); 
//						 }else if(code.equals("base64"))
//						 {
//							 content = new String(Base64.decodeBase64(content), character);
//						 }
//					 }
//					 
////					if (code.equals("quoted-printable")) {
////						content = new String(new QuotedPrintableCodec().decode(TempContent, character));
////					} else if (code.equals("base64")) {
////						content = new String(Base64.decodeBase64(TempContent),character);
////					}
//				}
//			} else {
//				
//				switch (i) {
//				case 0:
//					String ContentType = "";
//					flag = "Content-Type:";
//					index1 = stringBuffer.indexOf(flag);
//					index2 = stringBuffer.indexOf(";", index1 + 1);
//
//	                if (index2 != -1) {
//	                    ContentType = stringBuffer.substring(index1 + flag.length(), index2).trim();
//	                }
//
//					if (ContentType.startsWith("multipart/alternative"))
//					{
//					    for(String p : part){
//					        String[] plainHtml = stringBuffer.toString().split(p);
//					        for(String html : plainHtml){
//					            String[] temp = html.split(splitBase);
//					            if(temp.length > 1 && temp[0].indexOf("text/html") > 0) {
//					                String code = temp[0].substring(temp[0].indexOf("charset=") + "charset=".length());
//					                code=code.replace("\n", "");
//					                code=code.replace("\"", "");
//	                                content = new String(Base64.decodeBase64(temp[1].trim()), code);
//					            } else { 
//					                temp = html.split(splitQP);
//					                if(temp.length > 1 && temp[0].indexOf("text/plain") > 0) {
//		                                String code = temp[0].substring(temp[0].indexOf("charset=") + "charset=".length());
//		                                code=code.replace("\n", "");
//						                code=code.replace("\"", "");
//		                                content = new String(new QuotedPrintableCodec().decode(temp[1].trim(), code));
//		                            }
//					            }
//					        }
//					    }
//					}
//					else if(ContentType.startsWith("text/html") || ContentType.startsWith("text/plain"))
//					{
//						 String character="",code="";
//						 String[] temp = stringBuffer.toString().split("\n");
//						 for(String strLine: temp)
//						 {
//							 if(strLine.startsWith(".")) continue;
//							 if(code.length() > 1 && character.length() > 1)
//							 {
//								 content+=strLine;
//								 continue;
//							 }
//							 
//							 index1=strLine.indexOf("charset=");
//							 if(index1 != -1)
//							 {
//								 character = strLine.substring(index1+"charset=".length());
//								 if(character.indexOf("\"")>= 0 && character.lastIndexOf("\"")>= 0)
//								 {
//									 character=character.substring(1, character.length()-1);
//									 character=character.trim();
//								 }
//							 }
//							 
//							 index1=strLine.indexOf("Content-Transfer-Encoding:");
//							 if(index1 != -1)
//							 {
//								 code = strLine.substring(index1+"Content-Transfer-Encoding:".length());
//								 code=code.trim();
//							 }
//						 }
//						 
//						 // 半编码的内容解码
//						 if(content.indexOf('<') != -1)
//						 {
//							 String TempContetn="";
//							 int index3=-1;
//							 while(true)
//							 {
//								 index1=content.indexOf("<",index3);
//								 index2=content.indexOf(">",index1+1);
//								 
//								 if(index1 == -1 || index2 == -1) break;
//								 
//								 String strPart1=content.substring(index3+1,index1);
//								 String strPart2=content.substring(index1,index2+1);
//								 index3=index2;
//								 
//								 // 进行解码
//								 String strPart3="";
//								 if(strPart1.length() > 0)
//								 {
//									 strPart1=strPart1.replace("==", "=");
//									 try {
//										if(code.equals("quoted-printable"))
//										 {
//											 strPart3 = new String(new QuotedPrintableCodec().decode(strPart1, character)); 
//										 }else if(code.equals("base64"))
//										 {
//											 strPart3 = new String(Base64.decodeBase64(strPart1), character);
//										 }
//									} catch (Exception e) {
//										System.out.print("解码失败:"+strPart1);
//									}
//								 }
//								 
//								 TempContetn+=strPart2+strPart3;
//							 }
//							 
//							 content=TempContetn;
//							 
//						 }// 全编码的解码
//						 else
//						 {
//							 if(code.equals("quoted-printable"))
//							 {
//								 content = new String(new QuotedPrintableCodec().decode(content, character)); 
//							 }else if(code.equals("base64"))
//							 {
//								 content = new String(Base64.decodeBase64(content), character);
//							 }
//						 }
//						 
//					}
//
//					break;
//				default:
//					String attachName = ""; //附件名
//					String fineurl = "";//附近路径
//					
//					String Disposition = "";
//					flag = "Content-Disposition:";
//					index1 = stringBuffer.indexOf(flag);
//					index2 = stringBuffer.indexOf(";", index1 + 1);
//
//	                if (index2 != -1) {
//	                    Disposition = stringBuffer.substring(index1 + flag.length(), index2).trim();
//	                }
//
//					// 判断是不是附件
//	                if(Disposition.startsWith("attachment")) {
//	                    // 获取文件名
//	                    flag = "filename=";
//	                    index1 = stringBuffer.indexOf(flag);
//	                    if (index1 == -1)
//	                        continue;
//	                    index2 = stringBuffer.indexOf("\n", index1 + 2);
//
//	                    String nameMsg = "", fileContent = "";
//	                    if (index2 != -1) {
//	                        nameMsg = stringBuffer.substring(index1 + flag.length() + 1, index2 - 1);
//	                        if(nameMsg.indexOf("?") > 0){
//	                            String[] tmp = nameMsg.substring(2, nameMsg.length() - 1).split("\\?");
//	                            if("B".equalsIgnoreCase(tmp[1])){ 
//	                                attachName = new String(Base64.decodeBase64(tmp[2]), tmp[0]);
//	                            } else if("Q".equalsIgnoreCase(tmp[1])){
//	                                attachName = new QuotedPrintableCodec().decode(tmp[2], tmp[0]);
//	                            } else {
//	                                attachName = nameMsg;
//	                            }
//	                        } else {
//	                            attachName = nameMsg;
//	                        }
//
//	                        // 获取附件
//	                        fineurl = dir + "temp/tmp-" +System.currentTimeMillis() + "-" + attachName;
//	                        int index3 = stringBuffer.indexOf("\n\n", index1);
//	                        if (index3 != -1) {
//	                            fileContent = stringBuffer.substring(index3+ 1);
//	                            saveTempFile(fileContent, fineurl);
//	                        }
//	                    }
//
//	                    PairFile file = new PairFile(attachName, fineurl);
//	                    vecAttach.add(file);
//	                }
//
//					break;
//				}
//			}
//
//		}
//		return true;
//	}
//
//    private void saveTempFile(String base64Code, String targetPath) throws Exception {
//        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
//        File file = new File(dir+"temp/");
//        if(!file.exists()){
//        	file.mkdirs();
//        }
//        FileOutputStream out = new FileOutputStream(new File(targetPath));
//        out.write(buffer);
//        out.close();
//    }
//	
//	public boolean AnalyMailData() {
//		// 第一段为正文，后面的分段为若干附件
//		boolean out = false, save = false;
//		File file = new File(filename);
//		// 读文件
//		BufferedReader reader = null;
//		//不存在boundary的解析
//		if(boundary==null||boundary.equals("")){
//			try {
//				if(file.exists()){
//					reader = new BufferedReader(new FileReader(file));
//		
//					String strLine = "";
//					boolean bStart=false;
//					StringBuffer stringBuffer = new StringBuffer();
//					while ((strLine = reader.readLine()) != null) 
//					{
//						strLine = strLine.trim();
//						if (strLine.startsWith("Content-Type:")) {
//							bStart=true;
//						}
//						
//						if(bStart)
//						{
//							stringBuffer.append(strLine+"\n");
//						}
//
//					}
//					
//					vecPartData.add(stringBuffer.toString());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	
//			// 关闭文件
//			try {
//				// outputStream.close();
//				if(reader!=null){
//					reader.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}else{
//			try {
//				if(file.exists()){
//					reader = new BufferedReader(new FileReader(file));
//		
//					String strLine = "";
//					StringBuffer stringBuffer = new StringBuffer();
//					while ((strLine = reader.readLine()) != null) {
//						strLine = strLine.trim();
//						if (strLine.startsWith("--" + boundary)) {
//							if (save == false) {
//								save = true;
//								out = false;
//							} else {
//								save = false;
//								out = true;
//							}
//						}
//		
//						if (save) {
//							stringBuffer.append(strLine + "\n");
//							out = false;
//						}
//						
//						if (out) {
//							vecPartData.add(stringBuffer.toString());
//							stringBuffer.setLength(0);
//							out = false;
//							save = true;
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	
//			// 关闭文件
//			try {
//				// outputStream.close();
//				if(reader!=null){
//					reader.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return true;
//	}
//
//	public boolean AnalyMailFile() {
//		File file = new File(filename);
//
//		// 邮件包含内容的格式 multipart/mixed：表示含有附件
//		String contentType = "";
//
//		// 读文件
//		BufferedReader reader = null;
//		try {
//			if(file.exists()){
//				reader = new BufferedReader(new FileReader(file));
//				String lineStr = null;
//				while ((lineStr = reader.readLine()) != null) {
//					lineStr = lineStr.trim();
//					// 解析邮件文件，获取邮件信息
//					if (messageID.length() == 0 && lineStr.startsWith("Message-ID:")) {
//						messageID = lineStr.substring("Message-ID:".length());
//					}
//					if (date.length() == 0 && lineStr.startsWith("Date:")) {
//						date = lineStr.substring("Date:".length()).trim();
//					}
//					if (from.length() == 0 && lineStr.startsWith("From:")) {
//						from = lineStr.substring("From:".length());
//					}
//					if (to.length() == 0 && lineStr.startsWith("To:")) {
//						to=lineStr.substring("To:".length());
//						if(to.lastIndexOf(",") == to.length()-1)
//						{
//							while ((lineStr = reader.readLine()) != null) {
//								to+=lineStr.trim();
//								if(lineStr.lastIndexOf(",") != to.length()-1) break;
//							}
//						}
//					}
//					if (cc.length() == 0 && lineStr.startsWith("Cc:")) {
//						String tmp = lineStr.substring("Cc:".length());
//						while(tmp.indexOf("<") > 0){
//							cc += tmp.substring(tmp.indexOf("<") + 1, tmp.indexOf(">")) + "; ";
//							tmp = tmp.substring(tmp.indexOf(">") + 1);
//						}
//					}
//					if (bcc.length() == 0 && lineStr.startsWith("Bcc:")) {
//						String tmp = lineStr.substring("Bcc:".length());
//						while(tmp.indexOf("<") > 0){
//							bcc += tmp.substring(tmp.indexOf("<") + 1, tmp.indexOf(">"));
//							tmp = tmp.substring(tmp.indexOf(">") + 1);
//						}
//					}
//					if (subject.length() == 0 && lineStr.startsWith("Subject:")) {
//						lineStr = lineStr.trim().substring("Subject:".length()).trim();
//						String[] tmp = lineStr.substring(0, lineStr.length() - 1).split("\\?");
//						if(tmp!=null&&tmp.length>1){
//							if("B".equalsIgnoreCase(tmp[1])){ 
//								subject = new String(Base64.decodeBase64(tmp[2]), tmp[0]);
//							} else if("Q".equalsIgnoreCase(tmp[1])){
//								subject = new QuotedPrintableCodec().decode(tmp[2], tmp[0]);
//							} else {
//								subject = lineStr;
//							}
//						}else{
//							subject = lineStr;
//						}
//					}
//					
//					if (lineStr.startsWith("boundary")) {
//	                    part.add("--" + lineStr.substring(lineStr.indexOf("\"") + 1, lineStr.lastIndexOf("\"")));
//	                }
//					
//	                // 获取邮件包含的内容类型，是否含有附件
//	                if (contentType.length() == 0 && lineStr.startsWith("Content-Type:")) {
//	                    contentType = lineStr.substring((new String("Content-Type:").length()));
//	                    contentType = contentType.trim();
//	
//	                    // 有的boundary="----=_Part_0_2322466.1367473957029" 分隔符号是接在Content-Type:后面的  有的是单独一行
//	                    
//	                    if (contentType.startsWith("multipart/mixed")) {
//	                        // 邮件带有附件
//	                        hasAttachment = true;
//	                        // 获取分隔符号
//	                        if(lineStr.indexOf("boundary=") != -1)
//	                        {
//								int p1 = lineStr.indexOf("\"");
//								int p2 = lineStr.indexOf("\"", p1 + 1);
//								
//								if (p1 != -1 && p2 != -1)
//									boundary = lineStr.substring(p1 + 1, p2);
//	                        }
//	                    }
//	                    
//	                    
//	                    // 读取分隔符号
//	                    while ((lineStr = reader.readLine()) != null) {
//                            if (lineStr.indexOf("boundary=") != -1) {
//                                int p1 = lineStr.indexOf("\"");
//                                int p2 = lineStr.indexOf("\"", p1 + 1);
//
//                                if (p1 != -1 && p2 != -1) {
//                                    boundary = lineStr.substring(p1 + 1, p2);
//                                    break;
//                                }
//                            }
//                        }
//	                }
//				}
//			}
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		} finally {			
//			// 关闭文件
//			try {
//				if(reader!=null){
//					reader.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return true;
//	}
//
//}
