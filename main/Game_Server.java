package project.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import project.network.Client;
import project.network.Dispatcher;
import project.util.Constant;

public class Game_Server {
	
	public static ServerSocket dataServerSocket;
	public static ServerSocket echoServerSocket;

	public volatile static ArrayList<Client> players;
	
	public static void main(String[] args) {
		
		
		players = new ArrayList<Client>();
		startServers(10);
		Thread t1 = new Thread(new Dispatcher());
		t1.start();
		
	}
	
	private static boolean startServers(int counter) {
		try {
			dataServerSocket = new ServerSocket(Constant.DATA_PORT);
			System.out.println("----- Positions Server started on port " + Constant.DATA_PORT + " -----");
			echoServerSocket = new ServerSocket(Constant.ECHO_PORT);
			System.out.println("----- Echo Positions Server started on port " + Constant.ECHO_PORT + " -----");
			
			return true;
		} catch (IOException e) {
			while(counter != 0) {
				System.out.println("----- Could not start servers on ports. -----");
				counter -= 1;
				
				try{TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e1){}
				
				startServers(counter);
			}
				System.out.println("----- Could not start server. Please restart. -----");
				System.exit(1);
		}
		
		return false;
	}
	
	
	public static void registerPlayer(Client client) {
		
		
		players.add(client);
		
	}
	
	public static void deregisterPlayer(Client client) {
		
		players.remove(client);
		
	}

}
