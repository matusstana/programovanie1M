package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class DataIO
{
  private static final String DIR = "data/"; 
  private static final String FILE_NAME = "znamkovanie_data.json"; 
  
  public static String nacitajSubor() throws IOException
  {
    File file = new File(DIR + FILE_NAME);
    BufferedReader br;
    try
    {
      br = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException e1)
    {
      e1.printStackTrace();
      throw new FileNotFoundException("Nenašiel sa súbor s dátami " + FILE_NAME);
    }
    
    StringBuilder sb = new StringBuilder();
    try
    {
      String line = br.readLine();
      while (line != null)
      {
        sb.append(line);
        sb.append("\n");
        line = br.readLine();
      }
    }
    finally
    {
      try
      {
        br.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }
  
  
  public static void zapisJson(String jsonString)
  {
    zapisOutput(DIR + FILE_NAME, jsonString);
  }

  private static void zapisOutput(String filePath, String stringToWrite)
  {
    Writer writer = null;

    try
    {
      writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(filePath)));
      writer.write(stringToWrite);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      try
      {
        writer.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }
}
