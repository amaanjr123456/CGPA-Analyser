import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== KTU 2024 CGPA Analyzer (User Syllabus) ===");
        System.out.println("Using provided SyllabusData.java (branch-based syllabus).\n");

        System.out.print("Student Name: "); String name = sc.nextLine();
        System.out.print("Roll Number: "); String roll = sc.nextLine();

        // choose branch
        String[] branches = SyllabusData.getAllBranches();
        System.out.println("Select Branch:"); 
        for (int i = 0; i < branches.length; i++) {
            System.out.printf("  %d) %s\n", i+1, branches[i]);
        }
        int branch = askIntInRange("Enter branch number: ", 1, branches.length) - 1;

        int currentSem = askIntInRange("Current Semester (1-8): ", 1, 8);
        double targetCGPA = askDoubleInRange("Target CGPA (e.g., 8.0): ", 0.0, 10.0);

        Student st = new Student(name, roll, targetCGPA);

        // Enter detailed marks for each subject up to current sem
        for (int semNo = 1; semNo <= currentSem; semNo++) {
            System.out.println("\n--- Semester " + semNo + " ---");
            Semester sem = new Semester(semNo);
            SyllabusData.Subject[] subs = SyllabusData.getSubjects(branch, semNo-1);
            if (subs.length == 0) {
                System.out.println("  No subjects defined for this branch & semester in SyllabusData. Skipping."); 
            }
            for (SyllabusData.Subject s : subs) {
                MarkedSubject ms = new MarkedSubject(s);
                System.out.printf("\nSubject: %s (%d credits) - %s\n", ms.getName(), ms.getCredit(), ms.getType());
                int ia1 = askIntInRange("  IA1 (0-" + GradePolicy.IA1_MAX + "): ", 0, GradePolicy.IA1_MAX);
                int ia2 = askIntInRange("  IA2 (0-" + GradePolicy.IA2_MAX + "): ", 0, GradePolicy.IA2_MAX);
                int asg = askIntInRange("  Assignment/Activity (0-" + GradePolicy.ASSIGNMENT_MAX + "): ", 0, GradePolicy.ASSIGNMENT_MAX);
                int ext = askIntInRange("  External (0-" + GradePolicy.EXTERNAL_MAX + "): ", 0, GradePolicy.EXTERNAL_MAX);
                ms.setMarks(ia1, ia2, asg, ext);
                sem.addSubject(ms);
                double total = ms.totalOutOf100();
                System.out.printf("    Total: %.1f/100  |  Grade: %s  |  GP: %.1f  |  Weighted: %.1f\n",
                        total, ms.gradeLetter(), ms.gradePoint(), ms.weightedGradePoints());
            }
            double sgpa = sem.calculateSGPA();
            System.out.printf("=> SGPA (Sem %d): %.3f | Credits: %.0f\n", semNo, sgpa, sem.getTotalCredits());
            st.addSemester(sem);
        }

        double cgpa = st.calculateCGPA();
        System.out.printf("\n=== Current Result (up to Sem %d) ===\nCGPA: %.3f\n", currentSem, cgpa);

        int totalRemainingSemesters = 8 - currentSem;
        if (totalRemainingSemesters > 0) {
            // compute remaining credits (next sem .. sem 8)
            int remainingCredits = 0;
            for (int s = currentSem + 1; s <= 8; s++) {
                SyllabusData.Subject[] subs = SyllabusData.getSubjects(branch, s-1);
                for (SyllabusData.Subject sub : subs) remainingCredits += sub.credits;
            }
            System.out.printf("\nRemaining semesters: %d | Remaining Credits until graduation: %d\n", totalRemainingSemesters, remainingCredits);

            double requiredAvgGP = st.requiredAvgGPAcrossRemainingCredits(remainingCredits);
            double requiredAvgSGPA = requiredAvgGP; // since SGPA is credit-weighted GP average
            double trendSGPA = st.predictNextSemSGPAFromTrend();

            if (remainingCredits <= 0) {
                System.out.println("No credit information for remaining semesters in SyllabusData. Cannot compute required SGPA.");
            } else {
                System.out.printf("To reach target CGPA %.2f by graduation:\n", st.getTargetCGPA());
                if (requiredAvgSGPA <= 0) {
                    System.out.println("  -> You have already exceeded the target CGPA (no further SGPA needed).");
                } else if (requiredAvgSGPA > 10.0) {
                    System.out.printf("  -> Required average (credit-weighted) GP across remaining credits = %.3f ( >10.0 ) -> Not achievable\n", requiredAvgSGPA);
                } else {
                    System.out.printf("  -> Required average (credit-weighted) GP across remaining credits = %.3f\n", requiredAvgSGPA);
                    // If the student wants equal SGPA each remaining sem, compute per-sem SGPA required
                    double perSemSGPAIfEqual = requiredAvgSGPA; // already credit-weighted average; per-sem SGPA will differ if credit counts vary
                    System.out.printf("  -> If you maintain this GP in remaining semesters, your next-sem target (credit-weighted) is approx: %.3f\n", perSemSGPAIfEqual);
                }
                System.out.printf("Predicted next-sem SGPA (trend-based): %.3f\n", trendSGPA);
            }
        } else {
            System.out.println("\nYou are in the final semester. No remaining-semester prediction calculated.");
        }

        System.out.println("\nNote: You can edit SyllabusData.java to adjust branch/semester subjects, and GradePolicy.java to adjust grading scheme.");
    }

    private static int askIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max) throw new NumberFormatException();
                return val;
            } catch (Exception e) {
                System.out.println("  -> Please enter an integer in [" + min + ", " + max + "].");
            }
        }
    }

    private static double askDoubleInRange(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val < min || val > max) throw new NumberFormatException();
                return val;
            } catch (Exception e) {
                System.out.println("  -> Please enter a number in [" + min + ", " + max + "].");
            }
        }
    }
}
