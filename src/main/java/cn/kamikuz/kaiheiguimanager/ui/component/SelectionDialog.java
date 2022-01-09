package cn.kamikuz.kaiheiguimanager.ui.component;

import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class SelectionDialog extends DialogPane {

  public SelectionDialog() {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/SelectionDialog.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(this);
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
