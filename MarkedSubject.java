package mypack;
import mypack.SyllabusData.Subject;

public class MarkedSubject {
    private final SyllabusData.Subject subject;

    private int ia1;
    private int ia2;
    private int assignment;
    private int labexam;
    private int external;

    public MarkedSubject(SyllabusData.Subject subject) {
        this.subject = subject;
    }
    
    public String getSubjectCode() {
        return subject.subjectCode;
    }
    public int getIa1() {
        return ia1;
    }
    public int getIa2() {
        return ia2;
    }
    public int getAssignment() {
        return assignment;
    }
    public int getLabExam() {
        return labexam;
    }
    public int getExternal() {
        return external;
    } 

    public void setMarks(int ia1, int ia2, int assignment, int lab, int external) {
        this.ia1 = clamp(ia1, GradePolicy.getIA1Max(subject.schemeType));
        this.ia2 = clamp(ia2, GradePolicy.getIA2Max(subject.schemeType));
        this.assignment = clamp(assignment, GradePolicy.getAssignmentMax(subject.schemeType));
        this.labexam = clamp(lab, GradePolicy.getLabMax(subject.schemeType));
        this.external = clamp(external, GradePolicy.getExternalMax(subject.schemeType));
    }

    private int clamp(int val, int max) {
        return Math.max(0, Math.min(val, max));
    }

    public String getName() {
        return subject.name;
    }

    public int getCredit() {
        return subject.credits;
    }

    public String getType() {
        return subject.type;
    }

    public String getSchemeType() {
        return subject.schemeType;
    }
    

    public double totalOutOf100() {
        int maxIA1 = GradePolicy.getIA1Max(subject.schemeType);
        int maxIA2 = GradePolicy.getIA2Max(subject.schemeType);
        int maxAsg = GradePolicy.getAssignmentMax(subject.schemeType);
        int maxExt = GradePolicy.getExternalMax(subject.schemeType);

        double totalMax = maxIA1 + maxIA2 + maxAsg + maxExt;
        double totalScore = ia1 + ia2 + assignment + external;

        if (totalMax == 0) return 0.0;
        return (totalScore / totalMax) * 100.0; // normalize to 100
    }

    public String gradeLetter() {
        return GradePolicy.gradeLetterFromTotal(totalOutOf100());
    }

    public double gradePoint() {
        return GradePolicy.gradePointFromTotal(totalOutOf100());
    }

    public double weightedGradePoints() {
        return gradePoint() * subject.credits;
    }

    @Override
    public String toString() {
        return String.format("%s (%d credits, %s, %s) -> Total: %.1f, Grade: %s, GP: %.1f",
                subject.name, subject.credits, subject.type, subject.schemeType,
                totalOutOf100(), gradeLetter(), gradePoint());
    }
}
