package Presentacion;

import DTOS.peliculaDTO;
import Persistencia.añadirPeliculaDAO;
// Ya no necesitas importar GeneroDAO, ActoresDAO, ProductoraDAO aquí si solo pides IDs
// y no implementas la búsqueda por nombre dentro de esta GUI directamente.

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuiConsultas extends javax.swing.JFrame {

    private JComboBox<String> cmbTipoConsulta;
    private JPanel pnlInputsContainer; // Panel que usará CardLayout
    private CardLayout cardLayout;

    // Paneles de input para cada tipo de consulta
    private JPanel pnlInputGenero;
    private JTextField txtIdGenero;

    private JPanel pnlInputActor;
    private JTextField txtIdActor;

    private JPanel pnlInputProductora;
    private JTextField txtIdProductora;

    private JPanel pnlInputRangoAnios;
    private JTextField txtAnioInicio;
    private JTextField txtAnioFin;
    
    private JPanel pnlInputVacio;

    private JButton btnEjecutarConsulta;
    private JButton btnRegresarAdmin; 
    private JTable tblResultados;
    private DefaultTableModel modeloTablaResultados;

    private añadirPeliculaDAO peliculaDAO;

    public GuiConsultas() {
        peliculaDAO = new añadirPeliculaDAO();

        initComponentsManual(); 
        setTitle("Consultas de Peliculas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(800, 750); // Un poco más de alto para el botón
        setLocationRelativeTo(null); 
    }

    private void initComponentsManual() {
        JPanel pnlPrincipal = new JPanel(new BorderLayout(10, 10));
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel pnlSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSeleccion.add(new JLabel("Seleccione tipo de consulta:"));
        String[] tiposConsulta = {
            "-- Seleccione una opcion --",
            "Listar peliculas por genero",
            "Películas donde actue un actor concreto",
            "Películas de una productora específica",
            "Peliculas estrenadas en un rango de años"
        };
        cmbTipoConsulta = new JComboBox<>(tiposConsulta);
        pnlSeleccion.add(cmbTipoConsulta);
        pnlPrincipal.add(pnlSeleccion, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        pnlInputsContainer = new JPanel(cardLayout);

        pnlInputVacio = new JPanel();
        pnlInputsContainer.add(pnlInputVacio, "-- Seleccione una opcion --");

        pnlInputGenero = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlInputGenero.add(new JLabel("ID del Genero:"));
        txtIdGenero = new JTextField(10);
        pnlInputGenero.add(txtIdGenero);
        pnlInputsContainer.add(pnlInputGenero, "Listar películas por genero");

        pnlInputActor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlInputActor.add(new JLabel("ID del Actor:"));
        txtIdActor = new JTextField(10);
        pnlInputActor.add(txtIdActor);
        pnlInputsContainer.add(pnlInputActor, "Peliculas donde actúe un actor concreto");

        pnlInputProductora = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlInputProductora.add(new JLabel("ID de la Productora:"));
        txtIdProductora = new JTextField(10);
        pnlInputProductora.add(txtIdProductora);
        pnlInputsContainer.add(pnlInputProductora, "Peliculas de una productora especifica");

        pnlInputRangoAnios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlInputRangoAnios.add(new JLabel("Año Inicio:"));
        txtAnioInicio = new JTextField(5);
        pnlInputRangoAnios.add(txtAnioInicio);
        pnlInputRangoAnios.add(new JLabel("  Año Fin:")); 
        txtAnioFin = new JTextField(5);
        pnlInputRangoAnios.add(txtAnioFin);
        pnlInputsContainer.add(pnlInputRangoAnios, "Peliculas estrenadas en un rango de años");
        
        JPanel pnlCentroControles = new JPanel(new BorderLayout(5,5));
        pnlCentroControles.add(pnlInputsContainer, BorderLayout.NORTH);
        
        btnEjecutarConsulta = new JButton("Ejecutar Consulta");
        JPanel pnlBotonEjecutar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBotonEjecutar.add(btnEjecutarConsulta);
        pnlCentroControles.add(pnlBotonEjecutar, BorderLayout.CENTER);
        
        pnlPrincipal.add(pnlCentroControles, BorderLayout.CENTER);

        String[] columnasTabla = {"ID Pelicula", "Título", "Año Estreno", "Pais Origen", "ID Productora"};
        modeloTablaResultados = new DefaultTableModel(columnasTabla, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblResultados = new JTable(modeloTablaResultados);
        JScrollPane scrollPaneTabla = new JScrollPane(tblResultados);
        

       
        JPanel pnlSur = new JPanel(new BorderLayout(10,10));
        pnlSur.add(scrollPaneTabla, BorderLayout.CENTER); 

        btnRegresarAdmin = new JButton("Regresar a Inicio Admin");
        JPanel pnlBotonRegresar = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        pnlBotonRegresar.add(btnRegresarAdmin);
        pnlSur.add(pnlBotonRegresar, BorderLayout.SOUTH); 

        pnlPrincipal.add(pnlSur, BorderLayout.SOUTH);

        
        cmbTipoConsulta.addActionListener(e -> {
            String seleccion = (String) cmbTipoConsulta.getSelectedItem();
            cardLayout.show(pnlInputsContainer, seleccion);
            limpiarCamposYTabla();
        });

        btnEjecutarConsulta.addActionListener(e -> ejecutarConsultaSeleccionada());

        btnRegresarAdmin.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAAdminInicio();
            }
        });

        add(pnlPrincipal);
        
    }
    
    private void limpiarCamposYTabla() {
        if (txtIdGenero != null) txtIdGenero.setText("");
        if (txtIdActor != null) txtIdActor.setText("");
        if (txtIdProductora != null) txtIdProductora.setText("");
        if (txtAnioInicio != null) txtAnioInicio.setText("");
        if (txtAnioFin != null) txtAnioFin.setText("");
        if (modeloTablaResultados != null) modeloTablaResultados.setRowCount(0);
    }

    private void ejecutarConsultaSeleccionada() {
        String tipoConsulta = (String) cmbTipoConsulta.getSelectedItem();
        if (tipoConsulta == null || tipoConsulta.equals("-- Seleccione una opción --")) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un tipo de consulta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTablaResultados.setRowCount(0); 
        List<peliculaDTO> resultados = null;

        try {
            switch (tipoConsulta) {
                case "Listar peliculas por genero":
                    if (txtIdGenero.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el ID del Genero.", "Entrada Requerida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int idGenero = Integer.parseInt(txtIdGenero.getText().trim());
                    resultados = peliculaDAO.obtenerPeliculasPorGenero(idGenero);
                    break;
                case "Peliculas donde actúe un actor concreto":
                    if (txtIdActor.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el ID del Actor.", "Entrada Requerida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int idActor = Integer.parseInt(txtIdActor.getText().trim());
                    resultados = peliculaDAO.obtenerPeliculasPorActor(idActor);
                    break;
                case "Peliculas de una productora específica":
                     if (txtIdProductora.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el ID de la Productora.", "Entrada Requerida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int idProductora = Integer.parseInt(txtIdProductora.getText().trim());
                    resultados = peliculaDAO.obtenerPeliculasPorProductora(idProductora);
                    break;
                case "Películas estrenadas en un rango de años":
                    if (txtAnioInicio.getText().trim().isEmpty() || txtAnioFin.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el Año Inicio y Año Fin.", "Entrada Requerida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int anioInicio = Integer.parseInt(txtAnioInicio.getText().trim());
                    int anioFin = Integer.parseInt(txtAnioFin.getText().trim());
                    if (anioInicio > anioFin) {
                        JOptionPane.showMessageDialog(this, "El Año Inicio no puede ser mayor al Año Fin.", "Error de Rango", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultados = peliculaDAO.obtenerPeliculasPorRangoDeAnios(anioInicio, anioFin);
                    break;
            }

            if (resultados != null && !resultados.isEmpty()) {
                for (peliculaDTO pelicula : resultados) {
                    modeloTablaResultados.addRow(new Object[]{
                        pelicula.getIdPelicula(),
                        pelicula.getTitulo(),
                        pelicula.getAnioEstreno(),
                        pelicula.getPaisOrigen(),
                        pelicula.getIdProductora()
                    });
                }
            } else if (resultados != null) { 
                 JOptionPane.showMessageDialog(this, "No se encontraron peliculas para los criterios seleccionados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID numerico válido o años validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error al ejecutar la consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void regresarAAdminInicio() {
        GUIAdminInicio adminInicio = new GUIAdminInicio();
        adminInicio.setVisible(true); 
        this.dispose(); 
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 try { 
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(GuiConsultas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                new GuiConsultas().setVisible(true);
            }
        });
    }
  
}