//package util.xml;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import util.Para;
//import util.encry.Blowfish;
// 
///**
// * 对XML文件进行读写操作,均为静态函数 <p>
// **/
//public class XmlOper{
//	
//    //将XML内容转换成List
//    public static List<Email> getXml() throws Exception
//    {
//    	Para para = new Para();
//		String root = para.getPara("adm_addr","file.properties","/xml/",false);
//		String path = root + "email.xml";
//    	
//    	List<Email>  list = new ArrayList<Email>();
//
//		Blowfish crypt = new Blowfish();
//		
//    	XmlBuilder xBuilder = new XmlBuilder(path);
//    	Document doc = xBuilder.getDoc();
//    	NodeList nodeList = doc.getElementsByTagName("email");
//        if(nodeList != null){
//        	for (int j=0; j<nodeList.getLength(); j++){
//		        Email e = new Email();
//		        e.setId(j);
//                e.setNum(j+1);
//                e.setSmtp(doc.getElementsByTagName("smtp").item(j).getFirstChild().getNodeValue());
//                e.setUsername(doc.getElementsByTagName("username").item(j).getFirstChild().getNodeValue());
//                e.setPassword(crypt.decryptString(doc.getElementsByTagName("password").item(j).getFirstChild().getNodeValue()));
//                e.setCharset(doc.getElementsByTagName("charset").item(j).getFirstChild().getNodeValue());
//                e.setMail(doc.getElementsByTagName("mail").item(j).getFirstChild().getNodeValue());
//                e.setName(doc.getElementsByTagName("name").item(j).getFirstChild().getNodeValue());
//		        list.add(e);
//        	}
//        }
//    	return list;
//    }
//    
//    //添加email信息
//    public static void addNode(Email email)
//    {
//    	try{ 
//	    	Para para = new Para();
//			String root = para.getPara("adm_addr","file.properties","/xml/",false);
//			String path = root + "email.xml";
//	    	
//	    	XmlBuilder xBuilder = new XmlBuilder(path);
//	    	Document doc = xBuilder.getDoc();
//	    	NodeList nodeList = doc.getElementsByTagName("emails");
//
//			Blowfish crypt = new Blowfish();
//			
//	    	//创建新的节点 
//	        Node emailNode = doc.createElement("email"); 
//	        Node smtpNode = doc.createElement("smtp"); 
//	        smtpNode.appendChild(doc.createTextNode(email.getSmtp())); 
//	        Node usernameNode = doc.createElement("username"); 
//	        usernameNode.appendChild(doc.createTextNode(email.getUsername())); 
//	        Node passwordNode = doc.createElement("password"); 
//	        passwordNode.appendChild(doc.createTextNode(crypt.encryptString(email.getPassword()))); 
//	        Node charsetNode = doc.createElement("charset"); 
//	        charsetNode.appendChild(doc.createTextNode(email.getCharset())); 
//	        Node mailNode = doc.createElement("mail"); 
//	        mailNode.appendChild(doc.createTextNode(email.getMail())); 
//	        Node nameNode = doc.createElement("name"); 
//	        nameNode.appendChild(doc.createTextNode(email.getName())); 
//	        
//	        // 添加节点 
//	        emailNode.appendChild(smtpNode); 
//	        emailNode.appendChild(usernameNode); 
//	        emailNode.appendChild(passwordNode); 
//	        emailNode.appendChild(charsetNode); 
//	        emailNode.appendChild(mailNode); 
//	        emailNode.appendChild(nameNode); 
//	        nodeList.item(0).appendChild(emailNode); 
//	        
//	        // 此时真正的处理将新数据添加到文件中（磁盘） 
//	    	modifyFile(doc, path); 
//	        
//    	}catch(Exception e){ 
//    		e.printStackTrace(); 
//    	}
//    }
//    
//    /** 
//     * 删除一个节点 
//     * @param name 
//     */ 
//    public static void deleteNode(String[] ids) throws Exception
//    { 
//    	Para para = new Para();
//		String root = para.getPara("adm_addr","file.properties","/xml/",false);
//		String path = root + "email.xml";
//    	
//    	XmlBuilder xBuilder = new XmlBuilder(path);
//    	Document doc = xBuilder.getDoc();
//    	NodeList nodeList = doc.getElementsByTagName("smtp");
//    	
//    	for(int i=ids.length-1; i>=0; i--){
//    		doc.getFirstChild().removeChild(nodeList.item(Integer.parseInt(ids[i])).getParentNode()); 
//    	}
//    	modifyFile(doc, path); 
//	} 
//    
//    /** 
//     * 将改动持久到文件 
//     * @param doc 
//     * @param distFileName 
//     */ 
//    public static void modifyFile(Document doc,String distFileName)
//    { 
//    	try{ 
//    		TransformerFactory tf = TransformerFactory.newInstance(); 
//    		Transformer tfer = tf.newTransformer(); 
//    		DOMSource dsource = new DOMSource(doc); 
//    		StreamResult sr = new StreamResult(new File(distFileName)); 
//    		tfer.transform(dsource, sr); 
//    	}catch(Exception e){ 
//    		e.printStackTrace(); 
//    	}
//	} 
//    
//    /** 
//     * 根据name修改某个节点的内容 
//     * @param name 
//     */ 
//	public static void updateNode(Email email) throws Exception
//	{ 
//		Para para = new Para();
//		String root = para.getPara("adm_addr","file.properties","/xml/",false);
//		String path = root + "email.xml";
//
//		Blowfish crypt = new Blowfish();
//		
//    	XmlBuilder xBuilder = new XmlBuilder(path);
//    	Document doc = xBuilder.getDoc();
//    	
//    	doc.getElementsByTagName("smtp").item(email.getId()).getFirstChild().setNodeValue(email.getSmtp());
//		doc.getElementsByTagName("username").item(email.getId()).getFirstChild().setNodeValue(email.getUsername());
//		doc.getElementsByTagName("password").item(email.getId()).getFirstChild().setNodeValue(crypt.encryptString(email.getPassword()));
//		doc.getElementsByTagName("charset").item(email.getId()).getFirstChild().setNodeValue(email.getCharset());
//		doc.getElementsByTagName("mail").item(email.getId()).getFirstChild().setNodeValue(email.getMail());
//		doc.getElementsByTagName("name").item(email.getId()).getFirstChild().setNodeValue(email.getName());
//    	
//		modifyFile(doc,path); 
//	} 
//
//    /** 
//     * 根据节点位置查询节点内容 
//     * @param name 
//     */ 
//	public static Email findNode(int n) throws Exception
//	{ 
//		Para para = new Para();
//		String root = para.getPara("adm_addr","file.properties","/xml/",false);
//		String path = root + "email.xml";
//    	
//		Blowfish crypt = new Blowfish();
//		
//    	XmlBuilder xBuilder = new XmlBuilder(path);
//    	Document doc = xBuilder.getDoc();
//		Email email = new Email();
//		email.setId(n);
//		email.setSmtp(doc.getElementsByTagName("smtp").item(n).getFirstChild().getNodeValue());
//		email.setUsername(doc.getElementsByTagName("username").item(n).getFirstChild().getNodeValue());
//		email.setPassword(crypt.decryptString(doc.getElementsByTagName("password").item(n).getFirstChild().getNodeValue()));
//		email.setCharset(doc.getElementsByTagName("charset").item(n).getFirstChild().getNodeValue());
//		email.setMail(doc.getElementsByTagName("mail").item(n).getFirstChild().getNodeValue());
//		email.setName(doc.getElementsByTagName("name").item(n).getFirstChild().getNodeValue());
//		return email;
//	} 
//
//    /** 
//     * 根据节点name和节点value判断此节点是否存在 
//     * @param name 
//     */ 
//	public static boolean findMatch(String node, String name) throws Exception
//	{ 
//		Para para = new Para();
//		String root = para.getPara("adm_addr","file.properties","/xml/",false);
//		String path = root + "email.xml";
//    	
//    	XmlBuilder xBuilder = new XmlBuilder(path);
//    	Document doc = xBuilder.getDoc();
//    	NodeList nodeList = doc.getElementsByTagName(node);
//    	
//    	boolean isb = true;
//    	for(int i=0; i<nodeList.getLength(); i++){ 
//    		String value = nodeList.item(i).getFirstChild().getNodeValue(); 
//    	    if(name != null && name.equals(value)){ 
//    	    	isb = false;
//    	    	break;
//    	    }
//    	}
//    	return isb;
//	} 
//
//}