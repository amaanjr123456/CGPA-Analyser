public class GradePolicy {
    // ====== KTU 2024 (editable) ======
    // Internal components assumed: IA1 15, IA2 15, Assignment/Activity 10 = 40
    // External exam assumed: 60
    // Total = 100
    public static final int IA1_MAX = 15;
    public static final int IA2_MAX = 15;
    public static final int ASSIGNMENT_MAX = 10;
    public static final int EXTERNAL_MAX = 60;

    // Minimum total (out of 100) to pass
    public static final double PASS_MIN_TOTAL = 40.0;

    public static double gradePointFromTotal(double total) {
        if (total >= 90) return 10.0;   // O
        if (total >= 85) return 9.0;    // A+
        if (total >= 75) return 8.5;    // A
        if (total >= 65) return 8.0;    // B+
        if (total >= 55) return 7.0;    // B
        if (total >= 50) return 6.0;    // C
        if (total >= PASS_MIN_TOTAL) return 5.0; // P
        return 0.0; // F
    }

    public static String gradeLetterFromTotal(double total) {
        if (total >= 90) return "S";
        if (total >= 85) return "A+";
        if (total >= 75) return "A";
        if (total >= 65) return "B+";
        if (total >= 55) return "B";
        if (total >= 50) return "C";
        if (total >= PASS_MIN_TOTAL) return "P";
        return "F";
    }
}
