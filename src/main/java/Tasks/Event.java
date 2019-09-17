package Tasks;

/**
 * Represents a task called event.
 */
public class Event extends Task {

    protected String by;

    @Override
    public String getType() {
        return "[E]";
    }

    /**
     * Creates an Event object.
     * @param description Description of a task
     * @param by Date of when a task is on
     */
    public Event(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts the Event object to a string.
     * @return This returns the string of the Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + by + ")";
    }

    @Override
    public String getDateTime() {
        return by;
    }
}