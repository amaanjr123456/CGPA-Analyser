import java.util.Scanner;

public class CGPACalculator {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== KTU 2024 CGPA Calculator ===\n");

        int branchIndex = selectBranch();
        if (branchIndex == -1) {
            System.out.println("Invalid branch selected. Exiting.");
            return;
        }

        int totalCredits = 0;
        double totalWeightedPoints = 0;

        // Loop over all semesters
        for (int sem = 0; sem < SyllabusData.NUM_SEMS; sem++) {
            SyllabusData.Subject[] subjects = SyllabusData.getSubjects(branchIndex, sem);

            if (subjects == null || subjects.length == 0) continue;

            System.out.println("\nSemester " + (sem + 1) + ":");

            int semesterCredits = 0;
            double semesterWeightedPoints = 0;

            for (SyllabusData.Subject subject : subjects) {
                // Ignore subjects with 0 credits (like some mandatory seminars)
                if (subject.credits == 0) continue;

                double marks = inputMarks(subject);

                int gradePoint = GradeUtils.marksToGradePoint(marks);
                String grade = GradeUtils.marksToGrade(marks);

                System.out.printf("%-40s | Marks: %5.2f | Grade: %-2s | GP: %d\n",
                                  subject.name, marks, grade, gradePoint);

                semesterWeightedPoints += gradePoint * subject.credits;
                semesterCredits += subject.credits;
            }

            if (semesterCredits == 0) {
                System.out.println("No credit subjects this semester.");
                continue;
            }

            double semesterGPA = semesterWeightedPoints / semesterCredits;
            System.out.printf("Semester %d GPA: %.2f\n", sem + 1, semesterGPA);

            totalCredits += semesterCredits;
            totalWeightedPoints += semesterWeightedPoints;
        }

        if (totalCredits == 0) {
            System.out.println("No credit subjects found in the syllabus.");
        } else {
            double cgpa = totalWeightedPoints / totalCredits;
            System.out.printf("\nOverall CGPA (KTU 2024 scheme): %.2f\n", cgpa);
        }

        System.out.println("=== Calculation complete. Thank you! ===");
        sc.close();
    }

    private static int selectBranch() {
        String[] branches = SyllabusData.getAllBranches();
        System.out.println("Select your branch:");
        for (int i = 0; i < branches.length; i++) {
            System.out.printf("  %d - %s\n", i, branches[i]);
        }
        System.out.print("Enter branch index: ");

        if (sc.hasNextInt()) {
            int branch = sc.nextInt();
            sc.nextLine(); // consume newline
            if (branch >= 0 && branch < branches.length) {
                return branch;
            }
        }
        sc.nextLine(); // discard invalid input
        return -1; // invalid selection
    }

    private static double inputMarks(SyllabusData.Subject subject) {
        double marks = -1;
        do {
            System.out.printf("Enter marks for %s (%d credits): ", subject.name, subject.credits);
            if (sc.hasNextDouble()) {
                marks = sc.nextDouble();
                sc.nextLine();
                if (marks < 0 || marks > 100) {
                    System.out.println("Invalid marks. Must be between 0 and 100.");
                    marks = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        } while (marks < 0);
        return marks;
    }
}
