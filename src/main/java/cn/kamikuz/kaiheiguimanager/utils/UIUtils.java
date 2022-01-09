package cn.kamikuz.kaiheiguimanager.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;

public class UIUtils {
  /**
   * The interface Ui reaction.
   *
   * @param <T> the type parameter
   */
  public interface UIReaction<T> {
    /**
     * Call the callback.
     *
     * @param res the res
     * @throws IOException the io exception
     */
    void call(T res) throws IOException;
  }

  /**
   * Simple alert.
   *
   * @param type    the type
   * @param title   the title
   * @param content the content
   */
  public static void Alert(Alert.AlertType type, String title, String content) {
    Platform.runLater(() -> {
      Alert alert = new Alert(type);
      alert.setTitle(title);
      alert.setContentText(content);
      alert.show();
    });
  }

  /**
   * Alert with acknowledgement.
   *
   * @param type     the type
   * @param title    the title
   * @param content  the content
   * @param callback the callback
   */
  public static void Alert(Alert.AlertType type, String title, String content, UIReaction<Void> callback) {
    Platform.runLater(() -> {
      ButtonType requestMenuBtn = new ButtonType("Refresh Menu");
      Alert alert = new Alert(type, content, requestMenuBtn);
      alert.setTitle(title);
      alert.setContentText(content);
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == requestMenuBtn) {
        try {
          callback.call(null);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      alert.show();
    });
  }

  /**
   * Promote text, with callback.
   *
   * @param title    the title
   * @param header   the header
   * @param label    the label
   * @param callback the callback
   */
  public static void PromoteText(String title, String header, String label, UIReaction<Optional<String>> callback) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    dialog.setContentText(label);
    Platform.runLater(() -> {
      try {
        callback.call(dialog.showAndWait());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
