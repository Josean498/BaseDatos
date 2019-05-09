/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiseñoVentana;

import basedatos.entities.Equipos;
import basedatos.entities.Patrocinador;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class DiseñoVentanaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private EntityManager entityManager;
    @FXML
    private TableColumn<Patrocinador, String> columnNombre;
    @FXML
    private TableColumn<Patrocinador, String> columnEmail;
    @FXML
    private TableView<Patrocinador> tableViewDiseño;
    @FXML
    private TableColumn<Patrocinador, String> columnNumpilotos;
    @FXML
    private TableColumn<Patrocinador, String> columnPatrocinador;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnNumpilotos.setCellValueFactory(new PropertyValueFactory<>("numpilotos"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPatrocinador.setCellValueFactory(new PropertyValueFactory<>("patrocinador"));
    }
    
    public void cargarTodasPersonas() {
    Query queryPatrocinadorFindAll = entityManager.createNamedQuery("Patrocinador.findAll");
    List<Patrocinador> listPatrocinador = queryPatrocinadorFindAll.getResultList();
    tableViewDiseño.setItems(FXCollections.observableArrayList(listPatrocinador));
    } 
}
