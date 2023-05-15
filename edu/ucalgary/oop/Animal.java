package edu.ucalgary.oop;

/**
 * Class Animal: animal object and animal methods for executing application
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
 * @version 2.8
 */
public class Animal {
    private int animalID;
    private String animalNickname;
    private String animalSpecies;
    private boolean task; // need relationship between task and animal
    private int feedTime; // added in feedTime, prepTime and cleanCageTime for setTimings()
    private int prepTime;
    private String activeType;
    private boolean feedingPrinted;
    private boolean nocturnalPrinted;
    private boolean diurnalPrinted;
    private boolean crepuscularPrinted;
    private int cleanCageTime;

    /**
     * Creates an Animal object with the animal ID, animal nickname, and
     * animalspecies
     * Sets the timings for feeding, preparation, and cage cleaning based on the
     * animal's species.
     * 
     * @param animalID The ID of the animal
     * 
     * @param animalNickname The nickname of the animal
     * 
     * @param animalSpecies The species of the animal
     */
    public Animal(int animalID, String animalNickname, String animalSpecies) {
        this.animalID = animalID;
        this.animalNickname = animalNickname;
        this.animalSpecies = animalSpecies;
        setTimings();
    }

    /**
     * Sets the feeding time, preparation time, cage cleaning time,
     * and active type based on the animal's species.
     */
    public void setTimings() {
        switch (animalSpecies) {
            case "fox":
                this.feedTime = 5;
                this.prepTime = 5;
                this.cleanCageTime = 5;
                this.activeType = "nocturnal";
                break;
            case "porcupine":
                this.feedTime = 5;
                this.prepTime = 0;
                this.cleanCageTime = 10;
                this.activeType = "crepuscular";
                break;
            case "coyote":
                this.feedTime = 5;
                this.prepTime = 10;
                this.cleanCageTime = 5;
                this.activeType = "crepuscular";
                break;
            case "beaver":
                this.feedTime = 5;
                this.prepTime = 0;
                this.cleanCageTime = 5;
                this.activeType = "diurnal";
                break;
            case "raccoon":
                this.feedTime = 5;
                this.prepTime = 0;
                this.cleanCageTime = 5;
                this.activeType = "nocturnal";
                break;
            default:
                break;
        }
    }

    /**
     * Getter returns the ID of the animal.
     * 
     * @return The ID of the animal
     */
    public int getAnimalID() {
        return this.animalID;
    }

    /**
     * Getter returns the nickname of the animal.
     * 
     * @return The nickname of the animal
     */
    public String getAnimalNickname() {
        return this.animalNickname;
    }

    /**
     * Getter returns the species of the animal.
     * 
     * @return The species of the animal
     */
    public String getAnimalSpecies() {
        return this.animalSpecies;
    }

    /**
     * Getter returns the task of the animal.
     * 
     * @return The task of the animal
     */
    public Boolean getTask() {
        return this.task;
    }

    /**
     * Getter returns the feed time of the animal.
     * 
     * @return The feed time of the animal
     */
    public int getFeedTime() {
        return this.feedTime;
    }

    /**
     * Getter returns the prep time D of the animal.
     * 
     * @return The prep time of the animal
     */
    public int getPrepTime() {
        return this.prepTime;
    }

    /**
     * Getter returns the cage cleaning time of the animal.
     * 
     * @return The cage cleaning time of the animal
     */
    public int getCageCleanTime() {
        return this.cleanCageTime;
    }

    /**
     * Getter returns the active type of the animal.
     * 
     * @return The active type of the animal
     */
    public String getActiveType() {
        return this.activeType;
    }

    /**
     * Sets whether or not the animal's feeding time has been printed in the
     * schedule.
     * 
     * @param printed true if the feeding time has been printed, false otherwise
     */
    public void setFeedingPrinted(boolean printed) {
        this.feedingPrinted = printed;
    }

    /**
     * Sets whether or not nocturnal animal have been printed in the schedule.
     * 
     * @param printed true if nocturnal animal have been printed, false otherwise
     */
    public void setNocturnalPrinted(boolean printed) {
        this.nocturnalPrinted = printed;
    }

    /**
     * Sets whether or not diurnal animal have been printed in the schedule.
     * 
     * @param printed true if diurnal animal have been printed, false otherwise
     */
    public void setDiurnalPrinted(boolean printed) {
        this.diurnalPrinted = printed;
    }

    /**
     * Sets whether or not crepuscular animal have been printed in the schedule.
     * 
     * @param printed true if crepuscular animal have been printed, false otherwise
     */
    public void setCrepuscularPrinted(boolean printed) {
        this.crepuscularPrinted = printed;
    }

    /**
     * Determines whether or not the animal's feeding time has been printed in the
     * schedule.
     * 
     * @return true if the feeding time has been printed, false otherwise
     */
    public boolean isFeedingPrinted() {
        return this.feedingPrinted;
    }

    /**
     * Determines whether or not the nocturnal animal's have been printed in the
     * schedule.
     * 
     * @return true if the nocturnal have been printed, false otherwise
     */
    public boolean isNocturnalPrinted() {
        return this.nocturnalPrinted;
    }

    /**
     * Determines whether or not the diurnal animal's have been printed in the
     * schedule.
     * 
     * @return true if the diurnal have been printed, false otherwise
     */
    public boolean isDiurnalPrinted() {
        return this.diurnalPrinted;
    }

    /**
     * Determines whether or not the crepuscular animal's have been printed in the
     * schedule.
     * 
     * @return true if the crepuscular have been printed, false otherwise
     */
    public boolean isCrepuscularPrinted() {
        return this.crepuscularPrinted;
    }
}
