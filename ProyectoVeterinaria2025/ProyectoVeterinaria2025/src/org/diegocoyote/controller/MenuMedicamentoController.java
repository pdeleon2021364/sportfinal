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
import org.diegocoyote.models.Medicamentos;
import org.diegocoyote.system.Principal;

public class MenuMedicamentoController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Medicamentos> listaMedicamentos;
    
    @FXML
    private TableColumn<Medicamentos, Integer> CodigoMedicamento;
    
    @FXML
    private TableColumn<Medicamentos, Integer> CodigoProveedor;
    
    @FXML
    private TableColumn<Medicamentos, Double> PrecioUnitario;
    
    @FXML
    private TableColumn<Medicamentos, String> Descripcion;
    
    @FXML
    private TableColumn<Medicamentos, String> NombreMedicamento;
    
    @FXML
    private TableColumn<Medicamentos, LocalDate> FechaVencimiento;
    
    @FXML
    private TableView<Medicamentos> TablaMedicamento;
    
    @FXML
    private TableColumn<Medicamentos, Integer> Stock;

    @FXML
    private TextField codigoMedicamentoField;

    @FXML
    private TextField codigoProveedorField;

    @FXML
    private TextField precioUnitarioField;

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField nombreMedicamentoField;

    @FXML
    private TextField stockField;
    
    @FXML
    private DatePicker fechaVencimientoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaMedicamento.setItems (getMedicamentos()) ;

    CodigoMedicamento.setCellValueFactory(new PropertyValueFactory<>( "codigoMedicamento") );
    NombreMedicamento.setCellValueFactory(new PropertyValueFactory<> ( "nombreMedicamento") );
    Descripcion.setCellValueFactory(new PropertyValueFactory<>( "descripcion") );
    Stock.setCellValueFactory(new PropertyValueFactory<> ( "stock") );
    PrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario") );
    FechaVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento") );
    CodigoProveedor.setCellValueFactory(new PropertyValueFactory<>( "codigoProveedor") );

    }
   
    
    public void MostrarLosDatos () {
    listaMedicamentos = FXCollections.observableArrayList() ;

    TablaMedicamento.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoMedicamentoField.setText (String.valueOf(newSelection.getCodigoMedicamento()));
    nombreMedicamentoField.setText ( newSelection. getNombreMedicamento () );
    descripcionField.setText (newSelection.getDescripcion ());
    stockField.setText (String.valueOf( newSelection.getStock ()));
    precioUnitarioField.setText (String.valueOf(newSelection.getPrecioUnitario ()));
    fechaVencimientoField.setValue(newSelection.getFechaVencimiento());
    codigoProveedorField.setText (String.valueOf(newSelection.getCodigoProveedor()));

    } else {
    codigoMedicamentoField.clear ();
    nombreMedicamentoField.clear ();
    descripcionField.clear ();
    stockField.clear ();
    precioUnitarioField.clear ();
    fechaVencimientoField.setValue(null);
    codigoProveedorField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Medicamentos> buscarMedicamentoPorCodigo(int codigo) {
        ObservableList<Medicamentos> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_medicamento(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Medicamentos(
                        rs.getInt("codigoMedicamento"),
                        rs.getString("nombreMedicamento"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getDouble("precioUnitario"),
                        rs.getDate("fechaVencimiento").toLocalDate(),                      
                        rs.getInt("codigoProveedor")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar medicamento: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Medicamentos> getMedicamentos() {
        ObservableList<Medicamentos> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_medicamento()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Medicamentos(
                         rs.getInt("codigoMedicamento"),
                        rs.getString("nombreMedicamento"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getDouble("precioUnitario"),
                        rs.getDate("fechaVencimiento").toLocalDate(),                      
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
        codigoMedicamentoField.clear();
        nombreMedicamentoField.clear();
        descripcionField.clear();
        stockField.clear();
        precioUnitarioField.clear();
        fechaVencimientoField.setValue(null);
        codigoProveedorField.clear();
        TablaMedicamento.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoMedicamentoField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoMedicamentoField.getText());
                TablaMedicamento.setItems(buscarMedicamentoPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de medicamento para buscar.");
        }
    }
    
    
    @FXML
    void handleButtonPressAgregarMedicamento(ActionEvent event) {
        String nombres = nombreMedicamentoField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        String stockTexto = stockField.getText().trim();
        String precioUnitarioTexto = precioUnitarioField.getText().trim();
        LocalDate fechaVencimiento = fechaVencimientoField.getValue();
        String codigoProveedorTexto = codigoProveedorField.getText().trim();

    // Validación de campos vacíos
    if (nombres.isEmpty() || descripcion.isEmpty() || stockTexto.isEmpty() ||
        precioUnitarioTexto.isEmpty() || fechaVencimiento == null || codigoProveedorTexto.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int stock;
    int codigoProveedor;
    double precioUnitario;

    // Validar campos numéricos
    try {
        stock = Integer.parseInt(stockTexto);
        codigoProveedor = Integer.parseInt(codigoProveedorTexto);
        precioUnitario = Double.parseDouble(precioUnitarioTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Stock, código de proveedor y precio unitario deben ser valores numéricos válidos.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Medicamento
    Medicamentos medicamento = new Medicamentos();
    medicamento.setNombreMedicamento(nombres);
    medicamento.setDescripcion(descripcion);
    medicamento.setStock(stock);
    medicamento.setPrecioUnitario(precioUnitario);
    medicamento.setFechaVencimiento(fechaVencimiento);
    medicamento.setCodigoProveedor(codigoProveedor);

    // Registrar medicamento en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_medicamento(?, ?, ?, ?, ?, ?) }");
        stmt.setString(1, medicamento.getNombreMedicamento());
        stmt.setString(2, medicamento.getDescripcion());
        stmt.setInt(3, medicamento.getStock());
        stmt.setDouble(4, medicamento.getPrecioUnitario());
        stmt.setDate(5, java.sql.Date.valueOf(medicamento.getFechaVencimiento()));
        stmt.setInt(6, medicamento.getCodigoProveedor());
        stmt.execute();

        cargarDatos(); // Refrescar vista o tabla

        System.out.println("Medicamento registrado exitosamente.");
    } catch (Exception e) {
        System.err.println("Error al registrar medicamento: " + e.getMessage());
        e.printStackTrace();

    }
}
    @FXML
    void handleButtonPressEditarMedicamento(ActionEvent event) {
        try {
                int codigo = Integer.parseInt(codigoMedicamentoField.getText().trim());
            String nombres = nombreMedicamentoField.getText().trim();
            String descripcion = descripcionField.getText().trim();
                int stock = Integer.parseInt(stockField.getText().trim());
            String precioUnitario = precioUnitarioField.getText().trim();
             LocalDate fechaVencimiento = fechaVencimientoField.getValue();
                int codigoProveedor = Integer.parseInt(codigoProveedorField.getText().trim());
 
           
             if (nombres.isEmpty() || descripcion.isEmpty() ||
            precioUnitario.isEmpty() ) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
        
             double precioUni;
             try {
            precioUni = Double.parseDouble(precioUnitario);
                } catch (NumberFormatException e) {
                System.out.println("Error: El precio debe ser un número válido.");
                return;
                }
 
            Medicamentos medicamento = new Medicamentos();
            medicamento.setCodigoMedicamento(codigo); // <-- Aquí corregido
            medicamento.setNombreMedicamento(nombres);
            medicamento.setDescripcion(descripcion);
            medicamento.setStock(stock);
            medicamento.setPrecioUnitario(precioUni);
            medicamento.setFechaVencimiento(fechaVencimiento);
            medicamento.setCodigoProveedor(codigoProveedor);
 
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_medicamento(?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, medicamento.getCodigoMedicamento());
             stmt.setString(2, medicamento.getNombreMedicamento());
            stmt.setString(3, medicamento.getDescripcion());
            stmt.setInt(4, medicamento.getStock());
            stmt.setDouble(5, medicamento.getPrecioUnitario());
            stmt.setDate(6, java.sql.Date.valueOf(medicamento.getFechaVencimiento()));
            stmt.setInt(7, medicamento.getCodigoProveedor());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del medicamento debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarMedicamento(ActionEvent event) {
    Medicamentos medicamentoSeleccionado = TablaMedicamento.getSelectionModel().getSelectedItem();
 
    if (medicamentoSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_medicamento(?) }");
            stmt.setInt(1, medicamentoSeleccionado.getCodigoMedicamento());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un medicamento de la tabla para eliminar.");
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


    
