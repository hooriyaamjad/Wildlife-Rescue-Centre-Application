package edu.ucalgary.oop;

import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.*;

/**
 * Class GUIController: creates the Graphical User Interface, and any necessary
 * schedule methods
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
 * @version 8.7
 */

public class GUIController implements ScheduleFormatter {
    private final JFrame FRM = new JFrame();
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);
    private RescueCenter rescueCenter;
    JTextArea scheduleArea = new JTextArea();
    JTextArea hourTextArea = new JTextArea();
    private boolean backup;
    JPanel scrollPanel = new JPanel();
    ArrayList<String> errors = new ArrayList<>();

    /**
     * This is the constructor of the GUIController class.
     * It creates a new instance of RescueCenter and assigns it to the rescueCenter
     * instance variable.
     */
    public GUIController() {
        this.rescueCenter = new RescueCenter();
    }

    /**
     * Displays the main menu of the program, which includes buttons for
     * getting a schedule, accessing the about page, and quitting the program.
     */
    public void mainMenu() {
        JPanel mainMenu = new JPanel();

        JButton begin = new JButton(new AbstractAction("Get Schedule") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                generateSchedule();
                if (errors.size() > 0) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (String error : errors) {
                        errorMessage.append(error);
                    }
                    JOptionPane.showMessageDialog(FRM, errorMessage.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton about = new JButton(new AbstractAction("About") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                about();
            }
        });

        JButton quit = new JButton(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainMenu.add(begin);
        mainMenu.add(about);
        mainMenu.add(quit);
        FRM.add(mainMenu);
        FRM.setTitle("WildLife Rescue Centre");
        FRM.setVisible(true);
        FRM.setSize(500, 100);
        FRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Displays information about the program, including the names of the authors.
     * Allows the user to return to the main menu or quit the program.
     */
    public void about() {
        FRM.setSize(500, 125);
        FRM.setResizable(false);
        FRM.setVisible(true);
        FRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menu = new JPanel();

        JLabel label1 = new JLabel("Made by: Eeman Abid, Hareem Khan, Hooriya Amjad, Sahiti Akella");

        JButton back = new JButton(new AbstractAction("Go Back") {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                mainMenu();
            }
        });
        JButton quit = new JButton(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(label1);
        menu.add(back);
        menu.add(quit);
        FRM.add(menu);
    }

    /**
     * Generates the schedule for the specified date and displays it on the GUI.
     * If any errors are encountered, displays an error message to the user.
     * If user saves before confirming backups, an error message is displayed
     * Allows the user to modify the schedule, confirm backup volunteers, and save
     * the schedule.
     */
    public void generateSchedule() {
        FRM.setSize(600, 500);
        FRM.setResizable(true);
        FRM.setVisible(true);
        FRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menu = new JPanel(new BorderLayout());
        scrollPanel.removeAll();
        scheduleArea.setText("");
        hourTextArea.setText("");

        scheduleArea.setLineWrap(true);
        scheduleArea.setText("Schedule for " + formattedDate);
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.add(scheduleArea, BorderLayout.NORTH);

        scheduleFormatter();

        JPanel messagePanel = new JPanel();
        messagePanel
                .add(new JLabel("If backup volunteer(s) is needed, please get confirmation before saving schedule."));
        scrollPanel.add(messagePanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menu.add(scrollPane, BorderLayout.CENTER);

        // get schedule page buttons
        JButton save = new JButton(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backup) {
                    JOptionPane.showMessageDialog(FRM,
                            "Please confirm that the backup(s) have been called before saving the schedule.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    saveSchedule();
                }
            }
        });

        JButton modify = new JButton(new AbstractAction("Modify Schedule") {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                modifyStartHour();
            }
        });
        JButton confirm = new JButton(new AbstractAction("Confirm Backup(s)") {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                getConfirmation();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(save);
        buttonPanel.add(modify);
        buttonPanel.add(confirm);
        menu.add(buttonPanel, BorderLayout.SOUTH);
        FRM.add(menu);
    }

    /**
     * Creates a schedule for the entire day, detailing animal feeding times and
     * treatment schedules.
     * If any errors are encountered, displays an error message to the user.
     * If all nocturnal animals are not fed, an error message is displayed
     * If all diurnal animals are not fed, an error message is displayed
     * If all crepuscular animals are not fed, an error message is displayed
     * If there are too many tasks at a certain hour, an error message is displayed
     */
    @Override
    public void scheduleFormatter() {
        for (int hour = 0; hour <= 23; hour++) {
            StringBuilder hourSchedule = new StringBuilder();
            hourSchedule.append("\n" + hour + ":00" + "\n");

            boolean hasTasks = false;
            int duration = 0;
            boolean unfedNocturnalAnimals = false;
            boolean unfedDiurnalAnimals = false;
            boolean unfedCrepuscularAnimals = false;

            // appends treatments from sql file
            for (Treatment treatment : rescueCenter.getTreatmentList()) {
                if (treatment.getStartHour() == hour) {
                    int taskID = treatment.getTaskID();
                    int animalID = treatment.getAnimalID();
                    Task task = rescueCenter.getTaskByID(taskID);
                    String taskDescription = task.getDescription();
                    Animal animal = rescueCenter.getAnimalByID(animalID);
                    String animalNickname = animal.getAnimalNickname();
                    duration += task.getDuration();
                    hourSchedule.append("* " + taskDescription + " " + "(" + animalNickname + ")\n");
                    hasTasks = true;
                }
            }

            ArrayList<String> foxesFed = new ArrayList<>();
            ArrayList<String> raccoonsFed = new ArrayList<>();
            ArrayList<String> beaversFed = new ArrayList<>();
            ArrayList<String> porcupinesFed = new ArrayList<>();
            ArrayList<String> coyotesFed = new ArrayList<>();
            boolean nocPrepTimeAdded = false;
            boolean diurPrepTimeAdded = false;
            boolean crepPrepTimeAdded = false;

            // adds feeding times into schedule - not in sql file
            for (Animal animal : rescueCenter.getAnimalList()) {
                boolean printed = animal.isFeedingPrinted();
                if (animal.getActiveType() == "nocturnal") {
                    if (hour >= 0 && hour < 3) {
                        if (!printed) {
                            String animalNickname = animal.getAnimalNickname();
                            String animalSpecies = animal.getAnimalSpecies();

                            if (!nocPrepTimeAdded) {
                                duration += animal.getPrepTime();
                                nocPrepTimeAdded = true;
                            }
                            duration += animal.getFeedTime();
                            if (duration < 60) {
                                if (animalSpecies.equals("fox")) {
                                    foxesFed.add(animalNickname);
                                }
                                if (animalSpecies.equals("raccoon")) {
                                    raccoonsFed.add(animalNickname);
                                }
                                animal.setFeedingPrinted(true);
                            } else {
                                duration -= animal.getFeedTime();
                                if (hour == 2) {
                                    unfedNocturnalAnimals = true;
                                }
                                continue;
                            }
                        }
                        hasTasks = true;
                    }
                }

                if (animal.getActiveType() == "diurnal") {
                    if (hour >= 8 && hour < 11) {
                        if (!printed) {
                            String animalNickname = animal.getAnimalNickname();
                            String animalSpecies = animal.getAnimalSpecies();

                            if (!diurPrepTimeAdded) {
                                duration += animal.getPrepTime();
                                diurPrepTimeAdded = true;
                            }
                            duration += animal.getFeedTime();
                            if (duration < 60) {
                                if (animalSpecies.equals("beaver")) {
                                    beaversFed.add(animalNickname);
                                }
                                animal.setFeedingPrinted(true);
                            } else {
                                duration -= animal.getFeedTime();
                                if (hour == 10) {
                                    unfedDiurnalAnimals = true;
                                }
                                continue;
                            }
                        }
                        hasTasks = true;
                    }
                }

                if (animal.getActiveType() == "crepuscular") {
                    if (hour >= 19 && hour < 22) {
                        if (!printed) {
                            String animalNickname = animal.getAnimalNickname();
                            String animalSpecies = animal.getAnimalSpecies();

                            if (!crepPrepTimeAdded) {
                                duration += animal.getPrepTime();
                                crepPrepTimeAdded = true;
                            }
                            duration += animal.getFeedTime();
                            if (duration < 60) {
                                if (animalSpecies.equals("coyote")) {
                                    coyotesFed.add(animalNickname);
                                }
                                if (animalSpecies.equals("porcupine")) {
                                    porcupinesFed.add(animalNickname);
                                }
                                animal.setFeedingPrinted(true);
                            } else {
                                duration -= animal.getFeedTime();
                                if (hour == 21) {
                                    unfedCrepuscularAnimals = true;
                                }
                                continue;
                            }
                        }
                        hasTasks = true;
                    }
                }
            }

            // prints amount of animals from each species that are able to be fed in that
            // hour
            if (foxesFed.size() > 0) {
                hourSchedule
                        .append("* Feeding - foxes (" + foxesFed.size() + ": " + String.join(", ", foxesFed) + ")\n");
            }
            if (raccoonsFed.size() > 0) {
                hourSchedule.append(
                        "* Feeding - raccoons (" + raccoonsFed.size() + ": " + String.join(", ", raccoonsFed) + ")\n");
            }
            if (beaversFed.size() > 0) {
                hourSchedule.append(
                        "* Feeding - beavers (" + beaversFed.size() + ": " + String.join(", ", beaversFed) + ")\n");
            }
            if (coyotesFed.size() > 0) {
                hourSchedule.append(
                        "* Feeding - coyotes (" + coyotesFed.size() + ": " + String.join(", ", coyotesFed) + ")\n");
            }
            if (porcupinesFed.size() > 0) {
                hourSchedule.append("* Feeding - porcupines (" + porcupinesFed.size() + ": "
                        + String.join(", ", porcupinesFed) + ")\n");
            }

            if (unfedNocturnalAnimals == true) {
                errors.add(
                        "Invalid Schedule: Not all nocturnal animals have been fed.\nContact staff vet or modify start hours");
            }
            if (unfedDiurnalAnimals == true) {
                errors.add(
                        "Invalid Schedule: Not all diurnal animals have been fed.\nContact staff vet or modify start hours");
            }
            if (unfedCrepuscularAnimals == true) {
                errors.add(
                        "Invalid Schedule: Not all crepuscular animals have been fed.\nContact staff vet or modify start hours");
            }

            // add cage cleaning times to schedule - not in sql file
            ArrayList<String> foxesCleaned = new ArrayList<>();
            ArrayList<String> raccoonsCleaned = new ArrayList<>();
            ArrayList<String> beaversCleaned = new ArrayList<>();
            ArrayList<String> porcupinesCleaned = new ArrayList<>();
            ArrayList<String> coyotesCleaned = new ArrayList<>();

            for (Animal animal : rescueCenter.getAnimalList()) {
                if (animal.getActiveType() == "nocturnal") {
                    boolean printed = animal.isNocturnalPrinted();
                    if (!printed) {
                        String animalNickname = animal.getAnimalNickname();
                        String animalSpecies = animal.getAnimalSpecies();
                        duration += animal.getCageCleanTime();
                        if (duration < 60) {
                            if (animalSpecies.equals("fox")) {
                                foxesCleaned.add(animalNickname);
                            }
                            if (animalSpecies.equals("raccoon")) {
                                raccoonsCleaned.add(animalNickname);
                            }
                            animal.setNocturnalPrinted(true);
                        } else {
                            duration -= animal.getCageCleanTime();
                            continue;
                        }
                    }
                }

                if (animal.getActiveType() == "diurnal") {
                    boolean printed = animal.isDiurnalPrinted();
                    if (!printed) {
                        String animalNickname = animal.getAnimalNickname();
                        String animalSpecies = animal.getAnimalSpecies();
                        duration += animal.getCageCleanTime();
                        if (duration < 60) {
                            if (animalSpecies.equals("beaver")) {
                                beaversCleaned.add(animalNickname);
                            }
                            animal.setDiurnalPrinted(true);
                        } else {
                            duration -= animal.getCageCleanTime();
                            continue;
                        }
                    }
                }

                if (animal.getActiveType() == "crepuscular") {
                    boolean printed = animal.isCrepuscularPrinted();
                    if (!printed) {
                        String animalNickname = animal.getAnimalNickname();
                        String animalSpecies = animal.getAnimalSpecies();
                        duration += animal.getCageCleanTime();
                        if (duration < 60) {
                            if (animalSpecies.equals("coyote")) {
                                coyotesCleaned.add(animalNickname);
                            }
                            if (animalSpecies.equals("porcupine")) {
                                porcupinesCleaned.add(animalNickname);
                            }
                            animal.setCrepuscularPrinted(true);
                        } else {
                            duration -= animal.getCageCleanTime();
                            continue;
                        }
                    }
                }
            }

            if (foxesCleaned.size() > 0) {
                hourSchedule.append("* Cage Cleaning - foxes (" + foxesCleaned.size() + ": "
                        + String.join(", ", foxesCleaned) + ")\n");
            }
            if (raccoonsCleaned.size() > 0) {
                hourSchedule.append("* Cage Cleaning - raccoons (" + raccoonsCleaned.size() + ": "
                        + String.join(", ", raccoonsCleaned) + ")\n");
            }
            if (beaversCleaned.size() > 0) {
                hourSchedule.append("* Cage Cleaning - beavers (" + beaversCleaned.size() + ": "
                        + String.join(", ", beaversCleaned) + ")\n");
            }
            if (coyotesCleaned.size() > 0) {
                hourSchedule.append("* Cage Cleaning - coyotes (" + coyotesCleaned.size() + ": "
                        + String.join(", ", coyotesCleaned) + ")\n");
            }
            if (porcupinesCleaned.size() > 0) {
                hourSchedule.append("* Cage Cleaning - porcupines (" + porcupinesCleaned.size() + ": "
                        + String.join(", ", porcupinesCleaned) + ")\n");
            }

            // checks if backup volunteers are required is time exceeds an hour
            if (duration > 60) {
                backup = true;
                hourSchedule.append(" [ + backup volunteer]\n");
            }
            if (backup == true && duration > 120) {
                errors.add("Invalid Schedule: Too many tasks at " + hour
                        + ":00.\nContact staff vet or modify start hours");
            }
            // total task time for convenience
            hourSchedule.append("Total task duration: " + duration + "\n");
            if (hasTasks) {
                hourTextArea.append(hourSchedule.toString());
                hourTextArea.setEditable(false);
                scrollPanel.add(hourTextArea);
            }
        }
    }

    /**
     * This method creates a JPanel menu to modify the start hour of treatments for
     * animals.
     * It fetches the treatments data from the SQL database/
     * Displays it in a JTable, and allows the user to edit the start hour by
     * clicking on a cell.
     * The method also adds buttons to the menu to generate a schedule or go back to
     * the previous menu.
     * After editing the start hour, the method updates the value in the SQL
     * database and the JTable model.
     * If any errors are encountered, displays an error message to the user.
     * If the new start hour is invalid or outside the maximum window, an error
     * message is displayed.
     */
    public void modifyStartHour() {
        // create new JPanel menu for modifying start hour
        JPanel menu = new JPanel(new BorderLayout());

        // create table to display treatments with option to modify start hour
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Treatment ID");
        model.addColumn("Animal ID");
        model.addColumn("Task");
        model.addColumn("Start Hour");
        model.addColumn("Max Window");

        // fetch treatments data from SQL database
        try {
            Statement stmt = rescueCenter.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM TREATMENTS JOIN ANIMALS ON TREATMENTS.AnimalID = ANIMALS.AnimalID JOIN TASKS ON TREATMENTS.TaskID = TASKS.TaskID");
            while (rs.next()) {
                int treatmentID = rs.getInt("TreatmentID");
                int animalID = rs.getInt("AnimalID");
                String animalNickname = rs.getString("AnimalNickname");
                String taskDescription = rs.getString("Description");
                int startHour = rs.getInt("StartHour");
                int maxWindow = rs.getInt("MaxWindow"); // retrieve the value of MaxWindow from the ResultSet
                model.addRow(new Object[] { treatmentID, animalID + " (" + animalNickname + ")", taskDescription,
                        startHour, maxWindow }); // add the MaxWindow column to the row
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching treatments data: " + ex.getMessage());
        }

        // create JTable to display treatments data
        JTable table = new JTable(model);

        // allow user to edit start hour by clicking on a cell
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    int treatmentID = (int) model.getValueAt(table.getSelectedRow(), 0);
                    int currentStartHour = (int) model.getValueAt(table.getSelectedRow(), 3);
                    int maxWindow = (int) model.getValueAt(table.getSelectedRow(), 4);
                    int newStartHour = Integer.parseInt(JOptionPane.showInputDialog(FRM,
                            "Enter new start hour for Treatment " + treatmentID + ":", currentStartHour));
                    if (newStartHour > 23 || newStartHour < 0) {
                        JOptionPane.showMessageDialog(FRM, "New start hour is invalid, please input an hour from 0-23",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        if (newStartHour > currentStartHour + maxWindow - 1) { // check if the new start hour is outside
                                                                               // the max window
                            JOptionPane.showMessageDialog(FRM,
                                    "New start hour is outside the maximum window for Treatment " + treatmentID
                                            + " (max window is " + maxWindow + " hours).",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (newStartHour > currentStartHour + maxWindow - 1) { // check if the new start hour is
                                                                                  // outside the max window
                        JOptionPane.showMessageDialog(FRM,
                                "New start hour is outside the maximum window for Treatment " + treatmentID
                                        + " (max window is " + maxWindow + " hours).",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        if (newStartHour > 23 || newStartHour < 0) {
                            JOptionPane.showMessageDialog(FRM,
                                    "New start hour is invalid, please input an hour from 0-23", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {
                            String sql = "UPDATE TREATMENTS SET StartHour = ? WHERE TreatmentID = ?";
                            PreparedStatement pstmt = rescueCenter.getConnection().prepareStatement(sql);
                            pstmt.setInt(1, newStartHour);
                            pstmt.setInt(2, treatmentID);
                            pstmt.executeUpdate();
                            // update model with new start hour value
                            model.setValueAt(newStartHour, table.getSelectedRow(), 3);
                        } catch (SQLException ex) {
                            System.out.println(
                                    "Error updating start hour for Treatment " + treatmentID + ": " + ex.getMessage());
                        }
                    }
                }
            }
        });

        // add table to menu
        menu.add(new JScrollPane(table), BorderLayout.CENTER);

        // add buttons to menu
        JButton generateButton = new JButton("Generate Schedule");
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FRM.dispose(); // close the current GUI
                main(new String[0]);
            }
        });
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                generateSchedule();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(generateButton);
        buttonPanel.add(goBack);
        menu.add(buttonPanel, BorderLayout.SOUTH);

        // add menu to JFrame
        FRM.getContentPane().add(menu);
        FRM.pack();
        FRM.setLocationRelativeTo(null);
    }

    /**
     * Displays a confirmation message at the top of the window.
     * Allows the user to save the schedule to a file or quit the program.
     */
    public void getConfirmation() {
        // add save schdeule button so that after confirming the schedule file can be
        // generated
        FRM.setSize(600, 500);
        FRM.setResizable(false);
        FRM.setVisible(true);
        FRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menu = new JPanel(new BorderLayout());

        JPanel confirmationPanel = new JPanel(new BorderLayout());
        confirmationPanel.setPreferredSize(new Dimension(600, 500));

        // Add a label at the top of the screen
        JPanel messagePanel = new JPanel();
        messagePanel.add(new JLabel("Backup volunteer(s) confirmed!"));

        // Create scrollPane to display the schedule
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        confirmationPanel.add(messagePanel, BorderLayout.NORTH);
        confirmationPanel.add(scrollPane, BorderLayout.CENTER);

        // Remove the previous messagePanel from scrollPanel and add the new one
        scrollPanel.remove(scrollPanel.getComponentCount() - 1);

        menu.add(confirmationPanel);

        // modify start hour page buttons
        JButton save = new JButton(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSchedule();
            }
        });

        JButton quit = new JButton(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(save);
        buttonPanel.add(quit);
        menu.add(buttonPanel, BorderLayout.SOUTH);
        FRM.add(menu);
    }

    /**
     * Saves the schedule to a user-selected file.
     * Prompts the user to choose a location to save the schedule file.
     * If the user selects a location, the schedule is saved to the selected file.
     * If any errors are encountered, displays an error message to the user.
     * If an error occurs while saving the file, an error message is displayed.
     */
    public void saveSchedule() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(FRM);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(scheduleArea.getText());
                writer.write("\n");
                writer.write(hourTextArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(FRM, "Schedule saved successfully!", "Save Schedule",
                        JOptionPane.INFORMATION_MESSAGE);
                FRM.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(FRM, "Error saving schedule. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This is the main method that starts the program.
     * It creates a new instance of the GUIController class
     * and calls its mainMenu() method to display the main menu of the program.
     * 
     * @param args an array of command-line arguments for the program
     */
    public static void main(String[] args) {
        GUIController gui = new GUIController();
        gui.mainMenu();
    }
}
