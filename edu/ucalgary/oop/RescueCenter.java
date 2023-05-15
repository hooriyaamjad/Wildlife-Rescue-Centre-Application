package edu.ucalgary.oop;

import java.util.*;
import java.sql.*;

/**
 * Class RescueCenter: reads required data from the sql database, contains
 * necessary methods
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
 * @version 4.2
 */

public class RescueCenter {

    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<Treatment> treatments = new ArrayList<Treatment>();
    private Connection dbConnect;
    private ResultSet results;
    private final String DBURL = "jdbc:mysql://localhost/EWR";
    private final String USERNAME = "oop";
    private final String PASSWORD = "password";

    /**
     * Constructor of the RescueCenter class.
     * Creates a connection to thesqldatabase.
     * Fetches data from the 'Animals', 'Tasks', and 'Treatments' tables.
     */
    public RescueCenter() {
        createConnection();

        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM Animals");
            results = myStmt.executeQuery();

            while (results.next()) {
                Animal animal = new Animal(results.getInt("AnimalID"), results.getString("AnimalNickname"),
                        results.getString("AnimalSpecies"));
                animals.add(animal);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM Tasks");
            results = myStmt.executeQuery();

            while (results.next()) {
                Task task = new Task(results.getInt("TaskID"), results.getString("Description"),
                        results.getInt("Duration"), results.getInt("MaxWindow"));
                tasks.add(task);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM Treatments");
            results = myStmt.executeQuery();

            while (results.next()) {
                Treatment treatment = new Treatment(results.getInt("AnimalID"), results.getInt("TaskID"),
                        results.getInt("StartHour"));
                treatments.add(treatment);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method creates a connection to the SQL database.
     */
    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method returns the SQL Connection object.
     * 
     * @return the Connection object to the SQL database.
     */
    public Connection getConnection() {
        return dbConnect;
    }

    /**
     * Getter method returns the list of Animal objects.
     * 
     * @return an ArrayList containing all Animal objects in the rescue center.
     */
    public ArrayList<Animal> getAnimalList() {
        return this.animals;
    }

    /**
     * Getter method returns the list of Task objects.
     * 
     * @return an ArrayList containing all Task objects in the rescue center.
     */
    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    /**
     * Getter method returns the list of Treatment objects.
     * 
     * @return an ArrayList containing all Treatment objects in the rescue center.
     */
    public ArrayList<Treatment> getTreatmentList() {
        return this.treatments;
    }

    /**
     * Retrieves the task with the specified task ID from the list of tasks.
     * 
     * @param taskID the ID of the task to retrieve
     * 
     * @return the Task object with the specified task ID, or null if not found
     */
    public Task getTaskByID(int taskID) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                return task;
            }
        }
        return null;
    }

    /**
     * Retrieves the animal with the specified animal ID from the list of animals.
     * 
     * @param animalID the ID of the animal to retrieve
     * 
     * @return the Animal object with the specified animal ID, or null if not found
     */
    public Animal getAnimalByID(int animalID) {
        for (Animal animal : animals) {
            if (animal.getAnimalID() == animalID) {
                return animal;
            }
        }
        return null;
    }  
}
