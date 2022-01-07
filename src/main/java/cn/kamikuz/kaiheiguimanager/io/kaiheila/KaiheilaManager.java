package cn.kamikuz.kaiheiguimanager.io.kaiheila;

import cn.fightingguys.kaiheila.Rabbit;
import cn.fightingguys.kaiheila.RabbitBuilder;
import cn.kamikuz.kaiheiguimanager.EventHandler;

public class KaiheilaManager {
  public static final KaiheilaManager Instance = new KaiheilaManager();
  private Rabbit rabbit;

  public boolean init(String token) {
    try {
      this.rabbit = RabbitBuilder.builder()
          .createDefault(token)
          .build();
      rabbit.addEventListener(new EventHandler());
      rabbit.login();
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
