
package org.diegocoyote.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Tratamientos;
import org.diegocoyote.system.Principal;

public class MenuTratamientoController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Tratamientos> listaTratamientos;
    
    @FXML
    private TableColumn<Tratamientos, Integer> CodigoTratamiento;
    
    @FXML
    private TableColumn<Tratamientos, Integer> CodigoConsulta;
    
    @FXML
    private TableColumn<Tratamientos, String> MedicamentosIndicados;
    
    @FXML
    private TableColumn<Tratamientos, LocalDate> FechaInicio;
    
    @FXML
    private TableColumn<Tratamientos, String> Descripcion;
    
    @FXML
    private TableView<Tratamientos> TablaTratamiento;
    
    @FXML
    private TableColumn<Tratamientos, LocalDate> FechaFin;

    @FXML
    private TextField codigoTratamientoField;

    @FXML
    private TextField codigoConsultaField;

    @FXML
    private TextField medicamentosIndicadosField;

    @FXML
    private DatePicker fechaInicioField;

    @FXML
    private TextField descripcionField;

    @FXML
    private DatePicker fechaFinField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaTratamiento.setItems (getTratamientos()) ;

    CodigoTratamiento.setCellValueFactory(new PropertyValueFactory<>( "codigoTratamiento") );
    Descripcion.setCellValueFactory(new PropertyValueFactory<> ( "descripcion") );
    FechaInicio.setCellValueFactory(new PropertyValueFactory<>( "fechaInicio") );
    FechaFin.setCellValueFactory(new PropertyValueFactory<> ( "fechaFin") );
    MedicamentosIndicados.setCellValueFactory(new PropertyValueFactory<>("medicamentosIndicados") );
    CodigoConsulta.setCellValueFactory(new PropertyValueFactory<>("codigoConsulta") );
    }
   
    
    public void MostrarLosDatos () {
    listaTratamientos = FXCollections.observableArrayList() ;

    TablaTratamiento.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoTratamientoField.setText (String.valueOf(newSelection.getCodigoTratamiento()));
    descripcionField.setText ( newSelection. getDescripcion () );
    fechaInicioField.setValue(newSelection.getFechaInicio());
    fechaFinField.setValue(newSelection.getFechaFin());
    medicamentosIndicadosField.setText (newSelection.getMedicamentosIndicados ());
    codigoConsultaField.setText (String.valueOf(newSelection.getCodigoConsulta()));
    } else {
    codigoTratamientoField.clear ();
    descripcionField.clear ();
    fechaInicioField.setValue(null);
    fechaFinField.setValue(null);
    medicamentosIndicadosField.clear ();
    codigoConsultaField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Tratamientos> buscarTratamientoPorCodigo(int codigo) {
        ObservableList<Tratamientos> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_tratamiento(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Tratamientos(
                        rs.getInt("codigoTratamiento"),
                        rs.getString("descripcion"),
                         rs.getDate("fechaInicio").toLocalDate(),                      
                         rs.getDate("fechaFin").toLocalDate(),                      
                        rs.getString("medicamentosIndicados"),
                        rs.getInt("codigoConsulta")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar tratamiento: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Tratamientos> getTratamientos() {
        ObservableList<Tratamientos> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_tratamiento()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Tratamientos(
                         rs.getInt("codigoTratamiento"),
                        rs.getString("descripcion"),
                         rs.getDate("fechaInicio").toLocalDate(),                      
                         rs.getDate("fechaFin").toLocalDate(),                      
                        rs.getString("medicamentosIndicados"),
                        rs.getInt("codigoConsulta")
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
        codigoTratamientoField.clear();
        descripcionField.clear();
        fechaInicioField.setValue(null);
        fechaFinField.setValue(null);
        medicamentosIndicadosField.clear();
        codigoConsultaField.clear();
        TablaTratamiento.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoTratamientoField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoTratamientoField.getText());
                TablaTratamiento.setItems(buscarTratamientoPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de tratamiento para buscar.");
        }
    }
    
    
    @FXML
    void handleButtonPressAgregarTratamiento(ActionEvent event) {
    String descripcion = descripcionField.getText().trim();
    LocalDate fechaInicio = fechaInicioField.getValue();
    LocalDate fechaFin = fechaFinField.getValue();
    String medicamentosIndicados = medicamentosIndicadosField.getText().trim();
    String codigoConsultaTexto = codigoConsultaField.getText().trim();

    // Validar campos vacíos o nulos
    if (descripcion.isEmpty() || medicamentosIndicados.isEmpty() ||
        fechaInicio == null || fechaFin == null || codigoConsultaTexto.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    // Validar que el código de consulta sea un número
    int codigoConsulta;
    try {
        codigoConsulta = Integer.parseInt(codigoConsultaTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Codigo de Consulta deben de ser numeros validos.");
        alert.showAndWait();
        return;
    }

    // Validar que la fecha de fin no sea antes de la fecha de inicio
    if (fechaFin.isBefore(fechaInicio)) {
        System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio.");
        return;
    }

    // Crear objeto Tratamientos
    Tratamientos tratamiento = new Tratamientos();
    tratamiento.setDescripcion(descripcion);
    tratamiento.setFechaInicio(fechaInicio);
    tratamiento.setFechaFin(fechaFin);
    tratamiento.setMedicamentosIndicados(medicamentosIndicados);
    tratamiento.setCodigoConsulta(codigoConsulta);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_tratamiento(?, ?, ?, ?, ?) }");
        stmt.setString(1, tratamiento.getDescripcion());
        stmt.setDate(2, java.sql.Date.valueOf(tratamiento.getFechaInicio()));
        stmt.setDate(3, java.sql.Date.valueOf(tratamiento.getFechaFin()));
        stmt.setString(4, tratamiento.getMedicamentosIndicados());
        stmt.setInt(5, tratamiento.getCodigoConsulta());
        stmt.execute();

        cargarDatos(); // Recargar tabla o vista
        System.out.println("Tratamiento registrado exitosamente.");

    } catch (Exception e) {
        System.out.println("Error al registrar tratamiento: " + e.getMessage());
        e.printStackTrace(); // Solo para debugging
    }
}

    @FXML
    void handleButtonPressEditarTratamiento(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoTratamientoField.getText().trim());
            String descripcion = descripcionField.getText().trim();
            LocalDate fechaInicio = fechaInicioField.getValue();
            LocalDate fechaFin = fechaFinField.getValue();
            String medicamentosIndicados = medicamentosIndicadosField.getText().trim();
                int codigoConsulta = Integer.parseInt(codigoConsultaField.getText().trim());
 
            
            if (descripcion.isEmpty() ||
            medicamentosIndicados.isEmpty() ) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
        }
 
            Tratamientos tratamiento = new Tratamientos();
            tratamiento.setCodigoTratamiento(codigo); // <-- Aquí corregido
            tratamiento.setDescripcion(descripcion);
            tratamiento.setFechaInicio(fechaInicio);
            tratamiento.setFechaFin(fechaFin);
            tratamiento.setMedicamentosIndicados(medicamentosIndicados);
            tratamiento.setCodigoConsulta(codigoConsulta);
 
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_tratamiento(?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, tratamiento.getCodigoTratamiento());
             stmt.setString(2, tratamiento.getDescripcion());
            stmt.setDate(3, java.sql.Date.valueOf(tratamiento.getFechaInicio()));
            stmt.setDate(4, java.sql.Date.valueOf(tratamiento.getFechaFin()));
            stmt.setString(5, tratamiento.getMedicamentosIndicados());
            stmt.setInt(6, tratamiento.getCodigoConsulta());
            stmt.execute();
            cargarDatos();
            System.out.println("Tratamiento actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del tratamiento debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar tratamiento: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarTratamiento(ActionEvent event) {
    Tratamientos tratamientoSeleccionado = TablaTratamiento.getSelectionModel().getSelectedItem();
 
    if (tratamientoSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_tratamiento(?) }");
            stmt.setInt(1, tratamientoSeleccionado.getCodigoTratamiento());
            stmt.execute();
 
            System.out.println("Tratamiento eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar tratamiento: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un tratamiento de la tabla para eliminar.");
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
}


    
