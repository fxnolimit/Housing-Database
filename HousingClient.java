// USERS:
// Make all residents ->

// Catch exceptions in the client

// QUERIES:
// Does applicant info match one of their preferences? If so, make it so that they are a resident there

// Need to match preference with what's available ->

// AVAILABILITY:
// Housing_unit -> if available, not full = 0; full = 1
// Showing results -> change in housing unit too
//    Building number, no of bedrooms, type, rent, married couples -> number item
//    Ask preferences 1, 2, 3 -> corresponds with numbered query results
// After: type in user information -> add that user to user table
// PRINT IT TO THE CONSOLE WITH THE INDEX FROM AN ARRAYLIST
// Then match them

import java.sql.*;
import java.util.*;

public class HousingClient {

    private static HousingSystem hs = null;

    public static void main(String args[]) throws ClassNotFoundException {
        try {
            hs = new HousingSystem();
            printMainMenu();
        } catch (SQLException e) {
        }
    }

    // Displays the top menu and allows users to pick initial high-level options
    public static void printMainMenu() throws SQLException {

        int action = 0;
        while (action != 4) {

            repeat(42, "*");
            formatString("Welcome to the Housing System", 7, " ");
            formatString("1. Resident Login", 4, " ");
            formatString("2. Applicant Registration/Apply", 4, " ");
            formatString("3. Admin", 4, " ");
            formatString("4. Quit\n", 4, " ");

            action = readInt("Please select an option: ", 4); // FIX THIS

            switch (action) {
                case 1:
                    residentLogIn();
                    break;

                case 2:
                    printApplicantTop();
                    break;

                case 3:
                    printAdminTop();
                    break;

                case 4:
                    return;

            }
        }
    }

    public static void residentLogIn() {

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter username: ");

        String username = input.nextLine();

        System.out.print("password:");

        String password = input.nextLine();

        System.out.println("Page down. Try again later.");
        return;
    }

    // Displays the applicant options and accepts an action
    public static void printApplicantTop() throws SQLException {
        /*
        a. Users to check the availability of apartments in a particular category.
           It shows a list with all the available apartments for users to check
           availability of apartments in a particular category.
        b. Submit Booking request- fill up application and choose one of the available
           housing categories
        c. Receive confirmation message
        d. After receiving confirmation can login to Residents login option in the main menu
        */
        int action = 0;
        while (action != 3) {

            repeat(42, "*");
            formatString("Welcome Applicant!", 12, " ");
            formatString("1. Get started!", 4, " ");
            formatString("2. Back to previous menu\n", 4, " ");

            repeat(42, "*");

            action = readInt("Please select an option: ", 2);

            switch (action) {
                case 1:
                    getPreferences();
                    break;
                case 2:
                    return;

            }
        }
        return;
    }

    // TASK 1:
    // Displays available housing options and accepts the user housing preferences
    public static void getPreferences() throws SQLException {

        System.out.println("The following housing options are available: ");
        ArrayList<HousingUnit> hu = hs.checkAvailability(); // returns array of strings

        // Prints out available housing
        //2
        // System.out.printf("%s %20s %5s %5s %10s %10s %s", "Name", "Building number", "Apt. number", "Submission date", "Date completed", "Comments", "\n");
        int index = 1;
        for (HousingUnit h : hu) {
            // System.out.printf("%s %20s %5s %5s %10s %10s %s",  request.getName(), request.getBuilding(), request.getAptNum(), request.getSubDate(), request.getDateCompleted(), request.getComm(), "\n");
            System.out.println(index + h.getBuilding() + h.getBedrooms() + h.getType() + h.getMarried() + h.getPrice());
            index++;
        }

        System.out.println("Please enter the index of your preferences: ");
        int pref1 = readInt("Top choice: ", index);
        int pref2 = readInt("Second choice: ", index);
        int pref3 = readInt("Third choice: ", index);

        //construct array of preferences to be sent to backend
        ArrayList<HousingUnit> preferences = new ArrayList<>(Arrays.asList(hu.get(pref1 - 1), hu.get(pref2 - 1), hu.get(pref3 - 1)));

        System.out.println("Please fill out the following information:\n");

        System.out.println("User information");

        String username = readString("Username: ");
        String password = readString("Password: ");
        String SID = readString("Student ID: ");
        String name = readString("Name: ");
        String phoneNumber = readString("Phone number: ");
        String gender = readString("Gender: ");
        int student_status_int = readInt("Student status (1 if student, 0 if not): ", 1);
        int marital_status_int = readInt("Marital status (1 if married, 0 if single): ", 1);
        String address = readString("Address: ");
        String college = readString("College: ");
        String department = readString("Department: ");
        String major = readString("Major: ");
        String familyHeadID = readString("Family head's SSN: ");
        String roommate = readString("Roommate Name: ");

        // Translate the input values into boolean values
        boolean marital_status = false;
        if (marital_status_int == 1){
            marital_status = true;
        }

        boolean student_status = true;
        if (student_status_int == 0){
            student_status = false;
        }

        hs.createApplicant(SID, username, password, name, gender, student_status, marital_status, address,
                phoneNumber, college, department, major, familyHeadID); // When do we add application number?

        hs.bookHousing(SID, preferences, roommate);

        // have them pay fee?
        return;
    }

