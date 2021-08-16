package thisistest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chattest/{userName}")
public class chattest {
	private static final Set<Session> cs = Collections.synchronizedSet(new HashSet<>());
	
	// 有一個OnOpen的方法 可以讓前端new出webSocket之後加入到我們的連線當中
	@OnOpen
	public void onOpen(@PathParam("userName") String userName , Session userSession) {
		System.out.println("安安，"+ userName+"，" + userSession+"我進來了");
		cs.add(userSession);
		System.out.println("裡面有"+cs);
		
	}
	@OnMessage
	public void OnMessage(Session userSession , String message) {
//		System.out.println("三十一行:"+message);
		// 找出目前所有的Session，接著將某個session所發送的訊息，丟給所有的session
		for(Session s:cs) {
			if(s.isOpen()) {
				s.getAsyncRemote().sendText(message);
				//此句可以看出session有幾個(也就是說幾個連線)
//				System.out.println("三十五行:"+s.getAsyncRemote());
			}
			System.out.println("39行:"+s.getAsyncRemote());
			System.out.println("40行:"+message);
		}
	}
	
	
	@OnClose
	public void onClose(Session userSession , CloseReason reason) {
		System.out.println("安安，"+ userSession + "要出去了");
		cs.remove(userSession);
		System.out.println("斷開後裡面有"+cs);
	}
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
}
