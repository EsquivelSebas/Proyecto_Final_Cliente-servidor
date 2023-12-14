/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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

public class Ventas extends javax.swing.JFrame {

    Connector conn = new Connector();
    Connection connection = conn.getConexion();
    DefaultTableModel model;
    DefaultTableModel model1;
    PreparedStatement pst;
    ResultSet rs;

    public Ventas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(883, 359);
        this.setResizable(false);

    }

    void realizarCompra(int idProducto, int cantidadCompra) {
        Connection connection = null;
        try {
            String precioQuery = "SELECT precio FROM producto WHERE id = ?";
            int precioUnitario = 0;

            connection = conn.getConexion();

            try (PreparedStatement precioPst = connection.prepareStatement(precioQuery)) {
                precioPst.setInt(1, idProducto);

                try (ResultSet precioRs = precioPst.executeQuery()) {
                    if (precioRs.next()) {
                        precioUnitario = precioRs.getInt("precio");
                    }
                }
            }

            int precioTotal = precioUnitario * cantidadCompra;

            // Actualiza el stock del producto
            String updateQuery = "UPDATE producto SET cantidad = cantidad - ? WHERE id = ?";
            try (PreparedStatement updatePst = connection.prepareStatement(updateQuery)) {
                updatePst.setInt(1, cantidadCompra);
                updatePst.setInt(2, idProducto);
                updatePst.executeUpdate();
            }

            // Registra la compra en la tabla de ventas
            String insertQuery = "INSERT INTO venta (idProducto, CantProducto, Precio, fecha) VALUES (?, ?, ?, CURRENT_DATE)";
            try (PreparedStatement insertPst = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPst.setInt(1, idProducto);
                insertPst.setInt(2, cantidadCompra);
                insertPst.setInt(3, precioTotal);
                insertPst.executeUpdate();

                // Recuperar el ID generado para la venta
                ResultSet generatedKeys = insertPst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idVenta = generatedKeys.getInt(1);
                    System.out.println("ID de venta generado: " + idVenta);
                }
            }

            JOptionPane.showMessageDialog(null, "Compra realizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar la compra.");
        } finally {
            // Cerrar la conexión en el bloque finally para asegurarse de que siempre se cierre
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    void mostrarVentas() {
        clean_table();

        String query = "SELECT v.idVenta, p.nombre AS productName, v.cantidad, v.precio, v.fecha "
                     + "FROM venta v "
                     + "JOIN producto p ON v.idProducto = p.id";

        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model1 = (DefaultTableModel) Ventastable.getModel();

            while (rs.next()) {
                Object[] venta = new Object[5];
                venta[0] = rs.getInt("idVenta");
                venta[1] = rs.getInt("idProducto");
                venta[2] = rs.getInt("CantProducto");
                venta[3] = rs.getInt("Precio");
                venta[4] = rs.getDate("fecha");

                model1.addRow(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar las ventas.");
        }
    }
     */

    void retrieve() {
        clean_table();
        String query = "select * from producto";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery(query);
            Object[] producto = new Object[4];
            model = (DefaultTableModel) Checkproductsventas.getModel();
            while (rs.next()) {
                producto[0] = rs.getInt("id");
                producto[1] = rs.getString("nombre");
                producto[2] = rs.getInt("precio");
                producto[3] = rs.getString("cantidad");

                model.addRow(producto);
            }
            Checkproductsventas.setModel(model);
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

    void retrieveventas() {
        clean_table();
        String query = "select * from venta";

        try {
            connection = conn.getConexion();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery(query);
            Object[] venta = new Object[5];
            model = (DefaultTableModel) Ventastable.getModel();
            while (rs.next()) {
                venta[0] = rs.getInt("idVenta");
                venta[1] = rs.getInt("idProducto");
                venta[2] = rs.getInt("CantProducto");
                venta[3] = rs.getInt("precio");
                venta[4] = rs.getDate("fecha");
                model.addRow(venta);
            }
            Ventastable.setModel(model);
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
        if (model != null) {
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llenando la tabla....");
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Ventastable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Checkproductsventas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Product_TXT = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Stock_TXT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Comprar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, -1, 686));

        Ventastable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idVenta", "idProduct", "Quantity", "Precio", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(Ventastable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 430, 190));

        Checkproductsventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Stock"
            }
        ));
        jScrollPane2.setViewportView(Checkproductsventas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 430, 190));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-back-32.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 32));

        jLabel2.setForeground(new java.awt.Color(51, 51, 55));
        jLabel2.setText("Back");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jButton2.setBackground(new java.awt.Color(222, 222, 222));
        jButton2.setForeground(new java.awt.Color(51, 51, 55));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-magnifying-glass-16.png"))); // NOI18N
        jButton2.setText("Check products");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        jLabel3.setBackground(new java.awt.Color(18, 30, 49));
        jLabel3.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(18, 30, 49));
        jLabel3.setText("Product ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(18, 30, 49));
        jLabel4.setText("Check sale ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, -1, -1));
        getContentPane().add(Product_TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 50, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(18, 30, 49));
        jLabel5.setText("Buy");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));
        getContentPane().add(Stock_TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 50, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(18, 30, 49));
        jLabel6.setText("Quantity");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        Comprar.setBackground(new java.awt.Color(255, 255, 255));
        Comprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-shopping-cart-30.png"))); // NOI18N
        Comprar.setBorder(null);
        Comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComprarActionPerformed(evt);
            }
        });
        getContentPane().add(Comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, 30));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-magnifying-glass-16.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 70, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/53405770-seamless-shopping-cart-pattern-background-texture.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        PostLogin1 postgui = new PostLogin1();
        postgui.setVisible(true);
        DefaultTableModel model = new DefaultTableModel();
        Checkproductsventas.setModel(model);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        retrieve();
        //clean_table();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComprarActionPerformed
        try {
            int idProducto = Integer.parseInt(Product_TXT.getText());
            int cantidadCompra = Integer.parseInt(Stock_TXT.getText());
            realizarCompra(idProducto, cantidadCompra);
            retrieveventas();
            clean_table();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos para ID y cantidad.");
        }
    }//GEN-LAST:event_ComprarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        retrieveventas();
    
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Checkproductsventas;
    private javax.swing.JButton Comprar;
    private javax.swing.JTextField Product_TXT;
    private javax.swing.JTextField Stock_TXT;
    private javax.swing.JTable Ventastable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
