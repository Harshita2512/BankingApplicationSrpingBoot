package bankingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bankingApp.db.Customer;
import bankingApp.db.DatabaseInf;

@Service
public class CustomerService {
	
	@Autowired
	@Qualifier("hibernateConnImpl")
	DatabaseInf db;
	
	public Customer getCustomer(String email) {
		return db.getCusometrInfo(email);
	}
	
	public void createCustomer(Customer c) throws Exception {
	    db.createCustomer(c);
	}
	
	public void updateInfo(Customer customer) throws Exception {
	    db.updateInformation(customer);
	}
	
	

}
