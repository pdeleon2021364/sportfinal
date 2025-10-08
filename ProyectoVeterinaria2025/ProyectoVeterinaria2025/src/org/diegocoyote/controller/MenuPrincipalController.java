package org.diegocoyote.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;

public class MenuPrincipalController {
    
     @FXML
    private void btnMascota(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuMascota.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Mascota");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnCliente(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuCliente.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Cliente");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
    
     @FXML
    private void btnVeterinario(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuVeterinario.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Veterinario");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
    @FXML
    private void btnVacuna(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuVacuna.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Vacuna");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
    @FXML
    private void btnProveedor(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuProveedor.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Proveedor");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnEmpleado(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuEmpleado.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Empleado");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnTratamiento(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuTratamiento.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Tratamiento");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }     
}   
    
     @FXML
    private void btnConsulta(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuConsulta.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Consulta");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnCita (ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuCita.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Cita");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnMedicamento(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuMedicamento.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Medicamento");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnVacunacion(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuVacunacion.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Vacunacion");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnReceta(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuReceta.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Receta");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnFactura(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuFactura.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Factura");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
     @FXML
    private void btnCompra(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuCompra.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Compra");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
    
    @FXML
    private void btnRedes(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/Redes.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Redes");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de Redes.");
            }
} 
    
    @FXML
        private void btnApagar(ActionEvent event) {   
        Platform.exit();
    }
        
        @FXML
    private void btnRegresarLogin(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/LoginVista.fxml"));

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Login");
            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
}
}


