package edu.ucalgary.oop;

/**
 * Class Task: task object and task methods for executing application
 * 
 * @since 1.0
 * @author Hooriya Amjad <a href=
 *         "mailto:hooriya.amjad@ucalgary.ca">hooriya.amjad@ucalgary.ca</a>
 * @author Sahiti Akella <a href=
 *         "mailto:sahiti.akella@ucalgary.ca">sahiti.akella@ucalgary.ca</a>
 * @author Eeman Abid
 *         <a href="mailto:eeman.abid@ucalgary.ca">eeman.abid@ucalgary.ca</a>
 * @author Hareem Khan
 *         <a href="mailto:hareem.khan@ucalgary.ca">hareem.khan@ucalgary.ca</a>
 * @version 1.6
 */

public class Task {

    private int taskID;
    private int duration;
    private String description;
    private int maxWindow;

    /**
     * Creates a new Task object with the given taskID, description, duration, and
     * maxWindow.
     * 
     * @param taskID the unique identifier of the task.
     * 
     * @param description the description of the task.
     * 
     * @param duration the duration of the task in minutes.
     * 
     * @param maxWindow the maximum time window in which the task can be scheduled.
     */
    public Task(int taskID, String description, int duration, int maxWindow) {
        this.taskID = taskID;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
    }

    /**
     * Getter returns the unique ID of the Task.
     * 
     * @return the unique ID of the task.
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     * Getter returns the duration of the Task in minutes.
     * 
     * @return the duration of the Task in minutes.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Getter returns the description of the Task.
     * 
     * @return the description of the Task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter returns the maximum time window in which the Task can be scheduled.
     * 
     * @return the maximum time window in which the Task can be scheduled.
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }
}
