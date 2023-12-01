/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author sebaw
 */
public class Connector {
    public static final String URL = "jdbc:mysql://localhost:3306/proyecto_final_cliente";
    public static final String USER = "root";
    public static final String CLAVE = "sebas442018";
    
    public Connection getConexion(){
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return con;
    }
    
}
