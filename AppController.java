package mypack;
import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AppController {
    private JFrame mainFrame;
    private final DataAccess dataAccess;
    private Student loggedInStudent;

    public AppController() {
        this.dataAccess = new DataAccess();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppController controller = new AppController();
            controller.start();
        });
    }

    public void start() {
        new LoadingScreen(this);
    }

    public void showAuthScreen() {
        mainFrame = new JFrame("Semester Planner - Authentication");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AuthScreen authScreenPanel = new AuthScreen(this);
        mainFrame.setContentPane(authScreenPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    public void showSetupScreen(Student newStudent) {
        mainFrame.setTitle("Semester Planner - Profile Setup");
        SemesterSetupPanel setupPanel = new SemesterSetupPanel(this, newStudent);
        mainFrame.setContentPane(setupPanel);
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    public void showMainDashboard() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        mainFrame = new MainDashboard(this, loggedInStudent);
    }
    
    public void logout() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        loggedInStudent = null;
        showAuthScreen();
    }

    public void attemptLogin(String email, String password) {
        try {
            Student foundUser = dataAccess.findByEmail(email);
            if (foundUser != null && foundUser.getPassword().equals(password)) {
                this.loggedInStudent = foundUser;
                JOptionPane.showMessageDialog(null, "Login Successful!");
                showMainDashboard();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error during login.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void attemptRegistration(String name, String email, String password) {
        try {
            if (dataAccess.findByEmail(email) != null) {
                JOptionPane.showMessageDialog(mainFrame, "An account with this email already exists.", "Registration Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Student newStudent = new Student(name, email, password, -1, 0.0);
            this.loggedInStudent = newStudent;
            showSetupScreen(newStudent);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error during registration check.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void completeStudentSetup(String rollNumber, double targetCgpa, int branchIndex) {
        if (loggedInStudent == null) return;
        try {
            loggedInStudent.setRollNumber(rollNumber);
            loggedInStudent.setTargetCGPA(targetCgpa);
            loggedInStudent.setBranchIndex(branchIndex);
            dataAccess.save(loggedInStudent);
            JOptionPane.showMessageDialog(mainFrame, "Profile setup complete!");
            showMainDashboard();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error while saving profile.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void saveSemesterMarks(Semester semester) {
        if (loggedInStudent == null || semester == null) return;
        try {
            loggedInStudent.addOrUpdateSemester(semester);
            dataAccess.update(loggedInStudent);
            JOptionPane.showMessageDialog(mainFrame, "Marks for Semester " + semester.getSemesterNumber() + " saved successfully!");
            showMainDashboard();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error while saving marks.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void updateTargetCgpa(double newTarget) {
        if (loggedInStudent == null) return;
        try {
            loggedInStudent.setTargetCGPA(newTarget);
            dataAccess.update(loggedInStudent);
            JOptionPane.showMessageDialog(mainFrame, "Target CGPA updated successfully!");
            showMainDashboard();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error while updating target CGPA.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void updateRollNumber(String newRollNumber) {
        if (loggedInStudent == null || newRollNumber == null || newRollNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Roll number cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            loggedInStudent.setRollNumber(newRollNumber.trim());
            dataAccess.update(loggedInStudent);
            JOptionPane.showMessageDialog(mainFrame, "Roll number updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error while updating roll number.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void changePassword(char[] currentPassword, char[] newPassword, char[] confirmPassword) {
        if (loggedInStudent == null) return;
        if (currentPassword.length == 0 || newPassword.length == 0 || confirmPassword.length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "All password fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Arrays.equals(newPassword, confirmPassword)) {
            JOptionPane.showMessageDialog(mainFrame, "New password and confirmation do not match.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!loggedInStudent.getPassword().equals(new String(currentPassword))) {
            JOptionPane.showMessageDialog(mainFrame, "Current password incorrect.", "Auth Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            loggedInStudent.setPassword(new String(newPassword));
            dataAccess.update(loggedInStudent);
            JOptionPane.showMessageDialog(mainFrame, "Password changed successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database error while changing password.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

