/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Consultas {
    Connector conn=new Connector();       	
    Connection connection = conn.getConexion();
    DefaultTableModel model;
    Statement st;
    ResultSet rs;
    public void guardarUsuario(String usuario, String password){
        Connector db = new Connector();
        String sql = "insert into user(email, password) values ('" + usuario +"', '" + password +"');";
        Statement st;
        Connection conexion = (Connection) db.getConexion();
        try
        {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Guardado correctamente");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    public boolean checkUserCredentials(String username, String password) {       
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    /*
    public void retrieve(){
        String query = "select * from producto";
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Object[] estudiante = new Object[7];
            model = (DefaultTableModel) userTable.;
            while (rs.next()) {
                estudiante[0] = rs.getInt("id");
                estudiante[1] = rs.getString("nombre");                
                estudiante[2] = rs.getDouble("precio");
                estudiante[3] = rs.getInt("cantidad");
                
                model.addRow(estudiante);
            }
            results.setModel(model);
        } catch (Exception e){
            System.out.println("Error while retrieving data: "+ e.getMessage());
        }
        finally {
            //Esto nos limpia los resultados obtenidos al ejecutar la consulta
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException error)
                {
                    error.printStackTrace();
                }
            }
            //Vamos a limpiar la memoria destinada para la consulta
            if(st != null)
            {
                try
                {
                    st.close();
                }
                catch(SQLException error)
                {
                    error.printStackTrace();
                }
            }
            //Vamos a cerrar la conexión
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch(SQLException error)
                {
                    error.printStackTrace();
                }
            }
        }
    }
*/
}
