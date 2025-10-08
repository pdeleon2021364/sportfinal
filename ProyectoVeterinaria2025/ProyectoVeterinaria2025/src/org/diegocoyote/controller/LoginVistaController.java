package org.diegocoyote.controller; //LOGIN VETE

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginVistaController {

    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contraseña;

     @FXML
    private void btnLogin(ActionEvent event) {
        String usuarioCorrecto = "1"; 
        String contraseñaCorrecta = "1"; 
        if (usuario.getText().equals(usuarioCorrecto) && contraseña.getText().equals(contraseñaCorrecta)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/MenuPrincipal.fxml"));

                Stage stage = (Stage) usuario.getScene().getWindow(); 

                Scene scene = new Scene(root); // Tamaño más pequeño
                stage.setScene(scene);
                stage.setTitle("Menú Principal");
                stage.centerOnScreen();
                stage.show();
                stage.setResizable(false);


            } catch (IOException e) {
                System.out.println("Error al cargar la vista de MenuPrincipal.");
            }
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setHeaderText(null);
        alert.setContentText("Usuario o contraseña incorrectos.");
        alert.showAndWait();
    }
}
}