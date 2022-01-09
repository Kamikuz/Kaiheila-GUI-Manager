package cn.kamikuz.kaiheiguimanager.ui.controller;

import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import cn.kamikuz.kaiheiguimanager.service.kaiheila.KaiheilaManager;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SetupController extends PrototypeController {
  @FXML
  private TextField tokenField;
  @FXML
  private Button loginBtn;
  @FXML
  private Label status;
  @FXML
  private ProgressIndicator indicator;

  @FXML
  protected void login() {
    if (tokenField.getText().isEmpty()) {
      alert(Alert.AlertType.ERROR, "请输入Token", null);
      return;
    }
    tokenField.setDisable(true);
    loginBtn.setDisable(true);
    status.setVisible(true);
    indicator.setVisible(true);
    status.setTextFill(Color.BLACK);
    new Thread(() -> {
      if (KaiheilaManager.Instance.init(tokenField.getText())) {
        Platform.runLater(() -> {
          status.setText(i18n.format("ui.login.success"));
          KaiheilaGuiManager.navTo(KaiheilaGuiManager.Pages.Main);
        });
      } else {
        Platform.runLater(() -> {
          indicator.setDisable(false);
          status.setText(i18n.format("ui.login.fail"));
          // set color to red
          status.setTextFill(Color.RED);
          tokenField.setDisable(false);
          loginBtn.setDisable(false);
          indicator.setVisible(false);
          alert(Alert.AlertType.ERROR, "登录失败", null);
        });
      }
    }).start();
  }
}