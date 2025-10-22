package mypack;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataAccess {

    public Student findByEmail(String email) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        Student student = null;
        int branchIndex = -1;

        String selectStudentSql = "SELECT name, password, branch_index, roll_number, target_cgpa FROM students WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(selectStudentSql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                branchIndex = rs.getInt("branch_index");
                student = new Student(
                    rs.getString("name"),
                    email,
                    rs.getString("password"),
                    branchIndex,
                    rs.getDouble("target_cgpa")
                );
                student.setRollNumber(rs.getString("roll_number"));
            } else {
                return null;
            }
        }

        String selectSubjectsSql = "SELECT * FROM marked_subjects WHERE student_email = ? ORDER BY semester_number";
        Map<Integer, Semester> semestersMap = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(selectSubjectsSql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int semNum = rs.getInt("semester_number");
                String subjectCode = rs.getString("subject_code");

                SyllabusData.Subject syllabusSubject = SyllabusData.findSubjectByCode(branchIndex, subjectCode);
                if (syllabusSubject == null) continue;

                MarkedSubject ms = new MarkedSubject(syllabusSubject);
                ms.setMarks(rs.getInt("ia1"), rs.getInt("ia2"), rs.getInt("assignment"), rs.getInt("lab_exam"), rs.getInt("external_exam"));
                Semester semester = semestersMap.computeIfAbsent(semNum, Semester::new);
                semester.addSubject(ms);
            }
        }

        for (Semester sem : semestersMap.values()) {
            // Use the correct method here
            student.addOrUpdateSemester(sem);
        }
        return student;
    }

    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (email, name, password, branch_index, roll_number, target_cgpa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getEmail());
            ps.setString(2, student.getName());
            ps.setString(3, student.getPassword());
            ps.setInt(4, student.getBranchIndex());
            ps.setString(5, student.getRollNumber());
            ps.setDouble(6, student.getTargetCGPA());
            ps.executeUpdate();
        }
    }

    public void update(Student student) throws SQLException {
        Connection conn = DatabaseManager.getConnection();

        String updateStudentSql = "UPDATE students SET name = ?, password = ?, branch_index = ?, roll_number = ?, target_cgpa = ? WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateStudentSql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getPassword());
            ps.setInt(3, student.getBranchIndex());
            ps.setString(4, student.getRollNumber());
            ps.setDouble(5, student.getTargetCGPA());
            ps.setString(6, student.getEmail());
            ps.executeUpdate();
        }

        String deleteSubjectsSql = "DELETE FROM marked_subjects WHERE student_email = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteSubjectsSql)) {
            ps.setString(1, student.getEmail());
            ps.executeUpdate();
        }
        String insertSubjectSql = "INSERT INTO marked_subjects (student_email, semester_number, subject_code, ia1, ia2, assignment, lab_exam, external_exam) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSubjectSql)) {
            for (Semester sem : student.getSemesters()) {
                for (MarkedSubject ms : sem.getSubjects()) {
                    ps.setString(1, student.getEmail());
                    ps.setInt(2, sem.getSemesterNumber());
                    ps.setString(3, ms.getSubjectCode());
                    ps.setInt(4, ms.getIa1());
                    ps.setInt(5, ms.getIa2());
                    ps.setInt(6, ms.getAssignment());
                    ps.setInt(7, ms.getLabExam());
                    ps.setInt(8, ms.getExternal());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }
}