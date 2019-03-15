import java.rmi.*;
public interface Communication_itf extends Remote{
  public void chatTo(String destUsername , String srcUsername , String msg) throws RemoteException;
  public void broadcastTo(String srcUsername , String msg) throws RemoteException;
  public String listofCLients(String username) throws RemoteException;
  public void getHistory_broadcast(String srcUsername) throws RemoteException;
  public String myHistoryMessages(String username) throws RemoteException;
}
