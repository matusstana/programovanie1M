package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import exception.ZnamkovanieException;
import io.DataIO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Skupina;
import model.Ziak;
import model.ZnamkovanieModel;

public class MainController extends Controller
{
  private ZnamkovanieModel model;
  @FXML
  private AnchorPane mainPane;
  @FXML
  private ComboBox<Skupina> cmbSkupiny;
  @FXML
  private TextField txtSkupina;
  @FXML
  private TextField txtPriezvisko;
  @FXML
  private TextField txtMeno;
  @FXML
  private TextField txtTrieda;
  @FXML
  private TableView<Ziak> tblZiaci;
  @FXML
  private TableColumn<Ziak, String> cPriezvisko;
  @FXML
  private TableColumn<Ziak, String> cMeno;
  @FXML
  private TableColumn<Ziak, String> cTrieda;
  @FXML
  private TableColumn<Ziak, String> cZnamky;
  @FXML
  private TableColumn<Ziak, String> cPriemer;
  @FXML
  private Button btnDeleteSkupina;
  @FXML
  private Button btnAddSkupina;
  @FXML
  private Button btnAddZiak;
  @FXML
  private Button btnDeleteZiak;
  @FXML
  private Button btnOznamkuj;
  
  @Override
  public void initialize(URL location, ResourceBundle resources)
  {
    model = new ZnamkovanieModel();
    cmbSkupiny.setItems(model.getSkupiny());
    /*if (cmbSkupiny.getItems().size() > 0)
      cmbSkupiny.getSelectionModel().selectFirst();*/
    
    cPriezvisko.setCellValueFactory(new PropertyValueFactory<Ziak, String>("priezvisko"));
    cPriezvisko.setCellFactory(TextFieldTableCell.<Ziak>forTableColumn());
    cMeno.setCellValueFactory(new PropertyValueFactory<Ziak, String>("meno"));
    cMeno.setCellFactory(TextFieldTableCell.<Ziak>forTableColumn());
    cTrieda.setCellValueFactory(new PropertyValueFactory<Ziak, String>("trieda"));
    cTrieda.setCellFactory(TextFieldTableCell.<Ziak>forTableColumn());
    cZnamky.setCellValueFactory(new PropertyValueFactory<Ziak, String>("znamkyStr"));
    cPriemer.setCellValueFactory(new PropertyValueFactory<Ziak, String>("priemerStr"));
  }

  @FXML
  private void handleDeleteSkupina(ActionEvent event)
  {
    vykonajAkciu(Akcia.DELETE_SKUPINA);
  }
  
  @FXML
  private void handleAddSkupina(ActionEvent event)
  {
    vykonajAkciu(Akcia.ADD_SKUPINA);
  }
  
  @FXML
  private void handleSelectSkupina(ActionEvent event)
  {
    vykonajAkciu(Akcia.SELECT_SKUPINA);
  }
  
  @FXML
  private void handleAddZiak(ActionEvent event)
  {
    vykonajAkciu(Akcia.ADD_ZIAK);
  }
  
  @FXML
  private void handleDeleteZiak(ActionEvent event)
  {
    vykonajAkciu(Akcia.DELETE_ZIAK);
  }
  
  @FXML
  private void handleOznamkuj(ActionEvent event)
  {
    vykonajAkciu(Akcia.OZNAMKUJ);
  }
  
  @FXML
  private void handleUlozit(ActionEvent event)
  {
    vykonajAkciu(Akcia.ULOZ);
  }
  
  @FXML
  private void handlePriezviskoEdit(TableColumn.CellEditEvent<Ziak, String> event)
  {
    vykonajAkciu(Akcia.EDIT_PRIEZVISKO, event.getNewValue());
  }
  
  @FXML
  private void handleMenoEdit(TableColumn.CellEditEvent<Ziak, String> event)
  {
    vykonajAkciu(Akcia.EDIT_MENO, event.getNewValue());
  }
  
  @FXML
  private void handleTriedaEdit(TableColumn.CellEditEvent<Ziak, String> event)
  {
    vykonajAkciu(Akcia.EDIT_TRIEDA, event.getNewValue());
  }
  
  @Override
  protected void vykonajAkciuImpl(Akcia akcia, String... params) throws ZnamkovanieException, IOException
  {
    switch (akcia)
    {
      case DELETE_SKUPINA:
      {
        Skupina s = getSelectedSkupina();
        model.deleteSkupina(s);
        break;
      }
      case ADD_SKUPINA:
      {
        String nazov = txtSkupina.getText();
        model.addSkupina(nazov);
        txtSkupina.clear();
        Skupina s = model.getSkupina(nazov);
        if (s != null)
          cmbSkupiny.getSelectionModel().select(s);
        break;
      }
      case SELECT_SKUPINA:
      {
        Skupina skupina = cmbSkupiny.getSelectionModel().getSelectedItem();
        if (skupina != null)
          tblZiaci.setItems(skupina.getZiaci());
        else
          tblZiaci.setItems(FXCollections.observableArrayList());
        break;
      }
      case ADD_ZIAK:
      {
        Skupina skupina = getSelectedSkupina();
        
        String priezvisko = txtPriezvisko.getText();
        String meno = txtMeno.getText();
        String trieda = txtTrieda.getText();
        
        skupina.addZiak(new Ziak(meno, priezvisko, trieda));
        
        txtPriezvisko.clear();
        txtMeno.clear();
        txtTrieda.clear();
        break;
      }
      case DELETE_ZIAK:
      {
        Skupina skupina = getSelectedSkupina();
        Ziak ziak = getSelectedZiak();
        skupina.deleteZiak(ziak);
        break;
      }
      case OZNAMKUJ:
      {
        Ziak ziak = getSelectedZiak();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Okno_znamky.fxml"));
        Parent parent = fxmlLoader.load();
        ZnamkyController dialogController = fxmlLoader.<ZnamkyController>getController();
        dialogController.setZiak(ziak);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Známky");
        stage.showAndWait();
        
        tblZiaci.refresh();
        break;
      }
      case ULOZ:
      {
        DataIO.zapisJson(model.toJsonString());
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informácia");
        alert.setHeaderText("Údaje boli úspešne uložené");
        alert.setContentText("");
        alert.showAndWait();
        
        break;
      }
      case EDIT_PRIEZVISKO:
      {
        Ziak z = getSelectedZiak();
        z.setPriezvisko(params[0]);
        break;
      }
      case EDIT_MENO:
      {
        Ziak z = getSelectedZiak();
        z.setMeno(params[0]);
        break;
      }
      case EDIT_TRIEDA:
      {
        Ziak z = getSelectedZiak();
        z.setTrieda(params[0]);
        break;
      }
      default:
    }
  }
  
  private Skupina getSelectedSkupina() throws ZnamkovanieException
  {
    Skupina skupina = cmbSkupiny.getSelectionModel().getSelectedItem();
    if (skupina == null)
      throw new ZnamkovanieException("Nie je vybratá žiadna skupina!");
    
    return skupina;
  }
  
  private Ziak getSelectedZiak() throws ZnamkovanieException
  {
    Ziak ziak = tblZiaci.getSelectionModel().getSelectedItem();
    if (ziak == null)
      throw new ZnamkovanieException("Nie je vybratý žiaden žiak!");
    
    return ziak;
  }
}
