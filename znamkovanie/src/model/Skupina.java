package model;

import exception.ZnamkovanieException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Skupina 
{
  private final String nazov;
  private final ObservableList<Ziak> ziaci;
  
  public Skupina(String nazov)
  {
    this.nazov = nazov;
    this.ziaci = FXCollections.observableArrayList();
  }
  
  public String getNazov()
  {
    return nazov;
  }
  
  public void addZiak(Ziak ziak) throws ZnamkovanieException
  {
    if (this.ziaci.contains(ziak))
      throw new ZnamkovanieException("Zadaný žiak už existuje!");
    this.ziaci.add(ziak);
    FXCollections.sort(this.ziaci);
  }
  
  public void deleteZiak(Ziak ziak)
  {
    this.ziaci.remove(ziak);
  }
  
  public ObservableList<Ziak> getZiaci()
  {
    return ziaci;
  }
  
  public void fillJSON(JSONObject o)
  {
    o.put("nazov", nazov);
    JSONArray arr = new JSONArray();
    o.put("ziaci", arr);
    
    for (int i = 0; i < ziaci.size(); i++)
    {
      JSONObject zo = new JSONObject();
      ziaci.get(i).fillJSON(zo);
      arr.add(zo);
    }
  }
  
  @Override
  public String toString()
  {
    return this.nazov;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Skupina)
    {
      Skupina s = (Skupina) obj;
      return this.nazov.equals(s.nazov);
    }
    else
      return super.equals(obj);
  }
}
