/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inmfacebookdb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author frice
 */
public class MySQLConnection {
    public static Connection getDBConnection() throws Exception{
        try{
            String driver ="com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/facebook_stats";
            String username="root";
            String password="fergal";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
            
        }catch(Exception e){
            e.printStackTrace();
        }
    return null;
    }
}
