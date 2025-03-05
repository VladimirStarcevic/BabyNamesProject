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

public class ProjectBabyNames extends Application{
    private CSVParser getParser(int year) {
        //String fileName = STR."yob\{year}short.csv";
        String fileName = "data/yob" + year + ".csv";
        FileResource fileResource = new FileResource(fileName);
        return fileResource.getCSVParser(false);
    }

    public void totalBirths(CSVParser parser) {
        int numOfUniqueGirlNames = 0;
        int numOfUniqueBoyNames = 0;

        int totalBornBoys = 0;
        int totalBornGirls = 0;

        int totalNumOfNames = 0;

        String priviousName = "";
        for (CSVRecord record: parser) {
            String name = record.get(0);
            String gender = record.get(1);
            int births = Integer.parseInt(record.get(2));
            totalNumOfNames += births;
            if (gender.equals("F")) {
                totalBornGirls += births;
                if (!name.equals(priviousName)) {
                    numOfUniqueGirlNames++;
                    priviousName = name;
                }

            } else if (gender.equals("M")){
                totalBornBoys += births;
                if (!name.equals(priviousName)) {
                    numOfUniqueBoyNames++;
                    priviousName = name;
                }
            }
        }

        int totalUniqueNames = numOfUniqueBoyNames + numOfUniqueGirlNames;

        System.out.println("Total girls: " + totalBornGirls);
        System.out.println("Total boys: " + totalBornBoys);

        System.out.println("Number of girl unique names: " + numOfUniqueGirlNames);
        System.out.println("Number of boy unique names: " + numOfUniqueBoyNames);
        System.out.println("Total of unique names: " + totalUniqueNames);
        System.out.println("Total number of births: " + totalNumOfNames);
    }

    public int getRank(int year, String name, String gender) {
        CSVParser parser = getParser(year);
        int rank = 1;

        for (CSVRecord record: parser) {
            String currentName = record.get(0);
            String currentGender = record.get(1);
            if (currentGender.equals(gender)) {
                if (currentName.equals(name)) {
                    return rank;
                }
                rank++;
            }
        }

        return -1;
    }

    public String getName(int year, int rank, String gender) {
        CSVParser parser = getParser(year);
        int currentRank = 1;

        for (CSVRecord record: parser) {
            String currentName = record.get(0);
            String currentGender = record.get(1);
            if (currentGender.equals(gender)) {
                if (currentRank == rank) {
                    return currentName;
                }
                currentRank++;
            }
        }

        return "NO NAME";
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        if (rank == -1) {
            System.out.printf("%s not found in %d\n", name, year);
            return;
        }

        String newName = getName(newYear, rank, gender);

        System.out.printf("%s born in %d would be %s if she was born in %d.\n", name, year, newName, newYear);
    }

    public int yearOfHighestRank(String name, String gender) {
        int highestRank = Integer.MAX_VALUE;
        int bestYear = -1;

        for (int year = 1880; year <= 2014; year++) {
            int rank = getRank(year, name, gender);
            if (rank != -1 && rank < highestRank) {
                highestRank = rank;
                bestYear = year;
            }
        }
        return bestYear;
    }

    public double getAverageRank(String name, String gender) {
        double sum = 0.0;
        int count = 0;

        for (int year = 1880; year <= 2014; year++) {
            int rank = getRank(year, name, gender);
            if (rank != -1) {
                sum += rank;
                count++;
            }
        }

        if (count == 0) return -1.0;

        return sum / count;
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        CSVParser parser = getParser(year);
        int totalNumberOfHigherRanks = 0;
        boolean found = false;

        for (CSVRecord record: parser) {
            if (record.get(1).equals(gender)) {
                if (record.get(0).equals(name)) {
                    found =true;
                    break;
                }
                totalNumberOfHigherRanks += Integer.parseInt(record.get(2));
            }
        }
        if (!found) return -1;

        return totalNumberOfHigherRanks;
    }
    public void testTotalBirths() {
        CSVParser csvParser = getParser(1905);
        totalBirths(csvParser);
    }

    public void testGetRank() {
        String name = "Frank";
        String gender = "M";
        int year = 1971;

        System.out.printf("Rank for name %s is: %d\n", name, getRank(year, name, gender));
    }

    public void testGetName() {
        String name = getName(1982, 450,"M");
        System.out.println(STR."Name is \{name}");
    }

    public void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }

    public void testBestYear() {
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name, gender);
        System.out.printf("Best year was %d for the name %s\n", year, name);
    }

    public void testAverageRank() {
        String name = "Susan";
        String gender = "F";
        double avg = getAverageRank(name, gender);
        System.out.printf("Average rank for name %s is: %.2f\n", name, avg);
    }

    public void testTotalBirthsRankedHigher() {
        String name = "Drew";
        String gender = "M";
        int year = 1990;
        int total = getTotalBirthsRankedHigher(year, name, gender);
        System.out.printf("Total num of higher ranked births is: %d\n", total);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField nameField = new TextField("Frank");
        TextField yearField = new TextField("1971");
        TextField genderField = new TextField("M");
        Button rankButton = new Button("Get Rank");
        Label resultLabel = new Label("Result will appear here");

        rankButton.setOnAction(event -> {
            String name = nameField.getText();
            int year = Integer.parseInt(yearField.getText());
            String gender = genderField.getText();
            int rank = getRank(year, name, gender);
            resultLabel.setText("Rank of " + name + " in " + year + ": " + rank);
        });

        VBox root = new VBox(10, new Label("Name:"), nameField,
                new Label("Year:"), yearField,
                new Label("Gender:"), genderField,
                rankButton, resultLabel);
        root.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Baby Names Calculator");
        scene.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); // Pokreće JavaFX aplikaciju
    }



//    public static void main(String[] ignoredArgs) {
//        ProjectBabyNames projectBabyNames = new ProjectBabyNames();
//        //projectBabyNames.testTotalBirths();
//        projectBabyNames.testGetRank();
//        //projectBabyNames.testGetName();
//        //projectBabyNames.testWhatIsNameInYear();
//        //projectBabyNames.testBestYear();
//        //projectBabyNames.testAverageRank();
//        //projectBabyNames.testTotalBirthsRankedHigher();
//    }





}
