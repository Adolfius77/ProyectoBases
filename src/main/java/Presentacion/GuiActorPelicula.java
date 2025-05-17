/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import DTOS.actorDTO;
import DTOS.generoDTO;
import DTOS.peliculaDTO;
import Persistencia.ActoresDAO;
import Persistencia.GeneroDAO;
import Persistencia.PeliculaActorDAO;
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
public class GuiActorPelicula extends javax.swing.JFrame {

    private añadirPeliculaDAO peliculaDAO;
    private ActoresDAO actoresDAO;
    private PeliculaActorDAO peliculaActorDAO;

    private DefaultTableModel modeloTablaPeliculasAsignables;
    private DefaultTableModel modeloTablaActorSeleccionado;

    private actorDTO actorSeleccionadoActual;

    /**
     * Creates new form GuiGeneroPelicula2
     */
    public GuiActorPelicula() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Asignar Peliculas a Actor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializar DAOs
        peliculaDAO = new añadirPeliculaDAO();
        actoresDAO = new ActoresDAO();
        peliculaActorDAO = new PeliculaActorDAO();

        configurarTablaPeliculasAsignables();
        configurarTablaActorSeleccionado();

        if (txtNombre != null) {
            txtNombre.setEditable(false);
            txtNombre.setBackground(new Color(230, 230, 230));
        }

    }

    private void configurarTablaActorSeleccionado() {
        String[] columnasActor = {"ID Actor", "Nombre", "Nacionalidad"};
        modeloTablaActorSeleccionado = new DefaultTableModel(columnasActor, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (jTable3 != null) {
            jTable3.setModel(modeloTablaActorSeleccionado);
        }
    }

    private void configurarTablaPeliculasAsignables() {
        String[] columnasPeliculas = {"Asignar", "ID Pelicula", "Título de la Película"};
        modeloTablaPeliculasAsignables = new DefaultTableModel(columnasPeliculas, 0) {
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
            jTable1.setModel(modeloTablaPeliculasAsignables);
            TableColumn idCol = jTable1.getColumnModel().getColumn(1);
            idCol.setMinWidth(0);
            idCol.setMaxWidth(0);
            idCol.setPreferredWidth(0);
            TableColumn checkCol = jTable1.getColumnModel().getColumn(0);
            checkCol.setPreferredWidth(60);
            TableColumn nombreCol = jTable1.getColumnModel().getColumn(2);
            nombreCol.setPreferredWidth(350);
        }
    }

    private void btnBuscarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {
        String criterioBusqueda = txtIdDirector.getText().trim();
        if (criterioBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese ID o Nombre del Actor.", "Criterio Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloTablaPeliculasAsignables != null) {
            modeloTablaPeliculasAsignables.setRowCount(0);
        }
        if (modeloTablaActorSeleccionado != null) {
            modeloTablaActorSeleccionado.setRowCount(0);
        }
        if (txtNombre != null) {
            txtNombre.setText("");
        }
        actorSeleccionadoActual = null;

        try {
            int idActor = Integer.parseInt(criterioBusqueda);
            actorSeleccionadoActual = actoresDAO.obtenerActorPorId(idActor);
        } catch (NumberFormatException e) {
            List<actorDTO> actoresEncontrados = actoresDAO.buscarActoresPorNombre(criterioBusqueda);
            if (actoresEncontrados != null && !actoresEncontrados.isEmpty()) {
                if (actoresEncontrados.size() == 1) {
                    actorSeleccionadoActual = actoresEncontrados.get(0);
                } else {
                    Object[] opciones = actoresEncontrados.stream()
                            .map(a -> a.getIdActor() + ": " + a.getNombre())
                            .toArray();
                    String seleccion = (String) JOptionPane.showInputDialog(this,
                            "Se encontraron varios actores, seleccione uno:", "Múltiples Actores",
                            JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
                    if (seleccion != null && !seleccion.isEmpty()) {
                        try {
                            int idSel = Integer.parseInt(seleccion.split(":")[0].trim());
                            actorSeleccionadoActual = actoresEncontrados.stream()
                                    .filter(a -> a.getIdActor() == idSel)
                                    .findFirst().orElse(null);
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }

        if (actorSeleccionadoActual != null) {
            txtIdDirector.setText(String.valueOf(actorSeleccionadoActual.getIdActor()));
            txtNombre.setText(actorSeleccionadoActual.getNombre());

            modeloTablaActorSeleccionado.addRow(new Object[]{
                actorSeleccionadoActual.getIdActor(),
                actorSeleccionadoActual.getNombre(),
                actorSeleccionadoActual.getNacionalidad()
            });
            cargarPeliculasParaAsignacionActual();
        } else {
            JOptionPane.showMessageDialog(this, "Actor no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarPeliculasParaAsignacionActual() {
        if (modeloTablaPeliculasAsignables == null || actorSeleccionadoActual == null) {
            return;
        }
        modeloTablaPeliculasAsignables.setRowCount(0);

        List<peliculaDTO> todasLasPeliculas = peliculaDAO.obtenerTodasLasPeliculas();
        List<peliculaDTO> peliculasAsignadas = peliculaActorDAO.obtenerPeliculasDeActor(actorSeleccionadoActual.getIdActor());

        Set<Integer> idsPeliculasAsignadas = new HashSet<>();
        if (peliculasAsignadas != null) {
            idsPeliculasAsignadas.addAll(peliculasAsignadas.stream().map(peliculaDTO::getIdPelicula).collect(Collectors.toList()));
        }

        if (todasLasPeliculas != null) {
            for (peliculaDTO pelicula : todasLasPeliculas) {
                boolean asignado = idsPeliculasAsignadas.contains(pelicula.getIdPelicula());
                modeloTablaPeliculasAsignables.addRow(new Object[]{asignado, pelicula.getIdPelicula(), pelicula.getTitulo()});
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
        guiGeneroPelicula21 = new Presentacion.GuiGeneroPelicula2();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtIdDirector = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tabla = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tablapeli = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
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

        jLabel1.setText("Agregar directores a peliculas");

        jLabel2.setText("ingrese el id del director ala que quiere agregar a una pelicula");

        jLabel3.setText("Nombre");

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
                    .addComponent(txtIdDirector, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(txtNombre))
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
                    .addComponent(txtIdDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel4.setText("Lista de peliculas");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "nombre del actor", "nacionalidad"
            }
        ));
        tabla.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("nombre del actor");
        }

        tablapeli.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tablapeliComponentAdded(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id_actor", "nombre", "nacionalidad"
            }
        ));
        tablapeli.setViewportView(jTable3);

        jLabel6.setText("Actores");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tablapeli, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
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
        if (actorSeleccionadoActual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, busque y seleccione un ACTOR primero.", "Actor no Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar las asignaciones de películas para el actor: " + actorSeleccionadoActual.getNombre() + "?",
                "Confirmar Guardado", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean exitoRemover = peliculaActorDAO.removerTodasPeliculasDeActor(actorSeleccionadoActual.getIdActor());
        if (!exitoRemover) {
            JOptionPane.showMessageDialog(this, "Error al limpiar películas previas asignadas al actor. Verifique el Stored Procedure 'sp_remover_todas_peliculas_de_actor'.", "Error Guardando", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean todasAsignadasCorrectamente = true;
        int peliculasAsignadasCount = 0;
        for (int i = 0; i < modeloTablaPeliculasAsignables.getRowCount(); i++) {
            Boolean asignado = (Boolean) modeloTablaPeliculasAsignables.getValueAt(i, 0);
            if (asignado != null && asignado) {
                int idPelicula = (int) modeloTablaPeliculasAsignables.getValueAt(i, 1);

                if (!peliculaActorDAO.asignarActorAPelicula(idPelicula, actorSeleccionadoActual.getIdActor())) {
                    todasAsignadasCorrectamente = false;
                    System.err.println("Fallo al asignar PELÍCULA ID: " + idPelicula + " al ACTOR ID: " + actorSeleccionadoActual.getIdActor());
                } else {
                    peliculasAsignadasCount++;
                }
            }
        }

        if (todasAsignadasCorrectamente) {
            JOptionPane.showMessageDialog(this, peliculasAsignadasCount + " película(s) asignadas/actualizadas para el actor: " + actorSeleccionadoActual.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Algunas películas no pudieron ser asignadas. Revise la consola.", "Error Parcial", JOptionPane.WARNING_MESSAGE);
        }
        cargarPeliculasParaAsignacionActual();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterioBusquedaActor = txtIdDirector.getText().trim();
        if (criterioBusquedaActor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID o nombre del ACTOR.", "Criterio Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloTablaPeliculasAsignables != null) {
            modeloTablaPeliculasAsignables.setRowCount(0);
        }
        if (modeloTablaActorSeleccionado != null) {
            modeloTablaActorSeleccionado.setRowCount(0);
        }
        if (txtNombre != null) {
            txtNombre.setText("");
        }
        actorSeleccionadoActual = null;

        try {
            int idActor = Integer.parseInt(criterioBusquedaActor);
            actorSeleccionadoActual = actoresDAO.obtenerActorPorId(idActor);
        } catch (NumberFormatException e) {
            List<actorDTO> actoresEncontrados = actoresDAO.buscarActoresPorNombre(criterioBusquedaActor);
            if (actoresEncontrados != null && !actoresEncontrados.isEmpty()) {
                if (actoresEncontrados.size() == 1) {
                    actorSeleccionadoActual = actoresEncontrados.get(0);
                } else {
                    Object[] opcionesActores = actoresEncontrados.stream()
                            .map(a -> a.getIdActor() + ": " + a.getNombre())
                            .toArray();
                    String seleccion = (String) JOptionPane.showInputDialog(this,
                            "Se encontraron varios actores, seleccione uno:", "Múltiples Actores Encontrados",
                            JOptionPane.PLAIN_MESSAGE, null, opcionesActores, opcionesActores[0]);
                    if (seleccion != null && !seleccion.isEmpty()) {
                        try {
                            int idSeleccionado = Integer.parseInt(seleccion.split(":")[0].trim());
                            actorSeleccionadoActual = actoresEncontrados.stream()
                                    .filter(a -> a.getIdActor() == idSeleccionado)
                                    .findFirst().orElse(null);
                        } catch (NumberFormatException ex) {
                            actorSeleccionadoActual = null;
                        }
                    }
                }
            }
        }

        if (actorSeleccionadoActual != null) {
            txtIdDirector.setText(String.valueOf(actorSeleccionadoActual.getIdActor()));
            txtNombre.setText(actorSeleccionadoActual.getNombre());

            if (modeloTablaActorSeleccionado != null) {
                modeloTablaActorSeleccionado.addRow(new Object[]{
                    actorSeleccionadoActual.getIdActor(),
                    actorSeleccionadoActual.getNombre(),
                    actorSeleccionadoActual.getNacionalidad()
                });
            }
            cargarPeliculasParaAsignacionActual();
        } else {
            JOptionPane.showMessageDialog(this, "Actor no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        GUIAdminInicio inicio = new GUIAdminInicio();
        inicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInicioActionPerformed

    private void tablapeliComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tablapeliComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapeliComponentAdded

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
            java.util.logging.Logger.getLogger(GuiActorPelicula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiActorPelicula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiActorPelicula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiActorPelicula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiActorPelicula().setVisible(true);
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
    private Presentacion.GuiGeneroPelicula2 guiGeneroPelicula21;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JTextField txtIdDirector;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
