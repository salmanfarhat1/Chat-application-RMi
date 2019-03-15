import java.rmi.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;

public class Communication_impl implements Communication_itf{

  private Registry_itf Reg;
  private HashMap<String, ArrayList<String>> msgsHistory;
  //private ArrayList<String> msg;

  public Communication_impl(Registry_itf Reg){
    this.Reg = Reg;
    msgsHistory =new HashMap<String, ArrayList<String> >();

    msgsHistory.put("broadcast" , new ArrayList<String>());
    File fout = new File("data.txt");
    String[] parts;
    ArrayList<String> list,list2;
    if (fout.exists()) {
      try{
        list = readDataToList();
        for(int i = 0 ; i < list.size() ; i++){
          parts = list.get(i).split(";");
          if(parts[3].equals("broadcast")){
            list2 = msgsHistory.get("broadcast");
            //System.out.println("-------------------------------->"+list.get(i));
            list2.add(list.get(i));
          }
          else if(parts[3].equals("direct")){ // direct Messages
            list2 = new ArrayList<String>();
            list2.add(list.get(i));
            msgsHistory.put(parts[1], list2);
          }
        }

      }
      catch(Exception e){
        System.out.print(e.getMessage());
      }
    }

  }

  public static void SaveMessage(String msg) throws IOException {
  	File fout = new File("data.txt");
  	FileOutputStream fos = new FileOutputStream(fout , true);
  	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

    bw.write(msg);
  	bw.newLine();

  	bw.close();
  }
  public static ArrayList<String> readDataToList() throws IOException{
    ArrayList<String> msgs = new ArrayList<>();
    File fout = new File("data.txt");
    FileInputStream fstream = new FileInputStream(fout );
    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

    String strLine;

    while ((strLine = br.readLine()) != null){
      msgs.add(strLine.trim());
    }

    fstream.close();
    return msgs;
  }

  public void chatTo(String srcUsername , String destUsername , String msg) throws RemoteException{
    Accounting_itf dest,src;
    ArrayList<String> list ;
    int UsernameExist = Reg.checkUserNameifExist(destUsername);
    if(UsernameExist == 1){
      dest = Reg.getObjectFromUsername(destUsername);
      dest.ServerMessages(msg+";"+srcUsername+";"+destUsername+";direct");
      //this.msg.add(msg+";srcUsername;destUsername;direct");
      if(msgsHistory.containsKey(srcUsername)){
        list = msgsHistory.get(srcUsername);
        list.add(msg+";"+srcUsername+";"+destUsername+";direct");
      }
      else {
          list = new ArrayList<String>();
          list.add(msg+";"+srcUsername+";"+destUsername+";direct");
          msgsHistory.put(srcUsername, list);
      }
    }
    try{
      SaveMessage(msg+";"+srcUsername+";"+destUsername+";direct");
    }
    catch(Exception e){
      System.out.print(e.getMessage());
    }

  }

  public void broadcastTo(String srcUsername , String msg) throws RemoteException{
    Accounting_itf client_list[] ;
    ArrayList<String> list;
    client_list = Reg.getAllExistingObjects();
    for(int i =0; i<client_list.length; i++){
      client_list[i].ServerMessages(msg+" Src is : "+srcUsername);
    }
    list = msgsHistory.get("broadcast");
    list.add(msg+";"+srcUsername+";to_all;broadcast");
    try{
      SaveMessage(msg+";"+srcUsername+";to_all;broadcast");
    }
    catch(Exception e){
      System.out.print(e.getMessage());
    }
    /*if(msgsHistory.containsKey(srcUsername)){
      // if the key has already been used,
      // we'll just grab the array list and add the value to it
      list = msgsHistory.get(srcUsername);
      list.add(msg);
    }
    else {
        // if the key hasn't been used yet,
        // we'll create a new ArrayList<String> object, add the value
        // and put it in the array list with the new key
        list = new ArrayList<String>();
        list.add(msg);
        msgsHistory.put(srcUsername, list);
    }*/

  }

  public void getHistory_broadcast(String srcUsername)throws RemoteException{
    Accounting_itf src;
    src = Reg.getObjectFromUsername(srcUsername);
    ArrayList<String> list;
    String S="";
    String[] parts;
    /*for(Map.Entry m:msgsHistory.entrySet()){
      src.ServerMessages("username : "+m.getKey()+" sent : "+m.getValue());
    }*/
    list = msgsHistory.get("broadcast");
    for(int i = 0; i < list.size() ; i++){
      parts = list.get(i).split(";");
      S= S + (i+1) + "- " + parts[0] +" Src: "+parts[1] + " Dest "+parts[2] +" Type: "+parts[3]+ "\n";
    }
    src.ServerMessages(S);

  }

  public String myHistoryMessages(String username) throws RemoteException{
    Accounting_itf user;
    user = Reg.getObjectFromUsername(username);
    String SFromU = "" ;
    String SToU="" ;
    String[] parts;
    ArrayList<String> Msgs;
    // Messages send by him
    /*if(msgsHistory.containsKey(username)){
      myMsgs = msgsHistory.get(username);
      if( myMsgs.size() == 0){
        S = "No messages Send by you";
      }
      for(int i =0 ; i < myMsgs.size() ; i++)
      {
        parts = myMsgs.get(i).split(";");
        S = S + (i+1) + "- " + parts[0] +" Src: "+parts[1] + " Dest "+parts[2] +" Type: "+parts[3]+ "\n";
      }
    }
    user.ServerMessages(S);*/
    for (String i : msgsHistory.keySet()) {
      Msgs = msgsHistory.get(i);
      for(int j = 0 ; j < Msgs.size() ; j++ ){
        parts = Msgs.get(j).split(";");
        if(parts[1].equals(username)){
          SFromU = SFromU + (i+1) + "- " + parts[0] +" Src: "+parts[1] + " Dest "+parts[2] +" Type: "+parts[3]+ "\n";
        }
        else if(parts[2].equals(username)){
          SToU = SToU + (i+1) + "- " + parts[0] +" Src: "+parts[1] + " Dest "+parts[2] +" Type: "+parts[3]+ "\n";
        }
      }
    }
    String STotal = "You Send :\n" + SFromU+"\n" + "You have Received :\n" + SToU+"\n";
    user.ServerMessages("You Send :\n");
    user.ServerMessages(SFromU+"\n");
    user.ServerMessages("You have Received :\n");
    user.ServerMessages(SToU+"\n");
    return STotal;
  }

  public String listofCLients(String username) throws RemoteException{
    Accounting_itf client_list[] , user;
    user = Reg.getObjectFromUsername(username);
    client_list = Reg.getAllExistingObjects();
    String S = "";
    String[] parts;
    for(int i =0; i<client_list.length; i++){
      //for(int k =0; k<client_list.length; k++){
      //  client_list[k].ServerMessages(client_list[i].getUserName()+";list");
         S += client_list[i].getUserName()+" - ";
    //  }
    }
    user.ServerMessages(S);

    return S;
  }
}
