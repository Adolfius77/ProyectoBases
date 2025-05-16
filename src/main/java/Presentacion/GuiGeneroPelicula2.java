/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import DTOS.generoDTO;
import DTOS.peliculaDTO;
import Persistencia.GeneroDAO;
import Persistencia.PeliculaGeneroDAO;
import Persistencia.añadirPeliculaDAO;
import java.awt.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author USER
 */
public class GuiGeneroPelicula2 extends javax.swing.JFrame {

    private añadirPeliculaDAO peliculaDAO;
    private GeneroDAO generoDAO;
    private PeliculaGeneroDAO peliculaGeneroDAO;

    private DefaultTableModel modeloTablaGeneros; // Modelo para jTable1 (géneros con checkboxes)
    private peliculaDTO peliculaSeleccionadaActual;

    /**
     * Creates new form GuiGeneroPelicula2
     */
    public GuiGeneroPelicula2() {
        initComponents(); // Llama al initComponents que TÚ debes adaptar en NetBeans
        setLocationRelativeTo(null);
        setTitle("Asignar Géneros a Película");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializar DAOs
        peliculaDAO = new añadirPeliculaDAO(); // O new añadirPeliculaDAO();
        generoDAO = new GeneroDAO();
        peliculaGeneroDAO = new PeliculaGeneroDAO();

        configurarTablaGeneros();
        
        txtTitulo.setEditable(false);
        txtTitulo.setBackground(new Color(230, 230, 230));

        // Conectar botones a acciones (asegúrate que los nombres en initComponents coincidan)
        if (btnBuscar != null) {
            btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        }
       
        if (btnGuardar != null) {
            btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        }
    }

    private void configurarTablaGeneros() {
        String[] columnas = {"Asignar", "ID Género", "Nombre del Género"};
        modeloTablaGeneros = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        // jTable1 es tu tabla para mostrar géneros según tu initComponents
        if (jTable1 != null) {
            jTable1.setModel(modeloTablaGeneros);
            TableColumn idColG = jTable1.getColumnModel().getColumn(1);
            idColG.setMinWidth(0); idColG.setMaxWidth(0); idColG.setPreferredWidth(0);
            TableColumn checkCol = jTable1.getColumnModel().getColumn(0);
            checkCol.setPreferredWidth(60);
            TableColumn nombreCol = jTable1.getColumnModel().getColumn(2);
            nombreCol.setPreferredWidth(350);
        }
    }

