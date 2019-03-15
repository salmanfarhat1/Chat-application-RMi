import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelloClient{

   static JButton bPrivateChat = new JButton("Private Chat");
   static JButton bBroadCast = new JButton("Broadcast");
   static JButton blistOfClients = new JButton("list of clients");
   static JButton bViewBroadCast = new JButton("view Broadcast");
   static JButton bViewHistory = new JButton("view my history");
   static JButton bsendPrivate = new JButton("Send");
   static JButton bsendBroad = new JButton("Send");
   static JButton bBack = new JButton("back");

   static JLabel label=new  JLabel("Enter Destination Name ");
   static JLabel labelmsg=new  JLabel("Enter Message ");
   static JLabel labelmsgbroad=new  JLabel("Enter Message ");
   static JLabel clientListLabel=new  JLabel("");
   static JLabel MyHistoryLabel=new  JLabel("");

   static JTextField destNameText = new JTextField();
   static JTextField msgTest = new JTextField();
   static JTextField msgTestBroad = new JTextField();

   static JFrame frame;
   static JFrame secondFrame;
   static JFrame broadFrame;
   static JFrame listClientFrame;
   static JFrame historyFrame;

   static String username;

  public static void main(String [] args) {

	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host>");
	   return;}

	String host = args[0];
  String name= args[1];

  //String email = arg[2];


	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host);



  Registry_itf r = (Registry_itf) registry.lookup("RegisterService");

  Communication_itf c = (Communication_itf) registry.lookup("CommunicationServer");

  Accounting_impl a = new Accounting_impl(name);
  Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(a, 0);


	// Remote method invocation
  System.out.println("Please Enter Username ");

  Scanner sc = new Scanner(System.in);

  int usernameExist = 1;  // to get a unique user name
   username = "";
  while(usernameExist == 1){ // Make Do while
      username = sc.next();
      usernameExist = r.checkUserNameifExist(username);
      if(usernameExist == 1)
        System.out.println("Please Enter a valid Username ");
  }

  a.setUserName(username);
  r.register(a_stub);
   frame = new JFrame();
  frame.setVisible(true);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //frame.setSize(500,500);
  frame.setBounds(0,0,1100,500);
  //frame.setResizable(false);
  frame.setLayout(null);

  /*JLabel label=new  JLabel("Select what you want");
  label.setBounds(0, 0, 200, 30);
  frame.getContentPane().add(label);

  JTextField myText = new JTextField();
   myText.setBounds(0, 40, 200, 70);
   frame.getContentPane().add(myText);*/


  //JButton bPrivateChat = new JButton("Private Chat");
  bPrivateChat.setBounds(10, 10, 200, 30);
  frame.getContentPane().add(bPrivateChat);

  bPrivateChat.addActionListener(new ActionListener()
{
  public void actionPerformed(ActionEvent e)
  {

    frame.setVisible(false);
    secondFrame = new JFrame("Second Frame");

    secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setSize(500,500);
    secondFrame.setBounds(0,0,1100,500);
    //frame.setResizable(false);
    secondFrame.setLayout(null);


    label.setBounds(50, 50, 200, 30);
    secondFrame.getContentPane().add(label);


    destNameText.setBounds(50, 90, 200, 30);
    secondFrame.getContentPane().add(destNameText);


    labelmsg.setBounds(50, 130, 200, 30);
    secondFrame.getContentPane().add(labelmsg);

    msgTest.setBounds(50, 180, 200, 30);
    secondFrame.getContentPane().add(msgTest);




    bsendPrivate.setBounds(50, 220, 100, 30);
    secondFrame.getContentPane().add(bsendPrivate);

    bsendPrivate.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // check if boxes are empty
        String destf="default" ;
        String msgtodest ="default";
        destf = destNameText.getText();
        msgtodest = msgTest.getText();
        try {
          c.chatTo(username,destf,msgtodest);
        }catch (Exception e1)  {
      		System.err.println("Error on client: " + e1);
      	}
      }
    });

    bBack.setBounds(990, 440, 100, 30);
    secondFrame.getContentPane().add(bBack);

    bBack.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        destNameText.setText("");
        msgTest.setText("");
        secondFrame.setVisible(false);
        frame.setVisible(true);

      }
    });
    secondFrame.setVisible(true);

  }
});

  //JButton bBroadCast = new JButton("Broadcast");
  bBroadCast.setBounds(220, 10, 200, 30);
  frame.getContentPane().add(bBroadCast);

  bBroadCast.addActionListener(new ActionListener()
{
  public void actionPerformed(ActionEvent e)
  {

    frame.setVisible(false);
    broadFrame = new JFrame("broadcast Frame");

    broadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setSize(500,500);
    broadFrame.setBounds(0,0,1100,500);
    //frame.setResizable(false);
    broadFrame.setLayout(null);

    labelmsgbroad.setBounds(50, 130, 200, 30);
    broadFrame.getContentPane().add(labelmsgbroad);

    msgTestBroad.setBounds(50, 180, 200, 30);
    broadFrame.getContentPane().add(msgTestBroad);




    bsendBroad.setBounds(50, 220, 100, 30);
    broadFrame.getContentPane().add(bsendBroad);

    bsendBroad.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // check if boxes are empt
        //String destf="default" ;
        String msgtodestBroad ="default";

        msgtodestBroad = msgTestBroad.getText();
        try {
          c.broadcastTo(username,msgtodestBroad);
        }catch (Exception e1)  {
          System.err.println("Error on client: " + e1);
        }
      }
    });

    bBack.setBounds(990, 440, 100, 30);
    broadFrame.getContentPane().add(bBack);

    bBack.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {

        msgTestBroad.setText("");
        broadFrame.setVisible(false);
        frame.setVisible(true);

      }
    });
    broadFrame.setVisible(true);

  }
});

  //JButton blistOfClients = new JButton("list of clients");
  blistOfClients.setBounds(430, 10, 200, 30);
  frame.getContentPane().add(blistOfClients);

  blistOfClients.addActionListener(new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      frame.setVisible(false);

      listClientFrame = new JFrame("list client Frame");

      listClientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //frame.setSize(500,500);
      listClientFrame.setBounds(0,0,1100,500);
      //frame.setResizable(false);
      listClientFrame.setLayout(null);

      clientListLabel.setBounds(50, 130, 200, 30);
      listClientFrame.getContentPane().add(clientListLabel);

      String Slist="";
      try {
         Slist = c.listofCLients(username);
      }catch (Exception e1)  {
        System.err.println("Error on client: " + e1);
      }
      clientListLabel.setText(Slist);
      Slist="";
      bBack.setBounds(990, 440, 100, 30);
      listClientFrame.getContentPane().add(bBack);

      bBack.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          clientListLabel.setText("");
          listClientFrame.setVisible(false);
          frame.setVisible(true);
        }
      });
      listClientFrame.setVisible(true);
    }
  });

  //JButton bViewBroadCast = new JButton("view Broadcast");
  bViewBroadCast.setBounds(640, 10, 200, 30);
  frame.getContentPane().add(bViewBroadCast);



  //JButton bViewHistory = new JButton("view my history");
  bViewHistory.setBounds(850, 10, 200, 30);
  frame.getContentPane().add(bViewHistory);

  bViewHistory.addActionListener(new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      frame.setVisible(false);

      historyFrame = new JFrame("My History");

      historyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //frame.setSize(500,500);
      historyFrame.setBounds(0,0,1100,1100);
      //frame.setResizable(false);
      historyFrame.setLayout(null);

      MyHistoryLabel.setBounds(20, 20, 1000, 1000);
      historyFrame.getContentPane().add(MyHistoryLabel);

      String Slist1="";
      try {
         Slist1 = c.myHistoryMessages(username);
      }catch (Exception e1)  {
        System.err.println("Error on client: " + e1);
      }
      MyHistoryLabel.setText(Slist1);
      Slist1="";
      bBack.setBounds(1000, 1020, 100, 30);
      historyFrame.getContentPane().add(bBack);

      bBack.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          clientListLabel.setText("");
          historyFrame.setVisible(false);
          frame.setVisible(true);
        }
      });
      historyFrame.setVisible(true);
    }
  });




  int choice=1;
  String dest="" , msg="";
  while(true){
      System.out.println("1- Send message to specific person\n2- BroadCast to all Clients\n3- List of Clients\n4- Get Broadcast History\n5- all History \n6- Exit");
      choice = sc.nextInt();
      if(choice == 1){

        System.out.println("Enter Destination name (username)");
        dest = sc.next();
        System.out.println("Dest : "+dest.trim());

        System.out.println("Enter a  Message");
        sc.nextLine();
        msg = sc.nextLine();
        c.chatTo(username,dest,msg);

      }
      else if(choice == 2){
        System.out.println("Enter a  Message");
        sc.nextLine();
        msg = sc.nextLine();
        c.broadcastTo(username,msg);
      }else if(choice == 3){
        c.listofCLients(username);
      }
      else if(choice == 4){
        c.getHistory_broadcast(username);
      }
      else if(choice == 5){
        c.myHistoryMessages(username);
      }
      else if(choice == 6)
        break;
      else{
        System.out.println("Choice is : "+choice);
      }
  }

  sc.close();
	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}
