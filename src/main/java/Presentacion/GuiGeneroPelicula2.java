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

    private DefaultTableModel modeloTablaGeneros; 
    private DefaultTableModel modeloTablaPeliculaSeleccionada;
    private peliculaDTO peliculaSeleccionadaActual;

    /**
     * Creates new form GuiGeneroPelicula2
     */
    public GuiGeneroPelicula2() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Asignar Generos a Pelicula");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializar DAOs
        peliculaDAO = new añadirPeliculaDAO();
        generoDAO = new GeneroDAO();
        peliculaGeneroDAO = new PeliculaGeneroDAO();

        configurarTablaGeneros();
        configurarTablaPeliculaSeleccionada();

        txtTitulo.setEditable(false);
        txtTitulo.setBackground(new Color(230, 230, 230));
    }

    private void configurarTablaPeliculaSeleccionada() {
        String[] columnasPelicula = {"ID Pelicula", "Título", "Año Estreno", "País Origen", "ID Productora"};
        modeloTablaPeliculaSeleccionada = new DefaultTableModel(columnasPelicula, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        if (jTable3 != null) { 
            jTable3.setModel(modeloTablaPeliculaSeleccionada);
        }
    }

    private void configurarTablaGeneros() {
        String[] columnas = {"Asignar", "ID Género", "Nombre del Genero"};
        modeloTablaGeneros = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        
        if (jTable1 != null) {
            jTable1.setModel(modeloTablaGeneros);
            TableColumn idColG = jTable1.getColumnModel().getColumn(1);
            idColG.setMinWidth(0);
            idColG.setMaxWidth(0);
            idColG.setPreferredWidth(0);
            TableColumn checkCol = jTable1.getColumnModel().getColumn(0);
            checkCol.setPreferredWidth(60);
            TableColumn nombreCol = jTable1.getColumnModel().getColumn(2);
            nombreCol.setPreferredWidth(350);
        }
    }

    private void btnBuscarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {
        String criterioBusqueda = txtPelicula.getText().trim();
        if (criterioBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o parte del título de la pelicula.", "Criterio Vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloTablaGeneros != null) {
            modeloTablaGeneros.setRowCount(0);
        }
        if (txtTitulo != null) {
            txtTitulo.setText("");
        }
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
                            "Se encontraron varias peliculas, seleccione una:", "Multiples Peliculas Encontradas",
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
            JOptionPane.showMessageDialog(this, "Pelicula no encontrada.", "No Encontrada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarGenerosParaAsignacionActual() {
        if (modeloTablaGeneros == null || peliculaSeleccionadaActual == null) {
            return;
        }
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
        BtnAgrear = new javax.swing.JButton();
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
        btnInicio = new javax.swing.JButton();

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

        BtnAgrear.setText("agregarRelacion");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Agregar genero a peliculas");

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

        btnInicio.setText("Volver al inicio");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInicio)
                .addGap(29, 29, 29)
                .addComponent(btnGuardar)
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnInicio))
                .addGap(20, 20, 20))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

       
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar los cambios de géneros para la pelicula: " + peliculaSeleccionadaActual.getTitulo() + "?",
                "Confirmar Guardado", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean exitoRemover = peliculaGeneroDAO.removerTodosGenerosDePelicula(peliculaSeleccionadaActual.getIdPelicula());
        if (!exitoRemover) {
          
            JOptionPane.showMessageDialog(this, "Error al limpiar generos previos de la pelicula. Verifique que el Stored Procedure 'sp_remover_todos_generos_de_pelicula' exista y funcione correctamente.", "Error Guardando", JOptionPane.ERROR_MESSAGE);
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
                    System.err.println("Fallo al asignar genero ID: " + idGenero + " a pelicula ID: " + peliculaSeleccionadaActual.getIdPelicula());
                   
                } else {
                    generosAsignadosCount++;
                }
            }
        }

        if (todosAsignadosCorrectamente) {
            JOptionPane.showMessageDialog(this, generosAsignadosCount + " genero(s) asignados/actualizados para: " + peliculaSeleccionadaActual.getTitulo(), "exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Algunos generos no pudieron ser asignados. Revise la consola y verifique los Stored Procedures correspondientes.", "Error Parcial", JOptionPane.WARNING_MESSAGE);
        }
        cargarGenerosParaAsignacionActual(); 
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterioBusqueda = txtPelicula.getText().trim();
        if (criterioBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o parte del titulo de la película.", "Criterio Vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloTablaGeneros != null) {
            modeloTablaGeneros.setRowCount(0);
        }
        if (modeloTablaPeliculaSeleccionada != null) { 
            modeloTablaPeliculaSeleccionada.setRowCount(0);
        }
        if (txtTitulo != null) {
            txtTitulo.setText("");
        }
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
                    Object[] opcionesPeliculas = peliculasEncontradas.stream()
                            .map(p -> p.getIdPelicula() + ": " + p.getTitulo())
                            .toArray();
                    String seleccion = (String) JOptionPane.showInputDialog(this,
                            "Se encontraron varias películas, seleccione una:", "Multiples Películas Encontradas",
                            JOptionPane.PLAIN_MESSAGE, null, opcionesPeliculas, opcionesPeliculas[0]);
                    if (seleccion != null) {
                        int idSeleccionado = Integer.parseInt(seleccion.split(":")[0]);
                        peliculaSeleccionadaActual = peliculasEncontradas.stream()
                                .filter(p -> p.getIdPelicula() == idSeleccionado)
                                .findFirst().orElse(null);
                    }
                }
            }
        }

        if (peliculaSeleccionadaActual != null) {
            txtPelicula.setText(String.valueOf(peliculaSeleccionadaActual.getIdPelicula()));
            txtTitulo.setText(peliculaSeleccionadaActual.getTitulo());

            // NUEVO: Llenar la tabla de película seleccionada (jTable3)
            if (modeloTablaPeliculaSeleccionada != null) {
                modeloTablaPeliculaSeleccionada.addRow(new Object[]{
                    peliculaSeleccionadaActual.getIdPelicula(),
                    peliculaSeleccionadaActual.getTitulo(),
                    peliculaSeleccionadaActual.getAnioEstreno(),
                    peliculaSeleccionadaActual.getPaisOrigen(),
                    peliculaSeleccionadaActual.getIdProductora()
                });
            }
            cargarGenerosParaAsignacionActual();
        } else {
            JOptionPane.showMessageDialog(this, "Pelicula no encontrada.", "No Encontrada", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        GUIAdminInicio inicio = new GUIAdminInicio();
        inicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInicioActionPerformed

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
    private javax.swing.JButton BtnAgrear;
    private javax.swing.JList<String> ListaGenerosAsigandos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnInicio;
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
