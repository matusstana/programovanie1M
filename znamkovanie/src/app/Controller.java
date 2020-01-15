package app;

import java.io.IOException;

import exception.ZnamkovanieException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller implements Initializable
{
  protected void vykonajAkciu(Akcia akcia, String... params)
  {
    try
    {
      vykonajAkciuImpl(akcia, params);
    }
    catch(ZnamkovanieException ze)
    {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Chyba");
      alert.setHeaderText(ze.getMessage());
      alert.setContentText("");
      alert.showAndWait();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Systémová chyba");
      alert.setHeaderText("Nastala systémová chyba!");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }
  
  protected abstract void vykonajAkciuImpl(Akcia akcia, String... params) throws ZnamkovanieException, IOException;
}
