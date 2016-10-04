package dk.sdu.mmmi.opn.assignment2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 * Controller for the application
 * @author ups
 *
 */
public class ClientController {

	/**
	 * Singleton pattern instance
	 */
	private static ClientController instance;

	/**
	 * Singleton pattern access method
	 */
	public static synchronized ClientController get() {
		if(instance==null) instance = new ClientController();
		return instance; 
	}

	/**
	 * The area in which text can be displayed
	 */
	private JTextPane displayArea;
	
	/**
	 * The label in which the current status can be displayed
	 */
	private JLabel statusLabel;
	
	/**
	 * The catalogue being manipulated
	 */
	private ICatalog catalogue;
	
	/**
	 * Initialize, including connecting to a specific catalogue
	 */
	private ClientController() { 
		//catalogue = new CatalogImpl();
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", Config.PORT);
			catalogue = (ICatalog) registry.lookup(Config.REMOTE_CATALOG_NAME);
		} catch(RemoteException | NotBoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Increase button clicked in GUI
	 */
	public void increaseAction(String productName, String amountText) throws RemoteException {
		changeEntry(productName,amountText,true);
	}

	/**
	 * Decrease button clicked in GUI
	 */
	public void decreaseAction(String productName, String amountText) throws RemoteException {
		changeEntry(productName,amountText,false);
	}

	/**
	 * Helper: generic implementation of the increase/decrease action
	 */
	private void changeEntry(String productName, String amountText, boolean increase) throws RemoteException {
		int amount = Integer.parseInt(amountText);
		IEntry entry = catalogue.getEntry(productName);
		if(entry==null) {
			setStatus("No such entry: "+productName);
			return;
		}
		if(entry.updateQuantity(increase ? amount : -amount)) // Returns true if success
			updateDisplay();
		else
			setStatus("Negative stock not allowed");
	}

	/**
	 * Show the status to the user 
	 */
	private void setStatus(String status) {
		if(statusLabel==null) throw new Error("Internal error: unable to display status");
		statusLabel.setText(status);
	}

	/**
	 * Update display, showing the current entries and their total value
	 */
	private void updateDisplay() throws RemoteException {
		// Check that controller is properly initialized
		if(catalogue==null) throw new Error("Internal error: catalogue not set");
		if(displayArea==null) throw new Error("Internal error: display area not set");
		// Then do the display
		StringBuffer result = new StringBuffer();
		float value = 0;
		// For each item in the stock, add it to the description list and the total value
		for(String name: catalogue.getEntryNames()) {
			IEntry entry = catalogue.getEntry(name);
			Product product = entry.getProduct();
			result.append(product.getName()+"@"+product.getPrice()+": "+entry.getQuantity()+"\n");
			value += entry.getQuantity()*product.getPrice();
		}
		result.append("Total value: "+value);
		displayArea.setText(result.toString());
	}

	/**
	 * Search button clicked in GUI: use text area to show matching products
	 */
	public void searchAction(String prefix) throws RemoteException {
		StringBuffer result = new StringBuffer();
		for(Product product: catalogue.search(prefix)) {
			result.append(product.getName()+"\n");
		}
		displayArea.setText(result.toString());
	}

	/**
	 * Clear button clicked in GUI: go back to inventory display
	 */
	public void clearAction() throws RemoteException {
		updateDisplay();
	}
	
	/**
	 * Set the inventory display object
	 * (Note: high coupling, ideally use observer instead)
	 */
	public void setInventoryDisplay(JTextPane entriesDisplay) throws RemoteException {
		this.displayArea = entriesDisplay;
		updateDisplay();
	}
	
	/**
	 * Set the status display object
	 * (Note: high coupling, ideally use observer instead)
	 */
	public void setStatusArea(JLabel label) {
		this.statusLabel = label;
		setStatus("Ready");
	}

}
