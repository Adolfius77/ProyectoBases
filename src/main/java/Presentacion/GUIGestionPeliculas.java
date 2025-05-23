/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import DTOS.peliculaDTO;
import Persistencia.añadirPeliculaDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author USER
 */
public class GUIGestionPeliculas extends javax.swing.JFrame {

    private añadirPeliculaDAO peliculaDAO;

    /**
     * Creates new form GUIGestionPeliculas
     */
    private añadirPeliculaDAO añadirPeliculaDAO;
    private DefaultTableModel tableModel;

    public GUIGestionPeliculas() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestión de Peliculas");

        peliculaDAO = new añadirPeliculaDAO();

        configurarTabla();
        cargarDatosEnTabla();

        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {

                if (!event.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                    int filaSeleccionada = jTable1.getSelectedRow();

                    int idPeliculaSeleccionada = (int) tableModel.getValueAt(filaSeleccionada, 0);

                    peliculaDTO pelicula = peliculaDAO.obtenerPeliculaPorID(idPeliculaSeleccionada);

                    if (pelicula != null) {
                        txtIdPelicula.setText(String.valueOf(pelicula.getIdPelicula()));
                        txtTitulo.setText(pelicula.getTitulo());
                        txtEsreno.setText(String.valueOf(pelicula.getAnioEstreno()));
                        txtPais.setText(pelicula.getPaisOrigen());
                        txtIdProductora.setText(String.valueOf(pelicula.getIdProductora()));
                    } else {
                        limpiarCampos();
                    }
                }
            }
        });
    }

    private void configurarTabla() {
        String[] columnas = {"ID Pelicula", "Título", "Año Estreno", "País Origen", "ID Productora"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarDatosEnTabla() {

        tableModel.setRowCount(0);
        List<peliculaDTO> peliculas = peliculaDAO.obtenerTodasLasPeliculas();
        if (peliculas != null) {
            for (peliculaDTO pelicula : peliculas) {
                Object[] rowData = {
                    pelicula.getIdPelicula(),
                    pelicula.getTitulo(),
                    pelicula.getAnioEstreno(),
                    pelicula.getPaisOrigen(),
                    pelicula.getIdProductora()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    private void limpiarCampos() {
        txtIdPelicula.setText("");
        txtTitulo.setText("");
        txtEsreno.setText("");
        txtPais.setText("");
        txtIdProductora.setText("");
        jTable1.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        btnAgregarGenero = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtIdProductora = new javax.swing.JTextField();
        txtPais = new javax.swing.JTextField();
        txtEsreno = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIdPelicula = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        BtnAgregar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tabla = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        BtnInicio = new javax.swing.JButton();
        BtnProductora = new javax.swing.JButton();
        BtnGenero = new javax.swing.JButton();
        BtnDirector = new javax.swing.JButton();
        btnActor = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel11.setText("jLabel11");

        jButton6.setText("jButton6");

        btnAgregarGenero.setText("agregar genero");
        btnAgregarGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarGeneroActionPerformed(evt);
            }
        });

        jLabel12.setText("Agregar genero a pelicula");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Titulo:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("año de estreno:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Pais de origen:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("id de la productora:");

        txtIdProductora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoraActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("id dela  pelicula:");

        txtIdPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPeliculaActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/neflix (4).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtEsreno, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(104, 104, 104)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdProductora, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtPais)
                            .addComponent(txtIdPelicula))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEsreno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtIdProductora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtIdPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        BtnAgregar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        BtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar imagen (2).png"))); // NOI18N
        BtnAgregar.setText("Agregar");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnModificar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/revert.png"))); // NOI18N
        BtnModificar.setText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel.png"))); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnBuscar.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(BtnAgregar)
                .addGap(55, 55, 55)
                .addComponent(BtnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnEliminar)
                .addGap(51, 51, 51)
                .addComponent(BtnBuscar)
                .addGap(42, 42, 42))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnAgregar)
                    .addComponent(BtnModificar)
                    .addComponent(BtnEliminar)
                    .addComponent(BtnBuscar))
                .addGap(36, 36, 36))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("Operaciones");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setText("Datos");

        tabla.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        jTable1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id_pelicula", "titulo", "año estreno", "pais"
            }
        ));
        tabla.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("id_pelicula");
            jTable1.getColumnModel().getColumn(1).setHeaderValue("titulo");
            jTable1.getColumnModel().getColumn(2).setHeaderValue("año estreno");
            jTable1.getColumnModel().getColumn(3).setHeaderValue("pais");
        }

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel8.setText("Lista de peliculas");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setText("GESTION PELICULAS");

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnInicio.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/inicio (2).png"))); // NOI18N
        BtnInicio.setText("Inicio");
        BtnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInicioActionPerformed(evt);
            }
        });

        BtnProductora.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnProductora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/productora (2).png"))); // NOI18N
        BtnProductora.setText("Productora");
        BtnProductora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProductoraActionPerformed(evt);
            }
        });

        BtnGenero.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnGenero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/genero (2).png"))); // NOI18N
        BtnGenero.setText("Genero");
        BtnGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGeneroActionPerformed(evt);
            }
        });

        BtnDirector.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnDirector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/director (3).png"))); // NOI18N
        BtnDirector.setText("Dirrectores");
        BtnDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDirectorActionPerformed(evt);
            }
        });

        btnActor.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnActor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/actor (3).png"))); // NOI18N
        btnActor.setText("Actores");
        btnActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(BtnInicio)
                .addGap(18, 18, 18)
                .addComponent(BtnProductora)
                .addGap(26, 26, 26)
                .addComponent(BtnGenero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(BtnDirector)
                .addGap(33, 33, 33)
                .addComponent(btnActor)
                .addGap(54, 54, 54))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnDirector)
                    .addComponent(btnActor)
                    .addComponent(BtnGenero)
                    .addComponent(BtnProductora)
                    .addComponent(BtnInicio))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdProductoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoraActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        String titulo = txtTitulo.getText().trim();
        String anioStr = txtEsreno.getText().trim();
        String pais = txtPais.getText().trim();
        String idProductoraStr = txtIdProductora.getText().trim();

        if (titulo.isEmpty() || anioStr.isEmpty() || pais.isEmpty() || idProductoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos (Título, Año, País, ID Productora) son obligatorios.", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int anioEstreno = Integer.parseInt(anioStr);
            int idProductora = Integer.parseInt(idProductoraStr);

            peliculaDTO nuevaPelicula = new peliculaDTO(0, titulo, anioEstreno, pais, idProductora); // ID 0 para nuevo
            boolean exito = peliculaDAO.agregarPelicula(nuevaPelicula);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Película agregada con ID: " + nuevaPelicula.getIdPelicula(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la película.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año de estreno y ID Productora deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al agregar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        String idStr = txtIdPelicula.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una película de la tabla para modificar.", "Ninguna Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = txtTitulo.getText().trim();
        String anioStr = txtEsreno.getText().trim();
        String pais = txtPais.getText().trim();
        String idProductoraStr = txtIdProductora.getText().trim();

        if (titulo.isEmpty() || anioStr.isEmpty() || pais.isEmpty() || idProductoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPelicula = Integer.parseInt(idStr);
            int anioEstreno = Integer.parseInt(anioStr);
            int idProductora = Integer.parseInt(idProductoraStr);

            peliculaDTO peliculaModificada = new peliculaDTO(idPelicula, titulo, anioEstreno, pais, idProductora);
            boolean exito = peliculaDAO.actualizarPelicula(peliculaModificada);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Pelicula modificada correctamente.", "exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar la película (ID no encontrado o datos sin cambios).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID, Año e ID Productora deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al modificar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_BtnModificarActionPerformed

    private void txtIdPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPeliculaActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        String idStr = txtIdPelicula.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una película de la tabla para eliminar.", "Ninguna Seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de que desea eliminar esta película?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPelicula = Integer.parseInt(idStr);
                boolean exito = peliculaDAO.eliminarPelicula(idPelicula);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Película eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosEnTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la película (ID no encontrado o tiene dependencias).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID de la película no es válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error inesperado al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        String terminoBusqueda = JOptionPane.showInputDialog(this, "Ingrese el título de la película a buscar:", "Buscar Película por Título", JOptionPane.QUESTION_MESSAGE);

        if (terminoBusqueda != null) {
            if (terminoBusqueda.trim().isEmpty()) {

                cargarDatosEnTabla();
                JOptionPane.showMessageDialog(this, "Mostrando todas las peliculas.", "Búsqueda Vacia", JOptionPane.INFORMATION_MESSAGE);
            } else {
                List<peliculaDTO> peliculasResultado = peliculaDAO.buscarPeliculasPorTitulo(terminoBusqueda.trim());

                tableModel.setRowCount(0);

                if (peliculasResultado != null && !peliculasResultado.isEmpty()) {
                    for (peliculaDTO pelicula : peliculasResultado) {
                        tableModel.addRow(new Object[]{
                            pelicula.getIdPelicula(),
                            pelicula.getTitulo(),
                            pelicula.getAnioEstreno(),
                            pelicula.getPaisOrigen(),
                            pelicula.getIdProductora()
                        });
                    }
                    JOptionPane.showMessageDialog(this, peliculasResultado.size() + " pelicula(s) encontrada(s).", "Resultado de Busqueda", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron películas con el título: '" + terminoBusqueda + "'", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        limpiarCampos();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInicioActionPerformed
        GUIAdminInicio admin = new GUIAdminInicio();
        admin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnInicioActionPerformed

    private void BtnProductoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProductoraActionPerformed
        GUIGestionProductora productora = new GUIGestionProductora();
        productora.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnProductoraActionPerformed

    private void BtnGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGeneroActionPerformed
        GUIGestionGenero genero = new GUIGestionGenero();
        genero.setVisible(true);
        this.dispose();;
    }//GEN-LAST:event_BtnGeneroActionPerformed

    private void BtnDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDirectorActionPerformed
        GUIGestionDirectores directores = new GUIGestionDirectores();
        directores.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnDirectorActionPerformed

    private void btnActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActorActionPerformed
        GUIGestionActores actores = new GUIGestionActores();
        actores.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnActorActionPerformed

    private void btnAgregarGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarGeneroActionPerformed

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
            java.util.logging.Logger.getLogger(GUIGestionPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIGestionPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIGestionPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIGestionPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIGestionPeliculas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnDirector;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGenero;
    private javax.swing.JButton BtnInicio;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnProductora;
    private javax.swing.JButton btnActor;
    private javax.swing.JButton btnAgregarGenero;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane tabla;
    private javax.swing.JTextField txtEsreno;
    private javax.swing.JTextField txtIdPelicula;
    private javax.swing.JTextField txtIdProductora;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
