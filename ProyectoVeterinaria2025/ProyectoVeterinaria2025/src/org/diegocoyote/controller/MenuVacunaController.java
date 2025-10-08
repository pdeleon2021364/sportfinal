
package org.diegocoyote.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.diegocoyote.models.Vacunas;
import org.diegocoyote.report.GenerarReporte;
import org.diegocoyote.system.Principal;

public class MenuVacunaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Vacunas> listaVacunas;
    
    @FXML
    private TableColumn<Vacunas, Integer> CodigoVacuna;
    
    @FXML
    private TableColumn<Vacunas, Integer> FrecuenciaMeses;
    
    @FXML
    private TableColumn<Vacunas, String> Descripcion;
    
    @FXML
    private TableColumn<Vacunas, String> NombreVacuna;
    
    @FXML
    private TableView<Vacunas> TablaVacuna;
    
    @FXML
    private TableColumn<Vacunas, String> Dosis;

    @FXML
    private TextField codigoVacunaField;

    @FXML
    private TextField frecuenciaMesesField;

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField nombreVacunaField;

    @FXML
    private TextField dosisField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaVacuna.setItems (getVacunas()) ;

    CodigoVacuna.setCellValueFactory(new PropertyValueFactory<>( "codigoVacuna") );
    NombreVacuna.setCellValueFactory(new PropertyValueFactory<> ( "nombreVacuna") );
    Descripcion.setCellValueFactory(new PropertyValueFactory<>( "descripcion") );
    Dosis.setCellValueFactory(new PropertyValueFactory<> ( "dosis") );
    FrecuenciaMeses.setCellValueFactory(new PropertyValueFactory<>("frecuenciaMeses") );
    }
   
    
    public void MostrarLosDatos () {
    listaVacunas = FXCollections.observableArrayList() ;

    TablaVacuna.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoVacunaField.setText (String.valueOf(newSelection.getCodigoVacuna()));
    nombreVacunaField.setText ( newSelection. getNombreVacuna () );
    descripcionField.setText (newSelection.getDescripcion ());
    dosisField.setText ( newSelection.getDosis ());
    frecuenciaMesesField.setText (String.valueOf(newSelection.getFrecuenciaMeses ()));
    } else {
    codigoVacunaField.clear ();
    nombreVacunaField.clear ();
    descripcionField.clear ();
    dosisField.clear ();
    frecuenciaMesesField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Vacunas> buscarVacunaPorCodigo(int codigo) {
        ObservableList<Vacunas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_vacuna(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Vacunas(
                        rs.getInt("codigoVacuna"),
                        rs.getString("nombreVacuna"),
                        rs.getString("descripcion"),
                        rs.getString("dosis"),
                        rs.getInt("frecuenciaMeses")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar vacuna: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Vacunas> getVacunas() {
        ObservableList<Vacunas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_vacuna()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Vacunas(
                        rs.getInt("codigoVacuna"),
                        rs.getString("nombreVacuna"),
                        rs.getString("descripcion"),
                        rs.getString("dosis"),
                        rs.getInt("frecuenciaMeses")
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
        codigoVacunaField.clear();
        nombreVacunaField.clear();
        descripcionField.clear();
        dosisField.clear();
        frecuenciaMesesField.clear();
        TablaVacuna.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoVacunaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoVacunaField.getText());
                TablaVacuna.setItems(buscarVacunaPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de vacuna para buscar.");
        }
    }
    
     @FXML
    void handleButtonPressAgregarVacuna(ActionEvent event) {
    String nombres = nombreVacunaField.getText().trim();
    String descripcion = descripcionField.getText().trim();
    String dosis = dosisField.getText().trim();
    String frecuenciaTexto = frecuenciaMesesField.getText().trim();

    // Validar campos vacíos
    if (nombres.isEmpty() || descripcion.isEmpty() || dosis.isEmpty() || frecuenciaTexto.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int frecuencia;
    try {
        frecuencia = Integer.parseInt(frecuenciaTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("La Frecuencia debe de ser un numero valido.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Vacunas
    Vacunas vacuna = new Vacunas();
    vacuna.setNombreVacuna(nombres);
    vacuna.setDescripcion(descripcion);
    vacuna.setDosis(dosis);
    vacuna.setFrecuenciaMeses(frecuencia);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_vacuna(?, ?, ?, ?) }");
        stmt.setString(1, vacuna.getNombreVacuna());
        stmt.setString(2, vacuna.getDescripcion());
        stmt.setString(3, vacuna.getDosis());
        stmt.setInt(4, vacuna.getFrecuenciaMeses());
        stmt.execute();

        cargarDatos(); // Actualizar tabla
        System.out.println("Vacuna registrada exitosamente");
        
    } catch (Exception e) {
        System.out.println("Error al registrar vacuna");
        e.printStackTrace(); // Solo para desarrollo
    }
}

    @FXML
    void handleButtonPressEditarVacuna(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoVacunaField.getText().trim());
             String nombres = nombreVacunaField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String dosis = dosisField.getText().trim();
            int frecuencia = Integer.parseInt(frecuenciaMesesField.getText().trim());
            
 
            if (nombres.isEmpty() || descripcion.isEmpty() ||
            dosis.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
 
            Vacunas vacuna = new Vacunas();
            vacuna.setCodigoVacuna(codigo); // <-- Aquí corregido
             vacuna.setNombreVacuna(nombres);
             vacuna.setDescripcion(descripcion);
            vacuna.setDosis(dosis);
            vacuna.setFrecuenciaMeses(frecuencia);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_vacuna(?, ?, ?, ?, ?) }");
            stmt.setInt(1, vacuna.getCodigoVacuna());
            stmt.setString(2, vacuna.getNombreVacuna());
            stmt.setString(3, vacuna.getDescripcion());
            stmt.setString(4, vacuna.getDosis());
            stmt.setInt(5, vacuna.getFrecuenciaMeses());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del vacuna debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar vacuna: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarVacuna(ActionEvent event) {
    Vacunas vacunaSeleccionado = TablaVacuna.getSelectionModel().getSelectedItem();
 
    if (vacunaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_vacuna(?) }");
            stmt.setInt(1, vacunaSeleccionado.getCodigoVacuna());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar vacuna: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un vacuna de la tabla para eliminar.");
        }
    }
    
     @FXML
    private void btnRegresar(ActionEvent event ) {
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
    

}


    
