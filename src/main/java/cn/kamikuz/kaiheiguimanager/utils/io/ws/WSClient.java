package cn.kamikuz.kaiheiguimanager.utils.io.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.extensions.permessage_deflate.PerMessageDeflateExtension;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class WSClient extends WebSocketClient {
  private static final Draft perMessageDeflateDraft = new Draft_6455(new PerMessageDeflateExtension());

  /**
   * Constructs a WebSocketClient instance and sets it to the connect to the specified URI. The
   * channel does not attampt to connect automatically. The connection will be established once you
   * call <var>connect</var>.
   *
   * @param serverUri the server URI to connect to
   */
  public WSClient(URI serverUri) {
    super(serverUri, perMessageDeflateDraft);
  }

  /**
   * Called after an opening handshake has been performed and the given websocket is ready to be
   * written on.
   *
   * @param handshakedata The handshake of the websocket instance
   */
  @Override
  public void onOpen(ServerHandshake handshakedata) {

  }

  /**
   * Callback for string messages received from the remote host
   *
   * @param message The UTF-8 decoded message that was received.
   * @see #onMessage(ByteBuffer)
   **/
  @Override
  public void onMessage(String message) {

  }

  /**
   * Callback for binary messages received from the remote host
   *
   * @param bytes The binary message that was received.
   * @see #onMessage(String)
   **/
  @Override
  public void onMessage(ByteBuffer bytes) {

  }

  /**
   * Called after the websocket connection has been closed.
   *
   * @param code   The codes can be looked up here:
   * @param reason Additional information string
   * @param remote Returns whether or not the closing of the connection was initiated by the remote
   **/
  @Override
  public void onClose(int code, String reason, boolean remote) {

  }

  /**
   * Called when errors occurs. If an error causes the websocket connection to fail {@link
   * #onClose(int, String, boolean)} will be called additionally.<br> This method will be called
   * primarily because of IO or protocol errors.<br> If the given exception is an RuntimeException
   * that probably means that you encountered a bug.<br>
   *
   * @param ex The exception causing this error
   **/
  @Override
  public void onError(Exception ex) {

  }
}
