/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InformationSystem;

import java.io.FileOutputStream;
import java.io.File;

import java.awt.Color;
import java.awt.Font;
import java.awt.Desktop;

import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import javax.swing.JFileChooser;




/**
 *
 * @author Ruth
 */
public final class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        loadMemberTableData();
        updateMemberTable();
        updateMemberCount();
        
        loadEventTableData();
        updateEventTable();
        updateEventCount();
        
        loadReportTableData();
        updateReportTable();
        updateReportCount();
        
        loadNewcomerTableData();
        updateNewcomerTable();
        updateNewcomerCount();
        
        loadInactiveMembersTableData();
        updateInactiveMembersTableData();
        
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setupTabLabels();  // Apply hover & click effects
        updateLabelHighlight();  // Set correct highlight at startup

         jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
        @Override
        protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
            return 0;
        }
    });
         jTabbedPane1.addChangeListener((javax.swing.event.ChangeEvent evt) -> {
             updateLabelHighlight();
        });
    }

    Connection con;
    //SQL Statement
    Statement st;
    //SQL Prepared Statement
    PreparedStatement pst;
    
public void resetFields() { //Reset Method
    fullnameTxt.setText("");
    ageTxt.setText("");
    birthdayDateChooser.setDate(null); // Reset JDateChooser
    genderComboBox.setSelectedIndex(0); // Reset ComboBox to first option
    contactTxt.setText("");
    addressTxt.setText("");
    lifeverseTxt.setText("");
    
    eventTitleTxt.setText("");
    descriptionEventTxtArea.setText("");
    eventDateChooser.setDate(null);
    
    nameReportTxt.setText("");
    descriptionReportTxtArea.setText("");
    typeReportComboBox.setSelectedIndex(0);
    eventDateChooser.setDate(null);
    amountReportTxt.setText("");
    
    fullnameNewcomerTxt.setText("");
    ageNewcomerTxt.setText("");
    newcomerDateChooser.setDate(null);
    newcomerfbaccTxt.setText("");
}

private int getTotalMembers() { //Total Members Method
    int count = 0;
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
        String sql = "SELECT COUNT(*) FROM members"; // Replace 'members' with your table name
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            count = rs.getInt(1); // Get the count value
        }
        
        rs.close();
        pst.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}


public void updateMemberCount() { // Total Active Member Count Method
    try {
        Connection con = Connector.getConnection(); // Use Connector class
        String sql = "SELECT COUNT(*) FROM memberdetails WHERE status = 'active'"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1); // Get the count from query
            totalmembersLabel.setText("Active Members: " + count); // Update JLabel
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error retrieving active member count: " + ex.getMessage());
    }
}

public void updateEventCount() { //Total Event Count Method
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
        String sql = "SELECT COUNT(*) FROM eventdetails"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1); // Get the count from query
            totaleventsLabel.setText("Current Events: " + count); // Update JLabel
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error retrieving member count: " + ex.getMessage());
    }
}

public void updateNewcomerCount() {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
        String sql = "SELECT COUNT(*) FROM newcomerdetails"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            totalnewcomerLabel.setText("Total Newcomers: " + count); // Make sure this label exists
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error retrieving newcomer count: " + ex.getMessage());
    }
}

public void updateReportCount() { // Total Report Count Method
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
        String sql = "SELECT COUNT(*) FROM reportdetails"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1); // Get the count from query
            totalreportsLabel.setText("Reports: " + count); // Update JLabel
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error retrieving report count: " + ex.getMessage());
    }
}

private void loadInactiveMembersTableData() {
    try {
        // 1. Connect to database
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");

        // 2. Prepare SQL query for inactive members
        String sql = "SELECT * FROM memberdetails WHERE status = 'inactive'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // 3. Get table model
        DefaultTableModel model = (DefaultTableModel) inactiveMembersTable.getModel();
        model.setRowCount(0); // Clear previous data

        // 4. Populate table rows
        while (rs.next()) {
            Object[] row = {
                rs.getString("memberid"),
                rs.getString("fullname"),
                rs.getString("age"),
                rs.getString("birthday"),
                rs.getString("gender"),
                rs.getString("contact"),
                rs.getString("address"),
                rs.getString("lifeverse"),
                rs.getString("status")
            };
            model.addRow(row);
        }

        // 5. Close
        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to load inactive members: " + e.getMessage());
    }
}

