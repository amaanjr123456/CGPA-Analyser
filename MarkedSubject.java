public class MarkedSubject {
    private final String name;
    private final int credit;
    private final String type;

    private int ia1 = 0;
    private int ia2 = 0;
    private int assignment = 0;
    private int external = 0;

    public MarkedSubject(SyllabusData.Subject s) {
        this.name = s.name;
        this.credit = s.credits;
        this.type = s.type;
    }

    public void setMarks(int ia1, int ia2, int assignment, int external) {
        this.ia1 = ia1;
        this.ia2 = ia2;
        this.assignment = assignment;
        this.external = external;
    }

    public double totalOutOf100() {
        return ia1 + ia2 + assignment + external;
    }

    public double gradePoint() {
        return GradePolicy.gradePointFromTotal(totalOutOf100());
    }

    public String gradeLetter() {
        return GradePolicy.gradeLetterFromTotal(totalOutOf100());
    }

    public double weightedGradePoints() {
        return gradePoint() * credit;
    }

    public int getCredit() { return credit; }
    public String getName() { return name; }
    public String getType() { return type; }

    public int getIa1() { return ia1; }
    public int getIa2() { return ia2; }
    public int getAssignment() { return assignment; }
    public int getExternal() { return external; }
}
