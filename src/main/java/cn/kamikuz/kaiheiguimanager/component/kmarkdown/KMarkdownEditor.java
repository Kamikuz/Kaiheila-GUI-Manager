package cn.kamikuz.kaiheiguimanager.component.kmarkdown;
import cn.fightingguys.kaiheila.entity.kmarkdown.KMarkdownBuilder;
import cn.kamikuz.kaiheiguimanager.i18n.i18n;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class KMarkdownEditor extends BorderPane {
  KMarkdownBuilder builder = new KMarkdownBuilder();

  public KMarkdownEditor() {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/kmarkdown/kmarkdown-editor.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(this);
    try {
      loader.load();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createBold() {

  }
}