    // Displays all admin options and accepts further action options
    public static void printAdminTop() throws SQLException {

        int action = 0;
        while (action != 6) {

            repeat(42, "*");
            System.out.println("Welcome to Bellevue College Housing System");
            formatString("Administrators Staff", 10, " ");
            formatString("1. Manage Residents", 4, " ");
            formatString("1. Manage Applicants", 4, " ");
            formatString("3. Demographic Studies", 4, " ");
            formatString("4. Manage Maintenance orders", 4, " ");
            formatString("5. Administrative Reports", 4, " ");
            formatString("6. Back to previous menu\n", 4, " ");
            repeat(42, "*");

            Scanner input = new Scanner(System.in);
            boolean valid = false;

            System.out.println("Please select an option: ");

            while (!valid) {
                action = input.nextInt();

                if (action == 5 || action == 6) {
                    valid = true;
                } else {
                    System.out.println("Please enter a valid action: ");
                }
            }

            switch (action) {
                case 5:
                    printReportsTop();
                    break;
                case 6:
                    return;
            }
        }
        return;
    }

    // Displays the reports menu and accepts user action choice
    public static void printReportsTop() throws SQLException {
        int action = 0;

        while (action != 5) {
            repeat(42, "*");
            formatString("Administrative Reports", 10, " ");
            formatString("1. Housing department reports", 4, " ");
            formatString("2. Applicants Reports", 4, " ");
            formatString("3. Resident Reports", 4, " ");
            formatString("4. Maintenance department reports", 4, " ");
            formatString("5. Quit", 4, " ");

            Scanner input = new Scanner(System.in);
            boolean valid = false;

            System.out.println("Please select an option: ");

            while (!valid && input.hasNextInt()) {
                action = input.nextInt();

                if (action == 4 || action == 5) {
                    valid = true;
                }
                System.out.println("Please enter a valid action: ");
            }

            ArrayList<MaintenanceRequestDue> m_report =  hs.runReports();

            switch (action) {
                case 4:
                    showReports(m_report);
                case 5:
                    return; //printAdminTop(); -> needed?
            }
        }
        return;
    }

    // TASK 2:
    // Prints the current maintenance results
    public static void showReports(ArrayList<MaintenanceRequestDue> m_report) {

        System.out.println("Active maintenance requests: ");

        System.out.printf("%s %20s %5s %5s %10s %10s %s", "Name", "Building number", "Apt. number", "Submission date", "Date completed", "Comments", "\n");

        for (MaintenanceRequestDue request: m_report){
            System.out.printf("%s %20s %5s %5s %10s %10s %s",  request.getName(), request.getBuilding(), request.getAptNum(), request.getSubDate(), request.getDateCompleted(), request.getComm(), "\n");

        }

        System.out.println();
        return;
    }

    // Manages user input for integer-specific values
    public static int readInt(String prompt, int max) {
        boolean valid = false;
        Scanner input = new Scanner(System.in);
        int value = 0;

        while (!valid) {
            System.out.println(prompt);
            if (input.hasNextInt()) {
                value = input.nextInt();
            } else {
                System.out.println("Please enter valid input");
            }

            if (value > 0 && value <= max) {
                valid = true;
            }
        }
        return value;
    }

    // Manages user input for string-specific input
    public static String readString(String prompt) {
        boolean valid = false;
        Scanner input = new Scanner(System.in);
        String output = "";

        System.out.print(prompt);

        while (!valid) {
            if (input.hasNext()) {
                output = input.nextLine();
                valid = true;
            } else {
                System.out.println("Please enter valid input");
            }
        }
        System.out.println();
        return output;
    }

    // Repeats a given character to a given amount
    public static void repeat(int num, String str){
        for (int i = 0; i <= num; i++) {
            System.out.print(str);
        }

        System.out.println("\n");
    }

    // Formats a string, adding a certain amount of buffer to center or indent
    static void formatString(String text, int num, String str){
        for (int i = 0; i < num; i++) {
            System.out.print(str);
        }

        System.out.println(text);
    }
}
