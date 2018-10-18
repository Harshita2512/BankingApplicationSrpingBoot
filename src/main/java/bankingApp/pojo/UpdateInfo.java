package bankingApp.pojo;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdateInfo {
    @Size(min=10,max=10)
    long phoneNumber;
    @NotEmpty
    String password;
    
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    

}
