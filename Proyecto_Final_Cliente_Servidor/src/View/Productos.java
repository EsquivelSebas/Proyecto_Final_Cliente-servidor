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

public class Productos extends javax.swing.JInternalFrame {

    Connector conn = new Connector();
    Connection connection = conn.getConexion();
    DefaultTableModel model;
    PreparedStatement pst;
    ResultSet rs;
    int idc = 0;
    int productId;

    public Productos() {
        initComponents();
        this.setSize(626, 300);
        this.setResizable(false);
        productId = 0;
    }

    void retrieve() {
        clean_table();
        String query = "select * from producto";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery(query);
            Object[] producto = new Object[4];
            model = (DefaultTableModel) results.getModel();
            while (rs.next()) {
                producto[0] = rs.getInt("id");
                producto[1] = rs.getString("nombre");
                producto[2] = rs.getInt("precio");
                producto[3] = rs.getString("cantidad");

                model.addRow(producto);
            }
            results.setModel(model);
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

    void add() {
        String na_value = nameproductos.getText();
        String pr_value = price.getText();
        String st_value = stock.getText();

        try {
            if (na_value.equals("") || pr_value.equals("") || st_value.equals("")) {
                JOptionPane.showMessageDialog(null, "Hay campos sin datos");
                clean_table();
            } else {
                String query = "insert into producto(nombre, precio, cantidad)"
                        + // aqui igual se fija si los nombres son iguales a la base de datos
                        "values ('" + na_value + "', '" + pr_value + "', '" + st_value + "')";

                connection = conn.getConexion();
                pst = connection.prepareStatement(query);
                pst.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
                clean_table();
            }
        } catch (Exception e) {
            System.out.println("Error while adding data: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al agregar Producto");
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

    void clean_table() {
        for (int i = 0; i < results.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

 void modify() {
    String na_value = nameproductos.getText();
    String pr_value = price.getText();
    String st_value = stock.getText();
    String id = idproductoTXT.getText();

    try {
        // Verifica si el ID es un número válido
        if (!id.isEmpty() && id.matches("\\d+")) {
            int productId = Integer.parseInt(id);

            // Verifica si los campos obligatorios no están vacíos
            if (!na_value.equals("") && !pr_value.equals("") && !st_value.equals("")) {
                String query = "UPDATE producto SET nombre=?, precio=?, cantidad=? WHERE id=?";

                connection = conn.getConexion();
                pst = connection.prepareStatement(query);
                pst.setString(1, na_value);
                pst.setString(2, pr_value);
                pst.setString(3, st_value);
                pst.setInt(4, productId);

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
                    clean_table();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar el producto. Verifica el ID.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hay campos sin datos");
                clean_table();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido de un producto existente.");
        }
    } catch (SQLException e) {
        System.out.println("Error while modifying product: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error al modificar producto");
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


    void delete() {
    String productIdText = idproductoTXT.getText();

    try {
        // Verifica si el ID es un número válido
        if (!productIdText.isEmpty() && productIdText.matches("\\d+")) {
            int productId = Integer.parseInt(productIdText);

            String query = "DELETE FROM producto WHERE id = ?";

            connection = conn.getConexion();
            pst = connection.prepareStatement(query);

            // Set the value for the parameter
            pst.setInt(1, productId);

            // Use executeUpdate without passing the query again
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
            clean_table();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de producto válido.");
        }
    } catch (SQLException e) {
        System.out.println("Error while deleting product: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error al eliminar producto");
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

    void clean_text() {
        idproductoTXT.setText("");
        nameproductos.setText("");
        price.setText("");
        stock.setText("");

        nameproductos.requestFocus();
    }
    // </editor-fold>

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void resultsMouseClicked(java.awt.event.MouseEvent evt) {

        int row = results.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null, "No hay una fila seleccionada");
        } else {
            idc = Integer.parseInt((String) results.getValueAt(row, 0).toString());
            String na_value = (String) results.getValueAt(row, 1);
            String pr_value = (String) results.getValueAt(row, 2);;
            String st_value = (String) results.getValueAt(row, 3);

            id.setText("" + idc);
            nameproductos.setText(na_value);
            price.setText(pr_value);
            stock.setText(st_value);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        results = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        add = new javax.swing.JButton();
        clean = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameproductos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        stock = new javax.swing.JTextField();
        idproductoTXT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel8.setText("Id:");

        id.setEditable(false);
        id.setBackground(new java.awt.Color(204, 204, 204));
        id.setEnabled(false);

        jLabel2.setText("Nombre:");

        setBackground(new java.awt.Color(46, 30, 32));
        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        results.setBackground(new java.awt.Color(222, 222, 222));
        results.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        results.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "price", "Stock"
            }
        ));
        jScrollPane1.setViewportView(results);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 620, 120));

        jButton1.setBackground(new java.awt.Color(1, 32, 99));
        jButton1.setForeground(new java.awt.Color(204, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-magnifying-glass-16.png"))); // NOI18N
        jButton1.setText("Check products");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 140, -1));

        delete.setBackground(new java.awt.Color(1, 32, 99));
        delete.setForeground(new java.awt.Color(204, 255, 255));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-x-16.png"))); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        getContentPane().add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 106, -1));

        edit.setBackground(new java.awt.Color(1, 32, 99));
        edit.setForeground(new java.awt.Color(204, 255, 255));
        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-pen-16.png"))); // NOI18N
        edit.setText("Modify");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        getContentPane().add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 118, -1));

        add.setBackground(new java.awt.Color(1, 32, 99));
        add.setForeground(new java.awt.Color(204, 255, 255));
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-check-16.png"))); // NOI18N
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        getContentPane().add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 76, -1));

        clean.setBackground(new java.awt.Color(1, 32, 99));
        clean.setForeground(new java.awt.Color(204, 255, 255));
        clean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-broom-16.png"))); // NOI18N
        clean.setText("Clean");
        clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanActionPerformed(evt);
            }
        });
        getContentPane().add(clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 117, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(1, 32, 99));
        jLabel9.setText("Id:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 40, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 32, 99));
        jLabel3.setText("Name:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 27));

        nameproductos.setBackground(new java.awt.Color(1, 32, 99));
        nameproductos.setForeground(new java.awt.Color(222, 222, 22));
        nameproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameproductosActionPerformed(evt);
            }
        });
        getContentPane().add(nameproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 63, 213, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 32, 99));
        jLabel4.setText("Price:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 8, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 32, 99));
        jLabel5.setText("Stock");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, -1, -1));

        price.setBackground(new java.awt.Color(1, 32, 99));
        price.setForeground(new java.awt.Color(222, 222, 22));
        price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceActionPerformed(evt);
            }
        });
        getContentPane().add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 6, 211, -1));

        stock.setBackground(new java.awt.Color(1, 32, 99));
        stock.setForeground(new java.awt.Color(222, 222, 22));
        getContentPane().add(stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 63, 211, -1));

        idproductoTXT.setBackground(new java.awt.Color(1, 32, 99));
        idproductoTXT.setForeground(new java.awt.Color(222, 222, 22));
        idproductoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idproductoTXTActionPerformed(evt);
            }
        });
        getContentPane().add(idproductoTXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 5, 213, 20));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 0, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/6204a337101f29a2b14dffd576fc32b3.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        retrieve();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        modify();
    }//GEN-LAST:event_editActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        add();
        clean_text();
    }//GEN-LAST:event_addActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed

    String productIdText = idproductoTXT.getText();
    try {     
        productId = Integer.parseInt(productIdText);      
        delete();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de producto válido.");
   }
    
    idproductoTXT.setText("");
    }//GEN-LAST:event_deleteActionPerformed

    private void priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceActionPerformed

    private void cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanActionPerformed
        clean_text();
    }//GEN-LAST:event_cleanActionPerformed

    private void nameproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameproductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameproductosActionPerformed

    private void idproductoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idproductoTXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idproductoTXTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton clean;
    private javax.swing.JButton delete;
    private javax.swing.JButton edit;
    private javax.swing.JTextField id;
    private javax.swing.JTextField idproductoTXT;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameproductos;
    private javax.swing.JTextField price;
    private javax.swing.JTable results;
    private javax.swing.JTextField stock;
    // End of variables declaration//GEN-END:variables
}
