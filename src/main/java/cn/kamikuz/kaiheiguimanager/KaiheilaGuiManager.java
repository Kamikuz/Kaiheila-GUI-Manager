package cn.kamikuz.kaiheiguimanager;

import cn.kamikuz.kaiheiguimanager.controller.PrototypeController;
import cn.kamikuz.kaiheiguimanager.i18n.i18n;
import cn.kamikuz.kaiheiguimanager.io.kaiheila.KaiheilaManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class KaiheilaGuiManager extends Application {
  public enum Pages {
    Setup("setup"),
    Main("main-page"),
    Server("server"),
    ;
    final String page;

    Pages(String page) {
      this.page = page;
    }

    public String getPage() {
      return page;
    }

    public FXMLLoader getFXML() {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/views/" + page + ".fxml"));
      fxmlLoader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
      return fxmlLoader;
    }
  }
  private static Stage mainStage;

  public static void navTo(Pages page, Object data) {
    try {
      FXMLLoader loader = page.getFXML();
      Scene temp = new Scene(loader.load(), 1280, 720);
      PrototypeController controller = loader.getController();
      controller.prepare(data);
      mainStage.setScene(temp);
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
      KaiheilaManager.Instance.quit(null);
      Platform.exit();
      System.exit(0);
    });
    FXMLLoader loader = Pages.Setup.getFXML();
    Scene scene = new Scene(loader.load(), 1280, 720);
    stage.setResizable(false);
    stage.setTitle(i18n.format("app.title", "v1.0 beta"));
    stage.setScene(scene);
    mainStage = stage;
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}