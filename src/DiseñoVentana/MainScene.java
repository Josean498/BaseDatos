/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiseñoVentana;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jose
 */
public class MainScene extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DiseñoVentana.fxml"));
        Parent root = fxmlLoader.load();

        emf = Persistence.createEntityManagerFactory("BaseDatosPU");
        em = emf.createEntityManager();
        
        DiseñoVentanaController equiposViewController = (DiseñoVentanaController) fxmlLoader.getController();                
        equiposViewController.setEntityManager(em);
        equiposViewController.cargarTodasPersonas();
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Base de datos");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    @Override
        public void stop() throws Exception {
            em.close(); 
            emf.close(); 
            try { 
                DriverManager.getConnection("jdbc:derby:BDProgramacion;shutdown=true"); 
            } catch (SQLException ex) { 
              }        
        }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
