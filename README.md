# KTU CGPA Analyzer (Using User SyllabusData.java)

This project integrates your provided `SyllabusData.java` (branch-based syllabus) and asks for **detailed marks**
(IA1, IA2, Assignment, External) for each subject up to the current semester. It then computes SGPA per semester,
overall CGPA, and the required average credit-weighted GP across remaining semesters to reach your target CGPA.

## Build & Run
```bash
javac *.java
java Main
```

## Notes
- Edit `GradePolicy.java` if your college uses different maximums or grade mapping.
- The program assumes a total of 100 marks per subject (IA1+IA2+Assignment+External).
- `SyllabusData.java` must provide the correct structure `SyllabusData.Subject[][][] syllabus` as you supplied.
