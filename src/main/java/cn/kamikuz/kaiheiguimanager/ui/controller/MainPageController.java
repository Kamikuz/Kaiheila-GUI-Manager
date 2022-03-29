package cn.kamikuz.kaiheiguimanager.ui.controller;

import cn.fightingguys.kaiheila.api.Guild;
import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import cn.kamikuz.kaiheiguimanager.service.kaiheila.KaiheilaManager;
import cn.kamikuz.kaiheiguimanager.ui.component.ServerListItem;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MainPageController extends PrototypeController {
  private final KaiheilaManager bot = KaiheilaManager.Instance;
  int row = 0, col = 0;
  Alert quitAlert;

  @FXML
  private GridPane serverList;

  @Override
  public void initialize() {
    List<Guild> servers = bot.getRabbit().getGuilds();
    for (Guild guild : servers) {
      ServerListItem item = new ServerListItem(guild);
      serverList.add(item, col, row);
      col++;
      if (col == 3) {
        col = 0;
        row++;
      }
    }
  }

  @FXML
  private void onQuit() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("退出");
    alert.setHeaderText("确定退出吗？");
    alert.setContentText("退出后需要重新登录");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {
      quitAlert = new Alert(Alert.AlertType.NONE);
      quitAlert.setTitle("退出");
      quitAlert.setContentText("退出中, 请稍等...");
      quitAlert.getDialogPane().getScene().getWindow().setOnCloseRequest(Event::consume);
      quitAlert.show();
      AtomicBoolean success = new AtomicBoolean(false);
      new Thread(() -> {
        AtomicInteger count = new AtomicInteger(0);
        while (!success.get()) {
          Platform.runLater(() -> quitAlert.setContentText(String.format("退出中, 请稍等(大约需要1分钟)...[%ds]", count.incrementAndGet())));
        }
      }).start();
      bot.quit((V) -> {
        success.set(true);
        Platform.runLater(() -> {
          ((Stage) this.quitAlert.getDialogPane().getScene().getWindow()).close();
          KaiheilaGuiManager.navTo(KaiheilaGuiManager.Pages.Setup);
        });
        return null;
      });
    }
  }
}
