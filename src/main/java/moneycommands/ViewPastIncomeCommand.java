package moneycommands;

import controlpanel.MoneyStorage;
import money.Account;
import controlpanel.DukeException;
import controlpanel.Ui;
import money.Income;
import java.text.DateFormatSymbols;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * This command allows users to check the income
 * for a previous or future month specified by the user input.
 */
public class ViewPastIncomeCommand extends MoneyCommand {
    private int month;
    private int year;

    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    //@@author chengweixuan
    /**
     * Constructor of the command which initialises the check income command
     * with the data for the month and year to check as given in the user input.
     * @param command Check command inputted from user
     */
    public ViewPastIncomeCommand(String command) throws DukeException {
        if (command.equals("list month")) {
            LocalDate currDate = LocalDate.now();
            month = currDate.getMonthValue();
            year = currDate.getYear();
        } else {
            try {
                String inputString = command.replaceFirst("check income ", "");
                String[] splitStr = inputString.split(" ", 2);
                month = Integer.parseInt(splitStr[0]);
                year = Integer.parseInt(splitStr[1]);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please include the year!");
            } catch (NumberFormatException e) {
                throw new DukeException("Please input in the format: check income <month> <year>\n");
            }
        }
    }

    /**
     * Returns the name of a month given the index of the month from 1-12.
     * @param month Index of the month
     * @return String of the month name
     */
    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the check income command. Takes the input from the user
     * and checks the Total Income List for expenditures which occur in the month specified
     * in the user input. Prints the income sources found and computes the total income for that
     * month and prints to the user.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (month < 1 || month > 12) {
            throw new DukeException("Month is invalid! Please pick a month from 1-12");
        }
        if (year < 1000 || year > 9999) {
            throw new DukeException("Only years dated from 1000-9999 are accepted by Financial Ghost :)");
        }

        float totalMonthIncome = 0;
        int counter = 1;
        for (Income i : account.getIncomeListTotal()) {
            if (i.getPayday().getMonthValue() == month && i.getPayday().getYear() == year) {
                ui.appendToGraphContainer(" " + counter + "." + i.toString() + "\n");
                counter++;
                totalMonthIncome += i.getPrice();
            }
        }
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");
        ui.appendToGraphContainer("Total income for " + getMonth(month) + " of " + year + " : $");
        ui.appendToGraphContainer(decimalFormat.format(totalMonthIncome) + "\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
