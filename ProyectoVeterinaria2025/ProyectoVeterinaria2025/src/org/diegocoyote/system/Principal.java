package org.diegocoyote.system; //POR DIEGO COYOTE 2024037 IN5BM

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override 
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/diegocoyote/view/LoginVista.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Veterinaria");
            primaryStage.show();
            
          //  primaryStage.getIcons().add(new Image(getClass().getResource("/org/diegocoyote/image/images.jpg")));
        } catch (IOException e) {
            System.out.println("Error al cargar la vista.");
        }

    }
    public static void main(String[] args) {
        launch(args);
    }

    public void MenuPrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}