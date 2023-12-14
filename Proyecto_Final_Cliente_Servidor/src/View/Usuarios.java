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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "email", "password"
            }
        ));
        jScrollPane1.setViewportView(userTable);

        Checkusers.setText("Check users");
        Checkusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckusersActionPerformed(evt);
            }
        });

        eliminarbtn.setText("Delete");
        eliminarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarbtnActionPerformed(evt);
            }
        });

        modificarbtn.setText("Modify");
        modificarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarbtnActionPerformed(evt);
            }
        });

        jLabel1.setText("ID:");

        jLabel2.setText("Email:");

        jLabel3.setText("Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IDTEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Checkusers))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(eliminarbtn)
                        .addGap(151, 151, 151)
                        .addComponent(modificarbtn)
                        .addGap(69, 69, 69))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(PASSTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(EMAILTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDTEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(PASSTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EMAILTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Checkusers)
                    .addComponent(eliminarbtn)
                    .addComponent(modificarbtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
    }//GEN-LAST:event_modificarbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Checkusers;
    private javax.swing.JTextField EMAILTXT;
    private javax.swing.JTextField IDTEXT;
    private javax.swing.JTextField PASSTXT;
    private javax.swing.JButton eliminarbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificarbtn;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
