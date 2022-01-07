package cn.kamikuz.kaiheiguimanager;

import cn.kamikuz.kaiheiguimanager.controller.PrototypeController;
import cn.kamikuz.kaiheiguimanager.i18n.i18n;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class KaiheilaGuiManager extends Application {
  public enum Pages {
    Setup("setup"),;
    final String page;

    Pages(String page) {
      this.page = page;
    }

    public String getPage() {
      return page;
    }

    public FXMLLoader getFXML() {
      return new FXMLLoader(getClass().getResource(String.format("views/%s.fxml", page)));
    }
  }
  private static Stage mainStage;

  public static void navTo(Pages page, Object data) {
    try {
      FXMLLoader loader = page.getFXML();
      Scene temp = new Scene(loader.load(), 1280, 720);
      mainStage.setScene(temp);
      PrototypeController controller = loader.getController();
      controller.prepare(data);
    } catch (IOException e) {
      Utils.Alert(Alert.AlertType.ERROR, "Error", "Cannot open the page: " + page.page);
      e.printStackTrace();
    }
  }

  public static void navTo(Pages page) {
    navTo(page, null);
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setOnCloseRequest(e -> {

      Platform.exit();
      System.exit(0);
    });

    mainStage = stage;
    FXMLLoader loader = Pages.Setup.getFXML();
    Scene scene = new Scene(loader.load(), 1280, 720);
    mainStage.setResizable(false);
    stage.setTitle(i18n.format("app.title", "v1.0"));
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}