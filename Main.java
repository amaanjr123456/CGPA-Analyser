import java.util.Scanner;

import mypack.DataAccess;
import mypack.DatabaseManager;
import mypack.GradePolicy;
import mypack.MarkedSubject;
import mypack.Semester;
import mypack.Student;
import mypack.SyllabusData;

import java.sql.SQLException;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DataAccess dataAccess = new DataAccess();

    public static void main(String[] args) {
        System.out.println("=== Semester Planner & CGPA Analyzer ===");
        
        Student student = null;
        int branch = -1; // To store the user's branch after they log in

        try {
            // --- Authentication Gate (Login/Register) ---
            while (student == null) {
                System.out.println("\n1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                int choice = askIntInRange("Choose an option: ", 1, 3);

                if (choice == 1) { // --- LOGIN ---
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String password = sc.nextLine().trim();
                    
                    System.out.println("\nSelect Your Branch:");
                    branch = selectBranch();

                    Student foundUser = dataAccess.findByEmail(email, branch);

                    // NOTE: In a real app, use a hashing library like BCrypt to compare passwords
                    if (foundUser != null && foundUser.getPassword().equals(password)) {
                        student = foundUser;
                        System.out.println("\nWelcome back, " + student.getName() + "!");
                    } else {
                        System.out.println(" -> Error: Invalid email or password. Please try again.");
                    }
                } else if (choice == 2) { // --- REGISTER ---
                    System.out.println("\n--- New User Registration ---");
                    System.out.print("Enter your Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter your Email: ");
                    String email = sc.nextLine().trim();

                    // Check if email already exists (branch doesn't matter for this check)
                    if (dataAccess.findByEmail(email, 0) != null) { 
                        System.out.println(" -> Error: An account with this email already exists. Please login.");
                        continue;
                    }

                    System.out.print("Create a Password: ");
                    String password = sc.nextLine().trim();
                    System.out.print("Enter your Roll Number (optional, can be added later): ");
                    String rollNumber = sc.nextLine().trim();
                    double targetCGPA = askDoubleInRange("Set your Target CGPA (e.g., 8.5): ", 0.0, 10.0);

                    student = new Student(name, email, password, targetCGPA);
                    student.setRollNumber(rollNumber);
                    dataAccess.save(student);
                    System.out.println("Registration successful! You are now logged in.");
                    
                    System.out.println("\nSelect Your Branch to continue:");
                    branch = selectBranch();

                } else { // --- EXIT ---
                    System.out.println("Exiting application.");
                    return;
                }
            }

            // --- MAIN APPLICATION LOGIC (STARTS AFTER SUCCESSFUL LOGIN) ---
            
            if (student.getLastSemesterNumber() > 0) {
                System.out.printf("Your current CGPA up to Semester %d is: %.3f\n", student.getLastSemesterNumber(), student.calculateCGPA());
            }

            int startSem = student.getLastSemesterNumber() + 1;
            if (startSem > 8) {
                System.out.println("\nAll 8 semesters have already been recorded.");
            } else {
                int endSem = askIntInRange("Enter marks up to which semester? (" + startSem + "-8): ", startSem, 8);

                for (int semNo = startSem; semNo <= endSem; semNo++) {
                    System.out.println("\n--- Semester " + semNo + " ---");
                    Semester sem = new Semester(semNo);
                    SyllabusData.Subject[] subs = SyllabusData.getSubjects(branch, semNo - 1);

                    if (subs == null || subs.length == 0) {
                        System.out.println("  No subjects defined for this branch & semester. Skipping.");
                        continue;
                    }
                    
                    for (SyllabusData.Subject s : subs) {
                        MarkedSubject ms = new MarkedSubject(s);
                        System.out.printf("\nSubject: %s (%d credits) - %s [%s]\n",
                                ms.getName(), ms.getCredit(), ms.getType(), ms.getSchemeType());

                        int ia1 = askIfApplicable("  IA1", GradePolicy.getIA1Max(s.schemeType));
                        int ia2 = askIfApplicable("  IA2", GradePolicy.getIA2Max(s.schemeType));
                        int asg = askIfApplicable("  Assignment+Attendance", GradePolicy.getAssignmentMax(s.schemeType));
                        int lab = askIfApplicable("  Lab", GradePolicy.getLabMax(s.schemeType));
                        int ext = askIfApplicable("  External", GradePolicy.getExternalMax(s.schemeType));

                        ms.setMarks(ia1, ia2, asg, lab, ext);
                        sem.addSubject(ms);

                        double total = ms.totalOutOf100();
                        System.out.printf("    Total: %.1f/100  |  Grade: %s  |  GP: %.1f  |  Weighted: %.1f\n",
                                total, ms.gradeLetter(), ms.gradePoint(), ms.weightedGradePoints());
                    }

                    if (!sem.getSubjects().isEmpty()) {
                        double sgpa = sem.calculateSGPA();
                        System.out.printf("=> SGPA (Sem %d): %.3f | Credits: %.0f\n", semNo, sgpa, sem.getTotalCredits());
                        student.addSemester(sem);
                    }
                }
                
                dataAccess.update(student);
                System.out.println("\nSuccessfully updated all data to the database for " + student.getName() + ".");
            }

            // --- Final Analysis ---
            double cgpa = student.calculateCGPA();
            int lastSem = student.getLastSemesterNumber();
            System.out.printf("\n=== Current Result (up to Sem %d) ===\nCGPA: %.3f\n", lastSem, cgpa);

            if (lastSem < 8) {
                int remainingCredits = 0;
                for (int s = lastSem; s < 8; s++) {
                    SyllabusData.Subject[] futureSubs = SyllabusData.getSubjects(branch, s);
                    if (futureSubs != null) {
                        for (SyllabusData.Subject sub : futureSubs) remainingCredits += sub.credits;
                    }
                }

                System.out.printf("\nRemaining Credits until graduation: %d\n", remainingCredits);

                if (remainingCredits > 0) {
                    double requiredAvgSGPA = student.requiredAvgGPAcrossRemainingCredits(remainingCredits);
                    System.out.printf("To reach target CGPA %.2f by graduation:\n", student.getTargetCGPA());
                    if (requiredAvgSGPA <= 0) {
                        System.out.println("  -> You have already exceeded your target CGPA!");
                    } else if (requiredAvgSGPA > 10.0) {
                        System.out.printf("  -> Required average SGPA across remaining credits = %.3f. This is not achievable.\n", requiredAvgSGPA);
                    } else {
                        System.out.printf("  -> You need an average SGPA of %.3f across all remaining semesters.\n", requiredAvgSGPA);
                    }
                }
            } else {
                System.out.println("\nCongratulations on completing all semesters!");
            }

        } catch (SQLException e) {
            System.err.println("\nDatabase Error: Could not connect or execute query.");
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
    }

    // --- Helper Methods ---

    private static int selectBranch() {
        String[] branches = SyllabusData.getAllBranches();
        for (int i = 0; i < branches.length; i++) {
            System.out.printf("  %d) %s\n", i + 1, branches[i]);
        }
        return askIntInRange("Enter branch number: ", 1, branches.length) - 1;
    }

    private static int askIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max) {
                    System.out.println("  -> Input out of range. Please enter a value between " + min + " and " + max + ".");
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("  -> Invalid input. Please enter an integer.");
            }
        }
    }

    private static double askDoubleInRange(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                 if (val < min || val > max) {
                    System.out.println("  -> Input out of range. Please enter a value between " + min + " and " + max + ".");
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("  -> Invalid input. Please enter a number.");
            }
        }
    }

    private static int askIfApplicable(String label, int max) {
        if (max <= 0) {
            return 0;
        }
        return askIntInRange(label + " (0-" + max + "): ", 0, max);
    }
}