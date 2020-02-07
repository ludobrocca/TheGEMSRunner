import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class ServerXMLParser {

    private HashMap servers =  new HashMap();
    private File xml;
    private Document doc;

    public ServerXMLParser() {  
        try {
            xml = new File("TheGEMSRunner/servers/servers.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            // DEBUG VALUE
            this.printSelectedServers("ROMA", "A22", "abcderv", "fggggfsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public Element printSelectedServers(String server, String number, String user, String password) {

      Element baseServer = this.getServer(server, number);
      
      // NEED TO BUILD DOC FROM SCRATCH?
      
      Element emsServer = baseServer;
      emsServer.setAttribute("password", password);
      emsServer.setAttribute("user", user);
      //System.out.println(emsServer.getNodeValue());
      return emsServer;
    }


    private Element getServer(String server, String number) {
      int indexFound= -1;
      NodeList servers = doc.getElementsByTagName("ConnectionNode");
      String finder = new String("EMS-" + server + "-" + number);
      System.out.println(finder);
      for (int temp = 0; temp < servers.getLength(); temp++) {
        Node nNode = servers.item(temp);
        Element eElement = (Element) nNode;
        String attr = eElement.getAttribute("alias");
       
       if(attr.contains(finder)) {
        System.out.println("HERE");
        indexFound = temp;
       }
      }

      Element el = (Element) servers.item(indexFound);
      return el;
    }
  

    public ArrayList<String> getConnectionNodes() {
      ArrayList<String> nodes = new ArrayList<String>();
      NodeList servers = doc.getElementsByTagName("ConnectionNode");
      for (int temp = 0; temp < servers.getLength(); temp++) {
        Node nNode = servers.item(temp);
        Element eElement = (Element) nNode;
        String attr = eElement.getAttribute("alias");
        nodes.add(attr);
      }
      return nodes;
    }


    /*public static void main(String[] args) {
        ServerXMLParser parser= new ServerXMLParser();
    }*/
}

/*

public class ReadXMLFile {

  public static void main(String argv[]) {
    try {
    File fXmlFile = new File("/Users/mkyong/staff.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    doc.getDocumentElement().normalize();

    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("staff");
    System.out.println("----------------------------");

    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            System.out.println("Staff id : "
                               + eElement.getAttribute("id"));
            System.out.println("First Name : "
                               + eElement.getElementsByTagName("firstname")
                                 .item(0).getTextContent());
            System.out.println("Last Name : "
                               + eElement.getElementsByTagName("lastname")
                                 .item(0).getTextContent());
            System.out.println("Nick Name : "
                               + eElement.getElementsByTagName("nickname")
                                 .item(0).getTextContent());
            System.out.println("Salary : "
                               + eElement.getElementsByTagName("salary")
                                 .item(0).getTextContent());
        }
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
  }
}

*/