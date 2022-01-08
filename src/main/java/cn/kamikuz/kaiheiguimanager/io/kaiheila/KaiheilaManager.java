package cn.kamikuz.kaiheiguimanager.io.kaiheila;

import cn.fightingguys.kaiheila.Rabbit;
import cn.fightingguys.kaiheila.RabbitBuilder;
import cn.kamikuz.kaiheiguimanager.EventHandler;
import cn.kamikuz.kaiheiguimanager.KaiheilaGuiManager;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.function.Function;

public class KaiheilaManager {
  public static final KaiheilaManager Instance = new KaiheilaManager();
  private Rabbit rabbit;

  public boolean init(String token) {
    try {
      this.rabbit = RabbitBuilder.builder()
          .createDefault(token)
          .build();
      rabbit.addEventListener(new EventHandler());
      return rabbit.login();
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final Rabbit getRabbit() {
    return rabbit;
  }

  public void quit(Function<Void, Void> callback) {
    if (rabbit != null) {
      new Thread(() -> {
        getRabbit().shutdown();
        callback.apply(null);
      }).start();
    }
  }
}
