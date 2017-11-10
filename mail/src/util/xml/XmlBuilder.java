package util.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlBuilder {
    /* 全局变量 */
    private String path; // xml文件路径
    private Document doc; // xml文件对应的document
    private Element root; // xml文件的根结点
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * 构造函数说明：
     * <p>
     * 参数说明：@param path
     * <p>
     **/
    public XmlBuilder(String path) {
        this.path = path;
        init();
    }
    
    /**
     * 初始化函数
     * <p>
     **/
    public void init() {
        buildDocument();
        buildRoot();
    }

    /**
     * 将XML文件生成Document
     * <p>
     **/
    private void buildDocument() {
        // System.setProperty("javax.xml.parsers.DocumentBuilderFactory","org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            logger.debug("Construct document builder success.");
            doc = builder.parse(new File(path));
            // System.out.println("doc==="+doc);
            logger.debug("Build xml document success.");
        } catch (ParserConfigurationException e) {
            logger.error("Construct document builder error:" + e);
        } catch (SAXException e) {
            logger.error("Parse xml file error:" + e);
        } catch (IOException e) {
            logger.error("Read xml file error:" + e);
        }
    }

    /**
     * 生成XML的根结点
     * <p>
     **/
    private void buildRoot() {
        root = doc.getDocumentElement();
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }

}
