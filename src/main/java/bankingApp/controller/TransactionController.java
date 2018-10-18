package bankingApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bankingApp.auth.SessionService;
import bankingApp.db.Account;
import bankingApp.pojo.TransactionInfo;
import bankingApp.service.AccountService;
import bankingApp.service.ActivityService;
import bankingApp.service.CustomerService;

@RestController
public class TransactionController {
    
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    ActivityService activityService;


	@RequestMapping(value="/transaction", method=RequestMethod.POST)
	public ResponseEntity<Void> transaction(@RequestHeader("auth_token") String token, @Valid @RequestBody TransactionInfo trans) {

	    String email = SessionService.getEmail(token);

        if (email== null) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        try {
        Account fromAccount = accountService.getAccNo(email);
        if (fromAccount.getAccountNumber()!= trans.getFromAccountNumber()) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED); 
        }
        
        if (fromAccount.getBalance()< trans.getAmount()) {
           return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);  
        }
        
        Account toAccount = accountService.getAccount(trans.getToAccountNumber());
        if (toAccount == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); 
        }
        
        fromAccount.setBalance(fromAccount.getBalance()-trans.getAmount());
        toAccount.setBalance(toAccount.getBalance()+trans.getAmount());
        
        accountService.updateAccount(fromAccount);
        accountService.updateAccount(toAccount);
        
        activityService.insertActivity(fromAccount.getAccountNumber(), "Debit", trans.getAmount());
        activityService.insertActivity(toAccount.getAccountNumber(), "Credit", trans.getAmount());
        
        }
        catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
