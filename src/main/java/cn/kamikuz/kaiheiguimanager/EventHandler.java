package cn.kamikuz.kaiheiguimanager;

import cn.fightingguys.kaiheila.Rabbit;
import cn.fightingguys.kaiheila.event.message.TextMessageEvent;
import cn.fightingguys.kaiheila.hook.EventListener;

public class EventHandler extends EventListener {
  @Override
  public void onTextMessageEvent(Rabbit rabbit, TextMessageEvent event) {
    // 监听文本消息事件内容
    System.out.println(event.getEventContent());
  }
}
