package bankingApp.pojo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;


public class TransactionInfo {
    
    @Min(0)
    long fromAccountNumber;
    @Min(0)
    long toAccountNumber;
    @DecimalMin(value ="0.0")
    float amount;
    
    public long getFromAccountNumber() {
        return fromAccountNumber;
    }
    public void setFromAccountNumber(long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }
    public long getToAccountNumber() {
        return toAccountNumber;
    }
    public void setToAccountNumber(long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

}
