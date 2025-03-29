import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * This class represents a JavaFX application for analyzing baby names data.
 */
public class ProjectBabyNames extends Application {

    /**
     * This method creates a CSVParser for a given year.
     * @param year The year for which to create the parser.
     * @return A CSVParser object.
     */
    private CSVParser getParser(int year) {
        String fileName = "test-data/yob" + year + "short.csv"; // The file name of the CSV data
        //String fileName = "data/yob" + year + ".csv";
        FileResource fileResource = new FileResource(fileName);
        return fileResource.getCSVParser(false); // Returns the CSV parser object
    }

    /**
     * Calculates and prints the total births, unique names, and gender-specific counts from a CSVParser.
     * @param parser The CSVParser containing baby names data.
     */
    public void totalBirths(CSVParser parser) {
        int numOfUniqueGirlNames = 0; // Counter for unique girl names
        int numOfUniqueBoyNames = 0; // Counter for unique boy names

        int totalBornBoys = 0; // Total number of boys born
        int totalBornGirls = 0; // Total number of girls born

        int totalNumOfNames = 0; // Total number of names

        String priviousName = ""; // Stores the previous name to check for uniqueness
        for (CSVRecord record: parser) {
            String name = record.get(0); // Extracts the name from the CSV record
            String gender = record.get(1); // Extracts the gender from the CSV record
            int births = Integer.parseInt(record.get(2)); // Extracts the number of births from the CSV record
            totalNumOfNames += births; // Adds the number of births to the total count
            if (gender.equals("F")) {
                totalBornGirls += births; // Adds the number of girls born to the total count
                if (!name.equals(priviousName)) {
                    numOfUniqueGirlNames++; // Increments the counter for unique girl names
                    priviousName = name; // Updates the previous name
                }

            } else if (gender.equals("M")){
                totalBornBoys += births; // Adds the number of boys born to the total count
                if (!name.equals(priviousName)) {
                    numOfUniqueBoyNames++; // Increments the counter for unique boy names
                    priviousName = name; // Updates the previous name
                }
            }
        }

        int totalUniqueNames = numOfUniqueBoyNames + numOfUniqueGirlNames; // Calculates the total number of unique names

        System.out.println("Total girls: " + totalBornGirls);
        System.out.println("Total boys: " + totalBornBoys);

        System.out.println("Number of girl unique names: " + numOfUniqueGirlNames);
        System.out.println("Number of boy unique names: " + numOfUniqueBoyNames);
        System.out.println("Total of unique names: " + totalUniqueNames);
        System.out.println("Total number of births: " + totalNumOfNames);
    }

    /**
     * Gets the rank of a name in a given year and gender.
     * @param year The year to get the rank for.
     * @param name The name to get the rank for.
     * @param gender The gender to get the rank for.
     * @return The rank of the name, or -1 if the name is not found.
     */
    public int getRank(int year, String name, String gender) {
        CSVParser parser = getParser(year); // Gets the CSV parser for the given year
        int rank = 1; // Initializes the rank to 1

        for (CSVRecord record: parser) {
            String currentName = record.get(0); // Gets the current name from the CSV record
            String currentGender = record.get(1); // Gets the current gender from the CSV record
            if (currentGender.equals(gender)) {
                if (currentName.equals(name)) {
                    return rank; // Returns the rank if the name matches
                }
                rank++; // Increments the rank
            }
        }

        return -1; // Returns -1 if the name is not found
    }

    /**
     * Gets the name at a given rank in a given year and gender.
     * @param year The year to get the name for.
     * @param rank The rank to get the name for.
     * @param gender The gender to get the name for.
     * @return The name at the given rank, or "NO NAME" if the rank is not found.
     */
    public String getName(int year, int rank, String gender) {
        CSVParser parser = getParser(year); // Gets the CSV parser for the given year
        int currentRank = 1; // Initializes the current rank to 1

        for (CSVRecord record: parser) {
            String currentName = record.get(0); // Gets the current name from the CSV record
            String currentGender = record.get(1); // Gets the current gender from the CSV record
            if (currentGender.equals(gender)) {
                if (currentRank == rank) {
                    return currentName; // Returns the name if the rank matches
                }
                currentRank++; // Increments the current rank
            }
        }

        return "NO NAME"; // Returns "NO NAME" if the rank is not found
    }

