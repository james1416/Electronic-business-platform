package com.chatroom.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;



@ServerEndpoint("/websocketDemo/{userName}")  
public class websocketDemo {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<String, Session>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onopen(@PathParam("userName") String userName, Session userSession) {
	//這邊沒問題
		sessionsMap.put(userName, userSession);
		Set<String> userNames = sessionsMap.keySet();
		State stateInfo = new State("open", userName, userNames);
		String stateInfoJSON = gson.toJson(stateInfo);
		Collection<Session> sessions = sessionsMap.values();
		for(Session session : sessions) {
			if(session.isOpen()) {
				session.getAsyncRemote().sendText(stateInfoJSON);
			}
		}
		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
		
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {

		//拿到資訊先json轉java物件為了要拿到sender跟receiver
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		//拿發送者跟接收者資訊

		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		String time= chatMessage.getTime();
		//如果使用者前端按下朋友名字列表
		//history OK!
		if("history".equals(chatMessage.getType())) {

			//redis中查歷史資料
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			//轉回json格式的字串物件
			
			String historyMsg = gson.toJson(historyData);
			//參數都拿完後裝進建構子物件
			
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg, time);
			//再把java物件轉成json格式一次  然後丟回前端
			if(userSession!=null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}		
		}
		System.out.println("RIOT - Overkill");
		//拿到資料送回去
		Session receiverSession = sessionsMap.get(receiver);
		
		if(receiverSession!=null && receiverSession.isOpen()) {
			
			System.out.println(receiverSession);
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			//這邊的message沒有被轉成java物件而是直接被推播回去 
			//要儲存的則是到saveChatmessage那邊才轉
			
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		//0723嘗試對離線狀態的使用者發訊息
		else if(receiverSession==null ) {
			System.out.println("helloOffline");
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		System.out.println("Message received: " + message);
		
	}
		@OnError
		public void onError(Session userSession, Throwable e) {
			System.out.println("Error: " + e.toString());
	}
		@OnClose
		public void onClose(Session userSession, CloseReason reason) {
			System.out.println("123456");
			String userNameClose = null;
			Set<String> userNames = sessionsMap.keySet();
			//遍歷所有從sessionMap中拿出的名字 並比對他們的session是否相同
			//相同就remove
			for(String userName:userNames){
				if(sessionsMap.get(userName).equals(userSession)) {
					userNameClose = userName;
					sessionsMap.remove(userName);
					break;
				}
			}
			if (userNameClose != null) {
				System.out.println("我到這了");
				State stateMessage = new State("close", userNameClose, userNames);
				String stateMessageJson = gson.toJson(stateMessage);
				Collection<Session> sessions = sessionsMap.values();
				for (Session session : sessions) {
					session.getAsyncRemote().sendText(stateMessageJson);
				}
			}
			String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
					reason.getCloseCode().getCode(), userNames);
			System.out.println(text);
		}
}
