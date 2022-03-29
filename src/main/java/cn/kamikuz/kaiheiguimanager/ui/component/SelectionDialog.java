package cn.kamikuz.kaiheiguimanager.ui.component;

import cn.fightingguys.kaiheila.entity.MemberEntity;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SelectionDialog<T> extends DialogPane {
  Dialog<MemberEntity> dialog;

  public SelectionDialog(List<T> items, String title, String propertyName, Callback<T> callback) {
    dialog = new Dialog<>();
    dialog.setOnCloseRequest(event -> {
      event.consume();
      dialog.close();
    });
    SelectionDialogController<T> controller = new SelectionDialogController<>(items, propertyName);
    controller.getChoiceBox().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        dialog.setResult((MemberEntity) newValue.item);
      }
    });
    dialog.setDialogPane(controller);
    dialog.setTitle(title);
    Optional<MemberEntity> result = dialog.showAndWait();
    if (result.isPresent()) {
      SelectionDialogController<T>.SelectionItem<T> selected = controller.getChoiceBox().getSelectionModel().getSelectedItem();
      callback.onSelected(selected.item);
    }
  }

  @FunctionalInterface
  public interface Callback<T> {
    void onSelected(T selected);
  }
}

class SelectionDialogController<T> extends DialogPane {
  @FXML
  Label fieldLabel;
  @FXML
  ChoiceBox<SelectionItem<T>> choiceBox;

  public SelectionDialogController(List<T> items, String propertyName) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/SelectionDialog.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    try {
      loader.load();
      this.fieldLabel.setText(propertyName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ChoiceBox<SelectionItem<T>> getChoiceBox() {
    return choiceBox;
  }

  public class SelectionItem<E> {
    public String name;
    public T item;

    public SelectionItem(T item, String name) {
      this.item = item;
      this.name = name;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }
  }
}
