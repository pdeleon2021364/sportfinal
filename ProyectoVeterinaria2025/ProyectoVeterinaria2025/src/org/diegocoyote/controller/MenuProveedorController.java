
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
import org.diegocoyote.models.Proveedores;
import org.diegocoyote.system.Principal;

public class MenuProveedorController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Proveedores> listaProveedores;
    
    @FXML
    private TableColumn<Proveedores, Integer> CodigoProveedor;
    
    @FXML
    private TableColumn<Proveedores, String> CorreoProveedor;
    
    @FXML
    private TableColumn<Proveedores, String> DireccionProveedor;
    
    @FXML
    private TableColumn<Proveedores, String> NombreProveedor;
    
    @FXML
    private TableView<Proveedores> TablaProveedor;
    
    @FXML
    private TableColumn<Proveedores, String> TelefonoProveedor;

    @FXML
    private TextField codigoProveedorField;

    @FXML
    private TextField correoProveedorField;

    @FXML
    private TextField direccionProveedorField;

    @FXML
    private TextField nombreProveedorField;

    @FXML
    private TextField telefonoProveedorField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaProveedor.setItems (getProveedores()) ;

    CodigoProveedor.setCellValueFactory(new PropertyValueFactory<>( "codigoProveedor") );
    NombreProveedor.setCellValueFactory(new PropertyValueFactory<> ( "nombreProveedor") );
    DireccionProveedor.setCellValueFactory(new PropertyValueFactory<>( "direccionProveedor") );
    TelefonoProveedor.setCellValueFactory(new PropertyValueFactory<> ( "telefonoProveedor") );
    CorreoProveedor.setCellValueFactory(new PropertyValueFactory<>("correoProveedor") );
    }
   
    
    public void MostrarLosDatos () {
    listaProveedores = FXCollections.observableArrayList() ;

    TablaProveedor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoProveedorField.setText (String.valueOf(newSelection.getCodigoProveedor()));
    nombreProveedorField.setText ( newSelection. getNombreProveedor () );
    direccionProveedorField.setText (newSelection.getDireccionProveedor ());
    telefonoProveedorField.setText ( newSelection.getTelefonoProveedor ());
    correoProveedorField.setText (newSelection.getCorreoProveedor ());
    } else {
    codigoProveedorField.clear ();
    nombreProveedorField.clear ();
    direccionProveedorField.clear ();
    telefonoProveedorField.clear ();
    correoProveedorField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Proveedores> buscarProveedorPorCodigo(int codigo) {
        ObservableList<Proveedores> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_proveedor(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Proveedores(
                        rs.getInt("codigoProveedor"),
                        rs.getString("nombreProveedor"),
                        rs.getString("direccionProveedor"),
                        rs.getString("telefonoProveedor"),
                        rs.getString("correoProveedor")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ObservableList<Proveedores> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_proveedor()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Proveedores(
                        rs.getInt("codigoProveedor"),
                        rs.getString("nombreProveedor"),
                        rs.getString("direccionProveedor"),
                        rs.getString("telefonoProveedor"),
                        rs.getString("correoProveedor")
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
        codigoProveedorField.clear();
        nombreProveedorField.clear();
        direccionProveedorField.clear();
        telefonoProveedorField.clear();
        correoProveedorField.clear();
        TablaProveedor.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoProveedorField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoProveedorField.getText());
                TablaProveedor.setItems(buscarProveedorPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de proveedor para buscar.");
        }
    }
    
    @FXML
    void handleButtonPressAgregarProveedor(ActionEvent event) {
        String nombres = nombreProveedorField.getText().trim();
        String direccion = direccionProveedorField.getText().trim();
        String telefono = telefonoProveedorField.getText().trim();
        String correo = correoProveedorField.getText().trim();

        if (nombres.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    // Crear objeto proveedor
    Proveedores proveedor = new Proveedores();
    proveedor.setNombreProveedor(nombres);
    proveedor.setDireccionProveedor(direccion);
    proveedor.setTelefonoProveedor(telefono);
    proveedor.setCorreoProveedor(correo);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_proveedor(?, ?, ?, ?) }");
        stmt.setString(1, proveedor.getNombreProveedor());
        stmt.setString(2, proveedor.getDireccionProveedor());
        stmt.setString(3, proveedor.getTelefonoProveedor());
        stmt.setString(4, proveedor.getCorreoProveedor());
        stmt.execute();

        cargarDatos(); // Actualizar tabla
        System.out.println("Proveedor registrado exitosamente.");

    } catch (Exception e) {
        System.out.println("Error al registrar proveedor: " + e.getMessage());
        e.printStackTrace(); // Solo durante desarrollo
    }
}

@FXML
    void handleButtonPressEditarProveedor(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoProveedorField.getText().trim());
            String nombres = nombreProveedorField.getText().trim();
            String direccion = direccionProveedorField.getText().trim();
            String telefono = telefonoProveedorField.getText().trim();
            String correo = correoProveedorField.getText().trim();
 
            if (telefono.length() > 8) {
                System.out.println("Error: El número de teléfono no debe exceder los 8 caracteres.");
                return;
            }
 
            if (nombres.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || correo.isEmpty()) {
                System.out.println("Error: Todos los campos deben estar llenos.");
                return;
            }
 
            Proveedores proveedor = new Proveedores();
            proveedor.setCodigoProveedor(codigo); // <-- Aquí corregido
            proveedor.setNombreProveedor(nombres);
            proveedor.setDireccionProveedor(direccion);
            proveedor.setTelefonoProveedor(telefono);
            proveedor.setCorreoProveedor(correo);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_proveedor(?, ?, ?, ?, ?) }");
            stmt.setInt(1, proveedor.getCodigoProveedor());
            stmt.setString(2, proveedor.getNombreProveedor());
            stmt.setString(3, proveedor.getDireccionProveedor()); 
            stmt.setString(4, proveedor.getTelefonoProveedor()); 
            stmt.setString(5, proveedor.getCorreoProveedor());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del proveedor debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar proveedor: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarProveedor(ActionEvent event) {
    Proveedores proveedorSeleccionado = TablaProveedor.getSelectionModel().getSelectedItem();
 
    if (proveedorSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_proveedor(?) }");
            stmt.setInt(1, proveedorSeleccionado.getCodigoProveedor());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un proveedor de la tabla para eliminar.");
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


    
