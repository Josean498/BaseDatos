/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiseñoVentana;

import basedatos.entities.Equipos;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private TableColumn<Equipos, String> columnNombre;
    @FXML
    private TableColumn<Equipos, String> columnEmail;
    @FXML
    private TableView<Equipos> tableViewDiseño;
    @FXML
    private TableColumn<Equipos, String> columnPatrocinador;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldEmail;
    
    private Equipos equipoSeleccionado;
    @FXML
    private AnchorPane rootDiseñoVentanaView;
    
// Para que en la ventana se puedan mostrar datos contenidos en la base de datos
// ,el controlador debe tener acceso al objeto EntityManager que permite el acceso a los datos.
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//    Cada columna tiene que estar asociada a una de las propiedades de la clase entidad que se ha indicado 
//    para el TableView.En este ejemplo se va a usar la clase Equipos, por lo que habrá que indicar 
//    qué propiedad de la clase Equipos se mostrará 
//    en la columna columnNombre, y así sucesivamente con cada columna.
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPatrocinador.setCellValueFactory(
            cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getPatrocinador() != null) {
                    property.setValue(cellData.getValue().getPatrocinador().getNombre());
                }
                return property;
                });
        tableViewDiseño.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            equipoSeleccionado = newValue;
            if (equipoSeleccionado != null) {
                textFieldNombre.setText(equipoSeleccionado.getNombre());
                textFieldEmail.setText(equipoSeleccionado.getEmail());         
            } else {
                textFieldNombre.setText("");
                textFieldEmail.setText("");
              }
            });
    }
    
    public void cargarTodosEquipos() {
        // Hace una consulta a la base de datos con todos los equipos disponibles.
        Query queryEquiposFindAll = entityManager.createNamedQuery("Equipos.findAll");
        List<Equipos> listEquipos = queryEquiposFindAll.getResultList();
        tableViewDiseño.setItems(FXCollections.observableArrayList(listEquipos));
    } 

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (equipoSeleccionado != null) {   
            equipoSeleccionado.setNombre(textFieldNombre.getText());
            equipoSeleccionado.setEmail(textFieldEmail.getText());
            entityManager.getTransaction().begin();
            entityManager.merge(equipoSeleccionado);
            entityManager.getTransaction().commit();
            
//          Se indicará que se actualicen en el TableView los nuevos valores del objeto. 
//          En primer lugar se obtiene el número de la fila seleccionada en el TableView y 
//          luego se vuelve a asignar el mismo objeto a esa fila, de manera que se mostrarán los 
//          nuevos valores que contenga el objeto
            int numFilaSeleccionada = tableViewDiseño.getSelectionModel().getSelectedIndex();
            tableViewDiseño.getItems().set(numFilaSeleccionada, equipoSeleccionado);
            
//          Si no se indicara nada más, el foco de la ventana permanecería en el botón Guardar, 
//          que es el último elemento que se ha usado. Es conveniente que el foco vuelva al TableView 
//          para que el usuario pueda seguir moviéndose por su contenido.
            TablePosition pos = new TablePosition(tableViewDiseño, numFilaSeleccionada, null);
            tableViewDiseño.getFocusModel().focus(pos);
            tableViewDiseño.requestFocus();
        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaFormulario.fxml"));
            Parent rootVentanaView = fxmlLoader.load(); 
            
            // Ocultar la vista de la lista
            rootDiseñoVentanaView.setVisible(false);
            
            // Añadir la vista de detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane)rootDiseñoVentanaView.getScene().getRoot();
            rootMain.getChildren().add(rootVentanaView);
            
            VentanaFormularioController ventanaFormularioController = (VentanaFormularioController) fxmlLoader.getController();  
            ventanaFormularioController.setRootDiseñoVentanaView(rootDiseñoVentanaView);
            
            equipoSeleccionado = new Equipos();
            // Hace referencia al formulario y si se pulsa este boton te crea un nuevo equipo.
            ventanaFormularioController.setEquipo(entityManager, equipoSeleccionado, true);
            
            ventanaFormularioController.setTableViewPrevio(tableViewDiseño);
            ventanaFormularioController.mostrarDatos();

            } catch (IOException ex) {
            Logger.getLogger(DiseñoVentanaController.class.getName()).log(Level.SEVERE, null, ex);
              }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
       if(equipoSeleccionado != null && equipoSeleccionado.getId() == null){ 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Desea suprimir el siguiente registro?");
            alert.setContentText(equipoSeleccionado.getNombre() + " "
                    + equipoSeleccionado.getEmail());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // Acciones a realizar si el usuario acepta
                entityManager.getTransaction().begin();
                entityManager.merge(equipoSeleccionado);
                entityManager.remove(equipoSeleccionado);
                entityManager.getTransaction().commit();

                tableViewDiseño.getItems().remove(equipoSeleccionado);

                tableViewDiseño.getFocusModel().focus(null);
                tableViewDiseño.requestFocus();
            } else {
                // Acciones a realizar si el usuario cancela
                int numFilaSeleccionada = tableViewDiseño.getSelectionModel().getSelectedIndex();
                tableViewDiseño.getItems().set(numFilaSeleccionada, equipoSeleccionado);
                TablePosition pos = new TablePosition(tableViewDiseño, numFilaSeleccionada, null);
                tableViewDiseño.getFocusModel().focus(pos);
                tableViewDiseño.requestFocus();  
            }
       } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atnción");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
       }
    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
      if(equipoSeleccionado != null){   
        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaFormulario.fxml"));
            Parent rootVentanaView = fxmlLoader.load(); 
            
            // Ocultar la vista de la lista
            rootDiseñoVentanaView.setVisible(false);
            
            VentanaFormularioController ventanaFormularioController = (VentanaFormularioController) fxmlLoader.getController();  
            ventanaFormularioController.setRootDiseñoVentanaView(rootDiseñoVentanaView);
            ventanaFormularioController.setTableViewPrevio(tableViewDiseño);
            
            // Hace referencia al formulario y si se pulsa este boton te permite modificar los datos del equipo.
            ventanaFormularioController.setEquipo(entityManager, equipoSeleccionado, false);
            ventanaFormularioController.mostrarDatos();
            
            // Añadir la vista de detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane)rootDiseñoVentanaView.getScene().getRoot();
            rootMain.getChildren().add(rootVentanaView);
            
        } catch (IOException ex) {
            Logger.getLogger(DiseñoVentanaController.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atnción");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
       }  
    }
}