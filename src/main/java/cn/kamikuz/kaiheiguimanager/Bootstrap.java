package cn.kamikuz.kaiheiguimanager;

import cn.kamikuz.kaiheiguimanager.i18n.i18n;
import com.google.common.eventbus.EventBus;

public class Bootstrap {
  private static final EventBus eventBus = new EventBus("kaiheiguimanager");

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    i18n.Instance.init();
    KaiheilaGuiManager.main(args);
  }

  /**
   * Register a lister.
   *
   * @param listener the listener
   */
  public static void registerLister(Object listener) {
    eventBus.register(listener);
  }

  /**
   * Remove a lister.
   *
   * @param listener the listener
   */
  public static void removeLister(Object listener) {
    eventBus.unregister(listener);
  }

  /**
   * Emit an event.
   *
   * @param event the event
   */
  public static void emit(Object event) {
    eventBus.post(event);
  }
}