private void loadMemberTableData() { // Load Table Data Members
    try {
        // Connect to database
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06")) {

            // SQL Query - ONLY load active members
            String sql = "SELECT * FROM memberdetails WHERE status = 'active'"; // Change this value if you're using 'Active' instead
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

                // Get table model
                DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
                model.setRowCount(0); // Clear existing data

                // Loop through result set
                while (rs.next()) {
                    // 🔍 Debugging output
                    

                    Object[] row = {
                        rs.getString("memberid"),
                        rs.getString("fullname"),
                        rs.getString("age"),
                        rs.getString("birthday"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("address"),
                        rs.getString("lifeverse")
                    };
                    model.addRow(row); // Add row to table
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadEventTableData() { // Load Table Date Event
    try {
        // SQL Query to fetch all records
        try ( // Connect to database
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06")) {
            // SQL Query to fetch all records
            String sql = "SELECT * FROM eventdetails";
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
                
                // Get table model
                DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();  
                model.setRowCount(0); // Clear existing data
                // Clear existing data
                
                // Loop through result set
                while (rs.next()) {
                    Object[] row = {
                        
                        rs.getString("eventid"),
                        rs.getString("eventtitle"),
                        rs.getString("description"),
                        rs.getString("eventdate"),

                    };
                    model.addRow(row); // Add row to table
                }
                // Close resources

            }
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadNewcomerTableData() { // Load Newcomer Table Data
    try {
        // Connect to database
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06")) {
            
            // SQL Query to fetch all records from newcomer table
            String sql = "SELECT * FROM newcomerdetails";
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
                
                // Get table model
                DefaultTableModel model = (DefaultTableModel) newcomerTable.getModel();  // Make sure your JTable is named newcomerTable
                model.setRowCount(0); // Clear existing data

                // Loop through result set
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("newcomerid"),          // Newcomer ID
                        rs.getString("newcomerfullname"), // Full Name
                        rs.getInt("newcomerage"),         // Age
                        rs.getString("newcomerbirthday"), // Birthday
                        rs.getString("newcomerfbacc")     // Facebook Account
                    };
                    model.addRow(row); // Add row to table
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadReportTableData() { // Load Report Table Data
    try {
        // Connect to database
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06")) {
            
            // SQL Query to fetch all records from reportdetails
            String sql = "SELECT * FROM reportdetails";
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
                
                // Get table model
                DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();  // Assuming JTable is named reportTable
                model.setRowCount(0); // Clear existing data

                // Loop through result set
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("reportid"),            // Report ID (Primary Key)
                        rs.getString("reportname"),       // Report Name
                        rs.getString("reportdescription"), // Report Description (from TextArea)
                        rs.getString("reporttype"),       // Report Type (from JComboBox)
                        rs.getString("reportdate")        // Report Date
                    };
                    model.addRow(row); // Add row to table
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void updateInactiveMembersTableData() {
    try {
        // Use connection from Connector.java
        Connection con = Connector.getConnection();

        String sql = "SELECT * FROM inactivememberdetails WHERE status = 'inactive'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) inactiveMembersTable.getModel();
        model.setRowCount(0); // Clear existing rows

        while (rs.next()) {
            Object[] row = {
                rs.getString("inactiveid"),
                rs.getString("fullname"),
                rs.getString("age"),
                rs.getString("birthday"),
                rs.getString("gender"),
                rs.getString("contact"),
                rs.getString("address"),
                rs.getString("lifeverse"),
                rs.getString("status")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to update inactive members table: " + e.getMessage());
    }
}

private void updateMemberTable() { // Update Table Member
    try {
        // Check if connection is null or closed, then reconnect
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/information_systemdb", 
                "root", 
                "@ruthmysql06"
            );
        }

        // ✅ Only select active members
        String sql = "SELECT * FROM memberdetails WHERE status = 'active'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
        model.setRowCount(0); // Clear existing rows

        while (rs.next()) {
            Object[] row = {
                rs.getString("memberid"),
                rs.getString("fullname"),
                rs.getString("age"),
                rs.getString("birthday"),
                rs.getString("gender"),
                rs.getString("contact"),
                rs.getString("address"),
                rs.getString("lifeverse")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to update member table: " + e.getMessage());
    }
}

private void updateEventTable() { // Update Table for Events
    if (con == null) {
        try {
            Connection(); // Ensure the connection is established
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    try {
        String sql = "SELECT * FROM eventdetails"; // Change table name to events
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
        model.setRowCount(0); // Clear existing data

        while (rs.next()) {
            Object[] row = {
                rs.getString("eventid"),           // Event ID (Primary Key)
                rs.getString("eventtitle"),  // Event Title
                rs.getString("description"),  // Event Description (from TextArea)
                rs.getString("eventdate")    // Event Date
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void updateNewcomerTable() {
    if (con == null) {
        try {
            Connection(); // Ensure the connection is established
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    try {
        String sql = "SELECT * FROM newcomerdetails"; // Adjusted table name
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) newcomerTable.getModel(); // Adjusted JTable name
        model.setRowCount(0); // Clear table

        while (rs.next()) {
            Object[] row = {
                rs.getInt("newcomerid"),
                rs.getString("newcomerfullname"),
                rs.getInt("newcomerage"),
                rs.getString("newcomerbirthday"),
                rs.getString("newcomerfbacc")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void updateReportTable() {
    if (con == null) {
        try {
            Connection(); // Ensure the connection is established
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    try {
        String sql = "SELECT * FROM reportdetails"; // Query reportdetails table
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) reportsTable.getModel(); // Assuming JTable is named reportTable
        model.setRowCount(0); // Clear existing data

        while (rs.next()) {
            Object[] row = {
                rs.getInt("reportid"),           // Report ID (Primary Key)
                rs.getString("reportname"),      // Report Name
                rs.getString("reportdescription"), // Report Description (from TextArea)
                rs.getString("reporttype"),      // Report Type (from JComboBox)
                rs.getString("reportdate"),     // Report Date
                rs.getString("reportamount")    // Report Amount
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private static final String DbName = "information_systemdb";
    private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPassword = "@ruthmysql06";
    
    public void Connection() throws SQLException{
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
            st = con.createStatement();
            
            if (con != null) {
                System.out.println("Connection Successful");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
            
// Array of sidebar labels corresponding to each tab
private JLabel[] tabLabels;
    private void setupTabLabels() {

    tabLabels = new JLabel[]{homeDashboardLabel, membersDashboardLabel, eventsDashboardLabel, newcomersDashboardLabel, reportsDashboardLabel, inactiveMembersDashboardLabel}; 

    for (int i = 0; i < tabLabels.length; i++) {
        final int tabIndex = i; // Store tab index

        // Add click event to switch tabs
        tabLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1.setSelectedIndex(tabIndex);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabLabels[tabIndex].setForeground(new Color(100, 100, 255)); // Hover color
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateLabelHighlight(); // Reset to correct color
            }
        });
    }
}
private void updateLabelHighlight() {
    for (int i = 0; i < tabLabels.length; i++) {
        if (jTabbedPane1.getSelectedIndex() == i) {
            tabLabels[i].setForeground(new Color(50, 50, 200)); // Active tab color
        } else {
            tabLabels[i].setForeground(new Color(0, 0, 0)); // Default color (Black)
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        homeDashboardLabel = new javax.swing.JLabel();
        membersDashboardLabel = new javax.swing.JLabel();
        eventsDashboardLabel = new javax.swing.JLabel();
        newcomersDashboardLabel = new javax.swing.JLabel();
        reportsDashboardLabel = new javax.swing.JLabel();
        logoutDashboardLabel = new javax.swing.JLabel();
        logoDashboardLabel = new javax.swing.JLabel();
        inactiveMembersDashboardLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
                return 0; // Hides the tab bar
            }
        });
        homePanel = new javax.swing.JPanel();
        totalmembersLabel = new javax.swing.JLabel();
        totaleventsLabel = new javax.swing.JLabel();
        totalreportsLabel = new javax.swing.JLabel();
        totalnewcomerLabel = new javax.swing.JLabel();
        membersPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        fullnameLabel = new javax.swing.JLabel();
        fullnameTxt = new javax.swing.JTextField();
        genderLabel = new javax.swing.JLabel();
        contactLabel = new javax.swing.JLabel();
        ageTxt = new javax.swing.JTextField();
        contactTxt = new javax.swing.JTextField();
        ageLabel = new javax.swing.JLabel();
        birthdayLabel = new javax.swing.JLabel();
        birthdayDateChooser = new com.toedter.calendar.JDateChooser();
        genderComboBox = new javax.swing.JComboBox<>();
        addressLabel = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        addBttn = new javax.swing.JButton();
        updateBttn = new javax.swing.JButton();
        deleteBttn = new javax.swing.JButton();
        resetBttn = new javax.swing.JButton();
        lifeverseLabel = new javax.swing.JLabel();
        lifeverseTxt = new javax.swing.JTextField();
        inactiveBttn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        membersTable = new javax.swing.JTable();
        searchMemberLabel = new javax.swing.JLabel();
        searchMemberTxt = new javax.swing.JTextField();
        memberinformationLabel = new javax.swing.JLabel();
        searchIcon = new javax.swing.JLabel();
        eventsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        eventsTable = new javax.swing.JTable();
        eventTitleLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        eventDateLabel = new javax.swing.JLabel();
        addEventBttn = new javax.swing.JButton();
        editEventBttn = new javax.swing.JButton();
        deleteEventBttn = new javax.swing.JButton();
        resetEventBttn = new javax.swing.JButton();
        eventTitleTxt = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionEventTxtArea = new javax.swing.JTextArea();
        eventDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        searchEventLabel = new javax.swing.JLabel();
        searchEventTxt = new javax.swing.JTextField();
        pdfEventBttn = new javax.swing.JToggleButton();
        newcomersPanel = new javax.swing.JPanel();
        fullnameNewcomerLabel = new javax.swing.JLabel();
        fullnameNewcomerTxt = new javax.swing.JTextField();
        ageNewcomerLabel = new javax.swing.JLabel();
        ageNewcomerTxt = new javax.swing.JTextField();
        birthdayNewlabel = new javax.swing.JLabel();
        newcomerDateChooser = new com.toedter.calendar.JDateChooser();
        newcomerfbaccLabel = new javax.swing.JLabel();
        newcomerfbaccTxt = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        newcomerTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        addNewcomerBttn = new javax.swing.JButton();
        editNewcomerBttn = new javax.swing.JButton();
        deleteNewcomerBttn = new javax.swing.JButton();
        resetNewcomerBttn = new javax.swing.JButton();
        dateReportLabel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        reportsTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        nameReportLabel = new javax.swing.JLabel();
        nameReportTxt = new javax.swing.JTextField();
        descriptionReportLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descriptionReportTxtArea = new javax.swing.JTextArea();
        typeReportLabel = new javax.swing.JLabel();
        typeReportComboBox = new javax.swing.JComboBox<>();
        typeReportLabel1 = new javax.swing.JLabel();
        addReportBttn = new javax.swing.JButton();
        editReportBttn = new javax.swing.JButton();
        deleteReportBttn = new javax.swing.JButton();
        resetReportBttn = new javax.swing.JButton();
        reportDateChooser = new com.toedter.calendar.JDateChooser();
        amountReportLabel = new javax.swing.JLabel();
        amountReportTxt = new javax.swing.JTextField();
        pdfReportBttn = new javax.swing.JButton();
        inactiveMembersPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        inactiveMembersTable = new javax.swing.JTable();
        restoreBttn = new javax.swing.JButton();
        inactiveMembersLabel = new javax.swing.JLabel();
        searchInactiveTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));

        homeDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        homeDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        homeDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-home-30.png"))); // NOI18N
        homeDashboardLabel.setText(" HOME");
        homeDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeDashboardLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeDashboardLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeDashboardLabelMouseExited(evt);
            }
        });

        membersDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        membersDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        membersDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        membersDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-people-30.png"))); // NOI18N
        membersDashboardLabel.setText(" MEMBERS");
        membersDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                membersDashboardLabelMouseClicked(evt);
            }
        });

        eventsDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        eventsDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        eventsDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventsDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-event-30.png"))); // NOI18N
        eventsDashboardLabel.setText(" EVENTS");

        newcomersDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        newcomersDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        newcomersDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newcomersDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-statistics-30.png"))); // NOI18N
        newcomersDashboardLabel.setText("NEW COMERS");

        reportsDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        reportsDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reportsDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportsDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-reports-30.png"))); // NOI18N
        reportsDashboardLabel.setText(" REPORTS");

        logoutDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        logoutDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        logoutDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-logout-30.png"))); // NOI18N
        logoutDashboardLabel.setText(" LOGOUT");
        logoutDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutDashboardLabelMouseClicked(evt);
            }
        });

        logoDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo-smaller.png"))); // NOI18N

        inactiveMembersDashboardLabel.setBackground(new java.awt.Color(255, 255, 255));
        inactiveMembersDashboardLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        inactiveMembersDashboardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inactiveMembersDashboardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-people-30.png"))); // NOI18N
        inactiveMembersDashboardLabel.setText("INACTIVE");
        inactiveMembersDashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inactiveMembersDashboardLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(eventsDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(newcomersDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
            .addComponent(reportsDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logoDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(membersDashboardLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logoutDashboardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(inactiveMembersDashboardLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logoDashboardLabel)
                .addGap(66, 66, 66)
                .addComponent(homeDashboardLabel)
                .addGap(50, 50, 50)
                .addComponent(membersDashboardLabel)
                .addGap(43, 43, 43)
                .addComponent(inactiveMembersDashboardLabel)
                .addGap(38, 38, 38)
                .addComponent(newcomersDashboardLabel)
                .addGap(42, 42, 42)
                .addComponent(eventsDashboardLabel)
                .addGap(40, 40, 40)
                .addComponent(reportsDashboardLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutDashboardLabel)
                .addGap(130, 130, 130))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        homePanel.setBackground(new java.awt.Color(102, 204, 255));

        totalmembersLabel.setBackground(new java.awt.Color(255, 204, 204));
        totalmembersLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        totalmembersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersLabel.setText("ACTIVE MEMBERS:");
        totalmembersLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalmembersLabel.setOpaque(true);

        totaleventsLabel.setBackground(new java.awt.Color(255, 204, 204));
        totaleventsLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        totaleventsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totaleventsLabel.setText("CURRENT EVENTS: 0");
        totaleventsLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totaleventsLabel.setOpaque(true);

        totalreportsLabel.setBackground(new java.awt.Color(255, 204, 204));
        totalreportsLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        totalreportsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalreportsLabel.setText("REPORTS: 0");
        totalreportsLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalreportsLabel.setOpaque(true);

        totalnewcomerLabel.setBackground(new java.awt.Color(255, 204, 204));
        totalnewcomerLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        totalnewcomerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalnewcomerLabel.setText("New Comer: 0");
        totalnewcomerLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalnewcomerLabel.setOpaque(true);

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalmembersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(totalreportsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(126, 126, 126)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totaleventsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalnewcomerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(533, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalmembersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totaleventsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(185, 185, 185)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalreportsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalnewcomerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(325, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", homePanel);

        membersPanel.setBackground(new java.awt.Color(102, 204, 255));

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(425, 1045));

        fullnameLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        fullnameLabel.setText("Fullname:");

        fullnameTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        genderLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        genderLabel.setText("Gender:");

        contactLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        contactLabel.setText("Contact:");

        ageTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ageTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ageTxtKeyTyped(evt);
            }
        });

        contactTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        contactTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactTxtKeyTyped(evt);
            }
        });

        ageLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        ageLabel.setText("Age:");

        birthdayLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        birthdayLabel.setText("Birthday:");

        birthdayDateChooser.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        genderComboBox.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        genderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderComboBoxActionPerformed(evt);
            }
        });

        addressLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        addressLabel.setText("Address:");

        addressTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        addBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addBttn.setText("Add");
        addBttn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBttnMouseClicked(evt);
            }
        });
        addBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBttnActionPerformed(evt);
            }
        });

        updateBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateBttn.setText("Update");
        updateBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBttnActionPerformed(evt);
            }
        });

        deleteBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deleteBttn.setText("Delete");
        deleteBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBttnActionPerformed(evt);
            }
        });

        resetBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetBttn.setText("Reset");
        resetBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBttnActionPerformed(evt);
            }
        });

        lifeverseLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lifeverseLabel.setText("Life Verse:");

        lifeverseTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        inactiveBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        inactiveBttn.setText("Inactive");
        inactiveBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inactiveBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(birthdayLabel)
                            .addComponent(genderLabel)
                            .addComponent(contactLabel)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ageLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fullnameLabel, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(addressLabel)
                            .addComponent(lifeverseLabel))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullnameTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ageTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(birthdayDateChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(genderComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contactTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addressTxt)
                            .addComponent(lifeverseTxt))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updateBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inactiveBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resetBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(birthdayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthdayDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(genderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lifeverseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lifeverseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(resetBttn)
                .addGap(175, 175, 175)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBttn)
                    .addComponent(updateBttn))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBttn)
                    .addComponent(inactiveBttn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        membersTable.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        membersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Fullname", "Age", "Brithday", "Gender", "Contact", "Address", "Life Verse"
            }
        ));
        membersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                membersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(membersTable);
        if (membersTable.getColumnModel().getColumnCount() > 0) {
            membersTable.getColumnModel().getColumn(0).setPreferredWidth(5);
            membersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            membersTable.getColumnModel().getColumn(2).setPreferredWidth(5);
            membersTable.getColumnModel().getColumn(4).setResizable(false);
            membersTable.getColumnModel().getColumn(4).setPreferredWidth(10);
            membersTable.getColumnModel().getColumn(5).setResizable(false);
            membersTable.getColumnModel().getColumn(6).setPreferredWidth(300);
        }

        searchMemberLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        searchMemberLabel.setText("Search Data:");

        searchMemberTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        searchMemberTxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        searchMemberTxt.setPreferredSize(new java.awt.Dimension(80, 25));
        searchMemberTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMemberTxtActionPerformed(evt);
            }
        });
        searchMemberTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchMemberTxtKeyReleased(evt);
            }
        });

        memberinformationLabel.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        memberinformationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        memberinformationLabel.setText("MEMBER INFORMATION");

        searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-search-30.png"))); // NOI18N

        javax.swing.GroupLayout membersPanelLayout = new javax.swing.GroupLayout(membersPanel);
        membersPanel.setLayout(membersPanelLayout);
        membersPanelLayout.setHorizontalGroup(
            membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(membersPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(membersPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(93, Short.MAX_VALUE))
                    .addGroup(membersPanelLayout.createSequentialGroup()
                        .addComponent(memberinformationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(72, 72, 72))
                    .addGroup(membersPanelLayout.createSequentialGroup()
                        .addComponent(searchMemberLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchMemberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchIcon)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        membersPanelLayout.setVerticalGroup(
            membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
            .addGroup(membersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(memberinformationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addGroup(membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchMemberTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchMemberLabel))
                    .addComponent(searchIcon))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("tab2", membersPanel);

        eventsPanel.setBackground(new java.awt.Color(102, 204, 255));

        eventsTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        eventsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Event No.", "Title", "Description", "Date"
            }
        ));
        eventsTable.setShowGrid(false);
        eventsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(eventsTable);
        if (eventsTable.getColumnModel().getColumnCount() > 0) {
            eventsTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        eventTitleLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        eventTitleLabel.setText("EVENT TITLE:");

        descriptionLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        descriptionLabel.setText("DESCRIPTION:");

        eventDateLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        eventDateLabel.setText("DATE:");

        addEventBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addEventBttn.setText("Add");
        addEventBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventBttnActionPerformed(evt);
            }
        });

        editEventBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        editEventBttn.setText("Edit");
        editEventBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEventBttnActionPerformed(evt);
            }
        });

        deleteEventBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deleteEventBttn.setText("Delete");
        deleteEventBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEventBttnActionPerformed(evt);
            }
        });

        resetEventBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetEventBttn.setText("Reset");
        resetEventBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetEventBttnActionPerformed(evt);
            }
        });

        eventTitleTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        descriptionEventTxtArea.setColumns(20);
        descriptionEventTxtArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        descriptionEventTxtArea.setRows(5);
        jScrollPane3.setViewportView(descriptionEventTxtArea);

        eventDateChooser.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CURRENT EVENTS");

        searchEventLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        searchEventLabel.setText("Search Event:");

        searchEventTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        searchEventTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventTxtActionPerformed(evt);
            }
        });
        searchEventTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchEventTxtKeyReleased(evt);
            }
        });

        pdfEventBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        pdfEventBttn.setText("Export to PDF");
        pdfEventBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfEventBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eventsPanelLayout = new javax.swing.GroupLayout(eventsPanel);
        eventsPanel.setLayout(eventsPanelLayout);
        eventsPanelLayout.setHorizontalGroup(
            eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventsPanelLayout.createSequentialGroup()
                .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eventsPanelLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(eventsPanelLayout.createSequentialGroup()
                                .addComponent(deleteEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(resetEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(eventTitleLabel)
                                .addComponent(descriptionLabel)
                                .addComponent(eventDateLabel)
                                .addComponent(eventTitleTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(eventDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(eventsPanelLayout.createSequentialGroup()
                                .addComponent(addEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(189, 189, 189)
                        .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)))
                    .addGroup(eventsPanelLayout.createSequentialGroup()
                        .addGap(656, 656, 656)
                        .addComponent(searchEventLabel)
                        .addGap(18, 18, 18)
                        .addComponent(searchEventTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(pdfEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(310, Short.MAX_VALUE))
        );
        eventsPanelLayout.setVerticalGroup(
            eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventsPanelLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(eventTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eventTitleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(eventDateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eventDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEventBttn)
                    .addComponent(editEventBttn))
                .addGap(26, 26, 26)
                .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteEventBttn)
                    .addComponent(resetEventBttn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, eventsPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchEventTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pdfEventBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(searchEventLabel))
                .addGap(368, 368, 368))
        );

        jTabbedPane1.addTab("tab3", eventsPanel);

        newcomersPanel.setBackground(new java.awt.Color(102, 204, 255));

        fullnameNewcomerLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        fullnameNewcomerLabel.setText("Fullname:");

        fullnameNewcomerTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        ageNewcomerLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        ageNewcomerLabel.setText("Age:");

        ageNewcomerTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ageNewcomerTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageNewcomerTxtActionPerformed(evt);
            }
        });

        birthdayNewlabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        birthdayNewlabel.setText("Birthday:");

        newcomerDateChooser.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        newcomerfbaccLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        newcomerfbaccLabel.setText("Facebook Account:");

        newcomerfbaccTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        newcomerTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        newcomerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Fullname", "Age", "Birthday", "Facebook"
            }
        ));
        newcomerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newcomerTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(newcomerTable);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("NEW COMERS");

        addNewcomerBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addNewcomerBttn.setText("Add");
        addNewcomerBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewcomerBttnActionPerformed(evt);
            }
        });

        editNewcomerBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        editNewcomerBttn.setText("Edit");
        editNewcomerBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNewcomerBttnActionPerformed(evt);
            }
        });

        deleteNewcomerBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deleteNewcomerBttn.setText("Delete");
        deleteNewcomerBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteNewcomerBttnActionPerformed(evt);
            }
        });

        resetNewcomerBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetNewcomerBttn.setText("Reset");
        resetNewcomerBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetNewcomerBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout newcomersPanelLayout = new javax.swing.GroupLayout(newcomersPanel);
        newcomersPanel.setLayout(newcomersPanelLayout);
        newcomersPanelLayout.setHorizontalGroup(
            newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newcomersPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newcomersPanelLayout.createSequentialGroup()
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullnameNewcomerLabel)
                            .addComponent(ageNewcomerLabel)
                            .addComponent(birthdayNewlabel)
                            .addComponent(newcomerfbaccLabel))
                        .addGap(56, 56, 56)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullnameNewcomerTxt)
                            .addComponent(ageNewcomerTxt)
                            .addComponent(newcomerDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(newcomerfbaccTxt))
                        .addGap(170, 170, 170))
                    .addGroup(newcomersPanelLayout.createSequentialGroup()
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteNewcomerBttn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(addNewcomerBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(80, 80, 80)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editNewcomerBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetNewcomerBttn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
                .addGap(202, 202, 202))
        );
        newcomersPanelLayout.setVerticalGroup(
            newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newcomersPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel4)
                .addGap(90, 90, 90)
                .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newcomersPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(newcomersPanelLayout.createSequentialGroup()
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullnameNewcomerLabel)
                            .addComponent(fullnameNewcomerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ageNewcomerLabel)
                            .addComponent(ageNewcomerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newcomersPanelLayout.createSequentialGroup()
                                .addComponent(newcomerDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(43, 43, 43))
                            .addGroup(newcomersPanelLayout.createSequentialGroup()
                                .addComponent(birthdayNewlabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newcomerfbaccLabel)
                            .addComponent(newcomerfbaccTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(98, 98, 98)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addNewcomerBttn)
                            .addComponent(editNewcomerBttn))
                        .addGap(30, 30, 30)
                        .addGroup(newcomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteNewcomerBttn)
                            .addComponent(resetNewcomerBttn))
                        .addGap(502, 502, 502))))
        );

        jTabbedPane1.addTab("tab4", newcomersPanel);

        dateReportLabel.setBackground(new java.awt.Color(102, 204, 255));

        reportsTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        reportsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Report Id", "Name", "Description", "Type", "Date", "Amount"
            }
        ));
        reportsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportsTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(reportsTable);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REPORTS");

        nameReportLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        nameReportLabel.setText("Name:");

        nameReportTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        descriptionReportLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        descriptionReportLabel.setText("Description:");

        descriptionReportTxtArea.setColumns(20);
        descriptionReportTxtArea.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        descriptionReportTxtArea.setRows(5);
        jScrollPane5.setViewportView(descriptionReportTxtArea);

        typeReportLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        typeReportLabel.setText("Type:");

        typeReportComboBox.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        typeReportComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Offerings", "Tithes", "Refunds", " " }));

        typeReportLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        typeReportLabel1.setText("Date:");

        addReportBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addReportBttn.setText("Add");
        addReportBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReportBttnActionPerformed(evt);
            }
        });

        editReportBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        editReportBttn.setText("Edit");
        editReportBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editReportBttnActionPerformed(evt);
            }
        });

        deleteReportBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deleteReportBttn.setText("Delete");
        deleteReportBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteReportBttnActionPerformed(evt);
            }
        });

        resetReportBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetReportBttn.setText("Reset");
        resetReportBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetReportBttnActionPerformed(evt);
            }
        });

        reportDateChooser.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        amountReportLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        amountReportLabel.setText("Amount:");

        amountReportTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        pdfReportBttn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        pdfReportBttn.setText("Export PDF");
        pdfReportBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfReportBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dateReportLabelLayout = new javax.swing.GroupLayout(dateReportLabel);
        dateReportLabel.setLayout(dateReportLabelLayout);
        dateReportLabelLayout.setHorizontalGroup(
            dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dateReportLabelLayout.createSequentialGroup()
                .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dateReportLabelLayout.createSequentialGroup()
                        .addContainerGap(42, Short.MAX_VALUE)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(dateReportLabelLayout.createSequentialGroup()
                                .addComponent(nameReportLabel)
                                .addGap(81, 81, 81)
                                .addComponent(nameReportTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(descriptionReportLabel)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dateReportLabelLayout.createSequentialGroup()
                                .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(typeReportLabel1)
                                        .addComponent(typeReportLabel))
                                    .addGroup(dateReportLabelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(amountReportLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(typeReportComboBox, 0, 180, Short.MAX_VALUE)
                                    .addComponent(reportDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(amountReportTxt))))
                        .addGap(276, 276, 276))
                    .addGroup(dateReportLabelLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(deleteReportBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addReportBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editReportBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetReportBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pdfReportBttn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(263, 263, 263))
        );
        dateReportLabelLayout.setVerticalGroup(
            dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateReportLabelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dateReportLabelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(pdfReportBttn))
                    .addGroup(dateReportLabelLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameReportLabel)
                            .addComponent(nameReportTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(descriptionReportLabel)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(typeReportLabel)
                            .addComponent(typeReportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeReportLabel1)
                            .addComponent(reportDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(amountReportLabel)
                            .addComponent(amountReportTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(118, 118, 118)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addReportBttn)
                            .addComponent(editReportBttn))
                        .addGap(31, 31, 31)
                        .addGroup(dateReportLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteReportBttn)
                            .addComponent(resetReportBttn))))
                .addContainerGap(308, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", dateReportLabel);

        inactiveMembersPanel.setBackground(new java.awt.Color(51, 204, 255));

        inactiveMembersTable.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        inactiveMembersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Fullname", "Age", "Birthday", "Gender", "Contact", "Address", "Life Verse", "Status"
            }
        ));
        jScrollPane7.setViewportView(inactiveMembersTable);
        if (inactiveMembersTable.getColumnModel().getColumnCount() > 0) {
            inactiveMembersTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            inactiveMembersTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            inactiveMembersTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            inactiveMembersTable.getColumnModel().getColumn(6).setPreferredWidth(300);
            inactiveMembersTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        }

        restoreBttn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        restoreBttn.setText("RESTORE");
        restoreBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreBttnActionPerformed(evt);
            }
        });

        inactiveMembersLabel.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        inactiveMembersLabel.setText("INACTIVE MEMBERS");

        searchInactiveTxt.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        searchInactiveTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInactiveTxtActionPerformed(evt);
            }
        });
        searchInactiveTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInactiveTxtKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Search:");

        javax.swing.GroupLayout inactiveMembersPanelLayout = new javax.swing.GroupLayout(inactiveMembersPanel);
        inactiveMembersPanel.setLayout(inactiveMembersPanelLayout);
        inactiveMembersPanelLayout.setHorizontalGroup(
            inactiveMembersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inactiveMembersPanelLayout.createSequentialGroup()
                .addGroup(inactiveMembersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inactiveMembersPanelLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inactiveMembersPanelLayout.createSequentialGroup()
                        .addGap(593, 593, 593)
                        .addComponent(inactiveMembersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(314, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inactiveMembersPanelLayout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addComponent(searchInactiveTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(restoreBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(331, 331, 331))
        );
        inactiveMembersPanelLayout.setVerticalGroup(
            inactiveMembersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inactiveMembersPanelLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(inactiveMembersLabel)
                .addGroup(inactiveMembersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inactiveMembersPanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(restoreBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(222, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inactiveMembersPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(inactiveMembersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchInactiveTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(242, 242, 242))))
        );

        jTabbedPane1.addTab("tab6", inactiveMembersPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1684, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeDashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeDashboardLabelMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_homeDashboardLabelMouseClicked

    private void membersDashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_membersDashboardLabelMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_membersDashboardLabelMouseClicked

    private void homeDashboardLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeDashboardLabelMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_homeDashboardLabelMouseEntered

    private void homeDashboardLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeDashboardLabelMouseExited
        // TODO add your handling code here:
        
    }//GEN-LAST:event_homeDashboardLabelMouseExited

    private void logoutDashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutDashboardLabelMouseClicked
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
        login.setLocationRelativeTo(null); // Center the Login Form

        // Close the current Dashboard
        this.dispose();
    }//GEN-LAST:event_logoutDashboardLabelMouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        int selectedTab = jTabbedPane1.getSelectedIndex();
        String tabTitle = jTabbedPane1.getTitleAt(selectedTab);

        if (tabTitle.equalsIgnoreCase("Home")) {
            int totalMembers = getTotalMembers();
            totalmembersLabel.setText("TOTAL MEMBERS: " + totalMembers);
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void deleteReportBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteReportBttnActionPerformed
        // TODO add your handling code here:

        int selectedRow = reportsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a report to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this report?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int reportId = Integer.parseInt(reportsTable.getValueAt(selectedRow, 0).toString());

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            String sql = "DELETE FROM reportdetails WHERE reportid=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, reportId);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Report deleted successfully.");
                updateReportTable();
                resetFields();
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteReportBttnActionPerformed

    private void editReportBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editReportBttnActionPerformed
        // TODO add your handling code here:

        int selectedRow = reportsTable.getSelectedRow(); // JTable

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a report to update.");
            return;
        }

        // Get report ID from selected row
        int reportId = Integer.parseInt(reportsTable.getValueAt(selectedRow, 0).toString());

        // Get updated input values
        String reportName = nameReportTxt.getText();
        String reportDescription = descriptionReportTxtArea.getText();
        String reportType = typeReportComboBox.getSelectedItem().toString();
        String reportDate;
        int reportAmount;

        try {
            reportAmount = Integer.parseInt(amountReportTxt.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount.");
            return;
        }

        Date date = reportDateChooser.getDate();
        reportDate = (date != null) ? new SimpleDateFormat("yyyy-MM-dd").format(date) : "";

        if (reportName.isEmpty() || reportDescription.isEmpty() || reportDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields.");
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            String sql = "UPDATE reportdetails SET reportname=?, reportdescription=?, reporttype=?, reportdate=?, reportamount=? WHERE reportid=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, reportName);
            pst.setString(2, reportDescription);
            pst.setString(3, reportType);
            pst.setString(4, reportDate);
            pst.setInt(5, reportAmount);
            pst.setInt(6, reportId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Report updated successfully.");
                updateReportTable();
                resetFields();
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editReportBttnActionPerformed

    private void addReportBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReportBttnActionPerformed
        // TODO add your handling code here:

        // Declare Variables
        String reportName, reportDescription, reportType, reportDate;
        int reportAmount; // New: Amount

        // Format Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = reportDateChooser.getDate();
        reportDate = (date != null) ? dateFormat.format(date) : "";

        // Get values from input fields
        reportName = nameReportTxt.getText();
        reportDescription = descriptionReportTxtArea.getText(); // JTextArea
        reportType = typeReportComboBox.getSelectedItem().toString(); // JComboBox

        // Get and validate amount
        try {
            reportAmount = Integer.parseInt(amountReportTxt.getText());
            if (reportAmount < 0) throw new NumberFormatException(); // Optional validation
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount (number only).");
            return;
        }

        // Validate input
        if (reportName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Name.");
            return;
        }
        if (reportDescription.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Report Description.");
            return;
        }
        if (reportDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a Date.");
            return;
        }

        // Insert data into database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            String sql = "INSERT INTO reportdetails (reportname, reportdescription, reporttype, reportdate, reportamount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, reportName);
            pst.setString(2, reportDescription);
            pst.setString(3, reportType);
            pst.setString(4, reportDate);
            pst.setInt(5, reportAmount); // New: amount

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Report Added Successfully");
                updateReportTable(); // Refresh JTable
                resetFields(); // Clear inputs
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addReportBttnActionPerformed

    private void reportsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsTableMouseClicked
        // TODO add your handling code here:
        try {
            int selectedIndex = reportsTable.getSelectedRow(); // Get selected row index

            if (selectedIndex != -1) { // Ensure a row is selected
                DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();
                int columnCount = model.getColumnCount(); // Get the actual column count

                if (columnCount < 6) { // Ensure there are enough columns (reportid, name, description, type, date, amount)
                    JOptionPane.showMessageDialog(null, "Not enough columns in the table!");
                    return;
                }

                // Get data from the selected row
                String reportName = model.getValueAt(selectedIndex, 1).toString();
                String reportDescription = model.getValueAt(selectedIndex, 2).toString();
                String reportType = model.getValueAt(selectedIndex, 3).toString();
                String reportDateStr = model.getValueAt(selectedIndex, 4).toString();
                String reportAmount = model.getValueAt(selectedIndex, 5).toString();

                // Convert reportDate (String) to Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date reportDate = dateFormat.parse(reportDateStr);

                // Set values to input fields
                nameReportTxt.setText(reportName);
                descriptionReportTxtArea.setText(reportDescription);
                typeReportComboBox.setSelectedItem(reportType);
                reportDateChooser.setDate(reportDate); // Set JDateChooser value
                amountReportTxt.setText(reportAmount);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error setting data from table: " + e.getMessage());
        }
    }//GEN-LAST:event_reportsTableMouseClicked

    private void searchEventTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchEventTxtKeyReleased
        // TODO add your handling code here:
        DefaultTableModel ob=(DefaultTableModel) eventsTable.getModel();
        TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(ob);
        eventsTable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchEventTxt.getText()));
    }//GEN-LAST:event_searchEventTxtKeyReleased

    private void searchEventTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchEventTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchEventTxtActionPerformed

    private void resetEventBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetEventBttnActionPerformed
        // TODO add your handling code here:
        resetFields();
    }//GEN-LAST:event_resetEventBttnActionPerformed

    private void deleteEventBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEventBttnActionPerformed
        // TODO add your handling code here:
        int selectedRow = eventsTable.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an event to delete.");
            return;
        }

        String eventID = eventsTable.getValueAt(selectedRow, 0).toString(); // Assuming first column is Event ID

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this event?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
                PreparedStatement pst = con.prepareStatement("DELETE FROM eventdetails WHERE eventid=?");

                pst.setString(1, eventID);

                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Event Deleted Successfully");
                    updateEventTable();
                    resetFields();
                }

                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteEventBttnActionPerformed

    private void editEventBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEventBttnActionPerformed
        // TODO add your handling code here:
        int selectedRow = eventsTable.getSelectedRow(); // Get selected row from JTable
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an event to update.");
            return;
        }

        String eventID = eventsTable.getValueAt(selectedRow, 0).toString(); // Assuming first column is Event ID
        String eventTitle = eventTitleTxt.getText();
        String eventDescription = descriptionEventTxtArea.getText();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = eventDateChooser.getDate();
        String eventDate = (date != null) ? dateFormat.format(date) : "";

        // Validation
        if (eventTitle.isEmpty() || eventDescription.isEmpty() || eventDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields before updating.");
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            PreparedStatement pst = con.prepareStatement("UPDATE eventdetails SET eventtitle=?, description=?, eventdate=? WHERE eventid=?");

            pst.setString(1, eventTitle);
            pst.setString(2, eventDescription);
            pst.setString(3, eventDate);
            pst.setString(4, eventID);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Event Updated Successfully");
                updateEventTable();
                resetFields();
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_editEventBttnActionPerformed

    private void addEventBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventBttnActionPerformed
        // TODO add your handling code here:
        String eventTitle, eventDescription, eventDate;

        // Format Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = eventDateChooser.getDate();
        eventDate = (date != null) ? dateFormat.format(date) : "";

        // Get values from input fields
        eventTitle = eventTitleTxt.getText();
        eventDescription = descriptionEventTxtArea.getText();  // Retrieve from JTextArea

        // Validate input
        if (eventTitle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Event Title.");
            return;
        }
        if (eventDescription.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Description.");
            return;
        }
        if (eventDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a Date.");
            return;
        }

        // Insert data into database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            PreparedStatement pst = con.prepareStatement("INSERT INTO eventdetails (eventtitle, description, eventdate) VALUES (?, ?, ?)");

            pst.setString(1, eventTitle);
            pst.setString(2, eventDescription);
            pst.setString(3, eventDate);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Event Added Successfully");
                updateEventTable(); // Refresh JTable after update
                resetFields(); // Reset input fields
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addEventBttnActionPerformed

    private void eventsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventsTableMouseClicked
        // TODO add your handling code here:
        try {
            int selectedIndex = eventsTable.getSelectedRow(); // Get selected row index

            if (selectedIndex != -1) { // Ensure a row is selected
                DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
                int columnCount = model.getColumnCount(); // Check column count

                if (columnCount < 4) { // title, description, date
                    JOptionPane.showMessageDialog(null, "Not enough columns in the table!");
                    return;
                }

                // Get data from the selected row
                String title = model.getValueAt(selectedIndex, 1).toString(); // column index 1 = title
                String description = model.getValueAt(selectedIndex, 2).toString(); // description
                String dateStr = model.getValueAt(selectedIndex, 3).toString(); // date

                // Convert date string to Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date eventDate = dateFormat.parse(dateStr);

                // Set data into input fields
                eventTitleTxt.setText(title);
                descriptionEventTxtArea.setText(description);
                eventDateChooser.setDate(eventDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error setting data from table: " + e.getMessage());
        }
    }//GEN-LAST:event_eventsTableMouseClicked

    private void searchMemberTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchMemberTxtKeyReleased
        // TODO add your handling code here:

        DefaultTableModel ob =(DefaultTableModel) membersTable.getModel();
        TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(ob);
        membersTable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchMemberTxt.getText()));
    }//GEN-LAST:event_searchMemberTxtKeyReleased

    private void searchMemberTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMemberTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchMemberTxtActionPerformed

    private void membersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_membersTableMouseClicked
        // TODO add your handling code here:
        try {
            int selectedIndex = membersTable.getSelectedRow(); // Get selected row index

            if (selectedIndex != -1) { // Ensure a row is selected
                DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
                int columnCount = model.getColumnCount(); // Get the actual column count

                if (columnCount < 8) { // Ensure we have at least 8 columns (index 0 to 7)
                    JOptionPane.showMessageDialog(null, "Not enough columns in the table!");
                    return;
                }

                // Get data from the selected row
                String fullname = model.getValueAt(selectedIndex, 1).toString();
                String age = model.getValueAt(selectedIndex, 2).toString();
                String birthdayStr = model.getValueAt(selectedIndex, 3).toString(); // Birthday as String
                String gender = model.getValueAt(selectedIndex, 4).toString();
                String contact = model.getValueAt(selectedIndex, 5).toString();
                String address = model.getValueAt(selectedIndex, 6).toString();
                String lifeverse = model.getValueAt(selectedIndex, 7).toString();

                // Convert Birthday (String) to Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthdayDate = dateFormat.parse(birthdayStr);

                // Set the values in input fields
                fullnameTxt.setText(fullname);
                ageTxt.setText(age);
                birthdayDateChooser.setDate(birthdayDate); // Set JDateChooser value
                genderComboBox.setSelectedItem(gender);
                contactTxt.setText(contact);
                addressTxt.setText(address);
                lifeverseTxt.setText(lifeverse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error setting data from table: " + e.getMessage());
        }

    }//GEN-LAST:event_membersTableMouseClicked

    private void resetBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBttnActionPerformed
        // TODO add your handling code here:
        resetFields(); // Reset input fields
    }//GEN-LAST:event_resetBttnActionPerformed

    private void deleteBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBttnActionPerformed
        // TODO add your handling code here:
        try {
            int selectedIndex = membersTable.getSelectedRow();

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(null, "Please select a record to delete.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
            String id = model.getValueAt(selectedIndex, 0).toString(); // Assuming first column is ID

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
                String sql = "DELETE FROM memberdetails WHERE id=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, id);

                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Record Deleted Successfully");
                    updateMemberTable(); // Refresh JTable after deletion
                    resetFields(); // Reset input fields
                    updateMemberCount();
                }

                pst.close();
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_deleteBttnActionPerformed

    private void updateBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBttnActionPerformed
        // TODO add your handling code here:
        try {
            int selectedIndex = membersTable.getSelectedRow();

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(null, "Please select a record to update.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) membersTable.getModel();
            String memberid = model.getValueAt(selectedIndex, 0).toString(); // Assuming first column is ID

            // Get updated values
            String fullname = fullnameTxt.getText();
            String age = ageTxt.getText();
            String birthday = new SimpleDateFormat("yyyy-MM-dd").format(birthdayDateChooser.getDate());
            String gender = (String) genderComboBox.getSelectedItem();
            String contact = contactTxt.getText();
            String address = addressTxt.getText();
            String lifeverse = lifeverseTxt.getText();

            // Validate inputs
            if (fullname.isEmpty() || age.isEmpty() || contact.isEmpty() || address.isEmpty() || lifeverse.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields before updating.");
                return;
            }

            // Execute update query
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            String sql = "UPDATE memberdetails SET fullname=?, age=?, birthday=?, gender=?, contact=?, address=?, lifeverse=? WHERE memberid=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, fullname);
            pst.setString(2, age);
            pst.setString(3, birthday);
            pst.setString(4, gender);
            pst.setString(5, contact);
            pst.setString(6, address);
            pst.setString(7, lifeverse);
            pst.setString(8, memberid); // Bind ID for WHERE condition

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Record Updated Successfully");
                updateMemberTable(); // Refresh JTable after update
                updateMemberCount();
                resetFields(); // Reset input fields
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating record: " + ex.getMessage());
            ex.printStackTrace();
        }

    }//GEN-LAST:event_updateBttnActionPerformed

    private void addBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBttnActionPerformed
        // TODO add your handling code here:

        // Declare Variables
        String fullname, age, birthday, gender, contact, address, lifeverse;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = birthdayDateChooser.getDate();
        birthday = (date != null) ? dateFormat.format(date) : "";

        fullname = fullnameTxt.getText();
        age = ageTxt.getText();
        contact = contactTxt.getText();

        address = addressTxt.getText();
        lifeverse = lifeverseTxt.getText();
        gender = (String) genderComboBox.getSelectedItem();

        // Validation for Age (must be a number)
        if (!age.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid age (numbers only)");
            return;
        }

        // Validation for Contact (must be 11-digit number)
        if (!contact.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid 11-digit contact number");
            return;
        }

        // Check if other fields are empty
        if (fullname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Full Name");
        } else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Address");
        } else if (lifeverse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Life Verse");
        } else {

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
                pst = con.prepareStatement("INSERT INTO memberdetails (fullname, age, birthday, gender, contact, address, lifeverse) VALUES (?, ?, ?, ?, ?, ?, ?)");

                pst.setString(1, fullname);
                pst.setString(2, age);
                pst.setString(3, birthday);
                pst.setString(4, gender);
                pst.setString(5, contact);
                pst.setString(6, address);
                pst.setString(7, lifeverse);

                int rowsInserted = pst.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Data Added Successfully");
                    updateMemberTable(); // Refresh JTable after update
                    resetFields(); // Reset input fields
                    updateMemberCount();
                }

                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_addBttnActionPerformed

    private void addBttnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBttnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addBttnMouseClicked

    private void genderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderComboBoxActionPerformed

    private void contactTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactTxtKeyTyped
        // TODO add your handling code here:

        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || contactTxt.getText().length() >= 11) {
            evt.consume(); // Ignore non-numeric input and limit length
        }
    }//GEN-LAST:event_contactTxtKeyTyped

    private void ageTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ageTxtKeyTyped
        // TODO add your handling code here:

        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // Prevent non-numeric input
        }
    }//GEN-LAST:event_ageTxtKeyTyped

    private void ageNewcomerTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageNewcomerTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageNewcomerTxtActionPerformed

    private void resetReportBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetReportBttnActionPerformed
        // TODO add your handling code here:
        resetFields(); // Reset input fields
    }//GEN-LAST:event_resetReportBttnActionPerformed

    private void addNewcomerBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewcomerBttnActionPerformed
        // TODO add your handling code here:
        // Declare Variables
        String newcomerFullname, newcomerBirthday, newcomerFBAcc;
        int newcomerAge;

        // Format Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = newcomerDateChooser.getDate();
        newcomerBirthday = (date != null) ? dateFormat.format(date) : "";

        // Get values from input fields
        newcomerFullname = fullnameNewcomerTxt.getText();
        newcomerFBAcc = newcomerfbaccTxt.getText(); // Facebook Account

        // Get and validate age
        try {
            newcomerAge = Integer.parseInt(ageNewcomerTxt.getText());
            if (newcomerAge < 0) throw new NumberFormatException(); // Optional: ensure non-negative
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid age (number only).");
            return;
        }

        // Validate inputs
        if (newcomerFullname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the Full Name.");
            return;
        }
        if (newcomerBirthday.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a Birthday.");
            return;
        }
        if (newcomerFBAcc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the Facebook Account.");
            return;
        }

        // Insert data into database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
            String sql = "INSERT INTO newcomerdetails (newcomerfullname, newcomerage, newcomerbirthday, newcomerfbacc) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, newcomerFullname);
            pst.setInt(2, newcomerAge);
            pst.setString(3, newcomerBirthday);
            pst.setString(4, newcomerFBAcc);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Newcomer Added Successfully");
                updateNewcomerTable(); // Refresh JTable method
                resetFields(); // Clear input fields
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addNewcomerBttnActionPerformed

    private void editNewcomerBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNewcomerBttnActionPerformed
        // TODO add your handling code here:
       
            int selectedRow = newcomerTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update.");
                return;
            }

            try {
                String id = newcomerTable.getValueAt(selectedRow, 0).toString();
                String fullname = fullnameNewcomerTxt.getText();
                int age = Integer.parseInt(ageNewcomerTxt.getText());
                String fbacc = newcomerfbaccTxt.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = newcomerDateChooser.getDate();
                String birthday = (date != null) ? sdf.format(date) : "";

                if (fullname.isEmpty() || birthday.isEmpty() || fbacc.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
                String sql = "UPDATE newcomerdetails SET newcomerfullname = ?, newcomerage = ?, newcomerbirthday = ?, newcomerfbacc = ? WHERE newcomerid = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, fullname);
                pst.setInt(2, age);
                pst.setString(3, birthday);
                pst.setString(4, fbacc);
                pst.setString(5, id);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Newcomer updated successfully!");
                    updateNewcomerTable(); // Refresh table
                    resetFields(); // Clear input fields
                }

                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating: " + e.getMessage());
            }
        
    }//GEN-LAST:event_editNewcomerBttnActionPerformed

    private void deleteNewcomerBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteNewcomerBttnActionPerformed
        // TODO add your handling code here:
            int selectedRow = newcomerTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this newcomer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                String id = newcomerTable.getValueAt(selectedRow, 0).toString();

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
                String sql = "DELETE FROM newcomerdetails WHERE newcomerid = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, id);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Newcomer deleted successfully!");
                    updateNewcomerTable(); // Refresh table
                    resetFields(); // Clear input fields
                }

                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting: " + e.getMessage());
            }

    }//GEN-LAST:event_deleteNewcomerBttnActionPerformed

    private void newcomerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newcomerTableMouseClicked
        // TODO add your handling code here:
        try {
            int selectedIndex = newcomerTable.getSelectedRow(); // Get selected row index

            if (selectedIndex != -1) { // Ensure a row is selected
                DefaultTableModel model = (DefaultTableModel) newcomerTable.getModel();
                int columnCount = model.getColumnCount(); // Check column count

                if (columnCount < 5) { // fullname, age, birthday, fbacc
                    JOptionPane.showMessageDialog(null, "Not enough columns in the table!");
                    return;
                }

                // Get data from the selected row
                String fullname = model.getValueAt(selectedIndex, 1).toString(); // column 1 = fullname
                String ageStr = model.getValueAt(selectedIndex, 2).toString();   // column 2 = age
                String birthdayStr = model.getValueAt(selectedIndex, 3).toString(); // column 3 = birthday
                String fbacc = model.getValueAt(selectedIndex, 4).toString();   // column 4 = fb account

                // Convert birthday string to Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthdayDate = dateFormat.parse(birthdayStr);

                // Set values into input fields
                fullnameNewcomerTxt.setText(fullname);
                ageNewcomerTxt.setText(ageStr);
                newcomerDateChooser.setDate(birthdayDate);
                newcomerfbaccTxt.setText(fbacc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error setting data from table: " + e.getMessage());
        }
    }//GEN-LAST:event_newcomerTableMouseClicked

    private void resetNewcomerBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetNewcomerBttnActionPerformed
        // TODO add your handling code here:
        resetFields(); // Clear input fields
    }//GEN-LAST:event_resetNewcomerBttnActionPerformed

    private void inactiveBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inactiveBttnActionPerformed
        // TODO add your handling code here:
        try {
           // 1. Get selected member ID from your JTable
           int selectedRow = membersTable.getSelectedRow();
           if (selectedRow == -1) {
               JOptionPane.showMessageDialog(null, "Please select a member to mark as inactive.");
               return;
           }

           int memberId = Integer.parseInt(membersTable.getValueAt(selectedRow, 0).toString());

           // 2. Connect to the database
           Connection conn = Connector.getConnection();

           // 3. Check if member is already in the inactivemember table
           String checkQuery = "SELECT * FROM inactivememberdetails WHERE inactiveid = ?";
           PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
           checkStmt.setInt(1, memberId);
           ResultSet rs = checkStmt.executeQuery();

           if (!rs.next()) {
               // 4. Insert all data from memberdetails into inactivememberdetails
               String insertQuery = "INSERT INTO inactivememberdetails " +
                       "(inactiveid, fullname, age, birthday, gender, contact, address, lifeverse, status) " +
                       "SELECT memberid, fullname, age, birthday, gender, contact, address, lifeverse, 'inactive' " +
                       "FROM memberdetails WHERE memberid = ?";
               PreparedStatement pstInsert = conn.prepareStatement(insertQuery);
               pstInsert.setInt(1, memberId);
               pstInsert.executeUpdate();
           }

           // 5. Update status in memberdetails
           String updateStatusQuery = "UPDATE memberdetails SET status = 'inactive' WHERE memberid = ?";
           PreparedStatement pstUpdate = conn.prepareStatement(updateStatusQuery);
           pstUpdate.setInt(1, memberId);
           pstUpdate.executeUpdate();

           // 6. Feedback to user
           JOptionPane.showMessageDialog(null, "Member marked as inactive successfully.");

           // 7. Refresh table
           updateInactiveMembersTableData();
           loadMemberTableData();

           conn.close();

       } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Error while marking inactive: " + ex.getMessage());
       }
    
    }//GEN-LAST:event_inactiveBttnActionPerformed

    private void inactiveMembersDashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inactiveMembersDashboardLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inactiveMembersDashboardLabelMouseClicked

    private void restoreBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreBttnActionPerformed
        // TODO add your handling code here:
        try {
        int selectedRow = inactiveMembersTable.getSelectedRow(); // JTable for inactive members
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a member to restore.");
            return;
        }

        int memberId = Integer.parseInt(inactiveMembersTable.getValueAt(selectedRow, 0).toString());

        // 1. Connect to DB
        Connection conn = Connector.getConnection();

        // 2. Update status back to 'active' in memberdetails
        String updateQuery = "UPDATE memberdetails SET status = 'active' WHERE memberid = ?";
        PreparedStatement pstUpdate = conn.prepareStatement(updateQuery);
        pstUpdate.setInt(1, memberId);
        pstUpdate.executeUpdate();

        // 3. Optional: Remove from inactivememberdetails
        String deleteQuery = "DELETE FROM inactivememberdetails WHERE inactiveid = ?";
        PreparedStatement pstDelete = conn.prepareStatement(deleteQuery);
        pstDelete.setInt(1, memberId);
        pstDelete.executeUpdate();

        // 4. Confirmation
        JOptionPane.showMessageDialog(null, "Member restored to active successfully.");

        // 5. Refresh both tables
        loadInactiveMembersTableData();
        loadMemberTableData();

        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error while restoring member: " + e.getMessage());
    }
    }//GEN-LAST:event_restoreBttnActionPerformed

    private void pdfEventBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfEventBttnActionPerformed
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Events Report");
        fileChooser.setSelectedFile(new File("events_report.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                Image logo = Image.getInstance("src\\Images\\Logo-smaller.png");
                logo.scaleToFit(100, 100);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);

                document.add(new Paragraph("Events", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                document.add(new Paragraph(" ")); // Spacer

                PdfPTable pdfTable = new PdfPTable(eventsTable.getColumnCount());

                for (int i = 0; i < eventsTable.getColumnCount(); i++) {
                    pdfTable.addCell(new PdfPCell(new Phrase(eventsTable.getColumnName(i))));
                }

                for (int row = 0; row < eventsTable.getRowCount(); row++) {
                    for (int col = 0; col < eventsTable.getColumnCount(); col++) {
                        Object cellValue = eventsTable.getValueAt(row, col);
                        pdfTable.addCell(cellValue != null ? cellValue.toString() : "");
                    }
                }

                document.add(pdfTable);
                document.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(filePath));
                }

                JOptionPane.showMessageDialog(null, "Events PDF Exported Successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error exporting events PDF: " + e.getMessage());
            }
        }
        
    }//GEN-LAST:event_pdfEventBttnActionPerformed

    private void pdfReportBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfReportBttnActionPerformed
        // TODO add your handling code here:
        
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Report PDF");
    fileChooser.setSelectedFile(new File("reports_export.pdf"));

    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            // Add logo
            Image logo = Image.getInstance("src/images/logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);

            // Add title
            document.add(new Paragraph("Reports Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" ")); // spacing

            PdfPTable pdfTable = new PdfPTable(reportsTable.getColumnCount());

            for (int i = 0; i < reportsTable.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(reportsTable.getColumnName(i))));
            }

            for (int row = 0; row < reportsTable.getRowCount(); row++) {
                for (int col = 0; col < reportsTable.getColumnCount(); col++) {
                    Object cellValue = reportsTable.getValueAt(row, col);
                    pdfTable.addCell(cellValue != null ? cellValue.toString() : "");
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(null, "Reports PDF Exported Successfully!");

            // Open the PDF file
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(fileToSave);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error exporting reports PDF: " + e.getMessage());
        }
    }


    }//GEN-LAST:event_pdfReportBttnActionPerformed

    private void searchInactiveTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInactiveTxtKeyReleased
        // TODO add your handling code here:
        DefaultTableModel ob =(DefaultTableModel) inactiveMembersTable.getModel();
        TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(ob);
        inactiveMembersTable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchInactiveTxt.getText()));
    }//GEN-LAST:event_searchInactiveTxtKeyReleased

    private void searchInactiveTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchInactiveTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchInactiveTxtActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBttn;
    private javax.swing.JButton addEventBttn;
    private javax.swing.JButton addNewcomerBttn;
    private javax.swing.JButton addReportBttn;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JLabel ageNewcomerLabel;
    private javax.swing.JTextField ageNewcomerTxt;
    private javax.swing.JTextField ageTxt;
    private javax.swing.JLabel amountReportLabel;
    private javax.swing.JTextField amountReportTxt;
    private com.toedter.calendar.JDateChooser birthdayDateChooser;
    private javax.swing.JLabel birthdayLabel;
    private javax.swing.JLabel birthdayNewlabel;
    private javax.swing.JLabel contactLabel;
    private javax.swing.JTextField contactTxt;
    private javax.swing.JPanel dateReportLabel;
    private javax.swing.JButton deleteBttn;
    private javax.swing.JButton deleteEventBttn;
    private javax.swing.JButton deleteNewcomerBttn;
    private javax.swing.JButton deleteReportBttn;
    private javax.swing.JTextArea descriptionEventTxtArea;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel descriptionReportLabel;
    private javax.swing.JTextArea descriptionReportTxtArea;
    private javax.swing.JButton editEventBttn;
    private javax.swing.JButton editNewcomerBttn;
    private javax.swing.JButton editReportBttn;
    private com.toedter.calendar.JDateChooser eventDateChooser;
    private javax.swing.JLabel eventDateLabel;
    private javax.swing.JLabel eventTitleLabel;
    private javax.swing.JTextField eventTitleTxt;
    private javax.swing.JLabel eventsDashboardLabel;
    private javax.swing.JPanel eventsPanel;
    private javax.swing.JTable eventsTable;
    private javax.swing.JLabel fullnameLabel;
    private javax.swing.JLabel fullnameNewcomerLabel;
    private javax.swing.JTextField fullnameNewcomerTxt;
    private javax.swing.JTextField fullnameTxt;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel homeDashboardLabel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton inactiveBttn;
    private javax.swing.JLabel inactiveMembersDashboardLabel;
    private javax.swing.JLabel inactiveMembersLabel;
    private javax.swing.JPanel inactiveMembersPanel;
    private javax.swing.JTable inactiveMembersTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lifeverseLabel;
    private javax.swing.JTextField lifeverseTxt;
    private javax.swing.JLabel logoDashboardLabel;
    private javax.swing.JLabel logoutDashboardLabel;
    private javax.swing.JLabel memberinformationLabel;
    private javax.swing.JLabel membersDashboardLabel;
    private javax.swing.JPanel membersPanel;
    private javax.swing.JTable membersTable;
    private javax.swing.JLabel nameReportLabel;
    private javax.swing.JTextField nameReportTxt;
    private com.toedter.calendar.JDateChooser newcomerDateChooser;
    private javax.swing.JTable newcomerTable;
    private javax.swing.JLabel newcomerfbaccLabel;
    private javax.swing.JTextField newcomerfbaccTxt;
    private javax.swing.JLabel newcomersDashboardLabel;
    private javax.swing.JPanel newcomersPanel;
    private javax.swing.JToggleButton pdfEventBttn;
    private javax.swing.JButton pdfReportBttn;
    private com.toedter.calendar.JDateChooser reportDateChooser;
    private javax.swing.JLabel reportsDashboardLabel;
    private javax.swing.JTable reportsTable;
    private javax.swing.JButton resetBttn;
    private javax.swing.JButton resetEventBttn;
    private javax.swing.JButton resetNewcomerBttn;
    private javax.swing.JButton resetReportBttn;
    private javax.swing.JButton restoreBttn;
    private javax.swing.JLabel searchEventLabel;
    private javax.swing.JTextField searchEventTxt;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTextField searchInactiveTxt;
    private javax.swing.JLabel searchMemberLabel;
    private javax.swing.JTextField searchMemberTxt;
    private javax.swing.JLabel totaleventsLabel;
    private javax.swing.JLabel totalmembersLabel;
    private javax.swing.JLabel totalnewcomerLabel;
    private javax.swing.JLabel totalreportsLabel;
    private javax.swing.JComboBox<String> typeReportComboBox;
    private javax.swing.JLabel typeReportLabel;
    private javax.swing.JLabel typeReportLabel1;
    private javax.swing.JButton updateBttn;
    // End of variables declaration//GEN-END:variables
}
