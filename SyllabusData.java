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
        public final String type; // "Theory", "Lab", "Project", "Mandatory", "Viva", "Elective"

        public Subject(String name, int credits, String type) {
            this.name = name;
            this.credits = credits;
            this.type = type;
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
                new Subject("Calculus & Linear Algebra", 4, "Theory"),
                new Subject("Engineering Physics A", 4, "Theory"),
                new Subject("Engineering Chemistry", 4, "Theory"),
                new Subject("Engineering Graphics & CAD", 3, "Theory"),
                new Subject("Basics of Electrical & Electronics Engineering", 3, "Theory"),
                new Subject("Algorithmic Thinking with Python", 3, "Theory"),
                new Subject("Physics Lab", 1, "Lab"),
                new Subject("Chemistry Lab", 1, "Lab"),
                new Subject("Life Skills & Professional Communication", 1, "Mandatory")
        };

        Subject[] sem2Common = new Subject[] {
                new Subject("Differential Equations & Vector Calculus", 4, "Theory"),
                new Subject("Engineering Physics B / Basic Electronics", 4, "Theory"),
                new Subject("Foundations of Computing", 3, "Theory"),
                new Subject("Programming in C", 4, "Theory"),
                new Subject("Engineering Mechanics", 3, "Theory"),
                new Subject("Programming Lab (C)", 1, "Lab"),
                new Subject("Basic Electrical Lab", 1, "Lab"),
                new Subject("Health & Wellness / Professional Ethics", 1, "Mandatory")
        };

        // initialize all branches sem1 and sem2 with common arrays
        for (int b = 0; b < NUM_BRANCHES; b++) {
            syllabus[b][0] = sem1Common;
            syllabus[b][1] = sem2Common;
        }

        // -------------------- CSE (branch 0) Sem 3 - 8 --------------------
        syllabus[0][2] = new Subject[] {
                new Subject("Discrete Mathematical Structures", 4, "Theory"),
                new Subject("Data Structures", 4, "Theory"),
                new Subject("Digital Logic Design", 3, "Theory"),
                new Subject("Computer Organization & Architecture", 3, "Theory"),
                new Subject("Object Oriented Programming", 3, "Theory"),
                new Subject("Data Structures Lab", 1, "Lab"),
                new Subject("OOP Lab", 1, "Lab"),
                new Subject("Economics for Engineers", 2, "Mandatory")
        };

        syllabus[0][3] = new Subject[] {
                new Subject("Probability & Statistics for Engineers", 4, "Theory"),
                new Subject("Database Management Systems", 4, "Theory"),
                new Subject("Operating Systems", 4, "Theory"),
                new Subject("Design & Analysis of Algorithms", 4, "Theory"),
                new Subject("Microprocessors & Interfacing", 3, "Theory"),
                new Subject("DBMS Lab", 1, "Lab"),
                new Subject("OS Lab", 1, "Lab"),
                new Subject("Constitution & Professional Ethics", 0, "Mandatory")
        };

        syllabus[0][4] = new Subject[] {
                new Subject("Computer Networks", 4, "Theory"),
                new Subject("Software Engineering", 4, "Theory"),
                new Subject("Web Technologies", 3, "Theory"),
                new Subject("Artificial Intelligence", 3, "Theory"),
                new Subject("Elective I (PE)", 3, "Elective"),
                new Subject("Networks Lab", 1, "Lab"),
                new Subject("Web Technologies Lab", 1, "Lab"),
                new Subject("Sustainable Development", 2, "Mandatory")
        };

        syllabus[0][5] = new Subject[] {
                new Subject("Machine Learning", 4, "Theory"),
                new Subject("Distributed & Cloud Computing", 4, "Theory"),
                new Subject("Cyber Security", 3, "Theory"),
                new Subject("Big Data Analytics", 3, "Theory"),
                new Subject("Elective II (PE)", 3, "Elective"),
                new Subject("ML / Data Lab", 1, "Lab"),
                new Subject("Mini Project", 2, "Project"),
                new Subject("Disaster Management", 0, "Mandatory")
        };

        syllabus[0][6] = new Subject[] {
                new Subject("Advanced Database Systems", 3, "Theory"),
                new Subject("Computer Vision", 3, "Theory"),
                new Subject("Deep Learning", 3, "Theory"),
                new Subject("Elective III (PE)", 3, "Elective"),
                new Subject("Elective IV (PE)", 3, "Elective"),
                new Subject("Project Phase I", 2, "Project"),
                new Subject("Seminar", 2, "Mandatory")
        };

        syllabus[0][7] = new Subject[] {
                new Subject("Internet of Things", 3, "Theory"),
                new Subject("Elective V (PE)", 3, "Elective"),
                new Subject("Elective VI (PE)", 3, "Elective"),
                new Subject("Major Project Phase II", 8, "Project"),
                new Subject("Comprehensive Viva", 2, "Viva")
        };

        // -------------------- ECE (branch 1) Sem 3 - 8 --------------------
        syllabus[1][2] = new Subject[] {
                new Subject("Network Theory & Transmission Lines", 4, "Theory"),
                new Subject("Analog Electronic Circuits", 4, "Theory"),
                new Subject("Signals & Systems", 4, "Theory"),
                new Subject("Digital Systems", 3, "Theory"),
                new Subject("Analog & Digital Lab", 1, "Lab"),
                new Subject("Signals Lab", 1, "Lab"),
                new Subject("Economics for Engineers", 2, "Mandatory")
        };

        syllabus[1][3] = new Subject[] {
                new Subject("Electronic Devices & Circuits", 4, "Theory"),
                new Subject("Microprocessors & Microcontrollers", 4, "Theory"),
                new Subject("Communication Systems", 4, "Theory"),
                new Subject("Electromagnetic Fields", 3, "Theory"),
                new Subject("Microprocessor Lab", 1, "Lab"),
                new Subject("Communication Lab", 1, "Lab"),
                new Subject("Constitution & Professional Ethics", 0, "Mandatory")
        };

        syllabus[1][4] = new Subject[] {
                new Subject("Digital Signal Processing", 4, "Theory"),
                new Subject("VLSI Design", 4, "Theory"),
                new Subject("Control Systems", 3, "Theory"),
                new Subject("Microwave Engineering", 3, "Theory"),
                new Subject("DSP / VLSI Lab", 1, "Lab"),
                new Subject("Control Systems Lab", 1, "Lab"),
                new Subject("Sustainable Development", 2, "Mandatory")
        };

        syllabus[1][5] = new Subject[] {
                new Subject("Wireless Communication", 4, "Theory"),
                new Subject("Antenna & Wave Propagation", 3, "Theory"),
                new Subject("Embedded Systems", 3, "Theory"),
                new Subject("Digital Image Processing", 3, "Theory"),
                new Subject("Elective I (PE)", 3, "Elective"),
                new Subject("Embedded Systems Lab", 1, "Lab"),
                new Subject("Image Processing Lab", 1, "Lab"),
                new Subject("Disaster Management", 0, "Mandatory")
        };

        syllabus[1][6] = new Subject[] {
                new Subject("Optical Communication", 3, "Theory"),
                new Subject("Advanced VLSI", 3, "Theory"),
                new Subject("Signal Processing for Comm.", 3, "Theory"),
                new Subject("Elective II (PE)", 3, "Elective"),
                new Subject("Elective III (PE)", 3, "Elective"),
                new Subject("Project Phase I", 2, "Project"),
                new Subject("Seminar", 2, "Mandatory")
        };

        syllabus[1][7] = new Subject[] {
                new Subject("Advanced Communication Systems", 3, "Theory"),
                new Subject("Elective IV (PE)", 3, "Elective"),
                new Subject("Elective V (PE)", 3, "Elective"),
                new Subject("Major Project Phase II", 8, "Project"),
                new Subject("Comprehensive Viva", 2, "Viva")
        };

        // -------------------- EEE (branch 2) Sem 3 - 8 --------------------
        syllabus[2][2] = new Subject[] {
                new Subject("Circuit Theory", 4, "Theory"),
                new Subject("Electrical Measurements & Instrumentation", 4, "Theory"),
                new Subject("Electromagnetic Theory", 3, "Theory"),
                new Subject("Power Systems I", 3, "Theory"),
                new Subject("Circuit & Instrumentation Lab", 1, "Lab"),
                new Subject("Power Systems Lab", 1, "Lab"),
                new Subject("Economics for Engineers", 2, "Mandatory")
        };

        syllabus[2][3] = new Subject[] {
                new Subject("Electrical Machines I", 4, "Theory"),
                new Subject("Power Electronics", 4, "Theory"),
                new Subject("Control Systems", 3, "Theory"),
                new Subject("Power System Analysis", 4, "Theory"),
                new Subject("Machines Lab", 1, "Lab"),
                new Subject("Power Electronics Lab", 1, "Lab"),
                new Subject("Constitution & Professional Ethics", 0, "Mandatory")
        };

        syllabus[2][4] = new Subject[] {
                new Subject("Advanced Power Electronics", 4, "Theory"),
                new Subject("High Voltage Engineering", 3, "Theory"),
                new Subject("Electrical Drives", 3, "Theory"),
                new Subject("Renewable Energy Systems", 3, "Theory"),
                new Subject("Power Systems Lab II", 1, "Lab"),
                new Subject("Drives Lab", 1, "Lab"),
                new Subject("Sustainable Development", 2, "Mandatory")
        };

        syllabus[2][5] = new Subject[] {
                new Subject("Smart Grid Technologies", 3, "Theory"),
                new Subject("Power System Protection", 3, "Theory"),
                new Subject("Flexible AC Transmission Systems", 3, "Theory"),
                new Subject("Elective I (PE)", 3, "Elective"),
                new Subject("Protection & Control Lab", 1, "Lab"),
                new Subject("Mini Project", 2, "Project"),
                new Subject("Disaster Management", 0, "Mandatory")
        };

        syllabus[2][6] = new Subject[] {
                new Subject("Power Quality", 3, "Theory"),
                new Subject("Advanced Drives & Control", 3, "Theory"),
                new Subject("Elective II (PE)", 3, "Elective"),
                new Subject("Elective III (PE)", 3, "Elective"),
                new Subject("Project Phase I", 2, "Project"),
                new Subject("Seminar", 2, "Mandatory")
        };

        syllabus[2][7] = new Subject[] {
                new Subject("Power System Operation & Control", 3, "Theory"),
                new Subject("Elective IV (PE)", 3, "Elective"),
                new Subject("Elective V (PE)", 3, "Elective"),
                new Subject("Major Project Phase II", 8, "Project"),
                new Subject("Comprehensive Viva", 2, "Viva")
        };

        // -------------------- ME (branch 3) Sem 3 - 8 --------------------
        syllabus[3][2] = new Subject[] {
                new Subject("Engineering Thermodynamics", 4, "Theory"),
                new Subject("Strength of Materials", 4, "Theory"),
                new Subject("Mechanics of Materials / Engineering Mechanics", 3, "Theory"),
                new Subject("Materials & Metallurgy", 3, "Theory"),
                new Subject("Strength of Materials Lab", 1, "Lab"),
                new Subject("Workshop Practice", 1, "Lab"),
                new Subject("Economics for Engineers", 2, "Mandatory")
        };

        syllabus[3][3] = new Subject[] {
                new Subject("Fluid Mechanics", 4, "Theory"),
                new Subject("Theory of Machines", 4, "Theory"),
                new Subject("Kinematics & Dynamics", 3, "Theory"),
                new Subject("Manufacturing Processes", 3, "Theory"),
                new Subject("Fluid Mechanics Lab", 1, "Lab"),
                new Subject("Manufacturing Lab", 1, "Lab"),
                new Subject("Constitution & Professional Ethics", 0, "Mandatory")
        };

        syllabus[3][4] = new Subject[] {
                new Subject("Machine Design", 4, "Theory"),
                new Subject("Heat Transfer", 3, "Theory"),
                new Subject("CAD/CAM", 3, "Theory"),
                new Subject("Dynamics of Machines", 3, "Theory"),
                new Subject("CAD/CAM Lab", 1, "Lab"),
                new Subject("Machine Design Lab", 1, "Lab"),
                new Subject("Sustainable Development", 2, "Mandatory")
        };

        syllabus[3][5] = new Subject[] {
                new Subject("Finite Element Analysis", 3, "Theory"),
                new Subject("Advanced Manufacturing", 3, "Theory"),
                new Subject("Automobile Engineering", 3, "Theory"),
                new Subject("Elective I (PE)", 3, "Elective"),
                new Subject("FEM / Manufacturing Lab", 1, "Lab"),
                new Subject("Mini Project", 2, "Project"),
                new Subject("Disaster Management", 0, "Mandatory")
        };

        syllabus[3][6] = new Subject[] {
                new Subject("Advanced Machine Design", 3, "Theory"),
                new Subject("Robotics & Automation", 3, "Theory"),
                new Subject("Elective II (PE)", 3, "Elective"),
                new Subject("Elective III (PE)", 3, "Elective"),
                new Subject("Project Phase I", 2, "Project"),
                new Subject("Seminar", 2, "Mandatory")
        };

        syllabus[3][7] = new Subject[] {
                new Subject("Advanced Manufacturing Systems", 3, "Theory"),
                new Subject("Elective IV (PE)", 3, "Elective"),
                new Subject("Elective V (PE)", 3, "Elective"),
                new Subject("Major Project Phase II", 8, "Project"),
                new Subject("Comprehensive Viva", 2, "Viva")
        };

        // -------------------- CE (branch 4) Sem 3 - 8 --------------------
        syllabus[4][2] = new Subject[] {
                new Subject("Strength of Materials", 4, "Theory"),
                new Subject("Surveying & Geomatics", 4, "Theory"),
                new Subject("Structural Analysis I", 4, "Theory"),
                new Subject("Building Materials & Construction", 3, "Theory"),
                new Subject("Surveying Lab", 1, "Lab"),
                new Subject("Material Testing Lab", 1, "Lab"),
                new Subject("Economics for Engineers", 2, "Mandatory")
        };

        syllabus[4][3] = new Subject[] {
                new Subject("Fluid Mechanics", 4, "Theory"),
                new Subject("Concrete Technology", 3, "Theory"),
                new Subject("Structural Analysis II", 4, "Theory"),
                new Subject("Geotechnical Engineering I", 4, "Theory"),
                new Subject("Concrete Lab", 1, "Lab"),
                new Subject("Fluid Mechanics Lab", 1, "Lab"),
                new Subject("Constitution & Professional Ethics", 0, "Mandatory")
        };

        syllabus[4][4] = new Subject[] {
                new Subject("Transportation Engineering I", 4, "Theory"),
                new Subject("Environmental Engineering I", 3, "Theory"),
                new Subject("Geotechnical Engineering II", 3, "Theory"),
                new Subject("Open Elective I (OE)", 3, "Elective"),
                new Subject("Transportation Engineering Lab", 1, "Lab"),
                new Subject("Environmental Engineering Lab", 1, "Lab"),
                new Subject("Sustainable Development", 2, "Mandatory")
        };

        syllabus[4][5] = new Subject[] {
                new Subject("Structural Design I", 4, "Theory"),
                new Subject("Water Resources Engineering", 3, "Theory"),
                new Subject("Elective I (PE)", 3, "Elective"),
                new Subject("Structural Design Lab", 1, "Lab"),
                new Subject("Mini Project", 2, "Project"),
                new Subject("Disaster Management", 0, "Mandatory")
        };

        syllabus[4][6] = new Subject[] {
                new Subject("Transportation Engineering II", 3, "Theory"),
                new Subject("Environmental Engineering II", 3, "Theory"),
                new Subject("Elective II (PE)", 3, "Elective"),
                new Subject("Project Phase I", 2, "Project"),
                new Subject("Seminar", 2, "Mandatory")
        };

        syllabus[4][7] = new Subject[] {
                new Subject("Advanced Structural Design", 3, "Theory"),
                new Subject("Elective III (PE)", 3, "Elective"),
                new Subject("Major Project Phase II", 8, "Project"),
                new Subject("Comprehensive Viva", 2, "Viva")
        };
    }

    // Helper to get subjects by branch and semester
    public static Subject[] getSubjects(int branchIndex, int semesterIndex) {
        if (branchIndex < 0 || branchIndex >= NUM_BRANCHES
                || semesterIndex < 0 || semesterIndex >= NUM_SEMS) {
            return new Subject[0];
        }
        Subject[] subjects = syllabus[branchIndex][semesterIndex];
        return subjects != null ? subjects : new Subject[0];
    }

    // Helper to get all branch names
    public static String[] getAllBranches() {
        return BRANCH_NAMES.clone();
    }
}