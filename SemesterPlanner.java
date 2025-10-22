package mypack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main container class for all UI components.
 * The actual program entry point (main method) is in AppController.java.
 */
public class SemesterPlanner {
    // This class is now just a container for the UI classes.
}

// --- UTILITY CLASS FOR PLACEHOLDERS ---
class PlaceholderFocusListener extends FocusAdapter {
    private final String placeholder;
    private final JTextField textField;
    boolean showingPlaceholder;
    private final Color placeholderColor = Color.GRAY;
    private final Color defaultColor;

    public PlaceholderFocusListener(JTextField textField, String placeholder) {
        this.textField = textField; this.placeholder = placeholder;
        this.defaultColor = textField.getForeground();
        showPlaceholder();
    }
    void showPlaceholder() {
        if (textField.isEnabled()) {
            textField.setText(placeholder); textField.setForeground(placeholderColor);
            showingPlaceholder = true;
        } else { showingPlaceholder = false; }
    }
    private void hidePlaceholder() { textField.setText(""); textField.setForeground(defaultColor); showingPlaceholder = false; }
    @Override public void focusGained(FocusEvent e) { if (showingPlaceholder && textField.isEnabled()) { hidePlaceholder(); } }
    @Override public void focusLost(FocusEvent e) { SwingUtilities.invokeLater(() -> { if (textField.getText().isEmpty() && textField.isEnabled()) { showPlaceholder(); } }); }
    public int getIntValue() {
        if (showingPlaceholder || !textField.isEnabled() || textField.getText().isEmpty() || textField.getText().equals("-")) { return 0; }
        try { return Integer.parseInt(textField.getText().trim()); }
        catch (NumberFormatException e) { System.err.println("Invalid number format: " + textField.getText()); return 0; }
    }
}


// --- UI SCREEN CLASSES ---

class LoadingScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    public LoadingScreen(AppController controller) {
        setTitle("Semester Planner"); setSize(400, 250); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        JPanel bg = new PurpleGradientPanel(); bg.setLayout(new GridBagLayout()); bg.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lbl = new JLabel("Semester Planner"); lbl.setFont(new Font("Segoe UI", Font.BOLD, 24)); lbl.setForeground(Color.BLACK);
        JProgressBar progressBar = new JProgressBar(); progressBar.setPreferredSize(new Dimension(250, 20)); progressBar.setForeground(new Color(142, 95, 255)); progressBar.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(10, 10, 10, 10);
        c.gridy = 0; bg.add(lbl, c); c.gridy = 1; bg.add(progressBar, c);
        add(bg); setVisible(true);
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) { progressBar.setValue(i); try { Thread.sleep(20); } catch (InterruptedException ignored) {} }
            dispose(); controller.showAuthScreen();
        }).start();
    }
}

class AuthScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public AuthScreen(AppController controller) {
        setBackground(new Color(243, 232, 255)); setLayout(new GridBagLayout());
        mainPanel = new JPanel(new CardLayout()); mainPanel.setOpaque(false);
        cardLayout = (CardLayout) mainPanel.getLayout();
        mainPanel.add(createLoginPanel(controller), "login");
        mainPanel.add(createSignupPanel(controller), "signup");
        add(mainPanel);
    }
    private JPanel createLoginPanel(AppController controller) {
        RoundedPanel panel = new RoundedPanel(); panel.setPreferredSize(new Dimension(380, 320)); panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(10, 10, 10, 10); c.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTitle = new JLabel("Welcome Back"); lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JTextField txtEmail = new JTextField(16); JPasswordField txtPass = new JPasswordField(16);
        PurpleButton btnLogin = new PurpleButton("Login"); PurpleButton btnToSignup = new PurpleButton("No account? Sign Up");
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; panel.add(lblTitle, c);
        c.gridwidth = 1; c.gridy++; panel.add(new JLabel("Email:"), c); c.gridx = 1; panel.add(txtEmail, c);
        c.gridx = 0; c.gridy++; panel.add(new JLabel("Password:"), c); c.gridx = 1; panel.add(txtPass, c);
        c.gridx = 0; c.gridy++; c.gridwidth = 2; panel.add(btnLogin, c); c.gridy++; panel.add(btnToSignup, c);
        btnLogin.addActionListener(e -> controller.attemptLogin(txtEmail.getText().trim(), new String(txtPass.getPassword())));
        btnToSignup.addActionListener(e -> cardLayout.show(mainPanel, "signup"));
        return panel;
    }
    private JPanel createSignupPanel(AppController controller) {
        RoundedPanel panel = new RoundedPanel(); panel.setPreferredSize(new Dimension(380, 360)); panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(10, 10, 10, 10); c.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTitle = new JLabel("Create Account"); lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JTextField txtName = new JTextField(16); JTextField txtEmail = new JTextField(16); JPasswordField txtPass = new JPasswordField(16);
        PurpleButton btnSignup = new PurpleButton("Sign Up"); PurpleButton btnToLogin = new PurpleButton("Already have an account? Login");
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; panel.add(lblTitle, c);
        c.gridwidth = 1; c.gridy++; panel.add(new JLabel("Name:"), c); c.gridx = 1; panel.add(txtName, c);
        c.gridx = 0; c.gridy++; panel.add(new JLabel("Email:"), c); c.gridx = 1; panel.add(txtEmail, c);
        c.gridx = 0; c.gridy++; panel.add(new JLabel("Password:"), c); c.gridx = 1; panel.add(txtPass, c);
        c.gridx = 0; c.gridy++; c.gridwidth = 2; panel.add(btnSignup, c); c.gridy++; panel.add(btnToLogin, c);
        btnSignup.addActionListener(e -> {
            String name = txtName.getText().trim(); String email = txtEmail.getText().trim(); String pass = new String(txtPass.getPassword());
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) { JOptionPane.showMessageDialog(this, "All fields are required."); }
            else { controller.attemptRegistration(name, email, pass); }
        });
        btnToLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        return panel;
    }
}

class MainDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    final CardLayout cardLayout;
    final JPanel mainPanel;

    public MainDashboard(AppController controller, Student student) {
        setTitle("Semester Planner - Dashboard"); setSize(1000, 700); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(); cardLayout = new CardLayout(); mainPanel.setLayout(cardLayout);
        mainPanel.add(new DetailedHomePanel(this, student, controller), "home");
        mainPanel.add(new ProfilePanel(this, student, controller), "profile");
        add(mainPanel); setVisible(true);
    }
    public void navigateToGradeEntry(int semesterNumber, AppController controller, Student student) {
        GradeEntryPanel gradePanel = new GradeEntryPanel(controller, student, semesterNumber);
        String panelName = "grades_sem_" + semesterNumber;
        mainPanel.add(gradePanel, panelName); cardLayout.show(mainPanel, panelName);
    }
    public void showCard(String name) { cardLayout.show(mainPanel, name); }
    public void logout(AppController controller) { dispose(); controller.logout(); }
}

class SemesterSetupPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JTextField txtRollNumber;
    private final JTextField txtTargetCgpa;
    private final JComboBox<String> branchComboBox;

    public SemesterSetupPanel(AppController controller, Student student) {
        setBackground(new Color(243, 232, 255)); setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(10, 10, 10, 10);
        RoundedPanel content = new RoundedPanel(); content.setPreferredSize(new Dimension(500, 400)); content.setLayout(new GridBagLayout());
        GridBagConstraints ic = new GridBagConstraints(); ic.insets = new Insets(10, 15, 10, 15); ic.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Complete Your Profile"); title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        ic.gridx = 0; ic.gridy = 0; ic.gridwidth = 2; ic.anchor = GridBagConstraints.CENTER; ic.insets = new Insets(15, 15, 20, 15);
        content.add(title, ic);
        ic.gridwidth = 1; ic.anchor = GridBagConstraints.WEST; ic.insets = new Insets(8, 15, 8, 15);

        ic.gridy = 1; ic.gridx = 0; content.add(new JLabel("Name:"), ic);
        JTextField txtName = new JTextField(student.getName()); txtName.setEditable(false);
        ic.gridx = 1; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; content.add(txtName, ic);

        ic.gridy = 2; ic.gridx = 0; ic.fill = GridBagConstraints.NONE; ic.weightx = 0.0; content.add(new JLabel("Roll Number:"), ic);
        txtRollNumber = new JTextField();
        ic.gridx = 1; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; content.add(txtRollNumber, ic);
        
        ic.gridy = 3; ic.gridx = 0; ic.fill = GridBagConstraints.NONE; ic.weightx = 0.0; content.add(new JLabel("Branch:"), ic);
        branchComboBox = new JComboBox<>(SyllabusData.getAllBranches());
        ic.gridx = 1; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; content.add(branchComboBox, ic);

        ic.gridy = 4; ic.gridx = 0; ic.fill = GridBagConstraints.NONE; ic.weightx = 0.0; content.add(new JLabel("Target CGPA:"), ic);
        txtTargetCgpa = new JTextField();
        ic.gridx = 1; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; content.add(txtTargetCgpa, ic);

        PurpleButton btnComplete = new PurpleButton("Complete Setup");
        ic.gridy = 5; ic.gridx = 0; ic.gridwidth = 2; ic.anchor = GridBagConstraints.CENTER; ic.fill = GridBagConstraints.NONE; ic.weightx = 0.0; ic.insets = new Insets(20, 15, 15, 15);
        content.add(btnComplete, ic);
        
        btnComplete.addActionListener(e -> {
            String roll = txtRollNumber.getText().trim(); int branchIndex = branchComboBox.getSelectedIndex(); String targetText = txtTargetCgpa.getText().trim();
            if (roll.isEmpty() || targetText.isEmpty()) { JOptionPane.showMessageDialog(this, "Please fill all fields."); return; }
            try { double target = Double.parseDouble(targetText); if (target < 0.0 || target > 10.0) { JOptionPane.showMessageDialog(this, "Target CGPA must be between 0 and 10."); return; } controller.completeStudentSetup(roll, target, branchIndex); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Please enter a valid number for Target CGPA."); }
        });
        add(content);
    }
}

class DetailedHomePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public DetailedHomePanel(MainDashboard parent, Student student, AppController controller) {
        setLayout(new BorderLayout()); setOpaque(false);
        add(createTopBar(parent, controller), BorderLayout.NORTH); add(createCenterContent(parent, student, controller), BorderLayout.CENTER);
    }
    private JPanel createTopBar(MainDashboard parent, AppController controller) { JPanel tB = new JPanel(new BorderLayout(10, 10)); tB.setOpaque(false); tB.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); PurpleButton bP = new PurpleButton("View/Edit Profile"); bP.addActionListener(e -> parent.showCard("profile")); PurpleButton bL = new PurpleButton("Logout"); bL.addActionListener(e -> parent.logout(controller)); JPanel l = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); l.setOpaque(false); l.add(bP); JPanel r = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); r.setOpaque(false); r.add(bL); tB.add(l, BorderLayout.WEST); tB.add(r, BorderLayout.EAST); return tB; }
    private JPanel createCenterContent(MainDashboard parent, Student student, AppController controller) { JPanel cP = new JPanel(new GridBagLayout()); cP.setOpaque(false); RoundedPanel card = new RoundedPanel(); card.setPreferredSize(new Dimension(880, 520)); card.setLayout(new GridBagLayout()); GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(18, 18, 18, 18); c.gridx = 0; c.gridy = 0; c.gridwidth = 3; c.anchor = GridBagConstraints.CENTER; JLabel t = new JLabel("Welcome, " + student.getName()); t.setFont(new Font("Segoe UI", Font.BOLD, 26)); card.add(t, c); c.gridy = 1; card.add(createInfoPanel(student), c); c.gridy = 2; card.add(createSemesterSelectionPanel(parent, controller, student), c); cP.add(card); return cP; }
    private JPanel createInfoPanel(Student student) { JPanel iP = new JPanel(new GridLayout(1, 4, 18, 18)); iP.setOpaque(false); iP.setPreferredSize(new Dimension(820, 90)); iP.add(makeInfoCard("Current CGPA", String.format("%.3f", student.calculateCGPA()))); iP.add(makeInfoCard("Target CGPA", String.format("%.2f", student.getTargetCGPA()))); iP.add(makeInfoCard("Completed", student.getLastSemesterNumber() + " sems")); iP.add(makeInfoCard("Required Avg. SGPA", calculateRequiredSgpaText(student))); return iP; }
    private JPanel createSemesterSelectionPanel(MainDashboard parent, AppController controller, Student student) { JPanel sP = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); sP.setOpaque(false); JLabel sL = new JLabel("Select Semester to Enter/Edit Marks:"); sL.setFont(new Font("Segoe UI", Font.PLAIN, 16)); JComboBox<Integer> sB = new JComboBox<>(); for (int i = 1; i <= 8; i++) { sB.addItem(i); } PurpleButton bG = new PurpleButton("Go"); bG.addActionListener(e -> parent.navigateToGradeEntry((Integer) sB.getSelectedItem(), controller, student)); sP.add(sL); sP.add(sB); sP.add(bG); return sP; }
    private String calculateRequiredSgpaText(Student student) { int lS=student.getLastSemesterNumber(); if(lS>=8){return "N/A (Graduated)";} int rC=0; for(int s=lS;s<8;s++){ SyllabusData.Subject[] fS=SyllabusData.getSubjects(student.getBranchIndex(),s); if(fS!=null){for(SyllabusData.Subject sub:fS)rC+=sub.credits;}} if(rC<=0){return "N/A";} double rA=student.requiredAvgGPAcrossRemainingCredits(rC); if(rA<=0)return "Target Met!"; if(rA>10.0)return "Not Possible"; return String.format("%.3f",rA); }
    private JPanel makeInfoCard(String t, String v){ RoundedPanel p=new RoundedPanel(); p.setPreferredSize(new Dimension(200,70)); p.setLayout(new BorderLayout()); p.setOpaque(false); JLabel tl=new JLabel(t); tl.setFont(new Font("Segoe UI",Font.PLAIN,12)); tl.setBorder(BorderFactory.createEmptyBorder(6,10,0,0)); tl.setForeground(Color.BLACK); JLabel vl=new JLabel(v); vl.setFont(new Font("Segoe UI",Font.BOLD,18)); vl.setBorder(BorderFactory.createEmptyBorder(0,10,6,0)); vl.setForeground(Color.BLACK); p.add(tl,BorderLayout.NORTH); p.add(vl,BorderLayout.CENTER); return p; }
    @Override protected void paintComponent(Graphics g) { super.paintComponent(g); Graphics2D g2=(Graphics2D)g.create(); Color l=new Color(243,232,255); Color m=new Color(167,139,250); GradientPaint gp=new GradientPaint(0,0,l,getWidth(),getHeight(),m); g2.setPaint(gp); g2.fillRect(0,0,getWidth(),getHeight()); g2.dispose();}
}

class ProfilePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    // --- Declare fields for components accessed by action listeners ---
    private final JTextField txtRollNumber;
    private final JTextField txtTargetCgpa;
    private final JPasswordField txtCurrentPass;
    private final JPasswordField txtNewPass;
    private final JPasswordField txtConfirmPass;

    public ProfilePanel(MainDashboard parent, Student student, AppController controller) {
        setBackground(new Color(243, 232, 255));
        setLayout(new GridBagLayout());
        RoundedPanel card = new RoundedPanel();
        card.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 15, 10, 15); c.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Profile Details"); title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 3; c.anchor = GridBagConstraints.CENTER; c.insets = new Insets(15, 15, 20, 15); card.add(title, c);
        c.gridwidth = 1; c.anchor = GridBagConstraints.WEST; c.insets = new Insets(8, 15, 8, 15);

        // --- Static Info ---
        addLabelAndValue(card, c, 1, "Name:", student.getName());
        addLabelAndValue(card, c, 2, "Email:", student.getEmail());
        addLabelAndValue(card, c, 3, "Branch:", SyllabusData.getAllBranches()[student.getBranchIndex()]);

        // --- Editable Roll Number ---
        c.gridy = 4; c.gridx = 0; card.add(new JLabel("Roll Number:"), c);
        txtRollNumber = new JTextField(student.getRollNumber()); // Initialize field
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; card.add(txtRollNumber, c);
        PurpleButton btnSaveRoll = new PurpleButton("Save");
        btnSaveRoll.addActionListener(e -> controller.updateRollNumber(txtRollNumber.getText()));
        c.gridx = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE; card.add(btnSaveRoll, c);

        // --- Editable Target CGPA ---
        c.gridy = 5; c.gridx = 0; card.add(new JLabel("Target CGPA:"), c);
        txtTargetCgpa = new JTextField(String.format("%.2f", student.getTargetCGPA())); // Initialize field
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; card.add(txtTargetCgpa, c);
        PurpleButton btnSaveTarget = new PurpleButton("Save");
        btnSaveTarget.addActionListener(e -> saveTargetCgpaAction(controller)); // Use the class field
        c.gridx = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE; card.add(btnSaveTarget, c);

        // --- Password Section ---
        JSeparator sep = new JSeparator(); c.gridy = 6; c.gridx = 0; c.gridwidth = 3; c.insets = new Insets(20, 0, 10, 0); c.fill = GridBagConstraints.HORIZONTAL; card.add(sep, c);
        c.insets = new Insets(8, 15, 8, 15); c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
        txtCurrentPass = addPasswordField(card, c, 7, "Current Password:");
        txtNewPass = addPasswordField(card, c, 8, "New Password:");
        txtConfirmPass = addPasswordField(card, c, 9, "Confirm New Pass:");
        PurpleButton btnChangePass = new PurpleButton("Change Password");
        btnChangePass.addActionListener(e -> controller.changePassword(txtCurrentPass.getPassword(), txtNewPass.getPassword(), txtConfirmPass.getPassword()));
        c.gridy = 10; c.gridx = 0; c.gridwidth = 3; c.anchor = GridBagConstraints.CENTER; c.insets = new Insets(15, 15, 10, 15); card.add(btnChangePass, c);

        // --- Back Button ---
        PurpleButton backButton = new PurpleButton("Back to Dashboard"); backButton.addActionListener(e -> parent.showCard("home"));
        c.gridy = 11; c.gridx = 0; c.gridwidth = 3; c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE; c.insets = new Insets(20, 15, 10, 15);
        card.add(backButton, c);
        add(card);
    }

    private void addLabelAndValue(JPanel p, GridBagConstraints c, int y, String l, String v) { c.gridy = y; c.gridx = 0; p.add(new JLabel(l), c); c.gridx = 1; c.gridwidth = 2; p.add(new JLabel(v), c); c.gridwidth = 1; }
    // Removed ActionListener parameter, field is now assigned to class member
    private JTextField addEditableField(JPanel p, GridBagConstraints c, int y, String l, String v) {
        c.gridy = y; c.gridx = 0; p.add(new JLabel(l), c);
        JTextField f = new JTextField(v);
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; p.add(f, c);
        PurpleButton b = new PurpleButton("Save");
        // ActionListener will be added separately where the field is initialized
        c.gridx = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE; p.add(b, c);
        return f; // Return the field so it can be assigned
    }
     // Overload for assigning action listener separately
    private JTextField addEditableField(JPanel p, GridBagConstraints c, int y, String l, String v, java.awt.event.ActionListener a) {
        JTextField f = addEditableField(p, c, y, l, v); // Call the base method
        // Find the save button added by the base method (it's the last component added)
        Component lastComp = p.getComponent(p.getComponentCount() - 1);
        if (lastComp instanceof PurpleButton) {
            ((PurpleButton) lastComp).addActionListener(a);
        }
        return f;
    }

    private JPasswordField addPasswordField(JPanel p, GridBagConstraints c, int y, String l) { c.gridy = y; c.gridx = 0; p.add(new JLabel(l), c); JPasswordField f = new JPasswordField(); c.gridx = 1; c.gridwidth = 2; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; p.add(f, c); c.gridwidth = 1; c.weightx = 0.0; return f; }
    // Uses the class field txtTargetCgpa
    private void saveTargetCgpaAction(AppController controller) { try { double t = Double.parseDouble(txtTargetCgpa.getText().trim()); if (t < 0.0 || t > 10.0) { JOptionPane.showMessageDialog(this, "Target must be 0-10."); return; } controller.updateTargetCgpa(t); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid number."); } }
}

class GradeEntryPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final List<PlaceholderFocusListener[]> subjectMarksListeners;

    public GradeEntryPanel(AppController controller, Student student, int semesterNumber) {
        this.subjectMarksListeners = new ArrayList<>();
        setLayout(new BorderLayout()); setBackground(new Color(243, 232, 255));
        JLabel title = new JLabel("Enter/Edit Marks for Semester " + semesterNumber, SwingConstants.CENTER); title.setFont(new Font("Segoe UI", Font.BOLD, 24)); title.setBorder(BorderFactory.createEmptyBorder(20,0,20,0)); add(title, BorderLayout.NORTH);
        add(createSubjectsScrollPane(student, semesterNumber), BorderLayout.CENTER);
        add(createBottomButtonPanel(controller, student, semesterNumber), BorderLayout.SOUTH);
    }
    private JScrollPane createSubjectsScrollPane(Student student, int semesterNumber) {
        JPanel subjectsPanel = new JPanel(); subjectsPanel.setLayout(new BoxLayout(subjectsPanel, BoxLayout.Y_AXIS)); subjectsPanel.setOpaque(false); subjectsPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        SyllabusData.Subject[] subjects = SyllabusData.getSubjects(student.getBranchIndex(), semesterNumber - 1);
        if (subjects != null && subjects.length > 0) {
            for (SyllabusData.Subject s : subjects) {
                subjectsPanel.add(createSubjectInputPanel(s, student.getSemester(semesterNumber)));
                subjectsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else { subjectsPanel.add(new JLabel("No subjects found for this semester.")); }
        JScrollPane scrollPane = new JScrollPane(subjectsPanel); scrollPane.setBorder(BorderFactory.createEmptyBorder()); scrollPane.getViewport().setBackground(new Color(243, 232, 255));
        return scrollPane;
    }
    private JPanel createBottomButtonPanel(AppController controller, Student student, int semesterNumber) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); bottomPanel.setOpaque(false); bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,0,20,0));
        PurpleButton saveButton = new PurpleButton("Save & Calculate SGPA");
        saveButton.addActionListener(e -> saveMarks(controller, student, semesterNumber));
        PurpleButton backButton = new PurpleButton("Back to Dashboard");
        backButton.addActionListener(e -> ((MainDashboard) SwingUtilities.getWindowAncestor(this)).showCard("home"));
        bottomPanel.add(backButton); bottomPanel.add(saveButton);
        return bottomPanel;
    }
    private JPanel createSubjectInputPanel(SyllabusData.Subject subject, Semester existingSemester) {
        RoundedPanel panel = new RoundedPanel(); panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); panel.setMaximumSize(new Dimension(950, 60)); panel.setMinimumSize(new Dimension(950, 60));
        JLabel subjectName = new JLabel(String.format("<html><b>%s</b> (%s)</html>", subject.name, subject.subjectCode)); subjectName.setPreferredSize(new Dimension(400, 30)); panel.add(subjectName);
        MarkedSubject existingMarks = (existingSemester != null) ? existingSemester.getSubject(subject.subjectCode) : null;
        PlaceholderFocusListener[] listeners = new PlaceholderFocusListener[5];
        JTextField[] fields = new JTextField[5];
        String[] labels = {"IA1", "IA2", "Asg", "Lab", "Ext"};
        int[] maxMarks = { GradePolicy.getIA1Max(subject.schemeType), GradePolicy.getIA2Max(subject.schemeType), GradePolicy.getAssignmentMax(subject.schemeType), GradePolicy.getLabMax(subject.schemeType), GradePolicy.getExternalMax(subject.schemeType) };

        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            fields[i] = new JTextField(4);
            String placeholder = String.format(" / %d", maxMarks[i]);
            listeners[i] = new PlaceholderFocusListener(fields[i], placeholder);
            fields[i].addFocusListener(listeners[i]);

            String initialValue = "";
            if (existingMarks != null) {
                int mark = switch (i) { case 0->existingMarks.getIa1(); case 1->existingMarks.getIa2(); case 2->existingMarks.getAssignment(); case 3->existingMarks.getLabExam(); case 4->existingMarks.getExternal(); default->0; };
                initialValue = String.valueOf(mark);
            }

            if (maxMarks[i] <= 0) {
                 fields[i].setText("-"); fields[i].setEnabled(false); label.setEnabled(false);
                 listeners[i].showingPlaceholder = false;
            } else if (!initialValue.isEmpty() && !initialValue.equals("0")) {
                 fields[i].setText(initialValue); fields[i].setForeground(Color.BLACK);
                 listeners[i].showingPlaceholder = false;
            } else {
                 listeners[i].showPlaceholder();
            }
            panel.add(label); panel.add(fields[i]);
        }
        subjectMarksListeners.add(listeners);
        return panel;
    }
    private void saveMarks(AppController controller, Student student, int semesterNumber) {
        Semester newSemester = new Semester(semesterNumber);
        SyllabusData.Subject[] subjects = SyllabusData.getSubjects(student.getBranchIndex(), semesterNumber - 1);
        if (subjects == null) return;
        try {
            if (subjectMarksListeners.size() != subjects.length) { throw new IllegalStateException("Listener/Subject mismatch."); }
            for (int i = 0; i < subjects.length; i++) {
                PlaceholderFocusListener[] listeners = subjectMarksListeners.get(i);
                int ia1 = listeners[0].getIntValue(); int ia2 = listeners[1].getIntValue(); int asg = listeners[2].getIntValue(); int lab = listeners[3].getIntValue(); int ext = listeners[4].getIntValue();
                MarkedSubject ms = new MarkedSubject(subjects[i]);
                ms.setMarks(ia1, ia2, asg, lab, ext);
                newSemester.addSubject(ms);
            }
            controller.saveSemesterMarks(newSemester);
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
             JOptionPane.showMessageDialog(this, "Error processing marks.", "Internal Error", JOptionPane.ERROR_MESSAGE); e.printStackTrace();
        }
    }
}

