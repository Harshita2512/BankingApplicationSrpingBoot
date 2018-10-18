package bankingApp.pojo;

import java.util.ArrayList;

public class CustomerInfo {

    String firstName;
    String lastName;
    String email;
    long phoneNumber;
    AccountInfo account;
    ArrayList<ActivitiesInfo> activities;
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public AccountInfo getAccount() {
        return account;
    }
    public void setAccount(AccountInfo account) {
        this.account = account;
    }
    public ArrayList<ActivitiesInfo> getActivities() {
        return activities;
    }
    public void setActivities(ArrayList<ActivitiesInfo> activities) {
        this.activities = activities;
    }
    
}
