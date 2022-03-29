package cn.kamikuz.kaiheiguimanager.ui.controller;

import cn.fightingguys.kaiheila.api.Channel;
import cn.fightingguys.kaiheila.api.Guild;
import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import cn.kamikuz.kaiheiguimanager.ui.component.MessageEditor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

public class ServerController extends PrototypeController {
  protected Channel selectedChannel;
  MessageEditor editorController;
  @FXML
  private Label serverName;
  @FXML
  private ListView<ToggleButton> channelList;
  @FXML
  private BorderPane editor;

  @Override
  public void initialize() {
    editorController = new MessageEditor(editor, this);
  }

  @Override
  public void prepare(Object data) {
    Guild guild = (Guild) data;
    serverName.setText(guild.getName());
    channelList.getItems().clear();
    for (Channel channel : guild.getChannels()) {
      if (channel.getChannelTypeRaw() == 1) {
        ToggleButton button = new ToggleButton(channel.getName());
        button.setPrefHeight(40);
        channelList.widthProperty().addListener((observable, oldValue, newValue) -> {
          button.setPrefWidth(newValue.doubleValue() - 10);
        });
        button.setOnAction(event -> {
          button.setSelected(true);
          selectedChannel = channel;
          channelList.getItems().filtered(btn -> !btn.equals(button)).forEach(btn -> btn.setSelected(false));
        });
        channelList.getItems().add(button);
      }
    }
    channelList.refresh();
  }

  public Channel getSelectedChannel() {
    return selectedChannel;
  }

  @FXML
  private void back() {
    KaiheilaGuiManager.navTo(KaiheilaGuiManager.Pages.Main);
  }
}
