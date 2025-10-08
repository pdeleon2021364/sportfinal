
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
import org.diegocoyote.models.Consultas;
import org.diegocoyote.system.Principal;

public class MenuConsultaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Consultas> listaConsultas;
    
    @FXML
    private TableColumn<Consultas, Integer> CodigoConsulta;
    
    @FXML
    private TableColumn<Consultas, Integer> CodigoMascota;
    
    @FXML
    private TableColumn<Consultas, Integer> CodigoVeterinario;
    
    @FXML
    private TableColumn<Consultas, String> Observaciones;
    
    @FXML
    private TableColumn<Consultas, String> Motivo;
    
    @FXML
    private TableColumn<Consultas, LocalDate> FechaConsulta;
    
    @FXML
    private TableView<Consultas> TablaConsulta;
    
    @FXML
    private TableColumn<Consultas, String> Diagnostico;

    @FXML
    private TextField codigoConsultaField;

    @FXML
    private TextField codigoMascotaField;

    @FXML
    private TextField codigoVeterinarioField;

    @FXML
    private TextField observacionesField;

    @FXML
    private TextField motivoField;

    @FXML
    private DatePicker fechaConsultaField;

    @FXML
    private TextField diagnosticoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaConsulta.setItems (getConsultas()) ;

    CodigoConsulta.setCellValueFactory(new PropertyValueFactory<>( "codigoConsulta") );
    FechaConsulta.setCellValueFactory(new PropertyValueFactory<> ( "fechaConsulta") );
    Motivo.setCellValueFactory(new PropertyValueFactory<>( "motivo") );
    Diagnostico.setCellValueFactory(new PropertyValueFactory<> ( "diagnostico") );
    Observaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones") );
    CodigoMascota.setCellValueFactory(new PropertyValueFactory<>("codigoMascota") );
    CodigoVeterinario.setCellValueFactory(new PropertyValueFactory<>("codigoVeterinario") );
    }
   
    
    public void MostrarLosDatos () {
    listaConsultas = FXCollections.observableArrayList() ;

    TablaConsulta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoConsultaField.setText (String.valueOf(newSelection.getCodigoConsulta()));
    fechaConsultaField.setValue(newSelection.getFechaConsulta());
    motivoField.setText (newSelection.getMotivo ());
    diagnosticoField.setText ( newSelection.getDiagnostico ());
    observacionesField.setText (newSelection.getObservaciones ());
    codigoMascotaField.setText (String.valueOf(newSelection.getCodigoMascota()));
    codigoVeterinarioField.setText (String.valueOf(newSelection.getCodigoVeterinario()));

    } else {
    codigoConsultaField.clear ();
    fechaConsultaField.setValue(null);
    motivoField.clear ();
    diagnosticoField.clear ();
    observacionesField.clear ();
    codigoMascotaField.clear ();
    codigoVeterinarioField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Consultas> buscarConsultaPorCodigo(int codigo) {
        ObservableList<Consultas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_consulta(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Consultas(
                        rs.getInt("codigoConsulta"),
                         rs.getDate("fechaConsulta").toLocalDate(),                      
                        rs.getString("motivo"),
                        rs.getString("diagnostico"),
                        rs.getString("observaciones"),
                        rs.getInt("codigoMascota"),
                        rs.getInt("codigoVeterinario")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar consulta: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Consultas> getConsultas() {
        ObservableList<Consultas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_consulta()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Consultas(
                       rs.getInt("codigoConsulta"),
                         rs.getDate("fechaConsulta").toLocalDate(),                      
                        rs.getString("motivo"),
                        rs.getString("diagnostico"),
                        rs.getString("observaciones"),
                        rs.getInt("codigoMascota"),
                        rs.getInt("codigoVeterinario")
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
        codigoConsultaField.clear();
         fechaConsultaField.setValue(null);
        motivoField.clear();
        diagnosticoField.clear();
        observacionesField.clear();
        codigoMascotaField.clear();
        codigoVeterinarioField.clear();
        TablaConsulta.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoConsultaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoConsultaField.getText());
                TablaConsulta.setItems(buscarConsultaPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de consulta para buscar.");
        }
    }
   
   @FXML
void handleButtonPressAgregarConsulta(ActionEvent event) {
    LocalDate fechaConsulta = fechaConsultaField.getValue();
    String motivo = motivoField.getText().trim();
    String diagnostico = diagnosticoField.getText().trim();
    String observaciones = observacionesField.getText().trim();
    String codigoMascotaTexto = codigoMascotaField.getText().trim();
    String codigoVeterinarioTexto = codigoVeterinarioField.getText().trim();

    // Validar campos vacíos o nulos
    if (fechaConsulta == null || motivo.isEmpty() || diagnostico.isEmpty() ||
        observaciones.isEmpty() || codigoMascotaTexto.isEmpty() || codigoVeterinarioTexto.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int codigoMascota, codigoVeterinario;
    try {
        codigoMascota = Integer.parseInt(codigoMascotaTexto);
        codigoVeterinario = Integer.parseInt(codigoVeterinarioTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Codigo de Mascota y Veterinario deben de ser numeros validos.");
        alert.showAndWait();
        return;
    }

    // Crear objeto consulta
    Consultas consulta = new Consultas();
    consulta.setFechaConsulta(fechaConsulta);
    consulta.setMotivo(motivo);
    consulta.setDiagnostico(diagnostico);
    consulta.setObservaciones(observaciones);
    consulta.setCodigoMascota(codigoMascota);
    consulta.setCodigoVeterinario(codigoVeterinario);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_consulta(?, ?, ?, ?, ?, ?) }");
        stmt.setDate(1, java.sql.Date.valueOf(consulta.getFechaConsulta()));
        stmt.setString(2, consulta.getMotivo());
        stmt.setString(3, consulta.getDiagnostico());
        stmt.setString(4, consulta.getObservaciones());
        stmt.setInt(5, consulta.getCodigoMascota());
        stmt.setInt(6, consulta.getCodigoVeterinario());
        stmt.execute();

        cargarDatos(); // Recargar tabla o vista
        System.out.println("Consulta registrada exitosamente.");

    } catch (Exception e) {
        System.out.println("Error al registrar consulta: " + e.getMessage());
        e.printStackTrace(); // Solo para debugging
    }
}

    
    @FXML
    void handleButtonPressEditarConsulta(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoConsultaField.getText().trim());
             LocalDate fechaConsulta = fechaConsultaField.getValue();
            String motivo = motivoField.getText().trim();
            String diagnostico = diagnosticoField.getText().trim();
            String observaciones = observacionesField.getText().trim();
            int codigoMascota = Integer.parseInt(codigoMascotaField.getText().trim());
            int codigoVeterinario = Integer.parseInt(codigoVeterinarioField.getText().trim());
 
           
             if ( motivo.isEmpty() ||
            observaciones.isEmpty() || diagnostico.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
        }
 
            Consultas consulta = new Consultas();
            consulta.setCodigoConsulta(codigo); // <-- Aquí corregido
            consulta.setFechaConsulta(fechaConsulta);
            consulta.setMotivo(motivo);
            consulta.setDiagnostico(diagnostico);
            consulta.setObservaciones(observaciones);
            consulta.setCodigoMascota(codigoMascota);
            consulta.setCodigoVeterinario(codigoVeterinario);
 
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_consulta(?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, consulta.getCodigoConsulta());
            stmt.setDate(2, java.sql.Date.valueOf(consulta.getFechaConsulta()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setString(4, consulta.getDiagnostico());
            stmt.setString(5, consulta.getObservaciones());
            stmt.setInt(6, consulta.getCodigoMascota());
            stmt.setInt(7, consulta.getCodigoVeterinario());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Consulta actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del consulta debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar consulta: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarConsulta(ActionEvent event) {
    Consultas consultaSeleccionado = TablaConsulta.getSelectionModel().getSelectedItem();
 
    if (consultaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_consulta(?) }");
            stmt.setInt(1, consultaSeleccionado.getCodigoConsulta());
            stmt.execute();
 
            System.out.println("Consulta eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar consulta: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un consulta de la tabla para eliminar.");
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


    
