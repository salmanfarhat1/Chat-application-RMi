import java.rmi.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Registry_impl implements Registry_itf{
  ConcurrentHashMap<Accounting_itf, Integer> ClientsMap ;

  public Registry_impl(){
    ClientsMap = new ConcurrentHashMap<Accounting_itf, Integer>();
  }

  public void update_number_of_calls(Accounting_itf client) throws RemoteException{
    int calls = ClientsMap.get(client);
    calls++;
    ClientsMap.remove(client);
    ClientsMap.put(client , calls);
    client.numberOfCalls(calls);
  }

  public int checkClientIfExist(Accounting_itf client) throws RemoteException{
    int exist = 0;
    for (Accounting_itf i : ClientsMap.keySet()) {
      if(client.equals(i)){
        exist = 1;
        //System.out.println("**********Already have this object***************");
      }
    }
    return exist;
	}
  public int checkUserNameifExist(String username) throws RemoteException{
    int exist = 0;
    for (Accounting_itf i : ClientsMap.keySet()) {
      if(i.getUserName().equals(username)){
        return 1;
        //System.out.println("**********Already have this object***************");
      }
    }
    return 0;
  }

  public Accounting_itf getObjectFromUsername(String username)throws RemoteException{
    for (Accounting_itf i : ClientsMap.keySet()) {
      if(i.getUserName().equals(username)){
        return i;
      }
    }
    return null;
  }
public Accounting_itf[] getAllExistingObjects()throws RemoteException{// it returns all the clients in the hashmap

   return ClientsMap.keySet().toArray(new  Accounting_itf[0]);
}

  public void register(Accounting_itf client) throws RemoteException{
    int isExist =0 ;
    for (Accounting_itf i : ClientsMap.keySet()) {
      if(client.equals(i)){
        isExist = 1;
        System.out.println("**********Already have this object***************");
      }
    }
    if(isExist == 0){
      ClientsMap.put(client , 0);
      System.out.println("************Added*****************");

    }

  }
  public void unregister(Accounting_itf client) throws RemoteException{
    ClientsMap.remove(client);
  }
}
