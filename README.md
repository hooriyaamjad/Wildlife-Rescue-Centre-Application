# ENSF380FinalProject

Your client, Example Wildlife Rescue (EWR), needs a program which can be used to generate a daily list
of tasks for volunteers to complete in order to support the animals in residence. EWR specializes in
medium-sized animals and refers other cases to other centres. They have facilities to support coyotes,
foxes, porcupines, beavers and raccoons.

You are being asked to design an application which will calculate a schedule for an entire day. EWR
currently uses a database which is updated throughout the day by the staff vet to keep track of required
medical tasks. Each animal will have at least 1 medical task associated with it. Your application should
read from the same database to identify the tasks. EWR expects that Sara will run the program each
evening and, if necessary, contact the backup volunteer.

Feeding information is not included in the database. It is based on set times according to whether the
animal is nocturnal, diurnal, or crepuscular. The amount of time required for feeding depends on the
type of animal (e.g., coyotes always take 5 minutes per animal, with 10 minutes of preparation time for
any feeding activity). For orphaned animals, feeding is considered a medical task rather than a standard
task. Cage cleaning is another activity not included in the database. It can be done at any time of day,
and takes a consistent amount of time for each type of animal.

The program should create a file containing the schedule. If Sara needs to call the backup volunteer, the
program should inform her and it should not exit until she has confirmed that she has called the
volunteer for the necessary time(s). If it is not possible to create a schedule, the program should report
the problem so that Sara can contact the staff vet to request a change of requirements. As the staff vet
may not be on site, Sara needs to be able to modify the start hour of one or more of the treatments
based on the advice she receives. The new schedule should be created after the database is updated.

The program should not exit without generating a schedule and getting confirmation for all backup volunteers (i.e., all errors should be caught before they are thrown by the JVM).
    o Users should be provided with meaningful error messages appropriate to the audience (end user, rather than programmer). Error messages must explain what was wrong and how it can be corrected (e.g., "It was impossible to complete the schedule due to too many events scheduled at 11 AM. Please shift some of the following activities: rebandage head wound (Spike), administer antibiotics (Spike, Shadow))."
    o Users shouldbe given theopportunity tocorrect their mistakes, and opportunities for mistakes should be limited. Some techniques for reducing user error are:
    o Providing instructions prior to data entry 
    o Normalization of input
    o Limiting choices to only valid options
    
## How do I run the application?

**On Windows from Command Line:**

>javac -cp .;lib\mysql-connector-java-8.0.23.jar;. edu\ucalgary\oop\GUIController.java

>java -cp .;lib\mysql-connector-java-8.0.23.jar;. edu.ucalgary.oop.GUIController

**On Mac from Command Line:**

>javac -cp .:lib/mysql-connector-java-8.0.23.jar:. edu/ucalgary/oop/GUIController.java

>java -cp .:lib/mysql-connector-java-8.0.23.jar:. edu.ucalgary.oop.GUIController

## How do I run the tests?

**On Windows from Command Line:**

>javac -cp .;lib/mysql-connector-java-8.0.23.jar;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar edu/ucalgary/oop/*.java

>java -cp .;lib/mysql-connector-java-8.0.23.jar;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore edu.ucalgary.oop.WildlifeRescueTests

**On Mac from Command Line:**

>javac -cp .:lib/mysql-connector-java-8.0.23.jar:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar edu/ucalgary/oop/*.java

>java -cp .:lib/mysql-connector-java-8.0.23.jar:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore edu.ucalgary.oop.WildlifeRescueTests

## Group 66 Team Members
Hooriya Amjad

Sahiti Akella

Eeman Abid

Hareem Khan