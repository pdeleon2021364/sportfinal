
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
import org.diegocoyote.models.Mascotas;
import org.diegocoyote.system.Principal;

public class MenuMascotaController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private ObservableList <Mascotas> listaMascotas;
    
    @FXML
    private TableColumn<Mascotas, Integer> CodigoMascota;
    
    @FXML
    private TableColumn<Mascotas, Integer> CodigoCliente;
    
    @FXML
    private TableColumn<Mascotas, String> Sexo;
    
    @FXML
    private TableColumn<Mascotas, String> Especie;
    
    @FXML
    private TableColumn<Mascotas, String> NombreMascota;
    
    @FXML
    private TableColumn<Mascotas, String> Color;
    
    @FXML
    private TableColumn<Mascotas, Double> PesoActualKg;
    
    @FXML
    private TableColumn<Mascotas, LocalDate> FechaNacimiento;
    
    @FXML
    private TableView<Mascotas> TablaMascota;
    
    @FXML
    private TableColumn<Mascotas, String> Raza;

    @FXML
    private TextField codigoMascotaField;

    @FXML
    private TextField codigoClienteField;

    @FXML
    private TextField sexoField;

    @FXML
    private TextField colorField;

    @FXML
    private TextField pesoActualKgField;

    @FXML
    private TextField especieField;

    @FXML
    private TextField nombreMascotaField;

    @FXML
    private TextField razaField;
    
    @FXML
    private DatePicker fechaNacimientoField;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        cargarDatos();
        MostrarLosDatos();
    }
    
    public void cargarDatos () {
    TablaMascota.setItems (getMascotas()) ;

    CodigoMascota.setCellValueFactory(new PropertyValueFactory<>( "codigoMascota") );
    NombreMascota.setCellValueFactory(new PropertyValueFactory<> ( "nombreMascota") );
    Especie.setCellValueFactory(new PropertyValueFactory<>( "especie") );
    Raza.setCellValueFactory(new PropertyValueFactory<> ( "raza") );
    Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo") );
    FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento") );
    Color.setCellValueFactory(new PropertyValueFactory<>("color") );
    PesoActualKg.setCellValueFactory(new PropertyValueFactory<>("pesoActualKg") );
    CodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente") );
    }
   
    
    public void MostrarLosDatos () {
    listaMascotas = FXCollections.observableArrayList() ;

    TablaMascota.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    codigoMascotaField.setText (String.valueOf(newSelection.getCodigoMascota()));
    nombreMascotaField.setText ( newSelection. getNombreMascota () );
    especieField.setText (newSelection.getEspecie ());
    razaField.setText ( newSelection.getRaza ());
    sexoField.setText (newSelection.getSexo ());
    fechaNacimientoField.setValue(newSelection.getFechaNacimiento());
    colorField.setText (newSelection.getColor ());
    pesoActualKgField.setText (String.valueOf(newSelection.getPesoActualKg ()));
    codigoClienteField.setText (String.valueOf(newSelection.getCodigoCliente ()));

    } else {
    codigoMascotaField.clear ();
    nombreMascotaField.clear ();
    especieField.clear ();
    razaField.clear ();
    sexoField.clear ();
    fechaNacimientoField.setValue(null);
    colorField.clear ();
    pesoActualKgField.clear ();
    codigoClienteField.clear ();
        }
    });  
}

    
    
    
    public ObservableList<Mascotas> buscarEmpleadoPorCodigo(int codigo) {
        ObservableList<Mascotas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_buscar_mascota(?) }");
            stmt.setInt(1, codigo); // Aquí mandas el código
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                lista.add(new Mascotas(
                        rs.getInt("codigoMascota"),
                        rs.getString("nombreMascota"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getString("sexo"),
                         rs.getDate("fechaNacimiento").toLocalDate(),                      
                        rs.getString("color"),
                        rs.getDouble("pesoActualKg"),
                        rs.getInt("codigoCliente")
                    )
                );
            }
 
        } catch (SQLException e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
        }
        return lista;
    }
    
    public ObservableList<Mascotas> getMascotas() {
        ObservableList<Mascotas> lista = FXCollections.observableArrayList();
 
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listar_mascota()}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Mascotas(
                       rs.getInt("codigoMascota"),
                        rs.getString("nombreMascota"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getString("sexo"),
                         rs.getDate("fechaNacimiento").toLocalDate(),                      
                        rs.getString("color"),
                        rs.getDouble("pesoActualKg"),
                        rs.getInt("codigoCliente")
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
        codigoMascotaField.clear();
        nombreMascotaField.clear();
        especieField.clear();
        razaField.clear();
        sexoField.clear();
        fechaNacimientoField.setValue(null);
        colorField.clear();
        pesoActualKgField.clear();
        codigoClienteField.clear();
        TablaMascota.getSelectionModel().clearSelection();
                cargarDatos();
    }
 
    @FXML
    void handleButtonPressBuscar(ActionEvent event) {
        if (!codigoMascotaField.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoMascotaField.getText());
                TablaMascota.setItems(buscarEmpleadoPorCodigo(codigo));
            } catch (NumberFormatException e) {
                System.err.println("El código debe ser un número entero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, ingrese un código de mascota para buscar.");
        }
    }
    
    @FXML
    void handleButtonPressAgregarMascota(ActionEvent event) {
    String nombres = nombreMascotaField.getText().trim();
    String especie = especieField.getText().trim();
    String raza = razaField.getText().trim();
    String sexo = sexoField.getText().trim();
    LocalDate fechaNacimiento = fechaNacimientoField.getValue();
    String color = colorField.getText().trim();
    String pesoActualKg = pesoActualKgField.getText().trim();
    String codigoClienteText = codigoClienteField.getText().trim();

    // Verificar campos vacíos
    if (nombres.isEmpty() || especie.isEmpty() || raza.isEmpty() || sexo.isEmpty() ||
        fechaNacimiento == null || color.isEmpty() || pesoActualKg.isEmpty() || codigoClienteText.isEmpty()) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos antes de continuar.");
        alert.showAndWait();
        return;
    }

    double pesoKg;
    int codigoCliente;

    try {
        pesoKg = Double.parseDouble(pesoActualKg);
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("El Peso debe de ser un numero valido.");
        alert.showAndWait();
        return;
    }

    try {
        codigoCliente = Integer.parseInt(codigoClienteText);
    } catch (NumberFormatException e) {   
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Formato");
        alert.setHeaderText(null);
        alert.setContentText("Codigo de Cliente debe de ser numeros validos.");
        alert.showAndWait();
    return;
    }

    // Crear objeto Mascotas
    Mascotas mascota = new Mascotas();
    mascota.setNombreMascota(nombres);
    mascota.setEspecie(especie);
    mascota.setRaza(raza);
    mascota.setSexo(sexo);
    mascota.setFechaNacimiento(fechaNacimiento);
    mascota.setColor(color);
    mascota.setPesoActualKg(pesoKg);
    mascota.setCodigoCliente(codigoCliente);

    // Ejecutar procedimiento almacenado
    try {
        Connection conn = Conexion.getInstancia().getConexion();
        CallableStatement stmt = conn.prepareCall("{ call sp_agregar_mascota(?, ?, ?, ?, ?, ?, ?, ?) }");
        stmt.setString(1, mascota.getNombreMascota());
        stmt.setString(2, mascota.getEspecie());
        stmt.setString(3, mascota.getRaza());
        stmt.setString(4, mascota.getSexo());
        stmt.setDate(5, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
        stmt.setString(6, mascota.getColor());
        stmt.setDouble(7, mascota.getPesoActualKg());
        stmt.setInt(8, mascota.getCodigoCliente());
        stmt.execute();
        
        cargarDatos(); // Recargar tabla o vista
    } catch (Exception e) {
        System.err.println("Error al Agregar mascota: " + e.getMessage());
            e.printStackTrace();
    }
}
@FXML
    void handleButtonPressEditarMascota(ActionEvent event) {
        try {
            int codigo = Integer.parseInt(codigoMascotaField.getText().trim());
            String nombres = nombreMascotaField.getText().trim();
            String especie = especieField.getText().trim();
            String raza = razaField.getText().trim();
            String sexo = sexoField.getText().trim();
            LocalDate fechaNacimiento = fechaNacimientoField.getValue();
            String color = colorField.getText().trim();
            String pesoActualKg = pesoActualKgField.getText().trim();
            int codigoCliente = Integer.parseInt(codigoClienteField.getText().trim());
 
            if (nombres.isEmpty() || especie.isEmpty() ||
            raza.isEmpty() || sexo.isEmpty() || 
            color.isEmpty() || pesoActualKg.isEmpty() ) {
            System.out.println("Error: Todos los campos deben estar llenos.");
            return;
            }
            
            double pesoKg;
    try {
        pesoKg = Double.parseDouble(pesoActualKg);
    } catch (NumberFormatException e) {
        System.out.println("Error: El peso debe ser un número válido.");
        return;
    }
            
 
            Mascotas mascota = new Mascotas();
            mascota.setCodigoMascota(codigo); // <-- Aquí corregido
            mascota.setNombreMascota(nombres);
            mascota.setEspecie(especie);
            mascota.setRaza(raza);
            mascota.setSexo(sexo);
            mascota.setFechaNacimiento(fechaNacimiento);
            mascota.setColor(color);
            mascota.setPesoActualKg(pesoKg);
            mascota.setCodigoCliente(codigoCliente);
 
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_actualizar_mascota(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            stmt.setInt(1, mascota.getCodigoMascota());
            stmt.setString(2, mascota.getNombreMascota());
            stmt.setString(3, mascota.getEspecie());
            stmt.setString(4, mascota.getRaza());
            stmt.setString(5, mascota.getSexo());
            stmt.setDate(6, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
            stmt.setString(7, mascota.getColor());
            stmt.setDouble(8, mascota.getPesoActualKg());
            stmt.setInt(9, mascota.getCodigoCliente());
 
            stmt.execute();
            cargarDatos();
            System.out.println("Proveedor actualizado exitosamente.");
 
        } catch (NumberFormatException e) {
            System.err.println("Error: El código del mascota debe ser un número.");
        } catch (Exception e) {
            System.err.println("Error al editar mascota: " + e.getMessage());
            e.printStackTrace();
        }
 
    }
    @FXML
    void handleButtonPressEliminarMascota(ActionEvent event) {
    Mascotas mascotaSeleccionado = TablaMascota.getSelectionModel().getSelectedItem();
 
    if (mascotaSeleccionado != null) {
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{ call sp_eliminar_mascota(?) }");
            stmt.setInt(1, mascotaSeleccionado.getCodigoMascota());
            stmt.execute();
 
            System.out.println("Proveedor eliminado exitosamente.");
            cargarDatos();
        } catch (Exception e) {
            System.err.println("Error al eliminar mascota: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, seleccione un mascota de la tabla para eliminar.");
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


    
