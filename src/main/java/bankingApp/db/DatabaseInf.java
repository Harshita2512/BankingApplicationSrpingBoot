package bankingApp.db;

import java.util.ArrayList;

public interface DatabaseInf {
	
	public void createCustomer (Customer c) throws Exception;
	
	public Customer getCusometrInfo (String email) ;
	
	public void insertAccountInfo (String email) throws Exception;
	
	public Account getAccountInfo (String email);
	
	public Account getAccountInfo (long accountNumber) ;
	
	public void insertActivities (long accountNumber, String  transactionType, double amount) throws Exception;
	
	public ArrayList<Activities> getAllActivites (long accountNumber);
	
	public void updateAccountInfo(Account acc) throws Exception;
	
	public void updateInformation (Customer customer) throws Exception;
	
	public ArrayList<Customer> getCustomers ();
}
