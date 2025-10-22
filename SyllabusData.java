package mypack;
public class SyllabusData {

    // Branch mapping
    // 0 = CSE
    // 1 = ECE
    // 2 = EEE
    // 3 = ME
    // 4 = CE

    public static final int NUM_BRANCHES = 5;
    public static final int NUM_SEMS = 8;

    public static class Subject {
        public final String name;
        public final int credits;
        public final String type;       // "Theory", "Lab", "Project", "Elective", "Mandatory"
        public final String schemeType;
        public final String subjectCode;// "CORE", "PBL", "NONCORE", "LAB", "PROJECT"

        public Subject(String name, int credits, String type, String schemeType, String subjectCode) {
            this.name = name;
            this.credits = credits;
            this.type = type;
            this.schemeType = schemeType;
            this.subjectCode = subjectCode;
        }
    }

    public static final String[] BRANCH_NAMES = {
            "Computer Science & Engineering",
            "Electronics & Communication Engineering",
            "Electrical & Electronics Engineering",
            "Mechanical Engineering",
            "Civil Engineering"
    };

    // syllabus[branchIndex][semesterIndex] -> Subject[]
    public static final Subject[][][] syllabus = new Subject[NUM_BRANCHES][NUM_SEMS][];

    static {
        // -------------------- Common First Year: Sem 1 & Sem 2 --------------------
    	Subject[] sem1Common = new Subject[] {
                new Subject("Mathematics For Information Science-1", 3, "Theory", "CORE3","GAMAT101"),
                new Subject("Physics For Information Science", 4, "Theory+Lab", "CORE1","GAPHT121"),
                new Subject("Engineering Graphics & CAD", 3, "Theory", "CORE3","GMEST103"),
                new Subject("Introduction To Electrical And Electronics Engineering", 4, "Theory", "CORE3","GXEST104"),
                new Subject("Algorithmic Thinking with Python", 4, "Theory+Lab", "CORE2","UCEST105"),
                new Subject("Basic Electrical And Electronics Engineering Workshop", 1, "Lab", "LAB","GXESL106"),
        };

        Subject[] sem2Common = new Subject[] {
                new Subject("Mathematics For Information Science-2", 3, "Theory", "CORE3","GAMAT201"),
                new Subject("Chemistry For Information Science and Electrical Science", 4, "Theory+Lab", "CORE1","GXCYT122"),
                new Subject("Foundations of Computing", 3, "Theory", "CORE3","GXEST203"),
                new Subject("Programming in C", 4, "Theory+Lab", "CORE2","GXEST204"),
                new Subject("Engineering Entrepreneurship And IPR", 3, "Project", "PROJECT","UCEST206"),
                new Subject("IT Workshop", 1, "Lab", "LAB","GXESL208"),
                new Subject("Discrete Mathematics", 4, "Theory", "CORE3","PCCST205"),
        };

        // -------------------- CSE --------------------
        syllabus[0][0] = sem1Common;
        syllabus[0][1] = sem2Common;
        syllabus[0][2] = new Subject[] {
            new Subject("Mathematics For Computer And Information Science-3", 3, "Theory", "CORE3","GAMAT301"),
            new Subject("Theory of Computation", 4, "Theory", "CORE3","PCCST302"),
            new Subject("Data Structures And Algorithms", 4, "Theory", "CORE3","PCCST303"),
            new Subject("Object Oriented Programming", 4, "Theory", "PBL","PBCST304"),
            new Subject("Digital Electronics And Logic Design", 4, "Theory", "CORE3","GAEST305"),
            new Subject("Economics for Engineers", 2, "Theory", "NONCORE","UCHUT346"),
            new Subject("Data Structures Lab", 2, "Lab", "LAB","PCCSL307"),
            new Subject("Digital Lab", 2, "Lab", "LAB","PCCSL308")
        };
        syllabus[0][3] = new Subject[] {
            new Subject("Mathematics For Computer And Information Science-4", 3, "Theory", "CORE3","GAMAT401"),
            new Subject("Computer Organization & Architecture", 4, "Theory", "PBL","PBCST404"),
            new Subject("Operating Systems", 4, "Theory", "CORE3","PCCST403"),
            new Subject("Database Management Systems", 4, "Theory", "CORE3","PCCST402"),
            new Subject("Engineering Ethics And Sustainable Development", 2, "Theory", "NONCORE","UCHUT347"),
            new Subject("Software Engineering", 3, "Theory", "CORE3","PECST411"),
            new Subject("Pattern Recognition", 3, "Theory", "CORE3","PECST412"),
            new Subject("Functional Programming", 3, "Theory", "CORE3","PECST413"),
            new Subject("Coding Theory", 3, "Theory", "CORE3","PECST414"),
            new Subject("Signals And Systems", 3, "Theory", "CORE3","PECST416"),
            new Subject("Soft Computing", 3, "Theory", "CORE3","PECST417"),
            new Subject("Computational Geometry", 3, "Theory", "CORE3","PECST418"),
            new Subject("Cyber Ethics,Privacy And Legal Issues", 3, "Theory", "CORE3","PECST419"),
            new Subject("VLSI Design", 3, "Elective", "CORE3","PECST415"),
            new Subject("Advanced Data Structures", 3, "Theory", "CORE3","PECST495"),
            new Subject("DBMS Lab", 2, "Lab", "LAB","PCCSL408"),
            new Subject("OS Lab", 2, "Lab", "LAB","PCCSL407")
        };
        syllabus[0][4] = new Subject[] {
            new Subject("Computer Networks", 4, "Theory", "CORE3","PCCST501"),
            new Subject("Design And Analysis Of Algorithms", 4, "Theory", "CORE3","PCCST502"),
            new Subject("Microcontrollers", 4, "Theory", "PBL","PBCST504"),
            new Subject("Machine Learning", 3, "Theory", "CORE3","PCCST503"),
            new Subject("Software Project Management", 3, "Theory", "CORE3","PECST521"),
            new Subject("Artificial Intelligence", 3, "Theory", "CORE3","PECST522"),
            new Subject("Data Analytics", 3, "Theory", "CORE3","PECST523"),
            new Subject("Data Compression", 3, "Theory", "CORE3","PECST524"),
            new Subject("Digital Signal Processing", 3, "Theory", "CORE3","PECST526"),
            new Subject("Computer Graphics & Multimedia", 3, "Theory", "CORE3","PECST527"),
            new Subject("Advanced Computer Architecture", 3, "Theory", "CORE3","PECST528"),
            new Subject("Data Mining", 5/3, "Theory", "CORE3","PECST525"),
            new Subject("Advanced Graph Algorithms", 5/3, "Theory", "CORE3","PECST595"),
            new Subject("Networks Lab", 2, "Lab", "LAB","PCCSL507"),
            new Subject("Machine Learning Lab", 2, "Lab", "LAB","PCCSL508")
        };
        syllabus[0][5] = new Subject[] {
            new Subject("Compiler Design", 4, "Theory", "CORE3","PCCST601"),
            new Subject("Advanced Computing Systems", 3, "Theory", "CORE3","PCCST602"),
            new Subject("Software Testing", 3, "Theory", "CORE3","PECST631"),
            new Subject("Deep Learning", 3, "Theory", "CORE3","PECST632"),
            new Subject("Wireless & Mobile Computing", 3, "Theory", "CORE3","PECST633"),
            new Subject("Advanced Database Systems", 3, "Theory", "CORE3","PECST634"),
            new Subject("Digital Image Processing", 3, "Theory", "CORE3","PECST636"),
            new Subject("Fundamentals Of Cryptography", 3, "Theory", "CORE3","PECST637"),
            new Subject("Quantum Computing", 3, "Theory", "CORE3","PECST638"),
            new Subject("Randomized Algorithms", 3, "Theory", "CORE3","PECST639"),
            new Subject("Cloud Computing", 5/3, "Theory", "CORE3","PECST635"),
            new Subject("Mobile Application Development", 5/3, "Theory", "CORE3","PECST695"),
            new Subject("Data Structures", 3, "Theory", "CORE3","OECST611"),
            new Subject("Data Communictaion", 3, "Theory", "CORE3","OECST612"),
            new Subject("Foundations Of Cryptography", 3, "Theory", "CORE3","OECST613"),
            new Subject("Machine Learning For Engineers", 3, "Theory", "CORE3","OECST614"),
            new Subject("Systems Lab", 2, "Lab", "LAB","PCCSL607")
        };
        syllabus[0][6] = new Subject[] {
            new Subject("Formal Methods In Software Engineering", 3, "Theory", "CORE3","PECST741"),
            new Subject("Web Programming", 3, "Theory", "CORE3","PECST742"),
            new Subject("Bioinformatics", 3, "Theory", "CORE3","PECST743"),
            new Subject("Information Security", 3, "Theory", "CORE3","PECST744"),
            new Subject("Embedded Systems", 3, "Theory", "CORE3","PECST746"),
            new Subject("Blockchain And Cryptocurrencies", 3, "Theory", "CORE3","PECST747"),
            new Subject("Real Time Systems", 3, "Theory", "CORE3","PECST748"),
            new Subject("Approximation Algorithms", 3, "Theory", "CORE3","PECST749"),
            new Subject("Computer Vision", 5/3, "Theory", "CORE3","PECST745"),
            new Subject("Topics In Theoretical Computer Science", 5/3, "Theory", "CORE3","PECST795"),
            new Subject("Advanced Computer Networks", 3, "Theory", "CORE3","PECST751"),
            new Subject("Responsible Artificial Intelligence", 3, "Theory", "CORE3","PECST752"),
            new Subject("Fuzzy Systems", 3, "Theory", "CORE3","PECST753"),
            new Subject("Digital Forensics", 3, "Theory", "CORE3","PECST754"),
            new Subject("Game Theory And Mechanism Design", 3, "Theory", "CORE3","PECST753"),
            new Subject("High Performance Computing", 3, "Theory", "CORE3","PECST757"),
            new Subject("Programming Languages", 3, "Theory", "CORE3","PECST758"),
            new Subject("Parallel Algorithms", 3, "Theory", "CORE3","PECST759"),
            new Subject("Internet Of Things", 5/3, "Theory", "CORE3","PECST755"),
            new Subject("Algorithms For Data Science", 5/3, "Theory", "CORE3","PECST785"),
            new Subject("Cyber Security", 3, "Theory", "CORE3","OECST721"),
            new Subject("Computer Networks", 3, "Theory", "CORE3","OECST724")
        };
        syllabus[0][7] = new Subject[] {
            new Subject("Software Architectures", 3, "Theory", "CORE3","PECST861"),
            new Subject("Natural Language Processing", 3, "Theory", "CORE3","PECST862"),
            new Subject("Topics In Security", 3, "Theory", "CORE3","PECST863"),
            new Subject("Computational Complexity", 3, "Theory", "CORE3","PECST864"),
            new Subject("Speech And Audio Processing", 3, "Theory", "CORE3","PECST866"),
            new Subject("Storage Systems", 3, "Theory", "CORE3","PECST867"),
            new Subject("Prompt Engineering", 3, "Theory", "CORE3","PECST868"),
            new Subject("Computational Number Theory", 3, "Theory", "CORE3","PECST869"),
            new Subject("Next Generation Interaction Design", 5/3, "Theory", "CORE3","PECST865"),
            new Subject("Computer Graphics", 3, "Theory", "CORE3", "OECST")
        };
    }  


    public static String[] getAllBranches() {
        return BRANCH_NAMES;
    }

    public static Subject[] getSubjects(int branch, int semIndex) {
        return syllabus[branch][semIndex];
    }
    
    public static Subject findSubjectByCode(int branch, String subjectCode) {
        // Safety check to prevent errors if an invalid branch is given
        if (branch < 0 || branch >= syllabus.length) {
            return null;
        }

        // Loop through all semesters for the given branch
        for (int sem = 0; sem < syllabus[branch].length; sem++) {
            // Check if the semester has subjects defined
            if (syllabus[branch][sem] != null) {
                // Loop through all subjects in that semester
                for (Subject subject : syllabus[branch][sem]) {
                    // If the code matches, we found it! Return the subject.
                    if (subject.subjectCode.equals(subjectCode)) {
                        return subject;
                    }
                }
            }
        }
        // If we finish all loops and haven't found a match, return null.
        return null;
    }
}