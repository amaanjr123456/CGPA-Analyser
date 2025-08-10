import java.util.Scanner;

public class CGPACalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("KTU 2024 CGPA Calculator");
        System.out.println("=========================");

        // Show branches
        String[] branches = SyllabusData.getAllBranches();
        for (int i = 0; i < branches.length; i++) {
            System.out.printf("%d. %s\n", i + 1, branches[i]);
        }

        System.out.print("Enter your branch number: ");
        int branch = sc.nextInt() - 1;

        System.out.print("Enter number of semesters completed (1-8): ");
        int numSems = sc.nextInt();

        double totalWeightedGradePoints = 0;
        int totalCredits = 0;

        for (int sem = 0; sem < numSems; sem++) {
            SyllabusData.Subject[] subjects = SyllabusData.getSubjects(branch, sem);
            if (subjects.length == 0) {
                System.out.printf("No subjects found for semester %d\n", sem + 1);
                continue;
            }
            System.out.printf("\nEnter marks for Semester %d:\n", sem + 1);

            int semCredits = 0;
            double semWeightedPoints = 0;

            for (SyllabusData.Subject subject : subjects) {
                if (subject.credits == 0) continue; // skip zero-credit subjects like ethics

                System.out.printf("  %s (%d credits): ", subject.name, subject.credits);
                int marks = sc.nextInt();

                int gradePoint = GradeUtils.marksToGradePoint(marks);
                semCredits += subject.credits;
                semWeightedPoints += gradePoint * subject.credits;
            }

            if (semCredits == 0) {
                System.out.println("No credits this semester, skipping.");
                continue;
            }

            double sgpa = semWeightedPoints / semCredits;
            System.out.printf("Semester %d SGPA: %.2f\n", sem + 1, sgpa);

            totalWeightedGradePoints += semWeightedPoints;
            totalCredits += semCredits;
        }

        if (totalCredits == 0) {
            System.out.println("No credits found. CGPA cannot be calculated.");
        } else {
            double cgpa = totalWeightedGradePoints / totalCredits;
            System.out.printf("\nOverall CGPA after %d semester(s): %.2f\n", numSems, cgpa);
        }

        sc.close();
    }
}
