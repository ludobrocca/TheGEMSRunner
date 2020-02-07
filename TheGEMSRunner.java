import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class TheGEMSRunner {

   private JFrame frame;
   private JPanel credentials;
   private JPanel servers;
   private JPanel container;
   private JPanel serversContainer;
   private JPanel logPanel;
   private JLabel username;
   private JTextField tUsername;
   private JLabel password;
   private JPasswordField tPassword;
   private JLabel menuEMS;
   private JComboBox lMenuEMS;
   private JButton submit;
   private JTextArea output;
   private ServerXMLParser parser;

   
   public TheGEMSRunner(){
      this.parser= new ServerXMLParser();
      loadGUI();

      }
    public void loadGUI() {
      frame=new JFrame("GEMS Runner");
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setSize(600,340);
      frame.setResizable(false);
      frame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
         System.exit(0);
      }
      });
      credentials = new JPanel();
      servers = new JPanel();
      container = new JPanel();
      logPanel = new JPanel();
      serversContainer = new JPanel();
      username = new JLabel("Username");
      tUsername = new JTextField(20);
      password =new JLabel("One-Time Password");
      tPassword=new JPasswordField(20);
      username = new JLabel("Username");
      tUsername = new JTextField(20);
      credentials.setLayout(new GridLayout(3,1));
      credentials.add(username);
      credentials.add(tUsername);
      credentials.add(password);
      credentials.add(tPassword);
      menuEMS = new JLabel("Server");
      lMenuEMS = new JComboBox();
      output= new JTextArea(10,50);
      logPanel.add(output);
      output.setEnabled(false);

      Font font = new Font("Courier", Font.PLAIN, 12);
      output.setFont(font);
      output.setBackground(Color.BLACK);
      output.setForeground(Color.WHITE);
      output.append(new String("Prova "));

      this.addConnectionsNode();
   

      submit=new JButton("Submit");
      submit.addActionListener(x -> {
        try {
         this.runGemsOnSubmit();
        } catch (IOException e) {
           e.printStackTrace();
        }
      });
        
      // must be changed later
      credentials.add(menuEMS);
      credentials.add(lMenuEMS);
      servers.add(submit);
      container.add(credentials);
      serversContainer.add(servers);
      frame.add(container,BorderLayout.NORTH);
      frame.add(serversContainer,BorderLayout.CENTER);
      frame.add(logPanel, BorderLayout.SOUTH); 
      frame.setVisible(true);

    }

    public void runGemsOnSubmit() throws IOException {
      // https://stackoverflow.com/questions/3275141/running-a-bat-cmd-file-from-java
      Process p = null; 
         try {
            Runtime run = Runtime.getRuntime(); 
            String cmd = new String("rungems.cmd"); 
            p = run.exec(cmd);
            p.waitFor();
            System.out.println(p.exitValue());
            InputStream inputStream = p.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
            String line;
            while((line = bufferedReader.readLine()) != null) {
               System.out.println(line);
            }
            output.append("\n text");

         } catch (Exception e) {
            e.printStackTrace();  
            System.out.println("ERROR.RUNNING.CMD");
            p.destroy();

         } finally {
            p.destroy();
         }

    }

   public static void main(String[] args) {
        
      try {
         final String PIN = TheGEMSRunner.getBasePIN();
      } catch (IOException e) {
         e.printStackTrace();
         System.out.println("Input/Output  error: cannot resolve, exiting..");
         System.exit(0);
      }
        
      TheGEMSRunner gems = new TheGEMSRunner();
      // gems.runGUI();
  
   }

   private void addConnectionsNode() {
      ArrayList<String> nodes = this.parser.getConnectionNodes();  
      nodes.forEach(x -> lMenuEMS.addItem(x));
   }

    public static String getBasePIN() throws FileNotFoundException, IOException {
      File file = new File("TheGEMSRunner/basePIN.txt"); 
      BufferedReader br = new BufferedReader(new FileReader(file)); 
      String st= new String("");
      st = br.readLine();
      br.close();
      return st;
    }
    
}
