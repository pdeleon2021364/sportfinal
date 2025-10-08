
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
import org.diegocoyote.models.Veterinarios;
import org.diegocoyote.system.Principal;

public class MenuVeterinarioController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Veterinarios> listaVeterinarios;
    
    @FXML
    private TableColumn<Veterinarios, Integer> CodigoVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, String> TelefonoVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, String> ApellidoVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, String> NombreVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, String> CorreoVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, LocalDate> FechaIngreso;
    
    @FXML
    private TableView<Veterinarios> TablaVeterinario;
    
    @FXML
    private TableColumn<Veterinarios, String> Especialidad;
    
    @FXML
    private TableColumn<Veterinarios, String> Estado;

    @FXML
    private TextField codigoVeterinarioField;

    @FXML
    private TextField especialidadField;

    @FXML
    private TextField telefonoVeterinarioField;

    @FXML
    private TextField apellidoVeterinarioField;

    @FXML
    private TextField nombreVeterinarioField;


    @FXML
    private TextField correoVeterinarioField;

    @FXML
    private DatePicker fechaIngresoField;

    @FXML
    private TextField estadoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaVeterinario.setItems (getVeterinarios()) ;

    CodigoVeterinario.setCellValueFactory(new PropertyValueFactory<>( "codigoVeterinario") );
    NombreVeterinario.setCellValueFactory(new PropertyValueFactory<> ( "nombreVeterinario") );
    ApellidoVeterinario.setCellValueFactory(new PropertyValueFactory<>( "apellidoVeterinario") );
    Especialidad.setCellValueFactory(new PropertyValueFactory<> ( "especialidad") );
    TelefonoVeterinario.setCellValueFactory(new PropertyValueFactory<>("telefonoVeterinario") );
    CorreoVeterinario.setCellValueFactory(new PropertyValueFactory<>("correoVeterinario") );
    FechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso") );
    Estado.setCellValueFactory(new PropertyValueFactory<>("estado") );
    }
   
    
    public void MostrarLosDatos () {
    listaVeterinarios = FXCollections.observableArrayList() ;

    TablaVeterinario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoVeterinarioField.setText (String.valueOf(newSelection.getCodigoVeterinario()));
    nombreVeterinarioField.setText ( newSelection. getNombreVeterinario () );
    apellidoVeterinarioField.setText (newSelection.getApellidoVeterinario ());
    especialidadField.setText ( newSelection.getEspecialidad ());
    telefonoVeterinarioField.setText (newSelection.getTelefonoVeterinario ());
    correoVeterinarioField.setText (newSelection.getCorreoVeterinario ());
    fechaIngresoField.setValue(newSelection.getFechaIngreso());
    estadoField.setText(newSelection.getEstado());
    } else {
    codigoVeterinarioField.clear ();
    nombreVeterinarioField.clear ();
    apellidoVeterinarioField.clear ();
    especialidadField.clear ();
    telefonoVeterinarioField.clear ();
    correoVeterinarioField.clear ();
    fechaIngresoField.setValue(null);
    estadoField.clear ()    ;
        }
    });  
}

    
    public ObservableList<Veterinarios> buscarVeterinarioPorCodigo(int codigo) {
        ObservableList<Veterinarios> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_veterinario(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Veterinarios(
                        rs.getInt("codigoVeterinario"),
                        rs.getString("nombreVeterinario"),
                        rs.getString("apellidoVeterinario"),
                        rs.getString("especialidad"),
                        rs.getString("telefonoVeterinario"),
                        rs.getString("correoVeterinario"),
                        rs.getDate("fechaIngreso").toLocalDate(),                      
                        rs.getString("estado")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar veterinario: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Veterinarios> getVeterinarios() {
        ObservableList<Veterinarios> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_veterinario()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Veterinarios(
                           rs.getInt("codigoVeterinario"),
                        rs.getString("nombreVeterinario"),
                        rs.getString("apellidoVeterinario"),
                        rs.getString("especialidad"),
                        rs.getString("telefonoVeterinario"),
                        rs.getString("correoVeterinario"),
                        rs.getDate("fechaIngreso").toLocalDate(),                      
                        rs.getString("estado")
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
        codigoVeterinarioField.clear();
        nombreVeterinarioField.clear();
        apellidoVeterinarioField.clear();
        especialidadField.clear();
        telefonoVeterinarioField.clear();
        correoVeterinarioField.clear ();
        fechaIngresoField.setValue(null);
        estadoField.clear ();
        TablaVeterinario.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoVeterinarioField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoVeterinarioField.getText());
                TablaVeterinario.setItems(buscarVeterinarioPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de veterinario para buscar.");
        }
    }
    
    @FXML
void handleButtonPressAgregarVeterinario(ActionEvent event) {
    String nombres = nombreVeterinarioField.getText().trim();
    String apellido = apellidoVeterinarioField.getText().trim();
    String especialidad = especialidadField.getText().trim();
    String telefono = telefonoVeterinarioField.getText().trim();
    String correo = correoVeterinarioField.getText().trim();
    LocalDate fechaIngreso = fechaIngresoField.getValue();
    String estado = estadoField.getText().trim();

    // Validación de campos vacíos
    if (nombres.isEmpty() || apellido.isEmpty() ||
        especialidad.isEmpty() || telefono.isEmpty() ||
        correo.isEmpty() || estado.isEmpty() || fechaIngreso == null) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Veterinarios
    Veterinarios veterinario = new Veterinarios();
    veterinario.setNombreVeterinario(nombres);
    veterinario.setApellidoVeterinario(apellido);
    veterinario.setEspecialidad(especialidad);
    veterinario.setTelefonoVeterinario(telefono);
    veterinario.setCorreoVeterinario(correo);
    veterinario.setFechaIngreso(fechaIngreso);
    veterinario.setEstado(estado);

    // Registrar en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_veterinario(?, ?, ?, ?, ?, ?, ?) }");
        stmt.setString(1, veterinario.getNombreVeterinario());
        stmt.setString(2, veterinario.getApellidoVeterinario());
        stmt.setString(3, veterinario.getEspecialidad());
        stmt.setString(4, veterinario.getTelefonoVeterinario());
        stmt.setString(5, veterinario.getCorreoVeterinario());
        stmt.setDate(6, java.sql.Date.valueOf(veterinario.getFechaIngreso()));
        stmt.setString(7, veterinario.getEstado());
        stmt.execute();

        cargarDatos(); // Recargar vista o tabla
        System.out.println("Veterinario registrado exitosamente.");

    } catch (Exception e) {
        System.out.println("Error al registrar veterinario: " + e.getMessage());
        e.printStackTrace(); // Solo para debugging
    }
}
@FXML
    void handleButtonPressEditarVeterinario(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoVeterinarioField.getText().trim());
            String nombres = nombreVeterinarioField.getText().trim();
            String apellido = apellidoVeterinarioField.getText().trim();
            String especialidad = especialidadField.getText().trim();
            String telefono = telefonoVeterinarioField.getText().trim();
            String correo = correoVeterinarioField.getText().trim();
            LocalDate fechaIngreso = fechaIngresoField.getValue();
            String estado = estadoField.getText().trim();
 
 
            if (telefono.length() > 8) {
                System.out.println("Error: El número de teléfono no debe exceder los 8 caracteres.");
                return;
            }
 
            if (nombres.isEmpty() || apellido.isEmpty() ||
            especialidad.isEmpty() || telefono.isEmpty() ||
            correo.isEmpty() ||
            estado.isEmpty()){
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
 
            Veterinarios veterinario = new Veterinarios();
            veterinario.setCodigoVeterinario(codigo); // <-- Aquí corregido
            veterinario.setNombreVeterinario(nombres);
            veterinario.setApellidoVeterinario(apellido);
            veterinario.setEspecialidad(especialidad);
            veterinario.setTelefonoVeterinario(telefono);
            veterinario.setCorreoVeterinario(correo);
            veterinario.setFechaIngreso(fechaIngreso);
            veterinario.setEstado(estado);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_veterinario(?, ?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, veterinario.getCodigoVeterinario());
            stmt.setString(2, veterinario.getNombreVeterinario());
            stmt.setString(3, veterinario.getApellidoVeterinario());
            stmt.setString(4, veterinario.getEspecialidad());
            stmt.setString(5, veterinario.getTelefonoVeterinario());
            stmt.setString(6, veterinario.getCorreoVeterinario());
            stmt.setDate(7, java.sql.Date.valueOf(veterinario.getFechaIngreso()));
            stmt.setString(8, veterinario.getEstado());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del veterinario debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar veterinario: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarVeterinario(ActionEvent event) {
    Veterinarios veterinarioSeleccionado = TablaVeterinario.getSelectionModel().getSelectedItem();
 
    if (veterinarioSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_veterinario(?) }");
            stmt.setInt(1, veterinarioSeleccionado.getCodigoVeterinario());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar veterinario: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un veterinario de la tabla para eliminar.");
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


    
