package mypack;
import java.util.*;

public class Semester {
    private final int semesterNumber;
    private final List<MarkedSubject> subjects = new ArrayList<>();

    public Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void addSubject(MarkedSubject s) {
        subjects.add(s);
    }

    public List<MarkedSubject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    // --- NEW METHOD ---
    // Finds a subject within this semester by its code.
    public MarkedSubject getSubject(String subjectCode) {
        for (MarkedSubject ms : subjects) {
            if (ms.getSubjectCode().equals(subjectCode)) {
                return ms;
            }
        }
        return null; // Return null if no subject with that code is found
    }
    // --- END NEW METHOD ---

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
        for (MarkedSubject s : subjects) {
            sum += s.getCredit();
        }
        return sum;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }
}