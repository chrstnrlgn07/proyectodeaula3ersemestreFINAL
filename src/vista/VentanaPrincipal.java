/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ArchivoProductos;
import controlador.GestorInventario;
import modelo.Producto;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.time.LocalDate;
/**
 *
 * @author User
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    
        private final GestorInventario gestor = new GestorInventario();
        private final ArchivoProductos archivo = new ArchivoProductos();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());
    private DefaultTableModel modeloTabla;

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Cantidad", "Fecha de Vencimiento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : gestor.listarProductos()) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                p.getPrecio(), p.getCantidad(), p.getFechaVencimiento()
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtFechaVencimiento.setText("");
        txtNombre.requestFocus();
    }

    private void cargarFilaEnCampos() {
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtCantidad.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtFechaVencimiento.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private boolean guardarEnArchivo() {
        try {
            archivo.guardarProductos(gestor.listarProductos());
            return true;
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo.");
            return false;
        }
    }
    
    private void mostrarAlertaStockBajo() {
        List<Producto> productos = gestor.obtenerProductosStockBajo();

        if (productos.isEmpty()) {
            return;
        }

        String mensaje = "Los siguientes productos tienen stock bajo:\n\n";

        for (Producto producto : productos) {
            mensaje += producto.getNombre()
                    + " - Cantidad disponible: "
                    + producto.getCantidad() + "\n";
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Alerta de stock bajo",
                JOptionPane.WARNING_MESSAGE
        );
    }
    
    
    private void mostrarAlertaVencimiento() {
        List<Producto> productos = gestor.obtenerProductosProximosAVencer();

        if (productos.isEmpty()) {
            return;
        }

        String mensaje = "Productos próximos a vencer o vencidos:\n\n";

        LocalDate hoy = LocalDate.now();

        for (Producto producto : productos) {
            LocalDate fecha = LocalDate.parse(producto.getFechaVencimiento());

            long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(
                    hoy, fecha
            );

            mensaje += producto.getNombre() + " - ";

            if (diasRestantes < 0) {
                mensaje += "VENCIDO hace " + Math.abs(diasRestantes) + " día/s";
            } else if (diasRestantes == 0) {
                mensaje += "VENCE HOY";
            } else {
                mensaje += "Vence en " + diasRestantes + " día/s";
            }

            mensaje += "\n";
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Alerta de vencimiento",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Creates new form VentanaPrincipal
     */
    
    public VentanaPrincipal() {
        initComponents();
        configurarTabla();

        try {
            gestor.cargarProductos(archivo.cargarProductos());

        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar los productos del archivo."
            );
        }

        actualizarTabla();
        mostrarAlertaStockBajo();
        mostrarAlertaVencimiento();
        setLocationRelativeTo(null);

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarFilaEnCampos();
            }
        });
    }
    
    
    private boolean fechaValida(String fecha) {
        try {
            LocalDate.parse(fecha);
            return true;

        } catch (java.time.format.DateTimeParseException e) {
            return false;
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtFechaVencimiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión de Inventario");

        jLabel1.setFont(new java.awt.Font("SugarFont Bold", 0, 18)); // NOI18N
        jLabel1.setText("SISTEMA DE GESTIÓN DE INVENTARIO");

        jLabel2.setText("Nombre del producto:");

        txtNombre.addActionListener(this::txtNombreActionPerformed);

        jLabel3.setText("Categoria:");

        jLabel4.setText("Precio:");

        jLabel5.setText("Cantidad:");

        jLabel6.setText("Fecha de Vencimiento:");

        btnRegistrar.setText("Registrar producto");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);

        btnMostrar.setText("Mostrar Productos");
        btnMostrar.setToolTipText("");
        btnMostrar.addActionListener(this::btnMostrarActionPerformed);

        btnEliminar.setText("Eliminar Producto");
        btnEliminar.setToolTipText("");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnActualizar.setText("Actualizar Producto");
        btnActualizar.setToolTipText("");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Categoría", "Precio", "Cantidad", "Fecha de Vencimiento"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtFechaVencimiento, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCategoria, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCantidad)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(btnRegistrar)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();
        String fechaVencimiento = txtFechaVencimiento.getText().trim();
        
        if (nombre.isBlank() || categoria.isBlank()
        || precioTexto.isBlank() || cantidadTexto.isBlank()
        || fechaVencimiento.isBlank()) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Por favor, completa todos los campos."
            );

            return;
        }
        
        if (!fechaValida(fechaVencimiento)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fecha inválida. Utiliza el formato AAAA-MM-DD.",
                    "Error de fecha",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        double precio;
        int cantidad;

        try {
            precio = Double.parseDouble(precioTexto);
            cantidad = Integer.parseInt(cantidadTexto);

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "El precio y la cantidad deben ser números válidos."
            );
            return;
        }
        
        if (!Double.isFinite(precio) || precio < 0 || cantidad < 0) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "El precio debe ser un número válido no negativo y la cantidad no puede ser negativa."
            );
            return;
        }
        
        int id = gestor.obtenerSiguienteId();

        Producto producto = new Producto(
            id,
            nombre,
            categoria,
            precio,
            cantidad,
            fechaVencimiento
        );
        
        
        if (gestor.registrarProducto(producto)) {

            if (!guardarEnArchivo()) {
                // Si falla el guardado, deshacemos el registro.
                gestor.eliminarProducto(id);
                actualizarTabla();
                return;
            }

            limpiarCampos();
            actualizarTabla();

            JOptionPane.showMessageDialog(
                    this,
                    "¡Producto registrado correctamente!"
            );

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el producto."
            );
        }

    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        actualizarTabla();
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = jTable1.getSelectedRow();

    if (fila < 0) {
        JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
        return;
    }

    int confirmar = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que deseas eliminar este producto?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
    );

    if (confirmar != JOptionPane.YES_OPTION) {
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

    // Conservamos el producto por si ocurre un error al guardar.
    Producto productoEliminado = gestor.buscarProducto(id);

    if (gestor.eliminarProducto(id)) {

        if (!guardarEnArchivo()) {
            // Si falla el guardado, recuperamos el producto en memoria.
            gestor.registrarProducto(productoEliminado);
            actualizarTabla();
            return;
        }

        limpiarCampos();
        actualizarTabla();

        JOptionPane.showMessageDialog(
                this,
                "Producto eliminado correctamente."
        );

    } else {
        JOptionPane.showMessageDialog(
                this,
                "No se pudo eliminar el producto."
        );
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    
    
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
         int fila = jTable1.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();
        String fechaVencimiento = txtFechaVencimiento.getText().trim();

        if (nombre.isBlank() || categoria.isBlank() || precioTexto.isBlank()
                || cantidadTexto.isBlank() || fechaVencimiento.isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }
        
        if (!fechaValida(fechaVencimiento)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fecha inválida. Utiliza el formato AAAA-MM-DD.",
                    "Error de fecha",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            double precio = Double.parseDouble(precioTexto);
            int cantidad = Integer.parseInt(cantidadTexto);

            // Buscamos el producto y conservamos sus datos anteriores.
            Producto productoAnterior = gestor.buscarProducto(id);

            if (productoAnterior == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el producto.");
                return;
            }

            String nombreAnterior = productoAnterior.getNombre();
            String categoriaAnterior = productoAnterior.getCategoria();
            double precioAnterior = productoAnterior.getPrecio();
            int cantidadAnterior = productoAnterior.getCantidad();
            String fechaAnterior = productoAnterior.getFechaVencimiento();

            if (gestor.actualizarProducto(id, nombre, categoria, precio,
                    cantidad, fechaVencimiento)) {

                if (!guardarEnArchivo()) {
                    // Si falla el guardado, restauramos los datos anteriores.
                    gestor.actualizarProducto(id, nombreAnterior, categoriaAnterior,
                            precioAnterior, cantidadAnterior, fechaAnterior);

                    actualizarTabla();
                    return;
                }

                limpiarCampos();
                actualizarTabla();

                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");

            } else {
                JOptionPane.showMessageDialog(this, "Datos inválidos: revisa los campos.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El precio y la cantidad deben ser números válidos.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtFechaVencimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
