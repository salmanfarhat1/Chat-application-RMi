import java.rmi.*;

public class Accounting_impl implements Accounting_itf{
  private String Username;
  private String Name;
  private int number_of_calls;
  public Accounting_impl(String Name ){
    this.Username = "";
    this.Name = Name;
    this.number_of_calls = 0;
  }

  public void numberOfCalls(int number) throws RemoteException{
    this.number_of_calls = number;
    if(number >= 5 ){
      System.out.println("Hey Hey, you reach "+number+" calls ");
    }
    else{
      //System.out.println("Fine");
    }
  }

  public void ServerMessages(String msg) throws RemoteException{
    String[] parts = msg.split(";");
    System.out.println(parts[0]);
    
  }

  public void setUserName(String username) throws RemoteException{
    //System.out.println("YOUR ************************************** : " + username);
    this.Username = username;
  }
  public String getUserName() throws RemoteException{
    return Username;
  }

  public String getName() throws RemoteException{
    return this.Name;
  }
}
