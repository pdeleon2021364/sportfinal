
package org.diegocoyote.controller;

import java.io.IOException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Clientes;
import org.diegocoyote.report.GenerarReporte;
import org.diegocoyote.system.Principal;

public class MenuClienteController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Clientes> listaClientes;
    
    @FXML
    private TableColumn<Clientes, Integer> CodigoCliente;
    @FXML
    private TableColumn<Clientes, String> DireccionCliente;
    @FXML
    private TableColumn<Clientes, String> ApellidoCliente;
    @FXML
    private TableColumn<Clientes, String> NombreCliente;
    @FXML
    private TableColumn<Clientes, String> EmailCliente;
    @FXML
    private TableColumn<Clientes, LocalDate> FechaRegistro;
    @FXML
    private TableView<Clientes> TablaCliente;
    @FXML
    private TableColumn<Clientes, String> TelefonoCliente;

    @FXML
    private TextField codigoClienteField;

    @FXML
    private TextField telefonoClienteField;

    @FXML
    private TextField apellidoClienteField;

    @FXML
    private TextField nombreClienteField;

    @FXML
    private TextField direccionClienteField;

    @FXML
    private TextField emailClienteField;

    @FXML
    private DatePicker fechaRegistroField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaCliente.setItems (getClientes()) ;

    CodigoCliente.setCellValueFactory(new PropertyValueFactory<>( "codigoCliente") );
    NombreCliente.setCellValueFactory(new PropertyValueFactory<> ( "nombreCliente") );
    ApellidoCliente.setCellValueFactory(new PropertyValueFactory<>( "apellidoCliente") );
    TelefonoCliente.setCellValueFactory(new PropertyValueFactory<> ( "telefonoCliente") );
    DireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente") );
    EmailCliente.setCellValueFactory(new PropertyValueFactory<>("emailCliente") );
    FechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro") );
    }
   
    
    public void MostrarLosDatos () {
    listaClientes = FXCollections.observableArrayList() ;

    TablaCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoClienteField.setText (String.valueOf(newSelection.getCodigoCliente()));
    nombreClienteField.setText ( newSelection. getNombreCliente () );
    apellidoClienteField.setText (newSelection.getApellidoCliente ());
    telefonoClienteField.setText ( newSelection.getTelefonoCliente ());
    direccionClienteField.setText (newSelection.getDireccionCliente ());
    emailClienteField.setText (newSelection.getEmailCliente ());
    fechaRegistroField.setValue(newSelection.getFechaRegistro());
    } else {
    codigoClienteField.clear ();
    nombreClienteField.clear ();
    apellidoClienteField.clear ();
    direccionClienteField.clear ();
    telefonoClienteField.clear ();
    emailClienteField.clear ();
    fechaRegistroField.setValue(null);
        }
    });  
}

    
    
    
    public ObservableList<Clientes> buscarClientePorCodigo(int codigo) {
        ObservableList<Clientes> lista = FXCollections.observableArrayList();
        
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_cliente(?) }");
            stmt.setInt(1, codigo); 
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Clientes(
                        rs.getInt("codigoCliente"),
                        rs.getString("nombreCliente"),
                        rs.getString("apellidoCliente"),
                        rs.getString("telefonoCliente"),
                        rs.getString("direccionCliente"),
                        rs.getString("emailCliente"),
                         rs.getDate("fechaRegistro").toLocalDate()                     
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Clientes> getClientes() {
        ObservableList<Clientes> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_cliente()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Clientes(
                        rs.getInt("codigoCliente"),
                        rs.getString("nombreCliente"),
                        rs.getString("apellidoCliente"),
                        rs.getString("telefonoCliente"),
                        rs.getString("direccionCliente"),
                        rs.getString("emailCliente"),
                         rs.getDate("fechaRegistro").toLocalDate()                      
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
        codigoClienteField.clear();
        nombreClienteField.clear();
        apellidoClienteField.clear();
        telefonoClienteField.clear();
        direccionClienteField.clear();
        emailClienteField.clear ();
        fechaRegistroField.setValue(null);
        TablaCliente.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoClienteField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoClienteField.getText());
                TablaCliente.setItems(buscarClientePorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de cliente para buscar.");
        }
    }
    
    @FXML
    void handleButtonPressAgregarCliente(ActionEvent event) {
    String nombres = nombreClienteField.getText().trim();
    String apellido = apellidoClienteField.getText().trim();
    String telefono = telefonoClienteField.getText().trim();
    String direccion = direccionClienteField.getText().trim();
    String email = emailClienteField.getText().trim();
    LocalDate fechaRegistro = fechaRegistroField.getValue();

    // Validación de campos vacíos
    if (nombres.isEmpty() || apellido.isEmpty() ||
        telefono.isEmpty() || direccion.isEmpty() ||
        email.isEmpty() || fechaRegistro == null) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }


    // Crear objeto Cliente
    Clientes cliente = new Clientes();
    cliente.setNombreCliente(nombres);
    cliente.setApellidoCliente(apellido);
    cliente.setTelefonoCliente(telefono);
    cliente.setDireccionCliente(direccion);
    cliente.setEmailCliente(email);
    cliente.setFechaRegistro(fechaRegistro);

    // Registrar cliente en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_cliente(?, ?, ?, ?, ?, ?) }");
        stmt.setString(1, cliente.getNombreCliente());
        stmt.setString(2, cliente.getApellidoCliente());
        stmt.setString(3, cliente.getTelefonoCliente());
        stmt.setString(4, cliente.getDireccionCliente());
        stmt.setString(5, cliente.getEmailCliente());
        stmt.setDate(6, java.sql.Date.valueOf(cliente.getFechaRegistro()));
        stmt.execute();

        cargarDatos(); // Refrescar tabla o vista
        System.out.println("Cliente registrado exitosamente.");

    } catch (Exception e) {
            System.err.println("Error al Registrar cliente: " + e.getMessage());
            e.printStackTrace();        e.printStackTrace(); 
    }
}
    @FXML
    void handleButtonPressEditarCliente(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoClienteField.getText().trim());
            String nombres = nombreClienteField.getText().trim();
            String apellido = apellidoClienteField.getText().trim();
            String telefono = telefonoClienteField.getText().trim();
            String direccion = direccionClienteField.getText().trim();
            String email = emailClienteField.getText().trim();
            LocalDate fechaRegistro = fechaRegistroField.getValue();
 
            if (apellido.length() > 8) {
                System.out.println("Error: El número de teléfono no debe exceder los 8 caracteres.");
                return;
            }
 
            if (nombres.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                System.out.println("Error: Todos los campos deben estar llenos.");
                return;
            }
 
            Clientes cliente = new Clientes();
            cliente.setCodigoCliente(codigo); // <-- Aquí corregido
            cliente.setNombreCliente(nombres);
            cliente.setApellidoCliente(apellido);
            cliente.setTelefonoCliente(telefono);
            cliente.setDireccionCliente(direccion);
            cliente.setEmailCliente(email);
            cliente.setFechaRegistro(fechaRegistro);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_cliente(?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, cliente.getCodigoCliente());
            stmt.setString(2, cliente.getNombreCliente());
            stmt.setString(3, cliente.getApellidoCliente()); 
            stmt.setString(4, cliente.getTelefonoCliente()); 
            stmt.setString(5, cliente.getDireccionCliente());
            stmt.setString(6, cliente.getEmailCliente());
            stmt.setDate(7, java.sql.Date.valueOf(cliente.getFechaRegistro()));
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del cliente debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar cliente: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarCliente(ActionEvent event) {
    Clientes clienteSeleccionado = TablaCliente.getSelectionModel().getSelectedItem();
 
    if (clienteSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_cliente(?) }");
            stmt.setInt(1, clienteSeleccionado.getCodigoCliente());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un cliente de la tabla para eliminar.");
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
        //parametros.put("codigoCliente",null);
        GenerarReporte.mostrarReporte("ReporteClientes2.jasper","Reporte de Clientes",parametros);
    }
}


    