// --- UI UTILITY CLASSES ---
class PurpleGradientPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    @Override protected void paintComponent(Graphics g) { super.paintComponent(g); Graphics2D g2d=(Graphics2D)g.create(); Color l=new Color(243, 232, 255); Color m=new Color(167, 139, 250); GradientPaint gp=new GradientPaint(0,0,l,getWidth(),getHeight(),m); g2d.setPaint(gp); g2d.fillRect(0,0,getWidth(),getHeight()); g2d.dispose();}
}
class RoundedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private float alpha = 1.0f; public RoundedPanel(){setOpaque(false);}
    public void setAlpha(float alpha){this.alpha=alpha; repaint();}
    @Override protected void paintComponent(Graphics g) { Graphics2D g2=(Graphics2D)g.create(); g2.setComposite(AlphaComposite.SrcOver.derive(alpha)); g2.setColor(new Color(255,255,255,240)); g2.fill(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20)); g2.dispose(); super.paintComponent(g);}
}
class PurpleButton extends JButton {
    private static final long serialVersionUID = 1L;
    public PurpleButton(String text){ super(text); setFocusPainted(false); setBackground(new Color(142,95,255)); setForeground(Color.WHITE); setFont(new Font("Segoe UI",Font.BOLD,14)); setBorder(BorderFactory.createEmptyBorder(10,20,10,20)); setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}