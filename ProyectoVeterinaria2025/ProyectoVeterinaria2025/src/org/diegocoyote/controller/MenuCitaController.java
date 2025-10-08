
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
import org.diegocoyote.models.Citas;
import org.diegocoyote.system.Principal;

public class MenuCitaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Citas> listaCitas;
    
    @FXML
    private TableColumn<Citas, Integer> CodigoCita;
    
    @FXML
    private TableColumn<Citas, Integer> CodigoMascota;
    
    @FXML
    private TableColumn<Citas, Integer> CodigoVeterinario;
    
    @FXML
    private TableColumn<Citas, String> Estado;
    
    @FXML
    private TableColumn<Citas, String> HoraCita;
    
    @FXML
    private TableColumn<Citas, LocalDate> FechaCita;
        
    @FXML
    private TableView<Citas> TablaCita;
    
    @FXML
    private TableColumn<Citas, String> Motivo;

    @FXML
    private TextField codigoCitaField;

    @FXML
    private TextField codigoMascotaField;

    @FXML
    private TextField codigoVeterinarioField;

    @FXML
    private TextField estadoField;

    @FXML
    private TextField horaCitaField;

    @FXML
    private DatePicker fechaCitaField;

    @FXML
    private TextField motivoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaCita.setItems (getCitas()) ;

    CodigoCita.setCellValueFactory(new PropertyValueFactory<>( "codigoCita") );
    FechaCita.setCellValueFactory(new PropertyValueFactory<> ( "fechaCita") );
    HoraCita.setCellValueFactory(new PropertyValueFactory<>( "horaCita") );
    Motivo.setCellValueFactory(new PropertyValueFactory<> ( "motivo") );
    Estado.setCellValueFactory(new PropertyValueFactory<>("estado") );
    CodigoMascota.setCellValueFactory(new PropertyValueFactory<>("codigoMascota") );
    CodigoVeterinario.setCellValueFactory(new PropertyValueFactory<>("codigoVeterinario") );
    }
   
    
    public void MostrarLosDatos () {
    listaCitas = FXCollections.observableArrayList() ;

    TablaCita.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoCitaField.setText (String.valueOf(newSelection.getCodigoCita()));
    fechaCitaField.setValue(newSelection.getFechaCita());
    horaCitaField.setText(newSelection.getHoraCita().toString());
    motivoField.setText ( newSelection.getMotivo());
    estadoField.setText (newSelection.getEstado ());
    codigoMascotaField.setText (String.valueOf(newSelection.getCodigoMascota()));
    codigoVeterinarioField.setText (String.valueOf(newSelection.getCodigoVeterinario()));
    } else {
    codigoCitaField.clear ();
    fechaCitaField.setValue(null);
    horaCitaField.clear ();
    motivoField.clear ();
    estadoField.clear ();
    codigoMascotaField.clear ();
    codigoVeterinarioField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Citas> buscarCitaPorCodigo(int codigo) {
        ObservableList<Citas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_cita(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Citas(
                        rs.getInt("codigoCita"),
                        rs.getDate("fechaCita").toLocalDate(),                      
                        rs.getString("horaCita"),
                        rs.getString("motivo"),
                        rs.getString("estado"),
                        rs.getInt("codigoMascota"),
                        rs.getInt("codigoVeterinario")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar cita: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Citas> getCitas() {
        ObservableList<Citas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_cita()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Citas(
                         rs.getInt("codigoCita"),
                        rs.getDate("fechaCita").toLocalDate(),                      
                        rs.getString("horaCita"),
                        rs.getString("motivo"),
                        rs.getString("estado"),
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
        codigoCitaField.clear();
        fechaCitaField.setValue(null);
        horaCitaField.clear();
        motivoField.clear();
        estadoField.clear();
        codigoMascotaField.clear();
        codigoVeterinarioField.clear();
        TablaCita.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoCitaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoCitaField.getText());
                TablaCita.setItems(buscarCitaPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de cita para buscar.");
        }
    }
    
    @FXML
    void handleButtonPressAgregarCita(ActionEvent event) {
        LocalDate fechaCita = fechaCitaField.getValue();
        String horaCita = horaCitaField.getText().trim();
        String motivo = motivoField.getText().trim();
        String estado = estadoField.getText().trim();
        String codigoMascotaTexto = codigoMascotaField.getText().trim();
        String codigoVeterinarioTexto = codigoVeterinarioField.getText().trim();

    // Validación de campos vacíos
    if (fechaCita == null || horaCita.isEmpty() || motivo.isEmpty() || estado.isEmpty() ||
        codigoMascotaTexto.isEmpty() || codigoVeterinarioTexto.isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int codigoMascota;
    int codigoVeterinario;

    try {
        codigoMascota = Integer.parseInt(codigoMascotaTexto);
        codigoVeterinario = Integer.parseInt(codigoVeterinarioTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Los códigos de mascota y veterinario deben ser números enteros válidos.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Cita
    Citas cita = new Citas();
    cita.setFechaCita(fechaCita);
    cita.setHoraCita(horaCita);
    cita.setMotivo(motivo);
    cita.setEstado(estado);
    cita.setCodigoMascota(codigoMascota);
    cita.setCodigoVeterinario(codigoVeterinario);

    // Registrar cita en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_cita(?, ?, ?, ?, ?, ?) }");
        stmt.setDate(1, java.sql.Date.valueOf(cita.getFechaCita()));
        stmt.setString(2, cita.getHoraCita());
        stmt.setString(3, cita.getMotivo());
        stmt.setString(4, cita.getEstado());
        stmt.setInt(5, cita.getCodigoMascota());
        stmt.setInt(6, cita.getCodigoVeterinario());
        stmt.execute();

        cargarDatos(); // Refrescar tabla o vista
        System.out.println("Cita registrada exitosamente.");
        
    } catch (Exception e) {
        System.err.println("Error al registrar cita: " + e.getMessage());
        e.printStackTrace();

        
    }
}

@FXML
    void handleButtonPressEditarCita(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoCitaField.getText().trim());
            LocalDate fechaCita = fechaCitaField.getValue();
            String horaCita = horaCitaField.getText().trim();
            String motivo = motivoField.getText().trim();
            String estado = estadoField.getText().trim();
            int codigoMascota = Integer.parseInt(codigoMascotaField.getText().trim());
            int codigoVeterinario = Integer.parseInt(codigoVeterinarioField.getText().trim());

            
 
            if (horaCita.isEmpty() || motivo.isEmpty() || estado.isEmpty()) {
                System.out.println("Error: Todos los campos deben estar llenos.");
                return;
            }
 
            Citas cita = new Citas();
            cita.setCodigoCita(codigo); // <-- Aquí corregido
            cita.setFechaCita(fechaCita);
            cita.setHoraCita(horaCita);
            cita.setMotivo(motivo);
            cita.setEstado(estado);
            cita.setCodigoMascota(codigoMascota);
            cita.setCodigoVeterinario(codigoVeterinario);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_cita(?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, cita.getCodigoCita());
            stmt.setDate(2, java.sql.Date.valueOf(cita.getFechaCita()));
            stmt.setString(3, cita.getHoraCita());
            stmt.setString(4, cita.getMotivo());
            stmt.setString(5, cita.getEstado());
            stmt.setInt(6, cita.getCodigoMascota());
            stmt.setInt(7, cita.getCodigoVeterinario());
            stmt.execute();
            cargarDatos();
            System.out.println("Cita actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del cita debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar cita: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarCita(ActionEvent event) {
    Citas citaSeleccionado = TablaCita.getSelectionModel().getSelectedItem();
 
    if (citaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_cita(?) }");
            stmt.setInt(1, citaSeleccionado.getCodigoCita());
            stmt.execute();
 
            System.out.println("Cita eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un cita de la tabla para eliminar.");
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


    
    