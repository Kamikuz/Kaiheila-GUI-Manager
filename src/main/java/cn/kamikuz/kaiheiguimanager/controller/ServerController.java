package cn.kamikuz.kaiheiguimanager.controller;

import cn.fightingguys.kaiheila.api.Channel;
import cn.fightingguys.kaiheila.api.Guild;
import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

public class ServerController extends PrototypeController{
  @FXML
  private Label serverName;
  @FXML
  private ListView<Button> channelList;

  @FXML
  private SplitPane splitPane;

  @Override
  public void initialize() {

  }

  @Override
  public void prepare(Object data) {
    Guild guild = (Guild) data;
    serverName.setText(guild.getName());
    channelList.getItems().clear();
    for (Channel channel : guild.getChannels()) {
      if (channel.getChannelTypeRaw() == 1) {
        Button button = new Button(channel.getName());
        button.setPrefHeight(40);
        button.setPrefWidth(channelList.getParent().getBoundsInLocal().getWidth());
        button.setOnAction(event -> {
          System.out.println(channel.getName());
        });
        channelList.getItems().add(button);
      }
    }
    channelList.refresh();
  }

  @FXML
  private void back() {
    KaiheilaGuiManager.navTo(KaiheilaGuiManager.Pages.Main);
  }
}
