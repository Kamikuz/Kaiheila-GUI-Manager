package cn.kamikuz.kaiheiguimanager.ui.component;

import cn.fightingguys.kaiheila.api.Guild;
import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class ServerListItem extends BorderPane {

  @FXML private ImageView serverIcon;
  @FXML private Label serverName;
  @FXML private Label serverID;
  @FXML private Label serverOwner;

  public ServerListItem(Guild server) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/server-card.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(this);
    try {
      loader.load();
      serverIcon.setImage(new Image(server.getIcon()));
      serverName.setText(server.getName());
      serverID.setText(server.getId());
      serverOwner.setText(server.getCreator().getFullName());
      setOnMouseClicked(event -> {
        if (event.getButton() == MouseButton.PRIMARY) {
          KaiheilaGuiManager.navTo(KaiheilaGuiManager.Pages.Server, server);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
