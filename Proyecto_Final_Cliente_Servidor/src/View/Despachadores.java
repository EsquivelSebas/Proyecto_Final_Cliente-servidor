/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Despachadores extends javax.swing.JInternalFrame {
    public Despachadores() {
        initComponents();
        this.setResizable(false);
        this.setSize(656, 333);
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
        insertar1 = new javax.swing.JButton();
        insertar2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nombre"
            }
        ));
        jScrollPane1.setViewportView(userTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 122, 648, 181));

        insertar.setText("Modify dispatchers");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });
        getContentPane().add(insertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        insertar1.setText("Check dispatchers ");
        insertar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertar1ActionPerformed(evt);
            }
        });
        getContentPane().add(insertar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 79, -1, -1));

        insertar2.setText("Modify dispatchers");
        insertar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertar2ActionPerformed(evt);
            }
        });
        getContentPane().add(insertar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed

    }//GEN-LAST:event_insertarActionPerformed

    private void insertar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_insertar1ActionPerformed

    private void insertar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_insertar2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton insertar;
    private javax.swing.JButton insertar1;
    private javax.swing.JButton insertar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
