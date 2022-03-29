package cn.kamikuz.kaiheiguimanager.utils.io.storage;

import java.io.FileWriter;
import java.util.Scanner;

public class SavedData {
  public String token = "";

  public SavedData(String token) {
    this.token = token;
  }

  public SavedData() {
  }

  public boolean save(FileWriter writer) {
    try {
      writer.write(token);
      writer.flush();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean readFrom(Scanner reader) {
    try {
      token = reader.nextLine();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
