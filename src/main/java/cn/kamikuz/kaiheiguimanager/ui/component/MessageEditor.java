package cn.kamikuz.kaiheiguimanager.ui.component;

import cn.kamikuz.kaiheiguimanager.ui.component.cardMessage.CardMessageEditor;
import cn.kamikuz.kaiheiguimanager.ui.component.kmarkdown.KMarkdownEditor;
import cn.kamikuz.kaiheiguimanager.ui.controller.ServerController;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class MessageEditor {
  @FXML
  public BorderPane editorC;
  @FXML
  public BorderPane editorK;
  @FXML
  TabPane editorT;

  KMarkdownEditor editorController;
  CardMessageEditor cardMessageEditor;

  public MessageEditor(BorderPane parent, ServerController controller) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/editor.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(parent);
    try {
      loader.load();
      editorController = new KMarkdownEditor(editorK, controller);
      cardMessageEditor = new CardMessageEditor(editorC, controller);
      editorT.getTabs().forEach(tab -> tab.getContextMenu().prefWidth(editorT.getWidth() / 2 - 10));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
