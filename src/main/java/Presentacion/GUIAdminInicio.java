/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import DTOS.peliculaDTO;
import Persistencia.añadirPeliculaDAO;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author USER
 */
public class GUIAdminInicio extends javax.swing.JFrame {

    private añadirPeliculaDAO peliculaDAO;

    /**
     * Creates new form GUIGestionPeliculas
     */
    private añadirPeliculaDAO añadirPeliculaDAO;
    private DefaultTableModel tableModel;
    private Timer imageSlideshowTimer;
    private List<String> listaRutasImagenes;
    private int indiceImagenActual1;
    private int indiceImagenActual2;
    private int indiceImagenActual3;
    private JLabel[] labelsDeImagenes; 

    public GUIAdminInicio() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestión de Películas");
        inicializarSlideshow();

    }

    private void inicializarSlideshow() {
       
        listaRutasImagenes = Arrays.asList(
                "/img/stranger things (4).jpg",
                "/img/breaking bad (4).jpg",
                "/img/dark (2).jpg",
                "/img/avengers.jpg", 
                "/img/llorona.jpg", 
                "/img/forest.jpg"
        );

       
        labelsDeImagenes = new JLabel[]{jLabel14, jLabel16, jLabel17};

        if (listaRutasImagenes.isEmpty() || listaRutasImagenes.size() < 3) {
            System.err.println("No hay suficientes imágenes para el slideshow o la lista está vacía.");
         
            jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stranger things (4).jpg")));
            jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/breaking bad (4).jpg")));
            jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dark (2).jpg")));
            return;
        }

        // Índices iniciales para cada JLabel (para que muestren imágenes diferentes al inicio)
        indiceImagenActual1 = 0;
        indiceImagenActual2 = 1 % listaRutasImagenes.size(); 
        indiceImagenActual3 = 2 % listaRutasImagenes.size(); 

        actualizarImagenEnLabel(labelsDeImagenes[0], indiceImagenActual1);
        actualizarImagenEnLabel(labelsDeImagenes[1], indiceImagenActual2);
        actualizarImagenEnLabel(labelsDeImagenes[2], indiceImagenActual3);

        // Configurar el Timer para que cambie las imágenes cada 5 segundos (5000 ms)
        imageSlideshowTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                indiceImagenActual1 = (indiceImagenActual1 + 1) % listaRutasImagenes.size();
                indiceImagenActual2 = (indiceImagenActual2 + 1) % listaRutasImagenes.size();
                indiceImagenActual3 = (indiceImagenActual3 + 1) % listaRutasImagenes.size();

                actualizarImagenEnLabel(labelsDeImagenes[0], indiceImagenActual1);
                actualizarImagenEnLabel(labelsDeImagenes[1], indiceImagenActual2);
                actualizarImagenEnLabel(labelsDeImagenes[2], indiceImagenActual3);
            }
        });
        imageSlideshowTimer.start(); 
    }

    private void actualizarImagenEnLabel(javax.swing.JLabel label, int indiceImagen) {
        if (listaRutasImagenes.isEmpty() || indiceImagen < 0 || indiceImagen >= listaRutasImagenes.size()) {
            label.setIcon(null); 
            return;
        }
        String rutaImagen = listaRutasImagenes.get(indiceImagen);
        try {
            java.net.URL imgUrl = getClass().getResource(rutaImagen);
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);

                if (label.getWidth() > 0 && label.getHeight() > 0) {
                    Image img = originalIcon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                } else {
                    label.setIcon(originalIcon);
                }

            } else {
                System.err.println("No se pudo encontrar la imagen: " + rutaImagen);
                label.setIcon(null);
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar la imagen " + rutaImagen + ": " + ex.getMessage());
            ex.printStackTrace();
            label.setIcon(null);
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

        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEsreno = new javax.swing.JTextField();
        txtPais = new javax.swing.JTextField();
        txtIdProductora = new javax.swing.JTextField();
        txtIdPelicula = new javax.swing.JTextField();
        BtnAgregar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tabla = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        BtnPeliculas = new javax.swing.JButton();
        btnProductora = new javax.swing.JButton();
        BtnGenero = new javax.swing.JButton();
        BtnDirrectores = new javax.swing.JButton();
        BtnActores = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        BtnAsignarGenerosPeliculas = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        BtnListarPeliculasPorGenero = new javax.swing.JButton();
        btnAsignarActoresPeliculas = new javax.swing.JButton();
        btnAsignarDirectoresAPeliculas = new javax.swing.JButton();
        btnPeliculasDondeActueUnActor = new javax.swing.JButton();
        BtnProductoraEspecifica = new javax.swing.JButton();
        BtnRangoAnios = new javax.swing.JButton();

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Titulo:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("año de estreno:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Pais de origen:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("id de la productora:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("id dela  pelicula:");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/neflix (4).png"))); // NOI18N

        txtIdProductora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoraActionPerformed(evt);
            }
        });

        txtIdPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPeliculaActionPerformed(evt);
            }
        });

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("Operaciones");

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
        );

        jLabel15.setText("jLabel12");

        jLabel13.setText("jLabel12");

        jButton14.setText("jButton14");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stranger things (4).jpg"))); // NOI18N

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/breaking bad (4).jpg"))); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dark (2).jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnPeliculas.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnPeliculas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar imagen (2).png"))); // NOI18N
        BtnPeliculas.setText("Peliculas");
        BtnPeliculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPeliculasActionPerformed(evt);
            }
        });

        btnProductora.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        btnProductora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/productora (2).png"))); // NOI18N
        btnProductora.setText("Productora");
        btnProductora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoraActionPerformed(evt);
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

        BtnDirrectores.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        BtnDirrectores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/director (3).png"))); // NOI18N
        BtnDirrectores.setText("Dirrectores");
        BtnDirrectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDirrectoresActionPerformed(evt);
            }
        });

        BtnActores.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        BtnActores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/actor (3).png"))); // NOI18N
        BtnActores.setText("Actores");
        BtnActores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActoresActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setText("GESTION PELICULAS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(BtnPeliculas)
                .addGap(18, 18, 18)
                .addComponent(btnProductora)
                .addGap(26, 26, 26)
                .addComponent(BtnGenero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(BtnDirrectores)
                .addGap(33, 33, 33)
                .addComponent(BtnActores)
                .addGap(54, 54, 54))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnDirrectores)
                    .addComponent(BtnActores)
                    .addComponent(BtnGenero)
                    .addComponent(btnProductora)
                    .addComponent(BtnPeliculas))
                .addGap(14, 14, 14))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setText("Peliculas Recientes");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        BtnAsignarGenerosPeliculas.setBackground(new java.awt.Color(255, 255, 255));
        BtnAsignarGenerosPeliculas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel12.setText("Asignar Relaciones");

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Asignar géneros a peliculas");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel18.setText("Consultas");

        BtnListarPeliculasPorGenero.setBackground(new java.awt.Color(153, 0, 0));
        BtnListarPeliculasPorGenero.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        BtnListarPeliculasPorGenero.setText("Listar peliculas por genero");
        BtnListarPeliculasPorGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnListarPeliculasPorGeneroActionPerformed(evt);
            }
        });

        btnAsignarActoresPeliculas.setBackground(new java.awt.Color(0, 0, 0));
        btnAsignarActoresPeliculas.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnAsignarActoresPeliculas.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarActoresPeliculas.setText("Asignar actores a peliculas");
        btnAsignarActoresPeliculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActoresPeliculasActionPerformed(evt);
            }
        });

        btnAsignarDirectoresAPeliculas.setBackground(new java.awt.Color(0, 0, 0));
        btnAsignarDirectoresAPeliculas.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnAsignarDirectoresAPeliculas.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarDirectoresAPeliculas.setText("Asignar directores a peliculas");
        btnAsignarDirectoresAPeliculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarDirectoresAPeliculasActionPerformed(evt);
            }
        });

        btnPeliculasDondeActueUnActor.setBackground(new java.awt.Color(102, 0, 0));
        btnPeliculasDondeActueUnActor.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnPeliculasDondeActueUnActor.setText("Películas donde actue un actor concreto");

        BtnProductoraEspecifica.setBackground(new java.awt.Color(102, 0, 0));
        BtnProductoraEspecifica.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        BtnProductoraEspecifica.setText("Peliculas de una productora especifica");

        BtnRangoAnios.setBackground(new java.awt.Color(204, 0, 0));
        BtnRangoAnios.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        BtnRangoAnios.setText("Peliculas estrenadas en un rango de años");

        javax.swing.GroupLayout BtnAsignarGenerosPeliculasLayout = new javax.swing.GroupLayout(BtnAsignarGenerosPeliculas);
        BtnAsignarGenerosPeliculas.setLayout(BtnAsignarGenerosPeliculasLayout);
        BtnAsignarGenerosPeliculasLayout.setHorizontalGroup(
            BtnAsignarGenerosPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnAsignarGenerosPeliculasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(BtnAsignarGenerosPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnListarPeliculasPorGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignarDirectoresAPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignarActoresPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BtnAsignarGenerosPeliculasLayout.createSequentialGroup()
                        .addGroup(BtnAsignarGenerosPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnPeliculasDondeActueUnActor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnProductoraEspecifica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnRangoAnios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BtnAsignarGenerosPeliculasLayout.setVerticalGroup(
            BtnAsignarGenerosPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnAsignarGenerosPeliculasLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAsignarActoresPeliculas)
                .addGap(18, 18, 18)
                .addComponent(btnAsignarDirectoresAPeliculas)
                .addGap(29, 29, 29)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(BtnListarPeliculasPorGenero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPeliculasDondeActueUnActor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnProductoraEspecifica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnRangoAnios)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAsignarGenerosPeliculas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnAsignarGenerosPeliculas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
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


    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed


    }//GEN-LAST:event_BtnModificarActionPerformed

    private void txtIdPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPeliculaActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed

    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed

    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnAsignarActoresPeliculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActoresPeliculasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsignarActoresPeliculasActionPerformed

    private void BtnListarPeliculasPorGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnListarPeliculasPorGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnListarPeliculasPorGeneroActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void btnAsignarDirectoresAPeliculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarDirectoresAPeliculasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsignarDirectoresAPeliculasActionPerformed

    private void BtnPeliculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPeliculasActionPerformed
      GUIGestionPeliculas peliculas = new GUIGestionPeliculas();
      peliculas.setVisible(true);
      this.dispose();
    }//GEN-LAST:event_BtnPeliculasActionPerformed

    private void btnProductoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoraActionPerformed
       GUIGestionProductora productora = new GUIGestionProductora();
       productora.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnProductoraActionPerformed

    private void BtnGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGeneroActionPerformed
        GUIGestionGenero genero = new GUIGestionGenero();
        genero.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnGeneroActionPerformed

    private void BtnDirrectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDirrectoresActionPerformed
      GUIGestionDirectores directores = new GUIGestionDirectores();
      directores.setVisible(true);
      this.dispose();
    }//GEN-LAST:event_BtnDirrectoresActionPerformed

    private void BtnActoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActoresActionPerformed
        GUIGestionActores actores = new GUIGestionActores();
        actores.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnActoresActionPerformed

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
            java.util.logging.Logger.getLogger(GUIAdminInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIAdminInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIAdminInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIAdminInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIAdminInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActores;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JPanel BtnAsignarGenerosPeliculas;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnDirrectores;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGenero;
    private javax.swing.JButton BtnListarPeliculasPorGenero;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnPeliculas;
    private javax.swing.JButton BtnProductoraEspecifica;
    private javax.swing.JButton BtnRangoAnios;
    private javax.swing.JButton btnAsignarActoresPeliculas;
    private javax.swing.JButton btnAsignarDirectoresAPeliculas;
    private javax.swing.JButton btnPeliculasDondeActueUnActor;
    private javax.swing.JButton btnProductora;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane tabla;
    private javax.swing.JTextField txtEsreno;
    private javax.swing.JTextField txtIdPelicula;
    private javax.swing.JTextField txtIdProductora;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
