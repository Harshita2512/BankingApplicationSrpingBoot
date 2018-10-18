package bankingApp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
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
import bankingApp.db.Activities;
import bankingApp.db.Customer;
import bankingApp.pojo.AccountInfo;
import bankingApp.pojo.ActivitiesInfo;
import bankingApp.pojo.CustomerInfo;
import bankingApp.pojo.RegisterUser;
import bankingApp.pojo.UpdateInfo;
import bankingApp.service.AccountService;
import bankingApp.service.ActivityService;
import bankingApp.service.CustomerService;
import bankingApp.utility.SendEmail;

@RestController
public class RegistrationController {

    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegisterUser reg)
            throws ServletException, IOException {

        Customer c = new Customer();
        c.setFirstName(reg.getFirstName());
        c.setLastName(reg.getLastName());
        c.setEmail(reg.getEmail());
        c.setPassword(reg.getPassword());
        c.setPhoneNumber(reg.getPhoneNumber());

        try {
            customerService.createCustomer(c);
            // accountService.createAccNo(reg.getEmail());
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            Account a = accountService.getAccNo(c.getEmail());
            SendEmail.sendAccountInfo(c.getEmail(), a.getAccountNumber());
        } catch (Exception e) {
            System.out.println("Warning: Mail server is down currently");
        }

        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CustomerInfo> customer(@RequestHeader("auth_token") String token) {

        String email = SessionService.getEmail(token);

        if (email == null) {
            return new ResponseEntity<CustomerInfo>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Customer c = customerService.getCustomer(email);
            Account acc = accountService.getAccNo(email);
            ArrayList<Activities> activities = activityService.listOfActivities(acc.getAccountNumber());

            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setFirstName(c.getFirstName());
            customerInfo.setEmail(c.getEmail());
            customerInfo.setLastName(c.getLastName());
            customerInfo.setPhoneNumber(c.getPhoneNumber());

            AccountInfo a = new AccountInfo();
            a.setAccountNumber(acc.getAccountNumber());
            a.setBalance(acc.getBalance());
            a.setType(acc.getType());
            customerInfo.setAccount(a);

            ArrayList<ActivitiesInfo> list = new ArrayList<ActivitiesInfo>();

            for (Activities activity : activities) {
                ActivitiesInfo act = new ActivitiesInfo();
                act.setAmount(activity.getAmount());
                act.setDate(activity.getDate());
                act.setTransactionType(activity.getTransactionType());

                list.add(act);
            }

            customerInfo.setActivities(list);
            return new ResponseEntity<CustomerInfo>(customerInfo, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<CustomerInfo>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Void> updateInfo(@RequestHeader("auth_token") String token, UpdateInfo updateInfo) {

        String email = SessionService.getEmail(token);

        if (email == null) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Customer c = customerService.getCustomer(email);

            c.setPhoneNumber(updateInfo.getPhoneNumber());
            c.setPassword(updateInfo.getPassword());

            customerService.updateInfo(c);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
