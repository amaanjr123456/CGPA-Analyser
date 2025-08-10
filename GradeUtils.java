public class GradeUtils {

    /**
     * Converts marks (0-100) to grade points as per KTU 2024 scheme.
     * @param marks Marks scored in the subject
     * @return Grade point (0-10)
     */
    public static int marksToGradePoint(double marks) {
        if (marks >= 90) {
            return 10; // S (previously O)
        } else if (marks >= 80) {
            return 9; // A+
        } else if (marks >= 70) {
            return 8; // A
        } else if (marks >= 60) {
            return 7; // B+
        } else if (marks >= 50) {
            return 6; // B
        } else if (marks >= 40) {
            return 5; // C
        } else {
            return 0; // F (Fail)
        }
    }

    /**
     * Converts marks to letter grade as per KTU 2024 scheme.
     * @param marks Marks scored in the subject
     * @return Letter grade as String (S, A+, A, B+, B, C, F)
     */
    public static String marksToGrade(double marks) {
        if (marks >= 90) {
            return "S";  // Changed from "O" to "S"
        } else if (marks >= 80) {
            return "A+";
        } else if (marks >= 70) {
            return "A";
        } else if (marks >= 60) {
            return "B+";
        } else if (marks >= 50) {
            return "B";
        } else if (marks >= 40) {
            return "C";
        } else {
            return "F";
        }
    }
    
    /**
     * Check if grade point corresponds to pass or fail.
     * @param gradePoint Grade point to check
     * @return true if pass, false if fail
     */
    public static boolean isPass(int gradePoint) {
        return gradePoint > 0;
    }
}
