package model;

import java.util.stream.Collectors;

import exception.ZnamkovanieException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Ziak implements Comparable<Ziak>
{
  private String meno;
  private String priezvisko;
  private String trieda;
  private final ObservableList<Znamka> znamky;
  
  public Ziak(String meno, String priezvisko, String trieda) throws ZnamkovanieException
  {
    if (priezvisko == null || priezvisko.trim().equals(""))
      throw new ZnamkovanieException("Zadajte priezvisko!");
    if (meno == null || meno.trim().equals(""))
      throw new ZnamkovanieException("Zadajte meno!");
    if (trieda == null || trieda.trim().equals(""))
      throw new ZnamkovanieException("Zadajte triedu!");
    
    this.meno = meno;
    this.priezvisko = priezvisko;
    this.trieda = trieda;
    this.znamky = FXCollections.observableArrayList();
  }
  
  public void addZnamka(Znamka znamka)
  {
    this.znamky.add(znamka);
  }
  
  public void deleteZnamka(Znamka znamka)
  {
    this.znamky.remove(znamka);
  }
  
  public String getMeno()
  {
    return meno;
  }
  
  public String getPriezvisko()
  {
    return priezvisko;
  }
  
  public String getTrieda()
  {
    return trieda;
  }
  
  public void setPriezvisko(String priezvisko)
  {
    this.priezvisko = priezvisko;
  }
  
  public void setMeno(String meno)
  {
    this.meno = meno;
  }
  
  public void setTrieda(String trieda)
  {
    this.trieda = trieda;
  }
  
  public ObservableList<Znamka> getZnamky()
  {
    return znamky;
  }
  
  public String getZnamkyStr()
  {
    return znamky.stream().map(Object::toString).collect(Collectors.joining(","));
  }
  
  public double getPriemer() throws ZnamkovanieException
  {
    if (znamky.size() == 0)
      throw new ZnamkovanieException("Neboli zadané žiadne známky");
    
    double sumaHodnot = 0.0;
    double sumaVah = 0.0;
    
    for (int i = 0; i < znamky.size(); i++)
    {
      Znamka znamka = znamky.get(i);
      sumaHodnot += znamka.getVazenaHodnota();
      sumaVah += znamka.getVaha();
    }
    
    return sumaHodnot / sumaVah;
  }
  
  public String getPriemerStr()
  {
    try
    {
      return String.format("%.2f", getPriemer());
    } 
    catch (ZnamkovanieException e)
    {
      return "";
    }
  }
  
  public void fillJSON(JSONObject o)
  {
    o.put("priezvisko", priezvisko);
    o.put("meno", meno);
    o.put("trieda", trieda);
    JSONArray arr = new JSONArray();
    o.put("znamky", arr);
    
    for (int i = 0; i < znamky.size(); i++)
    {
      JSONObject zo = new JSONObject();
      znamky.get(i).fillJSON(zo);
      arr.add(zo);
    }
  }
  
  @Override
  public String toString()
  {
    return meno + " " + priezvisko + "(" + trieda + ")";
  }

  @Override
  public int compareTo(Ziak o)
  {
    return this.priezvisko.compareTo(o.priezvisko);
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Ziak)
    {
      Ziak z = (Ziak) obj;
      return this.priezvisko.equals(z.priezvisko)
          && this.meno.equals(z.meno)
          && this.trieda.equals(z.trieda);
    }
    else
      return super.equals(obj);
  }
}
