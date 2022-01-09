package cn.kamikuz.kaiheiguimanager.ui.component.kmarkdown.element;

import javafx.beans.NamedArg;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class KmarkdownComponentButton extends Button {
  public final Type type;

  /**
   * Creates a button with an empty string for its label.
   */
  public KmarkdownComponentButton(@NamedArg("type") Type type) {
    super();
    this.type = type;
  }

  /**
   * Creates a button with the specified text as its label.
   *
   * @param text A text string for its label.
   */
  public KmarkdownComponentButton(String text, @NamedArg("type") Type type) {
    super(text);
    this.type = type;
  }

  /**
   * Creates a button with the specified text and icon for its label.
   *
   * @param text    A text string for its label.
   * @param graphic the icon for its label.
   */
  public KmarkdownComponentButton(String text, Node graphic, @NamedArg("type") Type type) {
    super(text, graphic);
    this.type = type;
  }

  public enum Type {
    Bold,
    Underline,
    Italic,
    StrikeThrough,
    Code,
    Link,
    Quote,
    DividerLine,
    CodeBlock,
    Emoji,
    ServerEmoji,
    HeiMu,
    Role,
    User,
    Channel
  }
}
