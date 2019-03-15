import java.rmi.*;

public interface Registry_itf extends Remote {
  public void register(Accounting_itf client) throws RemoteException;
  public int checkClientIfExist(Accounting_itf client) throws RemoteException;
  public void update_number_of_calls(Accounting_itf client) throws RemoteException;
  public void unregister(Accounting_itf client) throws RemoteException;
  public int checkUserNameifExist(String name) throws RemoteException;
  public Accounting_itf getObjectFromUsername(String username)throws RemoteException;
  public Accounting_itf[] getAllExistingObjects()throws RemoteException;


}
