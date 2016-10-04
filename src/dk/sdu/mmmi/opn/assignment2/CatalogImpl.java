package dk.sdu.mmmi.opn.assignment2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of a catalog
 * (part of the model layer of the application)
 * @author ups
 */
public class CatalogImpl extends UnicastRemoteObject implements ICatalog {

	/**
	 * Contents of the catalog
	 */
	private Map<String,IEntry> stock = new HashMap<String,IEntry>();
	
	/**
	 * Create and initialize the stock
	 */
	public CatalogImpl() throws RemoteException {
		initializeStock();
	}
	
	/**
	 * Lookup entry by name
	 */
	@Override
	public IEntry getEntry(String name) {
		return stock.get(name);
	}

	/**
	 * Search catalog and return all products that match the given prefix
	 * @throws RemoteException 
	 */
	@Override
	public List<Product> search(String pattern) throws RemoteException {
		ArrayList<Product> result = new ArrayList<Product>();
		for(Map.Entry<String, IEntry> entry: stock.entrySet())
			if(entry.getKey().startsWith(pattern)) result.add(entry.getValue().getProduct());
		return result;
	}

	/**
	 * Get all names of entries
	 */
	@Override
	public Set<String> getEntryNames() {
		return new HashSet<String>(stock.keySet());
	}

	/**
	 * Initialize stock with dummy products
	 * @throws RemoteException 
	 */
	private void initializeStock() throws RemoteException {
		Random random = new Random();
		for(int i=0; i<PRODUCTNAMES.length; i++)
			stock.put(PRODUCTNAMES[i],new EntryImpl(new Product(PRODUCTNAMES[i],random.nextInt(1000)/100.0f),random.nextInt(10)));
	}
	
	private static final String[] PRODUCTNAMES = { 
		"cucumber", "carrot", "potato", "pear", "apple", "zucchini", "beet", "onion", "tomato", "orange", "banana", "grapes", "sweet potato",
		"garlic", "lemon", "lime", "cabbage", "corn"
	};

}
