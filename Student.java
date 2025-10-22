package mypack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private final String name;
    private final String email;
    private String password;
    private int branchIndex;
    private String rollNumber;
    private double targetCGPA;
    private final List<Semester> semesters = new ArrayList<>();

    public Student(String name, String email, String password, int branchIndex, double targetCGPA) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.branchIndex = branchIndex;
        this.targetCGPA = targetCGPA;
    }

    public void addOrUpdateSemester(Semester sem) {
        semesters.removeIf(s -> s.getSemesterNumber() == sem.getSemesterNumber());
        semesters.add(sem);
        semesters.sort((s1, s2) -> Integer.compare(s1.getSemesterNumber(), s2.getSemesterNumber()));
    }

    public List<Semester> getSemesters() {
        return Collections.unmodifiableList(semesters);
    }
    
    public Semester getSemester(int semesterNumber) {
        return semesters.stream()
            .filter(s -> s.getSemesterNumber() == semesterNumber)
            .findFirst()
            .orElse(null);
    }

    public double getTotalCreditsSoFar() {
        double c = 0.0;
        for (Semester s : semesters) {
            c += s.getTotalCredits();
        }
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

    public double requiredAvgGPAcrossRemainingCredits(double remainingCredits) {
        if (remainingCredits <= 0) {
            return 0.0;
        }
        double totalCredits = getTotalCreditsSoFar();
        double totalGradePoints = getTotalWeightedGradePointsSoFar();
        double requiredTotalGradePoints = targetCGPA * (totalCredits + remainingCredits);
        double requiredRemainingGradePoints = requiredTotalGradePoints - totalGradePoints;
        return requiredRemainingGradePoints / remainingCredits;
    }

    public double predictNextSemSGPAFromTrend() {
        if (semesters.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Semester s : semesters) {
            sum += s.calculateSGPA();
        }
        return sum / semesters.size();
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public double getTargetCGPA() { return targetCGPA; }
    public void setTargetCGPA(double t) { this.targetCGPA = t; }
    public String getName() { return name; }
    public int getBranchIndex() { return branchIndex; }
    public void setBranchIndex(int branchIndex) { this.branchIndex = branchIndex; }

    public int getLastSemesterNumber() {
        if (semesters.isEmpty()) {
            return 0;
        }
        return semesters.get(semesters.size() - 1).getSemesterNumber();
    }
}