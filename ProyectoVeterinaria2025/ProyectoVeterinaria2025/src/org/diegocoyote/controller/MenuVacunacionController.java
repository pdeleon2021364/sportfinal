
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
import org.diegocoyote.db.Conexion;
import org.diegocoyote.models.Vacunaciones;
import org.diegocoyote.report.GenerarReporte;
import org.diegocoyote.system.Principal;

public class MenuVacunacionController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Vacunaciones> listaVacunaciones;
    
    @FXML
    private TableColumn<Vacunaciones, Integer> CodigoVacunacion;
    
    @FXML
    private TableColumn<Vacunaciones, Integer> CodigoVeterinario;
    
    @FXML
    private TableColumn<Vacunaciones, Integer> CodigoVacuna;
    
    @FXML
    private TableColumn<Vacunaciones, String> Observaciones;
    
    @FXML
    private TableColumn<Vacunaciones, LocalDate> FechaAplicacion;
    
    @FXML
    private TableView<Vacunaciones> TablaVacunacion;
    
    @FXML
    private TableColumn<Vacunaciones, Integer> CodigoMascota;

    @FXML
    private TextField codigoVacunacionField;

    @FXML
    private TextField codigoVeterinarioField;

    @FXML
    private TextField codigoVacunaField;

    @FXML
    private TextField observacionesField;

    @FXML
    private DatePicker fechaAplicacionField;

    @FXML
    private TextField codigoMascotaField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaVacunacion.setItems (getVacunaciones()) ;

    CodigoVacunacion.setCellValueFactory(new PropertyValueFactory<>( "codigoVacunacion") );
    FechaAplicacion.setCellValueFactory(new PropertyValueFactory<> ( "fechaAplicacion") );
    Observaciones.setCellValueFactory(new PropertyValueFactory<>( "observaciones") );
    CodigoMascota.setCellValueFactory(new PropertyValueFactory<> ( "codigoMascota") );
    CodigoVacuna.setCellValueFactory(new PropertyValueFactory<>("codigoVacuna") );
    CodigoVeterinario.setCellValueFactory(new PropertyValueFactory<>("codigoVeterinario") );
    }
   
    
    public void MostrarLosDatos () {
    listaVacunaciones = FXCollections.observableArrayList() ;

    TablaVacunacion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoVacunacionField.setText (String.valueOf(newSelection.getCodigoVacunacion()));
    fechaAplicacionField.setValue(newSelection.getFechaAplicacion());
    observacionesField.setText(newSelection.getObservaciones());
    codigoMascotaField.setText (String.valueOf(newSelection.getCodigoMascota()));
    codigoVacunaField.setText (String.valueOf(newSelection.getCodigoVacuna()));
    codigoVeterinarioField.setText (String.valueOf(newSelection.getCodigoVeterinario()));
    } else {
    codigoVacunacionField.clear ();
    fechaAplicacionField.setValue(null);
    observacionesField.clear ();
    codigoMascotaField.clear ();
    codigoVacunaField.clear ();
    codigoVeterinarioField.clear ();
        }
    });  
}
    
    public ObservableList<Vacunaciones> buscarVacunacionPorCodigo(int codigo) {
        ObservableList<Vacunaciones> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_vacunacion(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Vacunaciones(
                        rs.getInt("codigoVacunacion"),
                        rs.getDate("fechaAplicacion").toLocalDate(),                      
                        rs.getString("observaciones"),
                        rs.getInt("codigoMascota"),
                        rs.getInt("codigoVacuna"),
                        rs.getInt("codigoVeterinario")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar vacunacion: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Vacunaciones> getVacunaciones() {
        ObservableList<Vacunaciones> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_vacunacion()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Vacunaciones(
                         rs.getInt("codigoVacunacion"),
                        rs.getDate("fechaAplicacion").toLocalDate(),                      
                        rs.getString("observaciones"),
                        rs.getInt("codigoMascota"),
                        rs.getInt("codigoVacuna"),
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
        codigoVacunacionField.clear();
        fechaAplicacionField.setValue(null);
        observacionesField.clear();
        codigoMascotaField.clear();
        codigoVacunaField.clear();
        codigoVeterinarioField.clear();
        TablaVacunacion.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoVacunacionField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoVacunacionField.getText());
                TablaVacunacion.setItems(buscarVacunacionPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de vacunacion para buscar.");
        }
    }
    
    @FXML
void handleButtonPressAgregarVacunacion(ActionEvent event) {
    LocalDate fechaAplicacion = fechaAplicacionField.getValue();
    String observaciones = observacionesField.getText().trim();
    String codigoMascotaTexto = codigoMascotaField.getText().trim();
    String codigoVacunaTexto = codigoVacunaField.getText().trim();
    String codigoVeterinarioTexto = codigoVeterinarioField.getText().trim();

    // Validación de campos vacíos
    if (fechaAplicacion == null || observaciones.isEmpty() ||
        codigoMascotaTexto.isEmpty() || codigoVacunaTexto.isEmpty() || codigoVeterinarioTexto.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    int codigoMascota;
    int codigoVacuna;
    int codigoVeterinario;

    // Validación de valores numéricos
    try {
        codigoMascota = Integer.parseInt(codigoMascotaTexto);
        codigoVacuna = Integer.parseInt(codigoVacunaTexto);
        codigoVeterinario = Integer.parseInt(codigoVeterinarioTexto);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Los códigos de mascota, vacuna y veterinario deben ser números enteros válidos.");
        alert.showAndWait();
        return;
    }

    // Crear objeto Vacunacion
    Vacunaciones vacunacion = new Vacunaciones();
    vacunacion.setFechaAplicacion(fechaAplicacion);
    vacunacion.setObservaciones(observaciones);
    vacunacion.setCodigoMascota(codigoMascota);
    vacunacion.setCodigoVacuna(codigoVacuna);
    vacunacion.setCodigoVeterinario(codigoVeterinario);

    // Registrar vacunación en la base de datos
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_vacunacion(?, ?, ?, ?, ?) }");
        stmt.setDate(1, java.sql.Date.valueOf(vacunacion.getFechaAplicacion()));
        stmt.setString(2, vacunacion.getObservaciones());
        stmt.setInt(3, vacunacion.getCodigoMascota());
        stmt.setInt(4, vacunacion.getCodigoVacuna());
        stmt.setInt(5, vacunacion.getCodigoVeterinario());
        stmt.execute();

        cargarDatos(); // Refrescar tabla o vista
        System.out.println("Vacunación registrada exitosamente.");

    } catch (Exception e) {
        System.err.println("Error al registrar vacunación: " + e.getMessage());
        e.printStackTrace();

       
    }
}

    @FXML
    void handleButtonPressEditarVacunacion(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoVacunacionField.getText().trim());
            LocalDate fechaAplicacion = fechaAplicacionField.getValue();
            String observaciones = observacionesField.getText().trim();
            int codigoMascota = Integer.parseInt(codigoMascotaField.getText().trim());
            int codigoVacuna = Integer.parseInt(codigoVacunaField.getText().trim());
            int codigoVeterinario = Integer.parseInt(codigoVeterinarioField.getText().trim());
            
             if (observaciones.isEmpty()) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
           }
 
            
            Vacunaciones vacunacion = new Vacunaciones();
            vacunacion.setCodigoVacunacion(codigo); // <-- Aquí corregido
            vacunacion.setFechaAplicacion(fechaAplicacion);
            vacunacion.setObservaciones(observaciones);
            vacunacion.setCodigoMascota(codigoMascota);
            vacunacion.setCodigoVacuna(codigoVacuna);
            vacunacion.setCodigoVeterinario(codigoVeterinario);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_vacunacion(?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, vacunacion.getCodigoVacunacion());
            stmt.setDate(2, java.sql.Date.valueOf(vacunacion.getFechaAplicacion()));
            stmt.setString(3, vacunacion.getObservaciones());
            stmt.setInt(4, vacunacion.getCodigoMascota());
            stmt.setInt(5, vacunacion.getCodigoVacuna());
            stmt.setInt(6, vacunacion.getCodigoVeterinario());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Vacunacion actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del vacunacion debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar vacunacion: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    
    @FXML
    void handleButtonPressEliminarVacunacion(ActionEvent event) {
    Vacunaciones vacunacionSeleccionado = TablaVacunacion.getSelectionModel().getSelectedItem();
 
    if (vacunacionSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_vacunacion(?) }");
            stmt.setInt(1, vacunacionSeleccionado.getCodigoVacunacion());
            stmt.execute();
 
            System.out.println("Vacunacion eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar vacunacion: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un vacunacion de la tabla para eliminar.");
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
        try {
            InputStream hoja = getClass().getResourceAsStream("/org/diegocoyote/image/hoja_membretada.jpg");
            if (hoja == null) {
                System.out.println("No se encontro la Imagen");
            }else{
                parametros.put("HojaMembretada",hoja);
            }
            
                GenerarReporte.mostrarReporte("/org/diegocoyote/report/ReporteCarneDeVacunacion.jasper","Reporte de Carne de Vacunacion",parametros);

        } catch (Exception e) {
            System.out.println("Error al cargar la imagen o generar reporte");
            e.printStackTrace();
        }
        //parametros.put("codigoCliente",null);
    }
}


    
