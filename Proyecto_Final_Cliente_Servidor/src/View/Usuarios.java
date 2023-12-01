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
    Connector conn=new Connector();       	
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
  
    void clean_table() {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
       void delete() { 
        int userId = Integer.parseInt(userIdTF.getText());

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
        insertar = new javax.swing.JButton();
        eliminarbtn = new javax.swing.JButton();
        AgregarUser = new javax.swing.JButton();
        modificarbtn = new javax.swing.JButton();
        userIdTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

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

        insertar.setText("Ver Usuarios");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        eliminarbtn.setText("eliminar");
        eliminarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarbtnActionPerformed(evt);
            }
        });

        AgregarUser.setText("Agregar");
        AgregarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarUserActionPerformed(evt);
            }
        });

        modificarbtn.setText("modificar");

        jLabel1.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(insertar)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarbtn)
                        .addGap(18, 18, 18)
                        .addComponent(AgregarUser)
                        .addGap(18, 18, 18)
                        .addComponent(modificarbtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertar)
                    .addComponent(eliminarbtn)
                    .addComponent(AgregarUser)
                    .addComponent(modificarbtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarUserActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
       retrieve();
    }//GEN-LAST:event_insertarActionPerformed

    private void eliminarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarbtnActionPerformed
    String productIdText = userIdTF.getText();
    try {     
        productIdText = (productIdText);      
        delete();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de User valido.");
   }
    
    userIdTF.setText("");
    }//GEN-LAST:event_eliminarbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarUser;
    private javax.swing.JButton eliminarbtn;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificarbtn;
    private javax.swing.JTextField userIdTF;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
