/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Model.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Despachadores extends javax.swing.JInternalFrame {

    Connector conn = new Connector();
    Connection connection = conn.getConexion();
    PreparedStatement pst;
    DefaultTableModel model;
    Statement st;
    ResultSet rs;

    public Despachadores() {
        initComponents();
        this.setResizable(false);
        this.setSize(656, 333);
    }

    private void clean_Table() {
        for (int i = 0; i < DispatcherTable.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    private void updateDispatcher(int dispatcherId, String newName) {
        String query = "UPDATE despachadores SET nombre = ? WHERE ID = ?";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            pst.setString(1, newName);
            pst.setInt(2, dispatcherId);

            // Execute the update query
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Nombre del despachador actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el nombre del despachador.");
            }

        } catch (SQLException e) {
            System.out.println("Error while updating dispatcher name: " + e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    public boolean checkID(String ID, String nombre) {
        Connector conn = new Connector();
        Connection connection = conn.getConexion();
        String query = "SELECT * FROM despachadores WHERE ID = ? OR nombre = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, nombre);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    void addDispatcher() {
        String Id_value = Id_txt12.getText();
        String Name_value = name_Txt.getText();

        try {
            if (Id_value.equals("") || checkID(Id_value, Name_value)) {
                JOptionPane.showMessageDialog(null, "ID vacío o ya existe el despachador.");
                clean_Table();
            } else {
                if (Name_value.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nombre vacío. Revisa nuevamente.");
                    clean_Table();
                } else {
                    String query = "insert into despachadores(ID, nombre)"
                            + "values ('" + Id_value + "', '" + Name_value + "')";

                    connection = conn.getConexion();
                    pst = connection.prepareStatement(query);
                    pst.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Despachador agregado exitosamente");
                    clean_Table();
                }
            }
        } catch (Exception e) {
            System.out.println("Error while adding data: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al agregar despachador");
            clean_Table();
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
    
 void delete() {
    try {
        String idText = Id_txt12.getText();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID para eliminar.");
            return;
        }

        // Verificar si Id_txt1 es un número válido
        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
            return;
        }

        int DispatcherId = Integer.parseInt(idText);

        if (DispatcherId < 0) {
            JOptionPane.showMessageDialog(null, "No hay una fila seleccionada");
        } else {
            String query = "DELETE FROM despachadores WHERE id = ?";

            connection = conn.getConexion();
            pst = connection.prepareStatement(query);

            // Establecer el valor para el parámetro
            pst.setInt(1, DispatcherId);

            // Utilizar executeUpdate sin pasar la consulta nuevamente
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Despachador eliminado exitosamente");
            clean_Table();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
    } catch (Exception e) {
        clean_Table();
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
 
    private void checksdispatchers() {
        clean_Table();
        String query = "select * from despachadores";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery(query);
            Object[] Dispatcher = new Object[2];
            model = (DefaultTableModel) DispatcherTable.getModel();
            while (rs.next()) {
                Dispatcher[0] = rs.getInt("id");
                Dispatcher[1] = rs.getString("nombre");
                model.addRow(Dispatcher);
            }
            DispatcherTable.setModel(model);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        DispatcherTable = new javax.swing.JTable();
        Namelabel = new javax.swing.JLabel();
        insertar = new javax.swing.JButton();
        Checkdispatcher = new javax.swing.JButton();
        updateDispatcher = new javax.swing.JButton();
        name_Txt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Id_txt12 = new javax.swing.JTextField();
        DelDispatcher = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DispatcherTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nombre"
            }
        ));
        jScrollPane1.setViewportView(DispatcherTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 172, 648, 100));

        Namelabel.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        Namelabel.setText("Name:");
        getContentPane().add(Namelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 40, -1));

        insertar.setBackground(new java.awt.Color(247, 233, 207));
        insertar.setForeground(new java.awt.Color(0, 128, 141));
        insertar.setText("Add dispatchers");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });
        getContentPane().add(insertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, -1));

        Checkdispatcher.setBackground(new java.awt.Color(247, 233, 207));
        Checkdispatcher.setForeground(new java.awt.Color(0, 128, 141));
        Checkdispatcher.setText("Check dispatchers ");
        Checkdispatcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckdispatcherActionPerformed(evt);
            }
        });
        getContentPane().add(Checkdispatcher, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        updateDispatcher.setBackground(new java.awt.Color(247, 233, 207));
        updateDispatcher.setForeground(new java.awt.Color(0, 128, 141));
        updateDispatcher.setText("Modify dispatchers");
        updateDispatcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDispatcherActionPerformed(evt);
            }
        });
        getContentPane().add(updateDispatcher, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));

        name_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_TxtActionPerformed(evt);
            }
        });
        getContentPane().add(name_Txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 80, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel3.setText("Modifying only!");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 90, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel4.setText("ID:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 30, -1));

        Id_txt12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Id_txt12ActionPerformed(evt);
            }
        });
        getContentPane().add(Id_txt12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 80, -1));

        DelDispatcher.setBackground(new java.awt.Color(247, 233, 207));
        DelDispatcher.setForeground(new java.awt.Color(0, 128, 141));
        DelDispatcher.setText("Delete Dispatcher");
        DelDispatcher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelDispatcherActionPerformed(evt);
            }
        });
        getContentPane().add(DelDispatcher, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/d93ef037ee6a0239287a57ea763d07c1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed

        addDispatcher();
        checksdispatchers();
    }//GEN-LAST:event_insertarActionPerformed

    private void CheckdispatcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckdispatcherActionPerformed
        checksdispatchers();
    }//GEN-LAST:event_CheckdispatcherActionPerformed

    private void updateDispatcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDispatcherActionPerformed

        String idString = Id_txt12.getText();
        String newName = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre:");
        try {

            int dispatcherId = Integer.parseInt(idString);
            if (dispatcherId > 0) {
                updateDispatcher(dispatcherId, newName);
                checksdispatchers();
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de despachador válido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
        }
    }//GEN-LAST:event_updateDispatcherActionPerformed

    private void name_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_TxtActionPerformed

    private void Id_txt12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Id_txt12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Id_txt12ActionPerformed

    private void DelDispatcherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelDispatcherActionPerformed
       delete();
       checksdispatchers();
    }//GEN-LAST:event_DelDispatcherActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Checkdispatcher;
    private javax.swing.JButton DelDispatcher;
    private javax.swing.JTable DispatcherTable;
    private javax.swing.JTextField Id_txt12;
    private javax.swing.JLabel Namelabel;
    private javax.swing.JButton insertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_Txt;
    private javax.swing.JButton updateDispatcher;
    // End of variables declaration//GEN-END:variables
}
