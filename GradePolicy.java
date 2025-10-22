package mypack;
public class GradePolicy {

    // ====== KTU 2024 Rules ======
    public static final double PASS_MIN_TOTAL = 40.0;

    // Internal/External split based on schemeType
    public static int getIA1Max(String schemeType) {
        switch (schemeType) {
            case "CORE1": return 10;
            case "CORE2": return 10;
            case "CORE3": return 10;
            case "PBL": return (int) 12.5;   // split inside CA
            case "NONCORE": return (int)12.5;
            case "LAB": return 45;
            case "PROJECT": return 10;
            default: return 0;
        }
    }

    public static int getIA2Max(String schemeType) {
        switch (schemeType) {
            case "CORE1": return 10;//phychem
            case "CORE2": return 10;//python
            case "CORE3": return 10;//math,graphics
            case "PBL": return (int) 12.5;
            case "NONCORE": return (int)12.5;
            case "PROJECT": return 10;
            default: return 0;
        }
    }

    public static int getLabMax(String schemeType) {
        switch (schemeType) {
            case "CORE1": return 5;
            case "CORE2": return 10;
            default: return 0;
        }
    }

    public static int getAssignmentMax(String schemeType) {
        switch (schemeType) {
            case "CORE1": return 15;
            case "CORE2": return 10;
            case "CORE3": return 20;
            case "PBL": return 35; // merged with CA
            case "NONCORE": return 25;
            case "LAB": return 5;
            case "PROJECT": return 40;
            default: return 0;
        }
    }

    public static int getExternalMax(String schemeType) {
        switch (schemeType) {
            case "CORE1": return 60;
            case "CORE2": return 60;
            case "CORE3": return 60;
            case "PBL": return 40;
            case "NONCORE": return 50;
            case "LAB": return 50;
            case "PROJECT": return 40;
            default: return 60;
        }
    }

    // ---------- Grade Point Calculation ----------
    public static double gradePointFromTotal(double total) {
        if (total >= 90) return 10.0;   // S
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
