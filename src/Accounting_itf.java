import java.rmi.*;
public interface Accounting_itf extends Remote {
  public void numberOfCalls(int number) throws RemoteException;
  public String getName() throws RemoteException;
  public void ServerMessages(String msg) throws RemoteException;
  public void setUserName(String username) throws RemoteException;
  public String getUserName() throws RemoteException;

}
