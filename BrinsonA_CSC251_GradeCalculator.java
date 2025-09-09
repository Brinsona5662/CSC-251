import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BrinsonA_CSC251_GradeCalculator {
    private static ArrayList<Double> grades = new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("#.##");
    
    public static void main(String[] args) {
        showWelcomeMessage();
        
        int choice;
        do {
            choice = showMenu();
            processChoice(choice);
        } while (choice != 4);
        
        showGoodbyeMessage();
    }
    
    /**
     * Shows welcome message to user
     */
    private static void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null,
            "Welcome to the Grade Calculator System!\n\n" +
            "This program will help you track your grades\n" +
            "and calculate your current average.",
            "Grade Calculator",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Displays main menu and gets user choice
     * @return user's menu choice
     */
    private static int showMenu() {
        String[] options = {
            "1. Add a Grade",
            "2. View Current Average", 
            "3. View Letter Grade",
            "4. Exit"
        };
        
        String menu = "Grade Calculator Menu\n" +
                     "=====================\n\n";
        for (String option : options) {
            menu += option + "\n";
        }
        menu += "\nPlease enter your choice (1-4):";
        
        int choice = 0;
        boolean validChoice = false;
        
        while (!validChoice) {
            try {
                String input = JOptionPane.showInputDialog(null, menu,
                    "Grade Calculator Menu", JOptionPane.QUESTION_MESSAGE);
                
                if (input == null) {
                    choice = 4; // Treat cancel as exit
                    validChoice = true;
                } else {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 4) {
                        validChoice = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Please enter a number between 1 and 4.",
                            "Invalid Choice", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "Please enter a valid number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        return choice;
    }
    
    /**
     * Processes user's menu choice
     * @param choice the selected menu option
     */
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                addGrade();
                break;
            case 2:
                viewAverage();
                break;
            case 3:
                viewLetterGrade();
                break;
            case 4:
                // Exit - handled in main loop
                break;
            default:
                JOptionPane.showMessageDialog(null,
                    "Invalid choice. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Adds a new grade to the collection
     */
    private static void addGrade() {
        String userInput = "Y";
        while (userInput.equals("Y")){
            try {
                String grade_input = JOptionPane.showInputDialog(null, "Please enter your number grade",
                "Add a Grade", JOptionPane.QUESTION_MESSAGE);

                if (grade_input == null){
                    break;
                }

                Double grade_num = Double.parseDouble(grade_input);

                if (grade_num >= 0 && grade_num <= 100) {
                    grades.add(grade_num);
                    userInput = JOptionPane.showInputDialog(null, "Your grade has been added!\n\n" + 
                    "Would you like to add another grade?\n\n" + "(Enter 'Y' for yes)", 
                    "Grade Confirmation", JOptionPane.QUESTION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null,
                        "Please enter a valid grade.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }

            }

            catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                        "Please enter a number.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
        }
    }
    
    /**
     * Calculates and displays current average
     */
    private static void viewAverage() {

        if (grades.isEmpty()){
            JOptionPane.showMessageDialog(null, "You have not entered any grades!", 
            "No Grades Entered", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            Double averageGrade = calculateAverage();

            displayGrades(averageGrade, grades);
        }

    }
    
    /**
     * Determines and displays letter grade based on average
     */
    private static void viewLetterGrade() {

         if (grades.isEmpty()){
            JOptionPane.showMessageDialog(null, "You have not entered any grades!", 
            "No Grades Entered", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            Double averageGrade = calculateAverage();
            String LetterGrade = getLetterGrade(averageGrade);

            JOptionPane.showMessageDialog(null, "Average Grade: " + averageGrade + "\n\n" + 
            "Letter Grade: " + LetterGrade, "Grades", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    
    /**
     * Shows goodbye message
     */
    private static void showGoodbyeMessage() {
        String message = "Thank you for using Grade Calculator!\n\n";
        if (!grades.isEmpty()) {
            double average = calculateAverage();
            message += "Final Statistics:\n" +
                      "Total Grades: " + grades.size() + "\n" +
                      "Final Average: " + df.format(average) + "%\n" +
                      "Letter Grade: " + getLetterGrade(average);
        }
        
        JOptionPane.showMessageDialog(null, message,
            "Goodbye", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // TODO: Add helper methods for calculations
    private static double calculateAverage() {
        double grade_avg = 0.0;

        for (double grade : grades) {
        grade_avg += grade;
        }

        grade_avg = grade_avg / grades.size();

        return grade_avg;
    }
    
    private static String getLetterGrade(double average) {
        String grade_Letter;

        if (average >= 90.0){
            grade_Letter = "A";
        }
        else if(average >= 80.0){
            grade_Letter = "B";
        }
        else if(average >= 70.0){
            grade_Letter = "C";
        }
        else if(average >= 60.0){
            grade_Letter = "D";
        }
        else {
            grade_Letter = "F";
        }

        return grade_Letter;
    }

    public static void displayGrades(double averageGrade, ArrayList<Double> grades) {
        // Format the average grade to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAverage = df.format(averageGrade);
        
        // Build the message string
        StringBuilder message = new StringBuilder();
        message.append("Average Grade: ").append(formattedAverage).append("\n\n");
        message.append("Individual Grades:\n");
        
        for (Double grade : grades) {
            String formattedGrade = df.format(grade);
            if (grade >= averageGrade) {
                message.append("✓ ").append(formattedGrade).append(" (Above/At Average)\n");
            } else {
                message.append("✗ ").append(formattedGrade).append(" (Below Average)\n");
            }
        }
        
        // Display using JOptionPane
        JOptionPane.showMessageDialog(
            null,
            message.toString(),
            "Grade Report",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

}