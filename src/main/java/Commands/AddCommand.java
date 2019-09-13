package Commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Tasks.*;
import ControlPanel.*;

public class AddCommand extends Command {

    private String type;
    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddCommand(String string, String cmd){
        type = string;
        inputString = cmd;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, DukeException {
        switch (type) {
            case "period": {
                String[] getDate1 = inputString.split("/from ");
                String[] getDate2 = getDate1[1].split("/to ");
                Date date1 = simpleDateFormat.parse(getDate2[0]);
                Date date2 = simpleDateFormat.parse(getDate2[1]);
                String formattedDate1 = simpleDateFormat.format(date1);
                String formattedDate2 = simpleDateFormat.format(date2);
                Task t = new Periods(getDate1[0].replaceFirst("period ", ""),
                        formattedDate1, formattedDate2);
                tasks.addTask(t);
                break;
            }
            case "deadline": {
                String[] getDate = inputString.split("/by ");
                Date date = simpleDateFormat.parse(getDate[getDate.length-1]);
                String formattedDate = simpleDateFormat.format(date);
                Task t = new Deadline(getDate[0].replaceFirst("deadline ", ""),
                        date);
                tasks.addTask(t);
                break;
            }
            case "event": {
                if (inputString.equals("event")){
                    throw new DukeException("OOPS!!! The description of a event cannot be empty.");
                }
                String[] getDate = inputString.split("/at ");
                String eventPeriod = getDate[getDate.length-1];
                String[] startendDate = eventPeriod.split(" to ");
                //System.out.println(startendDate[1]);
                Date startDate = simpleDateFormat.parse(startendDate[0]);
                Date endDate = simpleDateFormat.parse(startendDate[1]);
                Task t = new Events(getDate[0].replaceFirst("event ", ""),
                        startDate, endDate);
                tasks.addTask(t);
                break;
            }
            case "todo": {
                if (inputString.equals("todo")){
                    throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                }
                Task t = new ToDos(inputString.replaceFirst("todo ", ""));
                tasks.addTask(t);
                break;
            }
        }
        storage.writeTheFile(tasks.getCheckList());
        System.out.println(" Got it. I've added this task: \n");
        System.out.println("     " + tasks.getTask(tasks.lengthOfList()-1).toString() + "\n");
        System.out.println(" Now you have " + tasks.lengthOfList() + " tasks in the list.");

    }


}