import java.util.*;

public class Semester {
    private final int semesterNumber;
    private final List<MarkedSubject> subjects = new ArrayList<>();

    public Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void addSubject(MarkedSubject s) { subjects.add(s); }

    public List<MarkedSubject> getSubjects() { return Collections.unmodifiableList(subjects); }

    public double calculateSGPA() {
        double totalCredits = 0.0;
        double totalGradePoints = 0.0;
        for (MarkedSubject s : subjects) {
            totalCredits += s.getCredit();
            totalGradePoints += s.weightedGradePoints();
        }
        return (totalCredits == 0.0) ? 0.0 : totalGradePoints / totalCredits;
    }

    public double getTotalCredits() {
        double sum = 0.0;
        for (MarkedSubject s : subjects) sum += s.getCredit();
        return sum;
    }

    public int getSemesterNumber() { return semesterNumber; }
}
