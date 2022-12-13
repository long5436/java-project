/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Long
 */
public class DatabaseUtil {

    public static Connection c;
    private static String db_url = "jdbc:mysql://localhost:3306/doandb";
    private static String username = "root";
    private static String password = "";

    public static Connection getConnection() throws Exception {
        if (c == null) {
           // Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(db_url, username, password);
        }
        return c;
    }
    
//    public static Connection connection() throws Exception {
//        Connection con = DatabaseUtil.getConnection();
//        return con;
//    }

    public static PreparedStatement createPreparedStatement(String sql) throws Exception {

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

}
