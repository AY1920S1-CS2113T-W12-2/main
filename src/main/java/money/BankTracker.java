package money;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class BankTracker {

    private String description;
    private int amt;
    private LocalDate latestDate;
    private double rate;
    private SimpleDateFormat simpleDateFormat;


    public BankTracker(String accountDescription, int initialAmt, LocalDate initialDate, double interestRate) {
        description = accountDescription;
        amt = initialAmt;
        latestDate =  initialDate;
        rate = interestRate;
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    public String getBankAccountInfo() {
        return "  Name: " + description + "\n  Balance: " + amt + "\n  Initial Date: "
                + simpleDateFormat.format(latestDate) + "\n  Interest Rate: " + rate;
    }
}
