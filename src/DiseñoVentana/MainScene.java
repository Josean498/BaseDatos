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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
        
        StackPane rootMain = new StackPane();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DiseñoVentana.fxml"));
        Pane rootDiseñoVentanaView = fxmlLoader.load();
        rootMain.getChildren().add(rootDiseñoVentanaView);

        emf = Persistence.createEntityManagerFactory("BaseDatosPU");
        em = emf.createEntityManager();
        
        DiseñoVentanaController equiposViewController = (DiseñoVentanaController) fxmlLoader.getController();                
        equiposViewController.setEntityManager(em);
        equiposViewController.cargarTodosEquipos();
        
        Scene scene = new Scene(rootMain, 600, 500);

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
