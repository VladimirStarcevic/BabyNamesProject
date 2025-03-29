# BabyNames

A JavaFX application for analyzing baby name data, including ranking, statistics, and historical trends.

## Overview

This project allows users to explore baby name data by entering a name, year, and gender to find its rank, as well as other features like total births and name trends over time. It uses CSV files containing baby name data and is built with JavaFX for the graphical interface.

This project is now using Maven for dependency management.

## Inspiration

This project was inspired by the "Baby Names" assignment from the **Duke University Java Programming course** on Coursera. It builds upon the concepts taught in the course, extending them with a GUI using JavaFX.

## Requirements

- Java 21 or higher.
- JavaFX 23.0.1 or higher.
- Duke University's `edu.duke` library for file handling.
- Apache Commons CSV for parsing CSV files.

## Dependencies

This project uses Maven to manage dependencies. The dependencies are defined in the `pom.xml` file.

-   **JavaFX:**  Used for creating the graphical user interface.
-   **Duke Library:**  Used for file handling.
-   **Apache Commons CSV:** Used for parsing CSV files.

## Setup and Running

1.  Clone the repository:

    ```bash
    git clone https://github.com/VladimirStarcevic/BabyNamesProject.git
    ```

2.  Open the project in IntelliJ IDEA.

3.  IntelliJ IDEA should automatically recognize the project as a Maven project and download the dependencies. If not, right-click on the `pom.xml` file and select "Maven" -> "Reload Project".

4.  For full data, download `data.zip` from the [Releases](https://github.com/VladimirStarcevic/BabyNamesProject/releases) section.

5.  Extract `data.zip` into a `test-data/` folder (or adjust the path in the `getParser` method).

6.  Run `ProjectBabyNames` by right-clicking on `src/main/java/com/example/babynames/ProjectBabyNames.java` and selecting "Run 'ProjectBabyNames.main()'".

7. Alternatively, use the included `test-data/` folder with sample files (`yob2012short.csv`, `yob2013short.csv`, `yob2014short.csv`) to test the application.

## Credits

-   **Duke University**: For the foundational concepts and data structure from their Java course on Coursera ([Java Programming: Solving Problems with Software](https://www.coursera.org/learn/java-programming)).
-   Built as a learning exercise to practice Java, OOP, and JavaFX.
