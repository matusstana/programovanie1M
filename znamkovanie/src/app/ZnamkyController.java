package app;

import java.net.URL;
import java.util.ResourceBundle;

import exception.ZnamkovanieException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Ziak;
import model.Znamka;

public class ZnamkyController extends Controller
{
  private Ziak ziak = null;
  @FXML
  private AnchorPane znamkyPane;
  @FXML
  private Label lblZiak;
  @FXML
  private TextField txtHodnota;
  @FXML
  private TextField txtVaha;
  @FXML
  private TableView<Znamka> tblZnamky;
  @FXML
  private TableColumn<Znamka, String> cHodnota;
  @FXML
  private TableColumn<Znamka, String> cVaha;
  @FXML
  private Button btnDeleteZnamka;
  @FXML
  private Button btnAddZnamka;
  @FXML
  private Button btnZavrietDialog;

  public void initialize(URL location, ResourceBundle resources)
  {
    cHodnota.setCellValueFactory(new PropertyValueFactory<Znamka, String>("hodnota"));
    cVaha.setCellValueFactory(new PropertyValueFactory<Znamka, String>("vahaStr"));
  }
  
  public void setZiak(Ziak ziak)
  {
    this.ziak = ziak;
    this.tblZnamky.setItems(ziak.getZnamky());
    this.lblZiak.setText(ziak.toString());
  }

  @FXML
  private void handleAddZnamka(ActionEvent event)
  {
    vykonajAkciu(Akcia.ADD_ZNAMKA);
  }
  
  @FXML
  private void handleDeleteZnamka(ActionEvent event)
  {
    vykonajAkciu(Akcia.DELETE_ZNAMKA);
  }
  
  @FXML
  private void handleZavrietDialog(ActionEvent event)
  {
    closeStage(event);
  }
  
  @Override
  protected void vykonajAkciuImpl(Akcia akcia, String... params) throws ZnamkovanieException
  {
    switch (akcia)
    {
      case ADD_ZNAMKA:
      {
        String hodnotaStr = txtHodnota.getText();
        String vahaStr = txtVaha.getText();
        int hodnota;
        double vaha;
        
        try
        {
          hodnota = Integer.parseInt(hodnotaStr);
        }
        catch (NumberFormatException e) 
        {
          throw new ZnamkovanieException("Zn·mka mÙûe maù hodnotu len od 1 do 5!");
        }
        try
        {
          vaha = Double.parseDouble(vahaStr);
        }
        catch (NumberFormatException e) 
        {
          throw new ZnamkovanieException("V·ha musÌ byù desatinnÈ ËÌslo v‰Ëöie ako 0.0!");
        }
        
        Znamka z = new Znamka(hodnota, vaha);
        
        ziak.addZnamka(z);
        
        txtHodnota.clear();
        txtVaha.clear();
        break;
      }
      case DELETE_ZNAMKA:
      {
        Znamka z = getSelectedZnamka();
        ziak.deleteZnamka(z);
        break;
      }
      default:
        break;
    }

  }

  private void closeStage(ActionEvent event) 
  {
    Node  source = (Node)  event.getSource(); 
    Stage stage  = (Stage) source.getScene().getWindow();
    stage.close();
  }
  
  private Znamka getSelectedZnamka() throws ZnamkovanieException
  {
    Znamka z = tblZnamky.getSelectionModel().getSelectedItem();
    if (z == null)
      throw new ZnamkovanieException("Nie je vybrat˝ ûiaden z·znam!");
    
    return z;
  }
}
