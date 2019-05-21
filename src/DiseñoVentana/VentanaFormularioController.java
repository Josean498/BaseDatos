/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiseñoVentana;

import basedatos.entities.Equipos;
import basedatos.entities.Patrocinador;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
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
    public static final String CARPETA_FOTOS = "Fotos";
    
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldNumPilot;
    @FXML
    private TextField textFieldNumEmpleados;
    @FXML
    private TextField textFieldNumCamp;
    @FXML
    private TextField textFieldNumVict;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private DatePicker datePickerFecCreacion;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private AnchorPane rootVentanaFormulario;
    @FXML
    private ComboBox<Patrocinador> comboBoxPatrocinador;
    @FXML
    private CheckBox checkBoxSi;
    @FXML
    private CheckBox checkBoxNo;
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
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }
    
    public void setEquipo(EntityManager entityManager, Equipos equipo, boolean nuevoEquipo) {
        this.entityManager = entityManager;
        // Comenzamos la transacción.
        entityManager.getTransaction().begin();
        // Si no es un nuevo equipo sino que lo hemos seleccionado de la base de datos te dara todos sus datos.
        if(!nuevoEquipo) {
            this.equipo = entityManager.find(Equipos.class, equipo.getId());
            //Si es un nuevo equipo te permitira crearlo desde cero.
        } else {
            this.equipo = equipo;
          }
        this.nuevoEquipo = nuevoEquipo;
//        entityManager.getTransaction().commit();
    }
    
    public void mostrarDatos() {
        textFieldNombre.setText(equipo.getNombre());
        textFieldEmail.setText(equipo.getEmail());
        
        if(equipo.getSalariomedioequipo() != null) {
            textFieldSalario.setText(equipo.getSalariomedioequipo().toString());
        }
        if(equipo.getNumpilotos() != null) {
            textFieldNumPilot.setText(equipo.getNumpilotos().toString());
        }   
        if(equipo.getNumempleados() != null) {
            textFieldNumEmpleados.setText(equipo.getNumempleados().toString());
        }  
        if(equipo.getNumcampeonatos() != null) {
            textFieldNumCamp.setText(equipo.getNumcampeonatos().toString());
        }  
        if(equipo.getNumvictorias() != null) {
            textFieldNumVict.setText(equipo.getNumvictorias().toString());
        }  
        if (equipo.getPatrocinio() != null) {
            checkBoxSi.setSelected(equipo.getPatrocinio());
        }
        if (equipo.getPatrocinio() != null) {
            checkBoxNo.setSelected(equipo.getPatrocinio());
        }
        if (equipo.getFeccreacion()!= null) {
            Date date = equipo.getFeccreacion();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFecCreacion.setValue(localDate);
        }
        if (equipo.getFoto() != null) {
            String imageFileName = equipo.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION, "No se encuentra la imagen");
            alert.showAndWait();
          }
        }
        
        Query queryPatrocinadorFindAll = entityManager.createNamedQuery("Patrocinador.findAll");
        List<Patrocinador> listPatrocinador = queryPatrocinadorFindAll.getResultList();
        comboBoxPatrocinador.setItems(FXCollections.observableArrayList(listPatrocinador));
        
        if (equipo.getPatrocinador()!= null) {
            comboBoxPatrocinador.setValue(equipo.getPatrocinador());
        }
        comboBoxPatrocinador.setCellFactory((ListView<Patrocinador> l) -> new ListCell<Patrocinador>() {
        @Override
            protected void updateItem(Patrocinador patrocinador, boolean empty) {
                super.updateItem(patrocinador, empty);
                if (patrocinador == null || empty) {
                    setText("");
                } else {
                    setText(patrocinador.getId() + "-" + patrocinador.getNombre());
                }
            }
        });
        comboBoxPatrocinador.setConverter(new StringConverter<Patrocinador>() {
        @Override
            public String toString(Patrocinador patrocinador) {
                if (patrocinador == null) {
                    return null;
                } else {
                    return patrocinador.getId() + "-" + patrocinador.getNombre();
                }
            }
            @Override
            public Patrocinador fromString(String userId) {
                return null;
            }
        });
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if(!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );
        File file = fileChooser.showOpenDialog(rootDiseñoVentanaView.getScene().getWindow());
        if(file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/"+file.getName()).toPath());
                equipo.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        int numFilaSeleccionada;
                // Aquí va el código para guardar el objeto en la base de datos
                //    y ocultar la vista actual
                StackPane rootMain = (StackPane)rootVentanaFormulario.getScene().getRoot();
                rootMain.getChildren().remove(rootVentanaFormulario);      

                rootDiseñoVentanaView.setVisible(true);

                equipo.setNombre(textFieldNombre.getText());
                equipo.setEmail(textFieldEmail.getText());
                equipo.setNumempleados(Short.MIN_VALUE);
                equipo.setNumcampeonatos(Short.MAX_VALUE);
                equipo.setNumpilotos(Short.MIN_VALUE);
                equipo.setNumvictorias(Short.MIN_VALUE);
                equipo.setSalariomedioequipo(BigDecimal.ZERO);
                equipo.setPatrocinio(checkBoxSi.isSelected());
                equipo.setPatrocinio(checkBoxNo.isSelected());
  
            if(!textFieldSalario.getText().isEmpty()) {
                try {
                    equipo.setSalariomedioequipo(BigDecimal.valueOf(Double.valueOf(textFieldSalario.getText())));
                } catch(NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(AlertType.INFORMATION, "Salario no válido");
                    alert.showAndWait();
                    textFieldSalario.requestFocus();
                }
            }
            if(!textFieldNumPilot.getText().isEmpty()) {
                try {
                    equipo.setNumpilotos(Short.valueOf(textFieldNumPilot.getText()));
                } catch(NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(AlertType.INFORMATION, "Indique pilotos");
                    alert.showAndWait();
                    textFieldNumPilot.requestFocus();
                }
            }
            if(!textFieldNumEmpleados.getText().isEmpty()) {
                try {
                    equipo.setNumempleados(Short.valueOf(textFieldNumEmpleados.getText()));
                } catch(NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(AlertType.INFORMATION, "Indique empleados");
                    alert.showAndWait();
                    textFieldNumEmpleados.requestFocus();
                }
            }
            if(!textFieldNumCamp.getText().isEmpty()) {
                try {
                    equipo.setNumcampeonatos(Short.valueOf(textFieldNumCamp.getText()));
                } catch(NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(AlertType.INFORMATION, "Indique campeonatos");
                    alert.showAndWait();
                    textFieldNumCamp.requestFocus();
                }
            }
            if(!textFieldNumVict.getText().isEmpty()) {
                try {
                    equipo.setNumvictorias(Short.valueOf(textFieldNumVict.getText()));
                } catch(NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(AlertType.INFORMATION, "Indique victorias");
                    alert.showAndWait();
                    textFieldNumVict.requestFocus();
                }
            }
            if (datePickerFecCreacion.getValue()!= null) {
                LocalDate localDate = datePickerFecCreacion.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date date = Date.from(instant);
                equipo.setFeccreacion(date);
             } else {
                equipo.setFeccreacion(null);
             }
            
            if(comboBoxPatrocinador.getValue() != null) {
                equipo.setPatrocinador(comboBoxPatrocinador.getValue());
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION, "Debe indicar una patrocinador");
                alert.showAndWait();
                errorFormato = true;
                    }
            // Recoger datos de pantalla
            if(!errorFormato) {  // Los datos introducidos son correctos
                try {
                    if(nuevoEquipo) {
                        entityManager.persist(equipo);
                    } else {
                        entityManager.merge(equipo);
                    }
                    entityManager.getTransaction().commit();
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

//                    entityManager.getTransaction().commit();
                    } catch (RollbackException ex) { // Los datos introducidos no cumplen los requisitos de la BD
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setHeaderText("No se han podido guardar los cambios. "
                                + "Compruebe que los datos cumplen los requisitos");
                        alert.setContentText(ex.getLocalizedMessage());
                        alert.showAndWait();
                    }
            } 
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootVentanaFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootVentanaFormulario);      

        rootDiseñoVentanaView.setVisible(true);
        entityManager.getTransaction().rollback();
//        entityManager.getTransaction().commit();
        
        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    @FXML
    private void onActionButtonEliminarFoto(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
                + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija la opción deseada:");

        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar){
            String imageFileName = equipo.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()) {
                file.delete();
            }
            equipo.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            equipo.setFoto(null);
            imageViewFoto.setImage(null);
        } 
    }
}