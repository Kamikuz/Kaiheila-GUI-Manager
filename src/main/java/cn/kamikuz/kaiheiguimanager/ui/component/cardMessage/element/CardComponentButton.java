package cn.kamikuz.kaiheiguimanager.ui.component.cardMessage.element;

import cn.fightingguys.kaiheila.entity.cardmessage.CardMessage;
import cn.kamikuz.kaiheiguimanager.ui.component.cardMessage.CardMessageEditor;
import javafx.scene.control.Button;


public class CardComponentButton extends Button {
  public final CardMessage.Module component;

  /**
   * Creates a button with the specified text as its label.
   *
   * @param text A text string for its label.
   */
  private CardComponentButton(String text, CardMessage.Module component, CardMessageEditor editor) {
    super(text);
    this.component = component;
    setOnAction(event -> {
      editor.cardMessageBuilder.current().add(component);
    });
  }

  public static CardComponentButton[] init(CardMessageEditor editor) {
    return new CardComponentButton[]{
        new CardComponentButton("Header", CardMessage.Module.Header(), editor),
    };
  }
}
