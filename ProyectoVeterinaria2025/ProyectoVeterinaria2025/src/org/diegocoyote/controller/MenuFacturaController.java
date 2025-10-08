
package org.diegocoyote.controller;

import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Facturas;
import org.diegocoyote.report.GenerarReporte;
import org.diegocoyote.system.Principal;

public class MenuFacturaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Facturas> listaFacturas;
    
    @FXML
    private TableColumn<Facturas, Integer> CodigoFactura;
    
    @FXML
    private TableColumn<Facturas, Integer> CodigoCliente;
    
    @FXML
    private TableColumn<Facturas, Integer> CodigoEmpleado;
    
    @FXML
    private TableColumn<Facturas, Double> Total;
    
    @FXML
    private TableColumn<Facturas, LocalDate> FechaEmision;
    
    @FXML
    private TableView<Facturas> TablaFactura;
    
    @FXML
    private TableColumn<Facturas, String> MetodoPago;

    @FXML
    private TextField codigoFacturaField;
    private int obtenerCodigoFacturaSeleccionado() throws Exception {
    String texto = codigoFacturaField.getText();
    if (texto != null && !texto.isEmpty()) {
        return Integer.parseInt(texto);
    } else {
        throw new Exception("Debe ingresar un código de factura.");
    }
    }

    @FXML
    private TextField codigoClienteField;

    @FXML
    private TextField codigoEmpleadoField;

    @FXML
    private TextField totalField;

    @FXML
    private DatePicker fechaEmisionField;

    @FXML
    private TextField metodoPagoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaFactura.setItems (getFacturas()) ;

    CodigoFactura.setCellValueFactory(new PropertyValueFactory<>( "codigoFactura") );
    FechaEmision.setCellValueFactory(new PropertyValueFactory<> ( "fechaEmision") );
    Total.setCellValueFactory(new PropertyValueFactory<>( "total") );
    MetodoPago.setCellValueFactory(new PropertyValueFactory<> ( "metodoPago") );
    CodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente") );
    CodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado") );
    }
   
    
    public void MostrarLosDatos () {
    listaFacturas = FXCollections.observableArrayList() ;

    TablaFactura.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoFacturaField.setText (String.valueOf(newSelection.getCodigoFactura()));
    fechaEmisionField.setValue(newSelection.getFechaEmision());
    totalField.setText(String.valueOf(newSelection.getTotal()));
    metodoPagoField.setText (newSelection.getMetodoPago());
    codigoClienteField.setText (String.valueOf(newSelection.getCodigoCliente()));
    codigoEmpleadoField.setText (String.valueOf(newSelection.getCodigoEmpleado()));
    } else {
    codigoFacturaField.clear ();
    fechaEmisionField.setValue(null);
    totalField.clear ();
    metodoPagoField.clear ();
    codigoClienteField.clear ();
    codigoEmpleadoField.clear ();
        }
    });  
}
    
    public ObservableList<Facturas> buscarFacturaPorCodigo(int codigo) {
        ObservableList<Facturas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_factura(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Facturas(
                        rs.getInt("codigoFactura"),
                        rs.getDate("fechaEmision").toLocalDate(),                      
                        rs.getDouble("total"),
                        rs.getString("metodoPago"),
                        rs.getInt("codigoCliente"),
                        rs.getInt("codigoEmpleado")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar factura: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Facturas> getFacturas() {
        ObservableList<Facturas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_factura()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Facturas(
                          rs.getInt("codigoFactura"),
                        rs.getDate("fechaEmision").toLocalDate(),                      
                        rs.getDouble("total"),
                        rs.getString("metodoPago"),
                        rs.getInt("codigoCliente"),
                        rs.getInt("codigoEmpleado")
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
        codigoFacturaField.clear();
        fechaEmisionField.setValue(null);
        totalField.clear();
        metodoPagoField.clear();
        codigoClienteField.clear();
        codigoEmpleadoField.clear();
        TablaFactura.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoFacturaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoFacturaField.getText());
                TablaFactura.setItems(buscarFacturaPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de factura para buscar.");
        }
    }
    
    
    @FXML
void handleButtonPressAgregarFactura(ActionEvent event) {
    LocalDate fechaEmision = fechaEmisionField.getValue();
    String totalTexto = totalField.getText().trim();
    String metodoPago = metodoPagoField.getText().trim();
    String codigoClienteTexto = codigoClienteField.getText().trim();
    String codigoEmpleadoTexto = codigoEmpleadoField.getText().trim();

    // Validación de campos vacíos
    if (fechaEmision == null || totalTexto.isEmpty() || metodoPago.isEmpty() ||
        codigoClienteTexto.isEmpty() || codigoEmpleadoTexto.isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    double total;
    int codigoCliente;
    int codigoEmpleado;

    // Validación de números
    try {
        total = Double.parseDouble(totalTexto);
        codigoCliente = Integer.parseInt(codigoClienteTexto);
        codigoEmpleado = Integer.parseInt(codigoEmpleadoTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("El total debe ser un número válido y los códigos deben ser enteros.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Factura
    Facturas factura = new Facturas();
    factura.setFechaEmision(fechaEmision);
    factura.setTotal(total);
    factura.setMetodoPago(metodoPago);
    factura.setCodigoCliente(codigoCliente);
    factura.setCodigoEmpleado(codigoEmpleado);

    // Registrar factura en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_factura(?, ?, ?, ?, ?) }");
        stmt.setDate(1, java.sql.Date.valueOf(factura.getFechaEmision()));
        stmt.setDouble(2, factura.getTotal());
        stmt.setString(3, factura.getMetodoPago());
        stmt.setInt(4, factura.getCodigoCliente());
        stmt.setInt(5, factura.getCodigoEmpleado());
        stmt.execute();

        cargarDatos(); // Refrescar vista o tabla
        System.out.println("Factura registrada exitosamente.");

    } catch (Exception e) {
        System.err.println("Error al registrar factura: " + e.getMessage());
        e.printStackTrace();

    }
}

    @FXML
    void handleButtonPressEditarFactura(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoFacturaField.getText().trim());
            LocalDate fechaEmision = fechaEmisionField.getValue();
            String total = totalField.getText().trim();
            String metodoPago = metodoPagoField.getText().trim();
            int codigoCliente = Integer.parseInt(codigoClienteField.getText().trim());
            int codigoEmpleado = Integer.parseInt(codigoEmpleadoField.getText().trim());
 
            
        if ( total.isEmpty() ||
             metodoPago.isEmpty()) {
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
 
            Facturas factura = new Facturas();
            factura.setCodigoFactura(codigo); // <-- Aquí corregido
            factura.setFechaEmision(fechaEmision);
            factura.setTotal(totalP);
            factura.setMetodoPago(metodoPago);
            factura.setCodigoCliente(codigoCliente);
            factura.setCodigoEmpleado(codigoEmpleado);
        
        
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_factura(?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, factura.getCodigoFactura());
            stmt.setDate(2, java.sql.Date.valueOf(factura.getFechaEmision()));
            stmt.setDouble(3, factura.getTotal());
            stmt.setString(4, factura.getMetodoPago());
            stmt.setInt(5, factura.getCodigoCliente());
            stmt.setInt(6, factura.getCodigoEmpleado());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Factura actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del factura debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar factura: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarFactura(ActionEvent event) {
    Facturas facturaSeleccionado = TablaFactura.getSelectionModel().getSelectedItem();
 
    if (facturaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_factura(?) }");
            stmt.setInt(1, facturaSeleccionado.getCodigoFactura());
            stmt.execute();
 
            System.out.println("Factura eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un factura de la tabla para eliminar.");
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
        InputStream subReporteEmpleadoStream = GenerarReporte.class.getResourceAsStream("/org/diegocoyote/report/SubReporte_Empleado.jasper");
        InputStream subReporteClienteStream = GenerarReporte.class.getResourceAsStream("/org/diegocoyote/report/SubReporte_Cliente.jasper");
        
        if (subReporteEmpleadoStream == null) {
            throw new RuntimeException("No se encontro SubReporteEmpleado.jasper");
        }
        
        if (subReporteClienteStream == null) {
            throw new RuntimeException("No se encontro SubReporte_Cliente.jasper");
        }
        
       
        
        try {
            int codigoFacturaSeleccionado = obtenerCodigoFacturaSeleccionado(); 

        parametros.put("codigoFactura", codigoFacturaSeleccionado);

            InputStream hoja = getClass().getResourceAsStream("/org/diegocoyote/image/hoja_membretada.jpg");
            if (hoja == null) {
                System.out.println("No se encontro la Imagen");
            }else{
                parametros.put("HojaMembretada",hoja);
            }
            
             JasperReport subReporteEmpleado = (JasperReport) JRLoader.loadObject(subReporteEmpleadoStream);
             JasperReport subReporteCliente = (JasperReport) JRLoader.loadObject(subReporteClienteStream);
             
             parametros.put("SubEmpleado", subReporteEmpleado );
             parametros.put("SubCliente", subReporteCliente );
            
            
            
                GenerarReporte.mostrarReporte("/org/diegocoyote/report/ReporteFactura.jasper","Reporte de Factura",parametros);

        } catch (Exception e) {
            System.out.println("Error al cargar la imagen o generar reporte");
            e.printStackTrace();
        }
        //x
    }
    
}


    
