# BabyNames

A JavaFX application for analyzing baby name data, including ranking, statistics, and historical trends.

## Overview
This project allows users to explore baby name data by entering a name, year, and gender to find its rank, as well as other features like total births and name trends over time. It uses CSV files containing baby name data and is built with JavaFX for the graphical interface.

## Inspiration
This project was inspired by the "Baby Names" assignment from the **Duke University Java Programming course** on Coursera. It builds upon the concepts taught in the course, extending them with a GUI using JavaFX.

## Requirements
- Java 8 or higher (JavaFX included).
- Duke University's `edu.duke` library for file handling.
- Apache Commons CSV for parsing CSV files.
- Required libraries (place in a `lib/` folder):
   - Download `edu.duke.jar` from [Duke Java Library](https://www.dukelearntoprogram.com/downloads/duke_library.php) and place it in `lib/`.
   - Download `commons-csv.jar` (e.g., version 1.10.0) from [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/download_csv.cgi) and place it in `lib/`.

## Setup and Running
1. Clone the repository: git clone https://github.com/VladimirStarcevic/BabyNames.git
2. For full data, download `data.zip` from the [Releases](https://github.com/VladimirStarcevic/BabyNames/releases) section.
3. Extract `data.zip` into a `test-data/` folder (or adjust the path in `getParser` method).
4. In IntelliJ:
- Open the project.
- Run `ProjectBabyNames` (Right-click on `src/ProjectBabyNames.java` > Run).
5. Alternatively, use the included `test-data/` folder with sample files (`yob2012short.csv`, `yob2013short.csv`, `yob2014short.csv`) to test the application.

## Credits
- **Duke University**: For the foundational concepts and data structure from their Java course on Coursera ([Java Programming: Solving Problems with Software](https://www.coursera.org/learn/java-programming)).
- Built as a learning exercise to practice Java, OOP, and JavaFX.