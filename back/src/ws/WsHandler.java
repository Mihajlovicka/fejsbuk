package ws;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import rest.SparkAppMain;

@WebSocket
public class WsHandler {

	private String sender, msg;

	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception {
		String sender = session.getUpgradeRequest().getParameterMap().get("sender").get(0);
		String receiver = session.getUpgradeRequest().getParameterMap().get("receiver").get(0);
		ArrayList<String > s = new ArrayList<>();
		s.add(sender);s.add(receiver);
		SparkAppMain.userUsernameMap.put(s, session);
//		SparkAppMain.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
		//vratiti sve poruke
	}

	@OnWebSocketClose
	public void onClose(Session user, int statusCode, String reason) {
//		String username = SparkAppMain.userUsernameMap.get(user);
//		SparkAppMain.userUsernameMap.remove(user);
//		SparkAppMain.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) {
		String sender = session.getUpgradeRequest().getParameterMap().get("sender").get(0);
		String receiver = session.getUpgradeRequest().getParameterMap().get("receiver").get(0);
		SparkAppMain.broadcastMessage(sender , receiver, msg = message);
		//sacuvati poruku
	}

}
