package cn.kamikuz.kaiheiguimanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

import java.util.Optional;

public abstract class PrototypeController {
  private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

  /**
   * Override this function if you want to render dynamic content.
   */
  public void initialize() {
  }

  /**
   * Prepare the data when changing the scene, override this function if you want to carry parameters data when changing the scene
   *
   * @param data the params of data
   */
  public void prepare(Object data) {
  }

  /**
   * Simple Alert.
   *
   * @param type    the type
   * @param title   the title
   * @param content the content
   */
  protected void alert(Alert.AlertType type, String title, String content) {
    alert.setAlertType(type);
    alert.setHeaderText(title);
    alert.setContentText(content);
    alert.show();
  }

  /**
   * Promote with user input and callback.
   *
   * @param type     the type
   * @param title    the title
   * @param content  the content
   * @param callback the callback
   */
  protected void promote(Alert.AlertType type, String title, String content, Callback<?, ?> callback) {
    alert.setAlertType(type);
    alert.setHeaderText(title);
    alert.setContentText(content);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      callback.call(null);
    }
  }

  /**
   * Help action, this is a global function.
   */
  @FXML
  protected void helpAction() {
    alert.setAlertType(Alert.AlertType.INFORMATION);
    alert.setHeaderText("Help");
    alert.setContentText("The waitress is on the way...");
    alert.show();
  }
}
