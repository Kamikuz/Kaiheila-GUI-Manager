package cn.kamikuz.kaiheiguimanager.utils.io.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StorageManager {
  public static final StorageManager Instance = new StorageManager();
  SavedData data = new SavedData();
  File file = new File("save.dat");

  public void init() {
    if (!file.exists()) {
      return;
    }
    Scanner scanner;
    try {
      scanner = new Scanner(file);
      data.readFrom(scanner);
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final SavedData getData() {
    return data;
  }

  public boolean createSavedData(String newToken) {
    data.token = newToken;
    if (file.exists()) {
      if (!file.delete()) return false;
    }
    try {
      if (!file.createNewFile()) {
        return false;
      }
      FileWriter writer = new FileWriter(file);
      writer.write(data.token);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
