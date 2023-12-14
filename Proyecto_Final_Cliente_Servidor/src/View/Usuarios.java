/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Model.Connector;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Usuarios extends javax.swing.JInternalFrame {

    Connector conn = new Connector();
    Connection connection = conn.getConexion();
    PreparedStatement pst;
    DefaultTableModel model;
    Statement st;
    ResultSet rs;

    public Usuarios() {
        initComponents();
        retrieve();
    }

    void retrieve() {
        clean_table();
        String query = "select * from user";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery(query);
            Object[] User = new Object[3];
            model = (DefaultTableModel) userTable.getModel();
            while (rs.next()) {
                User[0] = rs.getInt("id");
                User[1] = rs.getString("email");
                User[2] = rs.getInt("password");

                model.addRow(User);
            }
            userTable.setModel(model);
        } catch (Exception e) {
            System.out.println("Error while retrieving data: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    public boolean checkSame(String email, String Pass) {
        Connector conn = new Connector();
        Connection connection = conn.getConexion();
        String query = "SELECT * FROM user WHERE email = ? OR password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, Pass);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    void modifyUser() {
        String userIdText = IDTEXT.getText();
        String email_value = EMAILTXT.getText();
        String Pass_value = PASSTXT.getText();

        try {
            if (userIdText.equals("")) {
                JOptionPane.showMessageDialog(null, "El Id esta vacio");
            } else if (email_value.equals("") || Pass_value.equals("")) {
                JOptionPane.showMessageDialog(null, "Email o Password no pueden estar vacíos");
            } else if (checkSame(email_value, Pass_value)) {
                JOptionPane.showMessageDialog(null, "Email o Password ya existen");
            } else {
                String query = "UPDATE user SET email = ?, password = ? WHERE id = ?";
                connection = conn.getConexion();
                pst = connection.prepareStatement(query);
                pst.setString(1, email_value);
                pst.setString(2, Pass_value);
                pst.setInt(3, Integer.parseInt(userIdText));

                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Usuario modificado exitosamente");
                clean_table();
            }
        } catch (Exception e) {
            clean_table();
            System.out.println("Error while modifying user: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al modificar usuario");
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    void clean_table() {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    void delete() {
        int userId = Integer.parseInt(IDTEXT.getText());

        try {

            if (userId < 0) {
                JOptionPane.showMessageDialog(null, "No hay una fila seleccionada");
            } else {
                String query = "DELETE FROM user WHERE id = ?";

                connection = conn.getConexion();
                pst = connection.prepareStatement(query);
                pst.setInt(1, userId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "usuario eliminado exitosamente");
                clean_table();
            }
        } catch (Exception e) {
            clean_table();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException error) {
                    error.printStackTrace();
                }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        Checkusers = new javax.swing.JButton();
        eliminarbtn = new javax.swing.JButton();
        modificarbtn = new javax.swing.JButton();
        IDTEXT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        EMAILTXT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        PASSTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "email", "password"
            }
        ));
        jScrollPane1.setViewportView(userTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 125, 580, 181));

        Checkusers.setBackground(new java.awt.Color(51, 0, 0));
        Checkusers.setForeground(new java.awt.Color(255, 255, 255));
        Checkusers.setText("Check users");
        Checkusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckusersActionPerformed(evt);
            }
        });
        getContentPane().add(Checkusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 83, 100, 30));

        eliminarbtn.setBackground(new java.awt.Color(51, 0, 0));
        eliminarbtn.setForeground(new java.awt.Color(255, 255, 255));
        eliminarbtn.setText("Delete");
        eliminarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarbtnActionPerformed(evt);
            }
        });
        getContentPane().add(eliminarbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 83, 80, 30));

        modificarbtn.setBackground(new java.awt.Color(51, 0, 0));
        modificarbtn.setForeground(new java.awt.Color(255, 255, 255));
        modificarbtn.setText("Modify");
        modificarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarbtnActionPerformed(evt);
            }
        });
        getContentPane().add(modificarbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 83, 80, 30));
        getContentPane().add(IDTEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 80, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 0));
        jLabel1.setText("ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 0));
        jLabel2.setText("Email:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        EMAILTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EMAILTXTActionPerformed(evt);
            }
        });
        getContentPane().add(EMAILTXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 110, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Password:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));
        getContentPane().add(PASSTXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 100, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/WhatsApp Image 2023-12-13 at 22.49.30_f9160e33.jpg"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckusersActionPerformed
        retrieve();
    }//GEN-LAST:event_CheckusersActionPerformed

    private void eliminarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarbtnActionPerformed
        String userID = IDTEXT.getText();
        try {
            userID = (userID);
            delete();
            retrieve();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de User valido.");
        }

        IDTEXT.setText("");
    }//GEN-LAST:event_eliminarbtnActionPerformed

    private void modificarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarbtnActionPerformed
        modifyUser();
        clean_table();
        retrieve();
    }//GEN-LAST:event_modificarbtnActionPerformed

    private void EMAILTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EMAILTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EMAILTXTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Checkusers;
    private javax.swing.JTextField EMAILTXT;
    private javax.swing.JTextField IDTEXT;
    private javax.swing.JTextField PASSTXT;
    private javax.swing.JButton eliminarbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificarbtn;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
