package bankingApp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bankingApp.db.Activities;
import bankingApp.db.DatabaseInf;

@Service
public class ActivityService {

    @Autowired
    @Qualifier("hibernateConnImpl")
    DatabaseInf db;
    
    public ArrayList<Activities> listOfActivities(long accountNumber){
        
        return db.getAllActivites(accountNumber);
        
    }
    
    public void insertActivity (long accountNumber, String transactionType, float amount) throws Exception {
        db.insertActivities(accountNumber, transactionType, amount);
    }
}
