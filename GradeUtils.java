public class GradeUtils {

    /**
     * Convert marks (0-100) to grade point (0-10 scale)
     * KTU 2024 scheme example:
     * 90-100 -> 10
     * 80-89  -> 9
     * 70-79  -> 8
     * 60-69  -> 7
     * 50-59  -> 6
     * 45-49  -> 5
     * 40-44  -> 4 (minimum pass)
     * <40    -> 0 (fail)
     */
    public static int marksToGradePoint(int marks) {
        if (marks >= 90) return 10;
        if (marks >= 80) return 9;
        if (marks >= 70) return 8;
        if (marks >= 60) return 7;
        if (marks >= 50) return 6;
        if (marks >= 45) return 5;
        if (marks >= 40) return 4;
        return 0;
    }

    /**
     * Convert letter grade to grade point (if using grades like A+, A, B, etc.)
     * Example mapping:
     * A+ -> 10, A -> 9, B+ -> 8, B ->7, C ->6, D ->5, F ->0
     */
    public static int letterGradeToGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A+":
                return 10;
            case "A":
                return 9;
            case "B+":
                return 8;
            case "B":
                return 7;
            case "C":
                return 6;
            case "D":
                return 5;
            case "F":
            case "E":
                return 0;
            default:
                return 0; // Invalid or fail
        }
    }
}
