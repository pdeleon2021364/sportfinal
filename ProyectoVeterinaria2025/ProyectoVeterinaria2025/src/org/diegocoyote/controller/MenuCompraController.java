
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
import org.diegocoyote.models.Compras;
import org.diegocoyote.system.Principal;

public class MenuCompraController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Compras> listaCompras;
    
    @FXML
    private TableColumn<Compras, Integer> CodigoCompra;
    
    @FXML
    private TableColumn<Compras, Integer> CodigoProveedor;
    
    
    @FXML
    private TableColumn<Compras, Double> Total;
    
    @FXML
    private TableColumn<Compras, LocalDate> FechaCompra;
    
    @FXML
    private TableView<Compras> TablaCompra;
    
    @FXML
    private TableColumn<Compras, String> Detalle;

    @FXML
    private TextField codigoCompraField;

    @FXML
    private TextField codigoProveedorField;
    

    @FXML
    private TextField totalField;

    @FXML
    private DatePicker fechaCompraField;

    @FXML
    private TextField detalleField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaCompra.setItems (getCompras()) ;

    CodigoCompra.setCellValueFactory(new PropertyValueFactory<>( "codigoCompra") );
    FechaCompra.setCellValueFactory(new PropertyValueFactory<> ( "fechaCompra") );
    Total.setCellValueFactory(new PropertyValueFactory<>( "total") );
    Detalle.setCellValueFactory(new PropertyValueFactory<> ( "detalle") );
    CodigoProveedor.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor") );
    }
   
    
    public void MostrarLosDatos () {
    listaCompras = FXCollections.observableArrayList() ;

    TablaCompra.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoCompraField.setText (String.valueOf(newSelection.getCodigoCompra()));
    fechaCompraField.setValue(newSelection.getFechaCompra());
    totalField.setText(String.valueOf(newSelection.getTotal()));
    detalleField.setText (newSelection.getDetalle());
    codigoProveedorField.setText (String.valueOf(newSelection.getCodigoProveedor()));
    } else {
    codigoCompraField.clear ();
    fechaCompraField.setValue(null);
    totalField.clear ();
    detalleField.clear ();
    codigoProveedorField.clear ();
        }
    });  
}
    
    public ObservableList<Compras> buscarCompraPorCodigo(int codigo) {
        ObservableList<Compras> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_compra(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Compras(
                        rs.getInt("codigoCompra"),
                        rs.getDate("fechaCompra").toLocalDate(),                      
                        rs.getDouble("total"),
                        rs.getString("detalle"),
                        rs.getInt("codigoProveedor")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar compra: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Compras> getCompras() {
        ObservableList<Compras> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_compra()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Compras(
                          rs.getInt("codigoCompra"),
                        rs.getDate("fechaCompra").toLocalDate(),                      
                        rs.getDouble("total"),
                        rs.getString("detalle"),
                        rs.getInt("codigoProveedor")
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
        codigoCompraField.clear();
        fechaCompraField.setValue(null);
        totalField.clear();
        detalleField.clear();
        codigoProveedorField.clear();
        TablaCompra.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoCompraField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoCompraField.getText());
                TablaCompra.setItems(buscarCompraPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de compra para buscar.");
        }
    }
    
    
    @FXML
void handleButtonPressAgregarCompra(ActionEvent event) {
    LocalDate fechaCompra = fechaCompraField.getValue();
    String totalTexto = totalField.getText().trim();
    String detalle = detalleField.getText().trim();
    String codigoProveedorTexto = codigoProveedorField.getText().trim();

    // Validación de campos vacíos
    if (fechaCompra == null || totalTexto.isEmpty() || detalle.isEmpty() || codigoProveedorTexto.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    double total;
    int codigoProveedor;

    // Validación de valores numéricos
    try {
        total = Double.parseDouble(totalTexto);
        codigoProveedor = Integer.parseInt(codigoProveedorTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("El total debe ser un número válido y el código de proveedor debe ser un número entero.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Compra
    Compras compra = new Compras();
    compra.setFechaCompra(fechaCompra);
    compra.setTotal(total);
    compra.setDetalle(detalle);
    compra.setCodigoProveedor(codigoProveedor);

    // Registrar compra en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_compra(?, ?, ?, ?) }");
        stmt.setDate(1, java.sql.Date.valueOf(compra.getFechaCompra()));
        stmt.setDouble(2, compra.getTotal());
        stmt.setString(3, compra.getDetalle());
        stmt.setInt(4, compra.getCodigoProveedor());
        stmt.execute();

        cargarDatos(); // Refrescar la vista o tabla
        System.out.println("Compra registrada exitosamente.");

    } catch (Exception e) {
        System.err.println("Error al registrar compra: " + e.getMessage());
        e.printStackTrace();

    }
}

    @FXML
    void handleButtonPressEditarCompra(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoCompraField.getText().trim());
            LocalDate fechaCompra = fechaCompraField.getValue();
            String total = totalField.getText().trim();
            String detalle = detalleField.getText().trim();
            int codigoProveedor = Integer.parseInt(codigoProveedorField.getText().trim());
 
            
             if ( total.isEmpty() ||
            detalle.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
        
         double totalP;
            try {
             totalP = Double.parseDouble(total);
            } catch (NumberFormatException e) {
            System.out.println("Error: El peso debe ser un número válido.");
            return;
            }
 
            Compras compra = new Compras();
            compra.setCodigoCompra(codigo); // <-- Aquí corregido
            compra.setFechaCompra(fechaCompra);
            compra.setTotal(totalP);
            compra.setDetalle(detalle);
            compra.setCodigoProveedor(codigoProveedor);
 
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_compra(?, ?, ?, ?, ?) }");
            stmt.setInt(1, compra.getCodigoCompra());
            stmt.setDate(2, java.sql.Date.valueOf(compra.getFechaCompra()));
            stmt.setDouble(3, compra.getTotal());
            stmt.setString(4, compra.getDetalle());
            stmt.setInt(5, compra.getCodigoProveedor());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Compra actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del compra debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar compra: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarCompra(ActionEvent event) {
    Compras compraSeleccionado = TablaCompra.getSelectionModel().getSelectedItem();
 
    if (compraSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_compra(?) }");
            stmt.setInt(1, compraSeleccionado.getCodigoCompra());
            stmt.execute();
 
            System.out.println("Compra eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar compra: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un compra de la tabla para eliminar.");
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


    
