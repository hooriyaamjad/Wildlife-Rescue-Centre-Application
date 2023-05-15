package edu.ucalgary.oop;

/**
 * Class Treatment: treatment object and treatment methods for executing
 * application
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
 * @version 1.5
 */

public class Treatment {
    private static int nextTreatmentID = 1;
    private int treatmentID;
    private int animalID;
    private int taskID;
    private int startHour;

    /**
     * Constructs a Treatment object with the given animal ID, task ID, and start
     * hour.
     * The treatment ID is automatically generated and assigned.
     * 
     * @param animalID the ID of the animal receiving the treatment
     * 
     * @param taskID the ID of the task associated with the treatment
     * 
     * @param startHour the hour at which the treatment is scheduled to begin
     */
    public Treatment(int animalID, int taskID, int startHour) {
        this.treatmentID = nextTreatmentID++;
        this.animalID = animalID;
        this.taskID = taskID;
        this.startHour = startHour;
    }

    /**
     * Getter returns the unique ID of the Treatment.
     * 
     * @return the treatment ID
     */
    public int getTreatmentID() {
        return this.treatmentID;
    }

    /**
     * Getter returns the ID of the animal of this Treatment.
     * 
     * @return the animal ID
     */
    public int getAnimalID() {
        return this.animalID;
    }

    /**
     * Getter returns the ID of the task of this Treatment.
     * 
     * @return the task ID
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * Getter returns the start hour of this Treatment.
     * 
     * @return the start hour
     */
    public int getStartHour() {
        return this.startHour;
    }
}
