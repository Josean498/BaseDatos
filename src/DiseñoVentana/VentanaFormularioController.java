/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiseñoVentana;

import basedatos.entities.Equipos;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class VentanaFormularioController implements Initializable {
    
    private EntityManager entityManager;
    private Pane rootDiseñoVentanaView;
    private TableView tableViewPrevio;
    private Equipos equipo;
    private boolean nuevoEquipo;
    private Equipos equipoSeleccionado;
    
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldNumPilot;
    @FXML
    private TextField textFieldPatrocinador;
    @FXML
    private TextField textFieldNumEmpleados;
    @FXML
    private TextField textFieldNumCamp;
    @FXML
    private TextField textFieldNumVict;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private RadioButton radioButtonSi;
    @FXML
    private RadioButton radioButtonNo;
    @FXML
    private DatePicker datePickerFecCreacion;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private AnchorPane rootVentanaFormulario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setRootDiseñoVentanaView(Pane rootDiseñoVentanaView) {
    this.rootDiseñoVentanaView = rootDiseñoVentanaView;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }
    
    public void setEquipo(EntityManager entityManager, Equipos equipo, boolean nuevoEquipo) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        if(!nuevoEquipo) {
            this.equipo = entityManager.find(Equipos.class, equipo.getId());
        } else {
            this.equipo = equipo;
          }
        this.nuevoEquipo = nuevoEquipo;
    }
    
    public void mostrarDatos() {
        textFieldNombre.setText(equipo.getNombre());
        textFieldEmail.setText(equipo.getEmail());
        
          // Esto se hace en el punto 11!!!!
//        textFieldNumPilot.setText(equipo.getNumpilotos());
//        textFieldPatrocinador.setText(equipo.getPatrocinador());
//        textFieldNumEmpleados.setText(equipo.getNumempleados());
//        textFieldNumCamp.setText(equipo.getNumcampeonatos());
//        textFieldNumVict.setText(equipo.getNumvictorias());
//        textFieldSalario.setText(equipo.getSalariomedioequipo());
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootVentanaFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootVentanaFormulario);      
  
        rootDiseñoVentanaView.setVisible(true);
        
        equipo.setNombre(textFieldNombre.getText());
        equipo.setEmail(textFieldEmail.getText());

        if(nuevoEquipo) {
            entityManager.persist(equipo);
        } else {
            entityManager.merge(equipo);
        }
        entityManager.getTransaction().commit();
        
        int numFilaSeleccionada;
        if(nuevoEquipo) {
            tableViewPrevio.getItems().add(equipo);
            numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada, equipo);
        }
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        
        entityManager.getTransaction().rollback();
        
        StackPane rootMain = (StackPane)rootVentanaFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootVentanaFormulario);      

        rootDiseñoVentanaView.setVisible(true);
        
        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }
    
}
