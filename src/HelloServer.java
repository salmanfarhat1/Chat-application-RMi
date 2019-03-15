import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.*;

public class HelloServer {

  public static void  main(String [] args) {
	  try {
		  // Create a Hello remote object


      
      // Create a new file Delete if exist

      Registry_impl r = new Registry_impl();
      Registry_itf r_stub = (Registry_itf) UnicastRemoteObject.exportObject(r, 0);

      Communication_impl c = new Communication_impl((Registry_itf)r);
      Communication_itf c_stub = (Communication_itf) UnicastRemoteObject.exportObject(c, 0);

	    Registry registry= LocateRegistry.getRegistry();
      //System.setProperty("java.rmi.server.hostname","192.168.1.2");
      registry.bind("RegisterService", r_stub);
      registry.bind("CommunicationServer", c_stub);

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