    /**
     * Prints what the name would be in a new year.
     * @param name The name to check.
     * @param year The year the name was born.
     * @param newYear The new year to check the name in.
     * @param gender The gender of the name.
     */
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender); // Gets the rank of the name in the given year
        if (rank == -1) {
            System.out.printf("%s not found in %d\n", name, year);
            return;
        }

        String newName = getName(newYear, rank, gender); // Gets the name at the same rank in the new year

        System.out.printf("%s born in %d would be %s if she was born in %d.\n", name, year, newName, newYear);
    }

    /**
     * Determines the year in which the given name had the highest rank.
     * @param name The name to search for.
     * @param gender The gender of the name.
     * @return The year with the highest rank or -1 if the name is not found in any year.
     */
    public int yearOfHighestRank(String name, String gender) {
        int highestRank = Integer.MAX_VALUE; // Initialize with a maximum value to find the highest rank
        int bestYear = -1; // Initialize with -1 to indicate that the name might not be found

        for (int year = 1880; year <= 2014; year++) {
            int rank = getRank(year, name, gender); // Get the rank for the given year
            if (rank != -1 && rank < highestRank) {
                highestRank = rank; // Update the highest rank if a better rank is found
                bestYear = year; // Update the year with the best rank
            }
        }
        return bestYear; // Return the year with the highest rank
    }

    /**
     * Calculates the average rank of a name across all years.
     * @param name The name to calculate the average rank for.
     * @param gender The gender of the name.
     * @return The average rank, or -1.0 if the name is not found in any year.
     */
    public double getAverageRank(String name, String gender) {
        double sum = 0.0; // Initialize the sum of ranks
        int count = 0; // Initialize the count of years

        for (int year = 1880; year <= 2014; year++) {
            int rank = getRank(year, name, gender); // Get the rank for the given year
            if (rank != -1) {
                sum += rank; // Add the rank to the sum
                count++; // Increment the count
            }
        }

        if (count == 0) return -1.0; // If the name is not found in any year, return -1.0

        return sum / count; // Calculate and return the average rank
    }

    /**
     * Calculates the total number of births of those names with higher rank in the same year with the same gender
     * @param year The year to calculate the number of births for.
     * @param name The name to stop at.
     * @param gender The gender of the name.
     * @return The total number of births of those names with higher rank.
     */
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        CSVParser parser = getParser(year); // Get the CSV parser for the given year
        int totalNumberOfHigherRanks = 0; // Initialize the total number of higher ranks
        boolean found = false; // Flag to indicate if the name has been found

        for (CSVRecord record: parser) {
            if (record.get(1).equals(gender)) { // Iterate through the records
                if (record.get(0).equals(name)) { // Check if the name matches
                    found =true; // Set the flag to true
                    break; // Break out of the loop
                }
                totalNumberOfHigherRanks += Integer.parseInt(record.get(2)); // Add the number of births to the total
            }
        }
        if (!found) return -1; // If the name is not found, return -1

        return totalNumberOfHigherRanks; // Return the total number of higher ranks
    }

    /**
     * Tests the totalBirths method.
     */
    public void testTotalBirths() {
        CSVParser csvParser = getParser(1905);
        totalBirths(csvParser);
    }

    /**
     * Tests the getRank method.
     */
    public void testGetRank() {
        String name = "Frank";
        String gender = "M";
        int year = 1971;

        System.out.printf("Rank for name %s is: %d\n", name, getRank(year, name, gender));
    }

    /**
     * Tests the getName method.
     */
    public void testGetName() {
        String name = getName(1982, 450,"M");
        System.out.println("Name is: " + name);
    }

    /**
     * Tests the whatIsNameInYear method.
     */
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }

    /**
     * Tests the bestYear method.
     */
    public void testBestYear() {
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name, gender);
        System.out.printf("Best year was %d for the name %s\n", year, name);
    }

    /**
     * Tests the averageRank method.
     */
    public void testAverageRank() {
        String name = "Susan";
        String gender = "F";
        double avg = getAverageRank(name, gender);
        System.out.printf("Average rank for name %s is: %.2f\n", name, avg);
    }

    /**
     * Tests the totalBirthsRankedHigher method.
     */
    public void testTotalBirthsRankedHigher() {
        String name = "Drew";
        String gender = "M";
        int year = 1990;
        int total = getTotalBirthsRankedHigher(year, name, gender);
        System.out.printf("Total num of higher ranked births is: %d\n", total);
    }

    /**
     * The main entry point for all JavaFX applications.
     * This method is called when the application starts.
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        TextField nameField = new TextField("Frank"); // TextField for entering the name
        TextField yearField = new TextField("1971"); // TextField for entering the year
        TextField genderField = new TextField("M"); // TextField for entering the gender
        Button rankButton = new Button("Get Rank"); // Button to trigger the rank calculation
        Label resultLabel = new Label("Result will appear here"); // Label to display the result

        /**
         * This event handler is triggered when the "Get Rank" button is clicked.
         */
        rankButton.setOnAction(event -> {
            String name = nameField.getText(); // Get the name from the nameField
            int year = Integer.parseInt(yearField.getText()); // Get the year from the yearField
            String gender = genderField.getText(); // Get the gender from the genderField
            int rank = getRank(year, name, gender); // Get the rank of the name in the given year and gender
            resultLabel.setText("Rank of " + name + " in " + year + ": " + rank); // Display the result in the resultLabel
        });

        VBox root = new VBox(10, new Label("Name:"), nameField,
                new Label("Year:"), yearField,
                new Label("Gender:"), genderField,
                rankButton, resultLabel); // Create a VBox layout with the input fields and button
        root.setPadding(new Insets(20, 20, 20, 20)); // Set padding for the layout
        Scene scene = new Scene(root, 600, 400); // Create a scene with the VBox layout
        primaryStage.setTitle("Baby Names Calculator"); // Set the title of the primary stage
        scene.getStylesheets().add("styles.css"); // Add a stylesheet to the scene
        primaryStage.setScene(scene); // Set the scene for the primary stage
        primaryStage.show(); // Show the primary stage
    }

    /**
     * The main method that launches the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }

    // The following code is commented out and was used for testing purposes
    // public static void main(String[] ignoredArgs) {
    //     ProjectBabyNames projectBabyNames = new ProjectBabyNames();
    //     //projectBabyNames.testTotalBirths();
    //     projectBabyNames.testGetRank();
    //     //projectBabyNames.testGetName();
    //     //projectBabyNames.testWhatIsNameInYear();
    //     //projectBabyNames.testBestYear();
    //     //projectBabyNames.testAverageRank();
    //     //projectBabyNames.testTotalBirthsRankedHigher();
    // }
}