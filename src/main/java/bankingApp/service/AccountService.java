package bankingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bankingApp.db.Account;
import bankingApp.db.DatabaseInf;

@Service
public class AccountService {
    
    @Autowired
    @Qualifier("hibernateConnImpl")
    DatabaseInf db;
    
    public void createAccNo(String email) throws Exception {
        db.insertAccountInfo(email);
    }
    
    public Account getAccNo(String email) throws Exception {
       return db.getAccountInfo(email);
    }
    
    public Account getAccount(long accountNumber) {
        return db.getAccountInfo(accountNumber);
    }
    
    public void updateAccount(Account a) throws Exception {
        db.updateAccountInfo(a);
    }
}
