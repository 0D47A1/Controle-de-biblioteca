/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.de.biblioteca;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author melksedek
 */
public class ControleDeBiblioteca extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
    
                
                
                
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        
        //Scene scene = new Scene(root);
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(false);
        
        String uri = getClass().getResource("css/estilo.css").toExternalForm();
        
        Scene scene = new Scene(decorator, 1280, 720);
        scene.getStylesheets().add(uri) ;
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
