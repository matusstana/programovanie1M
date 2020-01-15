package model;

import exception.ZnamkovanieException;
import net.minidev.json.JSONObject;

public class Znamka
{
  private final int hodnota;
  private final double vaha;
  
  public Znamka(int hodnota, double vaha) throws ZnamkovanieException
  {
    if (hodnota < 1 || hodnota > 5)
      throw new ZnamkovanieException("Zn·mka mÙûe maù hodnotu len od 1 do 5!");
    
    if (vaha <= 0.0)
      throw new ZnamkovanieException("V·ha musÌ byù desatinnÈ ËÌslo v‰Ëöie ako 0.0!");
    
    this.hodnota = hodnota;
    this.vaha = vaha;
  }
  
  public double getVazenaHodnota()
  {
    return hodnota * vaha;
  }
  
  public int getHodnota()
  {
    return hodnota;
  }
  
  public double getVaha()
  {
    return vaha;
  }
  
  public String getVahaStr()
  {
    return String.format("%.2f", getVaha());
  }
  
  public void fillJSON(JSONObject o)
  {
    o.put("hodnota", hodnota);
    o.put("vaha", vaha);
  }
  
  @Override
  public String toString()
  {
    return String.valueOf(hodnota);
  }
}