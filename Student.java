import java.util.*;

public class Student {
    private final String name;
    private final String rollNumber;
    private double targetCGPA;
    private final List<Semester> semesters = new ArrayList<>();

    public Student(String name, String rollNumber, double targetCGPA) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.targetCGPA = targetCGPA;
    }

    public void addSemester(Semester sem) { semesters.add(sem); }

    public List<Semester> getSemesters() { return Collections.unmodifiableList(semesters); }

    public double getTotalCreditsSoFar() {
        double c = 0.0;
        for (Semester s : semesters) c += s.getTotalCredits();
        return c;
    }

    public double getTotalWeightedGradePointsSoFar() {
        double wp = 0.0;
        for (Semester sem : semesters) {
            for (MarkedSubject s : sem.getSubjects()) {
                wp += s.weightedGradePoints();
            }
        }
        return wp;
    }

    public double calculateCGPA() {
        double totalCredits = getTotalCreditsSoFar();
        double totalGradePoints = getTotalWeightedGradePointsSoFar();
        return (totalCredits == 0.0) ? 0.0 : totalGradePoints / totalCredits;
    }

    // required average (credit-weighted) GP across remaining credits
    public double requiredAvgGPAcrossRemainingCredits(double remainingCredits) {
        if (remainingCredits <= 0) return 0.0;
        double totalCredits = getTotalCreditsSoFar();
        double totalGradePoints = getTotalWeightedGradePointsSoFar();
        double requiredTotalGradePoints = targetCGPA * (totalCredits + remainingCredits);
        double requiredRemainingGradePoints = requiredTotalGradePoints - totalGradePoints;
        return requiredRemainingGradePoints / remainingCredits;
    }

    // predict SGPA from trend (average of past SGPAs)
    public double predictNextSemSGPAFromTrend() {
        if (semesters.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Semester s : semesters) sum += s.calculateSGPA();
        return sum / semesters.size();
    }

    public void setTargetCGPA(double t) { this.targetCGPA = t; }
    public double getTargetCGPA() { return targetCGPA; }
}
