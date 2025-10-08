
package org.diegocoyote.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Empleados;
import org.diegocoyote.system.Principal;

public class MenuEmpleadoController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Empleados> listaEmpleados;
    
    @FXML
    private TableColumn<Empleados, Integer> CodigoEmpleado;
    
    @FXML
    private TableColumn<Empleados, String> TelefonoEmpleado;
    
    @FXML
    private TableColumn<Empleados, String> ApellidoEmpleado;
    
    @FXML
    private TableColumn<Empleados, String> NombreEmpleado;
    
    @FXML
    private TableColumn<Empleados, String> CorreoEmpleado;
    
    @FXML
    private TableView<Empleados> TablaEmpleado;
    
    @FXML
    private TableColumn<Empleados, String> Cargo;

    @FXML
    private TextField codigoEmpleadoField;

    @FXML
    private TextField telefonoEmpleadoField;

    @FXML
    private TextField apellidoEmpleadoField;

    @FXML
    private TextField nombreEmpleadoField;

    @FXML
    private TextField cargoField;
    
    @FXML
    private TextField correoEmpleadoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaEmpleado.setItems (getEmpleados()) ;

    CodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>( "codigoEmpleado") );
    NombreEmpleado.setCellValueFactory(new PropertyValueFactory<> ( "nombreEmpleado") );
    ApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<>( "apellidoEmpleado") );
    Cargo.setCellValueFactory(new PropertyValueFactory<> ( "cargo") );
    TelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<>("telefonoEmpleado") );
    CorreoEmpleado.setCellValueFactory(new PropertyValueFactory<>("correoEmpleado") );
    }
   
    
    public void MostrarLosDatos () {
    listaEmpleados = FXCollections.observableArrayList() ;

    TablaEmpleado.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoEmpleadoField.setText (String.valueOf(newSelection.getCodigoEmpleado()));
    nombreEmpleadoField.setText ( newSelection. getNombreEmpleado () );
    apellidoEmpleadoField.setText (newSelection.getApellidoEmpleado ());
    cargoField.setText ( newSelection.getCargo ());
    telefonoEmpleadoField.setText (newSelection.getTelefonoEmpleado ());
    correoEmpleadoField.setText (newSelection.getCorreoEmpleado ());
    } else {
    codigoEmpleadoField.clear ();
    nombreEmpleadoField.clear ();
    apellidoEmpleadoField.clear ();
    cargoField.clear ();
    telefonoEmpleadoField.clear ();
    correoEmpleadoField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Empleados> buscarEmpleadoPorCodigo(int codigo) {
        ObservableList<Empleados> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_empleado(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Empleados(
                        rs.getInt("codigoEmpleado"),
                        rs.getString("nombreEmpleado"),
                        rs.getString("apellidoEmpleado"),
                        rs.getString("cargo"),
                        rs.getString("telefonoEmpleado"),
                        rs.getString("correoEmpleado")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Empleados> getEmpleados() {
        ObservableList<Empleados> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_empleado()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Empleados(
                         rs.getInt("codigoEmpleado"),
                        rs.getString("nombreEmpleado"),
                        rs.getString("apellidoEmpleado"),
                        rs.getString("cargo"),
                        rs.getString("telefonoEmpleado"),
                        rs.getString("correoEmpleado")
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
        codigoEmpleadoField.clear();
        nombreEmpleadoField.clear();
        apellidoEmpleadoField.clear();
        cargoField.clear();
        telefonoEmpleadoField.clear();
        correoEmpleadoField.clear();
        TablaEmpleado.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoEmpleadoField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoEmpleadoField.getText());
                TablaEmpleado.setItems(buscarEmpleadoPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de empleado para buscar.");
        }
    }
    
    @FXML
void handleButtonPressAgregarEmpleado(ActionEvent event) {
    String nombres = nombreEmpleadoField.getText().trim();
    String apellido = apellidoEmpleadoField.getText().trim();
    String cargo = cargoField.getText().trim();
    String telefono = telefonoEmpleadoField.getText().trim();
    String correo = correoEmpleadoField.getText().trim();

    // Validar campos vacíos
    if (nombres.isEmpty() || apellido.isEmpty() ||
        cargo.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Empleados
    Empleados empleado = new Empleados();
    empleado.setNombreEmpleado(nombres);
    empleado.setApellidoEmpleado(apellido);
    empleado.setCargo(cargo);
    empleado.setTelefonoEmpleado(telefono);
    empleado.setCorreoEmpleado(correo);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_empleado(?, ?, ?, ?, ?) }");
        stmt.setString(1, empleado.getNombreEmpleado());
        stmt.setString(2, empleado.getApellidoEmpleado());
        stmt.setString(3, empleado.getCargo());
        stmt.setString(4, empleado.getTelefonoEmpleado());
        stmt.setString(5, empleado.getCorreoEmpleado());
        stmt.execute();

        cargarDatos(); // Actualizar vista o tabla
        System.out.println("Empleado registrado exitosamente.");

    } catch (Exception e) {
        System.out.println("Error al registrar empleado: " + e.getMessage());
        e.printStackTrace();
    }
}
@FXML
    void handleButtonPressEditarEmpleado(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoEmpleadoField.getText().trim());
            String nombres = nombreEmpleadoField.getText().trim();
            String apellido = apellidoEmpleadoField.getText().trim();
            String cargo = cargoField.getText().trim();
            String telefono = telefonoEmpleadoField.getText().trim();
            String correo = correoEmpleadoField.getText().trim();
 
            if (telefono.length() > 8) {
                System.out.println("Error: El número de teléfono no debe exceder los 8 caracteres.");
                return;
            }
 
            if (nombres.isEmpty() || apellido.isEmpty() ||
            cargo.isEmpty() || telefono.isEmpty() ||
            correo.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
 
            Empleados empleado = new Empleados();
            empleado.setCodigoEmpleado(codigo); // <-- Aquí corregido
            empleado.setNombreEmpleado(nombres);
            empleado.setApellidoEmpleado(apellido);
            empleado.setCargo(cargo);
            empleado.setTelefonoEmpleado(telefono);
            empleado.setCorreoEmpleado(correo);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_empleado(?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, empleado.getCodigoEmpleado());
            stmt.setString(2, empleado.getNombreEmpleado());
            stmt.setString(3, empleado.getApellidoEmpleado());
            stmt.setString(4, empleado.getCargo());
            stmt.setString(5, empleado.getTelefonoEmpleado());
            stmt.setString(6, empleado.getCorreoEmpleado());
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del empleado debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar empleado: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarEmpleado(ActionEvent event) {
    Empleados empleadoSeleccionado = TablaEmpleado.getSelectionModel().getSelectedItem();
 
    if (empleadoSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_empleado(?) }");
            stmt.setInt(1, empleadoSeleccionado.getCodigoEmpleado());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un empleado de la tabla para eliminar.");
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


    
