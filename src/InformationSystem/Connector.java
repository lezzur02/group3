/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package InformationSystem;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author Ruth
 */
public class Connector {


    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use cj for modern MySQL drivers
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_systemdb", "root", "@ruthmysql06");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        // Optional: You can test the connection here
        Connection testConn = getConnection();
        if (testConn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}

  
