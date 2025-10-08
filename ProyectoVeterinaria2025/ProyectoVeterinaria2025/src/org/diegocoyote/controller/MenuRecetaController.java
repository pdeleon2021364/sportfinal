
package org.diegocoyote.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Recetas;
import org.diegocoyote.report.GenerarReporte;
import org.diegocoyote.system.Principal;

public class MenuRecetaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Recetas> listaRecetas;
    
    @FXML
    private TableColumn<Recetas, Integer> CodigoReceta;
    
    @FXML
    private TableColumn<Recetas, Integer> CodigoMedicamento;
    
    @FXML
    private TableColumn<Recetas, String> Indicaciones;
    
    @FXML
    private TableColumn<Recetas, String> Frecuencia;
    
    @FXML
    private TableColumn<Recetas, String> Dosis;
    
    @FXML
    private TableColumn<Recetas, Integer> CodigoConsulta;
    
    @FXML
    private TableView<Recetas> TablaReceta;
    
    @FXML
    private TableColumn<Recetas, Integer> DuracionDias;

    @FXML
    private TextField codigoRecetaField;

    @FXML
    private TextField codigoMedicamentoField;

    @FXML
    private TextField indicacionesField;

    @FXML
    private TextField frecuenciaField;

    @FXML
    private TextField dosisField;

    @FXML
    private TextField duracionDiasField;
    
    @FXML
    private TextField codigoConsultaField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaReceta.setItems (getRecetas()) ;

    CodigoReceta.setCellValueFactory(new PropertyValueFactory<>( "codigoReceta") );
    Dosis.setCellValueFactory(new PropertyValueFactory<> ( "dosis") );
    Frecuencia.setCellValueFactory(new PropertyValueFactory<>( "frecuencia") );
    DuracionDias.setCellValueFactory(new PropertyValueFactory<> ( "duracionDias") );
    Indicaciones.setCellValueFactory(new PropertyValueFactory<>("indicaciones") );
    CodigoConsulta.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta") );
    CodigoMedicamento.setCellValueFactory(new PropertyValueFactory<>( "codigoMedicamento") );

    }
   
    
    public void MostrarLosDatos () {
    listaRecetas = FXCollections.observableArrayList() ;

    TablaReceta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoRecetaField.setText (String.valueOf(newSelection.getCodigoReceta()));
    dosisField.setText ( newSelection. getDosis () );
    frecuenciaField.setText (newSelection.getFrecuencia ());
    duracionDiasField.setText (String.valueOf( newSelection.getDuracionDias ()));
    indicacionesField.setText (newSelection.getIndicaciones ());
    codigoConsultaField.setText (String.valueOf(newSelection.getCodigoConsulta ()));
    codigoMedicamentoField.setText (String.valueOf(newSelection.getCodigoMedicamento()));

    } else {
    codigoRecetaField.clear ();
    dosisField.clear ();
    frecuenciaField.clear ();
    duracionDiasField.clear ();
    indicacionesField.clear ();
    codigoConsultaField.clear ();
    codigoMedicamentoField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Recetas> buscarRecetaPorCodigo(int codigo) {
        ObservableList<Recetas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_receta(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Recetas(
                        rs.getInt("codigoReceta"),
                        rs.getString("dosis"),
                        rs.getString("frecuencia"),
                        rs.getInt("duracionDias"),
                        rs.getString("indicaciones"),
                        rs.getInt("codigoConsulta"),
                        rs.getInt("codigoMedicamento")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar receta: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Recetas> getRecetas() {
        ObservableList<Recetas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_receta()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Recetas(
                         rs.getInt("codigoReceta"),
                        rs.getString("dosis"),
                        rs.getString("frecuencia"),
                        rs.getInt("duracionDias"),
                        rs.getString("indicaciones"),
                        rs.getInt("codigoConsulta"),
                        rs.getInt("codigoMedicamento")
                    )
                );
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return lista;
    }
   
    @FXML
    void handleButtonPressLimpiar(ActionEvent event) {
        codigoRecetaField.clear();
        dosisField.clear();
        frecuenciaField.clear();
        duracionDiasField.clear();
        indicacionesField.clear();
        codigoConsultaField.clear();
        codigoMedicamentoField.clear();
        TablaReceta.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoRecetaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoRecetaField.getText());
                TablaReceta.setItems(buscarRecetaPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de receta para buscar.");
        }
    }
    
    @FXML
void handleButtonPressAgregarReceta(ActionEvent event) {
    String dosis = dosisField.getText().trim();
    String frecuencia = frecuenciaField.getText().trim();
    String duracionDiasTexto = duracionDiasField.getText().trim();
    String indicaciones = indicacionesField.getText().trim();
    String codigoConsultaTexto = codigoConsultaField.getText().trim();
    String codigoMedicamentoTexto = codigoMedicamentoField.getText().trim();

    // Validación de campos vacíos
    if (dosis.isEmpty() || frecuencia.isEmpty() || duracionDiasTexto.isEmpty() ||
        indicaciones.isEmpty() || codigoConsultaTexto.isEmpty() || codigoMedicamentoTexto.isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int duracionDias;
    int codigoConsulta;
    int codigoMedicamento;

    // Validación de valores numéricos
    try {
        duracionDias = Integer.parseInt(duracionDiasTexto);
        codigoConsulta = Integer.parseInt(codigoConsultaTexto);
        codigoMedicamento = Integer.parseInt(codigoMedicamentoTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Duración en días, código de consulta y código de medicamento deben ser números válidos.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Receta
    Recetas receta = new Recetas();
    receta.setDosis(dosis);
    receta.setFrecuencia(frecuencia);
    receta.setDuracionDias(duracionDias);
    receta.setIndicaciones(indicaciones);
    receta.setCodigoConsulta(codigoConsulta);
    receta.setCodigoMedicamento(codigoMedicamento);

    // Registrar receta en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_receta(?, ?, ?, ?, ?, ?) }");
        stmt.setString(1, receta.getDosis());
        stmt.setString(2, receta.getFrecuencia());
        stmt.setInt(3, receta.getDuracionDias());
        stmt.setString(4, receta.getIndicaciones());
        stmt.setInt(5, receta.getCodigoConsulta());
        stmt.setInt(6, receta.getCodigoMedicamento());
        stmt.execute();

        cargarDatos(); // Refrescar tabla o vista
        System.out.println("Receta registrada exitosamente.");

    } catch (Exception e) {
        System.err.println("Error al registrar receta: " + e.getMessage());
        e.printStackTrace();

        
    }
}

    @FXML
    void handleButtonPressEditarReceta(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoRecetaField.getText().trim());
            String dosis = dosisField.getText().trim();
            String frecuencia = frecuenciaField.getText().trim();
                int duracionDias = Integer.parseInt(duracionDiasField.getText().trim());
             String indicaciones = indicacionesField.getText().trim();
                int codigoConsulta = Integer.parseInt(codigoConsultaField.getText().trim());
                int codigoMedicamento = Integer.parseInt(codigoMedicamentoField.getText().trim());
 
            
 
            if (dosis.isEmpty() || frecuencia.isEmpty() ||
            indicaciones.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
 
 
            Recetas receta = new Recetas();
            receta.setCodigoReceta(codigo); // <-- Aquí corregido
            receta.setDosis(dosis);
            receta.setFrecuencia(frecuencia);
            receta.setDuracionDias(duracionDias);
            receta.setIndicaciones(indicaciones);
            receta.setCodigoConsulta(codigoConsulta);
            receta.setCodigoMedicamento(codigoMedicamento);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_receta(?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, receta.getCodigoReceta());
            stmt.setString(2, receta.getDosis());
            stmt.setString(3, receta.getFrecuencia());
            stmt.setInt(4, receta.getDuracionDias());
            stmt.setString(5, receta.getIndicaciones());
            stmt.setInt(6, receta.getCodigoConsulta());
            stmt.setInt(7, receta.getCodigoMedicamento());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Receta actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del receta debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar receta: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarReceta(ActionEvent event) {
    Recetas recetaSeleccionado = TablaReceta.getSelectionModel().getSelectedItem();
 
    if (recetaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_receta(?) }");
            stmt.setInt(1, recetaSeleccionado.getCodigoReceta());
            stmt.execute();
 
            System.out.println("Receta eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar receta: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un receta de la tabla para eliminar.");
        }
    }
    
     @FXML
    private void btnRegresar(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuPrincipal.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
    } catch (IOException e) {
        System.out.println("Error al regresar al Menú Principal.");
    }
}
    
    
    @FXML
    public void generarReporte(){
        JOptionPane.showMessageDialog(null,"Su reporte esta Listo");
        imprimirReporte();
    }
    
    @FXML
    public void imprimirReporte(){
        Map<String,Object> parametros  = new HashMap<>();
        try {
            InputStream hoja = getClass().getResourceAsStream("/org/diegocoyote/image/hoja_membretada.jpg");
            if (hoja == null) {
                System.out.println("No se encontro la Imagen");
            }else{
                parametros.put("HojaMembretada",hoja);
            }
            
                GenerarReporte.mostrarReporte("/org/diegocoyote/report/ReporteReceta.jasper","Reporte de Recetas",parametros);

        } catch (Exception e) {
            System.out.println("Error al cargar la imagen o generar reporte");
            e.printStackTrace();
        }
        //parametros.put("codigoCliente",null);
    }
}


    
