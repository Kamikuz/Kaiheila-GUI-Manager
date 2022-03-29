package cn.kamikuz.kaiheiguimanager.ui.component.cardMessage;

import cn.fightingguys.kaiheila.entity.cardmessage.CardMessageBuilder;
import cn.kamikuz.kaiheiguimanager.ui.component.cardMessage.element.CardComponentButton;
import cn.kamikuz.kaiheiguimanager.ui.controller.ServerController;
import cn.kamikuz.kaiheiguimanager.utils.UIUtils;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ResourceBundle;

public class CardMessageEditor extends Pane {
  @FXML
  public VBox toolBar;
  @FXML
  public VBox editor;
  @FXML
  public VBox property;
  public ServerController serverController;
  public CardMessageBuilder cardMessageBuilder;

  public CardMessageEditor(Pane parent, ServerController serverController) {
    this.serverController = serverController;
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/cardMessage/card-message-editor.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(parent);
    try {
      loader.load();
      toolBar.getChildren().addAll(CardComponentButton.init(this));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void send() {
    switch (serverController.getSelectedChannel().getChannelOperation().broadcastMessage(cardMessageBuilder)) {
      case SUCCESS -> {
        editor.getChildren().clear();
        UIUtils.Alert(Alert.AlertType.INFORMATION, "发送成功", "发送成功");
      }
      case FAILED -> {
        UIUtils.Alert(Alert.AlertType.ERROR, "发送失败", "发送失败");
      }
    }
  }
}
