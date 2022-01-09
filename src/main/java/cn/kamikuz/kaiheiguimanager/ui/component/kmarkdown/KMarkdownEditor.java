package cn.kamikuz.kaiheiguimanager.ui.component.kmarkdown;

import cn.fightingguys.kaiheila.entity.kmarkdown.KMarkdownBuilder;
import cn.kamikuz.kaiheiguimanager.ui.component.kmarkdown.element.KmarkdownComponentButton;
import cn.kamikuz.kaiheiguimanager.ui.controller.ServerController;
import cn.kamikuz.kaiheiguimanager.utils.UIUtils;
import cn.kamikuz.kaiheiguimanager.utils.i18n.i18n;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class KMarkdownEditor extends Pane {

  KMarkdownBuilder builder = new KMarkdownBuilder();
  @FXML
  ToolBar toolBar;
  @FXML
  TextArea editor;
  @FXML
  Button sendButton;

  ServerController serverController;

  public KMarkdownEditor(Pane parent, ServerController serverController) {
    this.serverController = serverController;
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/template/kmarkdown/kmarkdown-editor.fxml"));
    loader.setResources(ResourceBundle.getBundle("lang." + i18n.lang.getLanguage()));
    loader.setController(this);
    loader.setRoot(parent);
    try {
      loader.load();
      toolBar.getItems().forEach(item -> {
        if (item instanceof KmarkdownComponentButton btn) {
          btn.setOnAction(event -> {
            switch (btn.type) {
              case Bold -> {
                editor.insertText(editor.getText().length(), "****");
                editor.positionCaret(editor.getCaretPosition() - 2);
                editor.requestFocus();
              }
              case Underline -> {
                editor.insertText(editor.getText().length(), "(ins)(ins)");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
              case Italic -> {
                editor.insertText(editor.getText().length(), "**");
                editor.positionCaret(editor.getCaretPosition() - 1);
                editor.requestFocus();
              }
              case StrikeThrough -> {
                editor.insertText(editor.getText().length(), "~~");
                editor.positionCaret(editor.getCaretPosition() - 1);
                editor.requestFocus();
              }
              case Code -> {
                editor.insertText(editor.getText().length(), "``");
                editor.positionCaret(editor.getCaretPosition() - 1);
                editor.requestFocus();
              }
              case Link -> {
                editor.insertText(editor.getText().length(), "[]()");
                editor.positionCaret(editor.getCaretPosition() - 3);
                editor.requestFocus();
              }
              case Quote -> {
                editor.insertText(editor.getText().length(), "> ");
                editor.requestFocus();
              }
              case DividerLine -> {
                editor.insertText(editor.getText().length(), "\n---\n");
                editor.requestFocus();
              }
              case CodeBlock -> {
                editor.insertText(editor.getText().length(), "```\n\n```");
                editor.positionCaret(editor.getCaretPosition() - 3);
                editor.requestFocus();
              }
              case Emoji -> {
                editor.insertText(editor.getText().length(), "::");
                editor.positionCaret(editor.getCaretPosition() - 1);
                editor.requestFocus();
              }
              case ServerEmoji -> {
                editor.insertText(editor.getText().length(), "(emj)(emj)");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
              case HeiMu -> {
                editor.insertText(editor.getText().length(), "(spl)(spl)");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
              case Role -> {
                editor.insertText(editor.getText().length(), "(rol)(rol");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
              case User -> {
                editor.insertText(editor.getText().length(), "(met)(met)");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
              case Channel -> {
                editor.insertText(editor.getText().length(), "(chn)(chn)");
                editor.positionCaret(editor.getCaretPosition() - 5);
                editor.requestFocus();
              }
            }
          });
        }
      });
      editor.setOnKeyTyped(event -> {
        sendButton.setDisable(editor.getText().isEmpty());
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void init() {
    toolBar.getItems().forEach(item -> {

    });
  }

  private void createBold() {

  }

  @FXML
  private void send() {
    switch (serverController.getSelectedChannel().getChannelOperation().broadcastMessage(getText(), true)) {
      case SUCCESS -> {
        editor.clear();
        UIUtils.Alert(Alert.AlertType.INFORMATION, "发送成功", "发送成功");
      }
      case FAILED -> {
        UIUtils.Alert(Alert.AlertType.ERROR, "发送失败", "发送失败");
      }
    }
  }

  public String getText() {
    return editor.getText();
  }
}
