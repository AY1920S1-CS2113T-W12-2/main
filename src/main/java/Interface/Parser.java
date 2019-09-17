package Interface;
import Commands.*;
import Tasks.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Deals with the input of the user and tries to understand the
 * user's input with fixed commands.
 */
public class Parser {
    private static String[] arr;
    private static String[] arr1;

    /**
     * This method breaks apart the user's input and tries to make sense with it.
     * @param fullCommand The user's input
     * @return This returns a Command object based on user's input
     * @throws DukeException On invalid input or when wrong input format is entered
     */
    public static Command parse(String fullCommand) throws DukeException {
        try {
            if (fullCommand.trim().equals("bye")) {
                return new ByeCommand();
            }

            else if (fullCommand.trim().equals("list")) {
                return new ListCommand();
            }

            else if (fullCommand.trim().substring(0, 4).equals("done")) {
                try {
                    arr = fullCommand.split(" ");
                    int index = Integer.parseInt(arr[1]) - 1;
                    return new DoneCommand(index);
                } catch (NumberFormatException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter a valid task number.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please do not leave task number blank.");
                }
            }

            else if (fullCommand.trim().substring(0, 4).equals("find")) {
                try {
                    String key = fullCommand.trim().substring(5);
                    if (key.trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! Please do not leave the keyword blank.");
                    } else {
                        return new FindCommand(key);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter keyword.");
                }
            }

            else if (fullCommand.trim().substring(0, 4).equals("todo")) {
                String activity = fullCommand.trim().substring(4).trim();
                if (activity.isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a todo cannot be empty.");
                } else {

                    return new AddCommand(new Todo(activity));
                }
            }

            else if (fullCommand.trim().substring(0, 5).equals("event")) {
                try {
                    String activity = fullCommand.trim().substring(5);
                    arr = activity.split("/at");
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(arr[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new AddCommand(new Event(arr[0].trim(), dateString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                            "event name_of_event /at dd/MM/yyyy HHmm\n" +
                            "For example: event project meeting /at 1/1/2020 1500");
                }
            }

            else if (fullCommand.trim().substring(0,6).equals("remind")){
                return new RemindCommand();
            }

            else if (fullCommand.trim().substring(0, 6).equals("delete")) {
                try {
                    arr = fullCommand.split(" ");
                    int index = Integer.parseInt(arr[1]) - 1;
                    return new DeleteCommand(index);
                } catch (NumberFormatException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please enter a valid task number.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException("\u2639" + " OOPS!!! Please do not leave task number blank.");
                }
            }

            else if (fullCommand.trim().substring(0, 8).equals("deadline")) {
                try {
                    String activity = fullCommand.trim().substring(8);
                    arr = activity.split("/by");
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(arr[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new AddCommand(new Deadline(arr[0].trim(), dateString));
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter deadline as follows:\n" +
                            "deadline name_of_activity /by dd/MM/yyyy HHmm\n" +
                            "For example: deadline return book /by 2/12/2019 1800");
                }
            }
            else if(fullCommand.trim().contains("when is the nearest day in which I have a ") && fullCommand.trim().contains(" hour free slot?")){
                try{
                    String duration = fullCommand;
                    duration = duration.replaceFirst("when is the nearest day in which I have a ", "");
                    duration = duration.replaceFirst(" hour free slot", "");
                    //duration = duration.replaceAll("\\D", "");
                    //duration = duration.replaceAll(".$", "");
                    duration = duration.substring(0, duration.indexOf('?'));

                    return new FindFreeTimesCommand(duration);
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new DukeException(" OOPS!!! Please enter find free time as follows:\n" +
                            " when is the nearest day in which I have a X hour free slot?\n" +
                            "For example:  when is the nearest day in which I have a 4.5 hour free slot?");
            }
            else if (fullCommand.equals("show schedule")) {
                return new ViewSchedulesCommand();
            }

            else if (fullCommand.trim().substring(0,6).equals("snooze")) {
                try {
                    String activity = fullCommand.trim().substring(6);
                    arr = activity.split("/to");
                    arr1 = arr[0].split(" ");
                    int index = Integer.parseInt(arr1[1].trim()) - 1;
                    if (arr[0].trim().isEmpty()) {
                        throw new DukeException("\u2639" + " OOPS!!! The index of a snooze cannot be empty.");
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                    Date date = formatter.parse(arr[1].trim());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                    String dateString = dateFormat.format(date);
                    return new SnoozeCommand(index, dateString);
                } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(" OOPS!!! Please enter snooze as follows:\n" +
                            "snooze index /to dd/MM/yyyy HHmm\n" +
                            "For example: snooze 2 /to 2/12/2019 1800");
                }
            }

            else {
                throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

        } catch (StringIndexOutOfBoundsException e){
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}