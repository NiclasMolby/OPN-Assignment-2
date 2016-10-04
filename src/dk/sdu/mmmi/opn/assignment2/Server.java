package dk.sdu.mmmi.opn.assignment2;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		
		try {
            CatalogImpl cata = new CatalogImpl();
            Registry r = LocateRegistry.createRegistry(Config.PORT);
            r.bind(Config.REMOTE_CATALOG_NAME, cata);
        }
        catch(RemoteException | AlreadyBoundException e){
            e.printStackTrace();
        }
        System.out.println("Server running with registry on port "+Config.PORT);


	}

}
