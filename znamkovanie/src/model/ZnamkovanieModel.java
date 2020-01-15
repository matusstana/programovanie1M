package model;

import com.cedarsoftware.util.io.JsonWriter;

import exception.ZnamkovanieException;
import io.DataIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class ZnamkovanieModel
{
  private ObservableList<Skupina> skupiny;
  
  public ZnamkovanieModel()
  {
    skupiny = FXCollections.observableArrayList();
    
    init();
  }
  
  public ObservableList<Skupina> getSkupiny()
  {
    return skupiny;
  }
  
  public Skupina getSkupina(String nazov)
  {
    return skupiny.stream().filter((s) -> s.getNazov().equals(nazov)).findFirst().orElse(null);
  }
  
  public void deleteSkupina(Skupina skupina)
  {
    skupiny.remove(skupina);
  }
  
  public void addSkupina(String nazov) throws ZnamkovanieException
  {
    if (nazov == null || nazov.trim().equals(""))
      throw new ZnamkovanieException("Zadajte názov skupiny!");
    
    Skupina s = new Skupina(nazov);
    if (skupiny.contains(s))
      throw new ZnamkovanieException("Skupina s týmto názvom už existuje!");
    
    skupiny.add(s);
  }
  
  public String toJsonString()
  {
    JSONObject o = new JSONObject();
    fillJSON(o);
    String output = JsonWriter.formatJson(o.toJSONString());
    return output;
  }
  
  public void fillJSON(JSONObject o)
  {
    JSONArray arr = new JSONArray();
    o.put("skupiny", arr);
    
    for (int i = 0; i < skupiny.size(); i++)
    {
      JSONObject so = new JSONObject();
      skupiny.get(i).fillJSON(so);
      arr.add(so);
    }
  }
  
  private void init()
  {
    // nacitanie dat zo suboru    
    String jsonString;
    try
    {
      jsonString = DataIO.nacitajSubor();

      JSONObject data = (JSONObject) JSONValue.parse(jsonString);
      JSONArray skupiny = (JSONArray) data.get("skupiny");
      for (int i = 0; i < skupiny.size(); i++)
      {
        JSONObject s = (JSONObject) skupiny.get(i);
        String nazov = s.getAsString("nazov");
        JSONArray ziaci = (JSONArray) s.get("ziaci");
        Skupina skupina = new Skupina(nazov);
        this.skupiny.add(skupina);
        
        for (int j = 0; j < ziaci.size(); j++)
        {
          JSONObject z = (JSONObject) ziaci.get(j);
          String priezvisko = z.getAsString("priezvisko");
          String meno = z.getAsString("meno");
          String trieda = z.getAsString("trieda");
          JSONArray znamky = (JSONArray) z.get("znamky");
          
          Ziak ziak = new Ziak(meno, priezvisko, trieda);
          skupina.addZiak(ziak);
          
          for (int k = 0; k < znamky.size(); k++)
          {
            JSONObject zn = (JSONObject) znamky.get(k);
            Integer hodnota = Integer.parseInt(zn.getAsString("hodnota"));
            Double vaha = Double.parseDouble(zn.getAsString("vaha"));
            Znamka znamka = new Znamka(hodnota, vaha);
            ziak.addZnamka(znamka);
          }
        }
      }
    } 
    catch (Exception e)
    {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Systémová chyba");
      alert.setHeaderText("Nastala chyba pri naèítaní dát zo súboru!");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
      return;
    }
  }
}