    private void btnBuscarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {
      String criterioBusqueda = txtPelicula.getText().trim();
        if (criterioBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o parte del título de la película.", "Criterio Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloTablaGeneros != null) modeloTablaGeneros.setRowCount(0);
        if (txtTitulo != null) txtTitulo.setText("");
        peliculaSeleccionadaActual = null;

        try {
            int idPelicula = Integer.parseInt(criterioBusqueda);
            peliculaSeleccionadaActual = peliculaDAO.obtenerPeliculaPorID(idPelicula);
        } catch (NumberFormatException e) {
            List<peliculaDTO> peliculasEncontradas = peliculaDAO.buscarPeliculasPorTitulo(criterioBusqueda);
            if (peliculasEncontradas != null && !peliculasEncontradas.isEmpty()) {
                if (peliculasEncontradas.size() == 1) {
                    peliculaSeleccionadaActual = peliculasEncontradas.get(0);
                } else {
                    // Manejar múltiples resultados: mostrar un diálogo para seleccionar
                    Object[] opcionesPeliculas = peliculasEncontradas.toArray();
                    peliculaDTO elegida = (peliculaDTO) JOptionPane.showInputDialog(this, 
                        "Se encontraron varias películas, seleccione una:", "Múltiples Películas Encontradas",
                        JOptionPane.PLAIN_MESSAGE, null, opcionesPeliculas, opcionesPeliculas[0]);
                    peliculaSeleccionadaActual = elegida;
                }
            }
        }

        if (peliculaSeleccionadaActual != null) {
            txtPelicula.setText(String.valueOf(peliculaSeleccionadaActual.getIdPelicula())); 
            txtTitulo.setText(peliculaSeleccionadaActual.getTitulo());
            cargarGenerosParaAsignacionActual();
        } else {
            JOptionPane.showMessageDialog(this, "Película no encontrada.", "No Encontrada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarGenerosParaAsignacionActual() {
        if (modeloTablaGeneros == null || peliculaSeleccionadaActual == null) return;
        modeloTablaGeneros.setRowCount(0);

        List<generoDTO> todosLosGeneros = generoDAO.obtenerTodosLosGeneros();
        List<generoDTO> generosAsignadosAPelicula = peliculaGeneroDAO.obtenerGenerosDePelicula(peliculaSeleccionadaActual.getIdPelicula());

        Set<Integer> idsGenerosAsignados = new HashSet<>();
        if (generosAsignadosAPelicula != null) {
            idsGenerosAsignados.addAll(generosAsignadosAPelicula.stream().map(generoDTO::getIdGenero).collect(Collectors.toList()));
        }

        if (todosLosGeneros != null) {
            for (generoDTO genero : todosLosGeneros) {
                boolean asignado = idsGenerosAsignados.contains(genero.getIdGenero());
                modeloTablaGeneros.addRow(new Object[]{asignado, genero.getIdGenero(), genero.getNombre()});
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

        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaGeneros = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListaGenerosAsigandos = new javax.swing.JList<>();
        btnMoverAsignados = new javax.swing.JButton();
        btnMoverAdisponibles = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtPelicula = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tabla = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tablapeli = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        listaGeneros.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaGeneros);

        ListaGenerosAsigandos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(ListaGenerosAsigandos);

        btnMoverAsignados.setText("<<");
        btnMoverAsignados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoverAsignadosActionPerformed(evt);
            }
        });

        btnMoverAdisponibles.setText(">>");
        btnMoverAdisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoverAdisponiblesActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jLabel5.setText("Generos Asigandos");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("jLabel1");

        jLabel2.setText("ingrese el id de la pelicula ala que quiere agregarle un genero");

        jLabel3.setText("Titulo:");

        btnBuscar.setText("buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPelicula, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(txtTitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addGap(48, 48, 48))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel4.setText("Generos para la pelicula");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "nombre del genero"
            }
        ));
        tabla.setViewportView(jTable1);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id_pelicula", "titulo", "anio", "pais", "id_productora"
            }
        ));
        tablapeli.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablapeli, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tablapeli, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        btnGuardar.setText("GuardarCambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(50, 50, 50))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnGuardar)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoverAsignadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoverAsignadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoverAsignadosActionPerformed

    private void btnMoverAdisponiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoverAdisponiblesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoverAdisponiblesActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       if (peliculaSeleccionadaActual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, busque y seleccione una película primero.", "Película no Seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exitoRemover = peliculaGeneroDAO.removerTodosGenerosDePelicula(peliculaSeleccionadaActual.getIdPelicula());
        if (!exitoRemover) {
            JOptionPane.showMessageDialog(this, "Error al limpiar géneros previos de la película.", "Error Guardando", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        boolean todosAsignadosCorrectamente = true;
        int generosAsignadosCount = 0;
        for (int i = 0; i < modeloTablaGeneros.getRowCount(); i++) {
            Boolean asignado = (Boolean) modeloTablaGeneros.getValueAt(i, 0);
            if (asignado != null && asignado) {
                int idGenero = (int) modeloTablaGeneros.getValueAt(i, 1);
                if (!peliculaGeneroDAO.asignarGeneroAPelicula(peliculaSeleccionadaActual.getIdPelicula(), idGenero)) {
                    todosAsignadosCorrectamente = false;
                    System.err.println("Fallo al asignar género ID: " + idGenero + " a película ID: " + peliculaSeleccionadaActual.getIdPelicula());
                } else {
                    generosAsignadosCount++;
                }
            }
        }
        
        if (todosAsignadosCorrectamente) {
            JOptionPane.showMessageDialog(this, generosAsignadosCount + " género(s) asignados/actualizados para: " + peliculaSeleccionadaActual.getTitulo(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Algunos géneros no pudieron ser asignados. Revise la consola.", "Error Parcial", JOptionPane.WARNING_MESSAGE);
        }
        cargarGenerosParaAsignacionActual(); 
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterioBusqueda = txtPelicula.getText().trim();
        if (criterioBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o parte del título de la película.", "Criterio Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTablaGeneros.setRowCount(0); // Limpiar tabla de géneros antes de nueva búsqueda
        txtTitulo.setText("");
        peliculaSeleccionadaActual = null;

        try {
            // Intentar buscar por ID primero
            int idPelicula = Integer.parseInt(criterioBusqueda);
            peliculaSeleccionadaActual = peliculaDAO.obtenerPeliculaPorID(idPelicula);
        } catch (NumberFormatException e) {
            // Si no es un número, buscar por título (asume que tu DAO tiene este método)
            // Este método debería devolver List<peliculaDTO>
            List<peliculaDTO> peliculasEncontradas = peliculaDAO.buscarPeliculasPorTitulo(criterioBusqueda);
            if (peliculasEncontradas != null && !peliculasEncontradas.isEmpty()) {
                if (peliculasEncontradas.size() == 1) {
                    peliculaSeleccionadaActual = peliculasEncontradas.get(0);
                } else {
                    // Si hay múltiples, pedir al usuario que seleccione una
                    // Esto se puede hacer con un JOptionPane.showInputDialog con un JComboBox o JList
                    // Por ahora, tomaremos la primera para simplificar.
                    peliculaSeleccionadaActual = peliculasEncontradas.get(0);
                    JOptionPane.showMessageDialog(this, "Se encontraron varias películas, se cargó la primera: " + peliculaSeleccionadaActual.getTitulo(), "Múltiples Resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (peliculaSeleccionadaActual != null) {
            txtPelicula.setText(String.valueOf(peliculaSeleccionadaActual.getIdPelicula())); // Mostrar ID encontrado
            txtTitulo.setText(peliculaSeleccionadaActual.getTitulo());
            cargarGenerosParaAsignacionActual();
        } else {
            JOptionPane.showMessageDialog(this, "Película no encontrada.", "No Encontrada", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(GuiGeneroPelicula2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiGeneroPelicula2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiGeneroPelicula2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiGeneroPelicula2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiGeneroPelicula2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListaGenerosAsigandos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMoverAdisponibles;
    private javax.swing.JButton btnMoverAsignados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JList<String> listaGeneros;
    private javax.swing.JScrollPane tabla;
    private javax.swing.JScrollPane tablapeli;
    private javax.swing.JTextField txtPelicula;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
