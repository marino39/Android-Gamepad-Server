package marino39.agamepad.server;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException; 
import java.util.Scanner;


public class ServerConfiguration {
	
	public static final int BROADCAST_PORT = 25078;
	public static final int D3GAMEPADSERVER_PORT = 11882;
	
	private String broadcastAddress;
	private String gamePadServerAddress;
	private int broadcastPort = BROADCAST_PORT;
	private int gamePadServerPort = D3GAMEPADSERVER_PORT;
	private boolean config_ok = false;
	
	/**
	 * Get's Default Configuration for Broadcast Server & D3GamePad Server
	 * 
	 * @throws UnknownHostException
	 * @throws SocketException 
	 */
	public ServerConfiguration() throws UnknownHostException, SocketException, FileNotFoundException {
		
		File fileToRead = new File("config.txt");
		
		if(fileToRead.isFile()){
			BufferedReader config = new BufferedReader(new FileReader(fileToRead));
			ArrayList list = new ArrayList();
			String temp1;
			
			try {
				while((temp1 = config.readLine()) != null){
					list.add(temp1);
					//System.out.println(temp1); fore some tests
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			broadcastAddress = list.get(0).toString();
			//broadcastPort = Integer.parseInt(list.get(2).toString());
			gamePadServerAddress = list.get(2).toString();
			//gamePadServerPort = Integer.parseInt(list.get(4).toString());
			
			printConfig();
			config_ok = true;
			
		}
		else{
			InetAddress ia = Inet4Address.getLocalHost();
			gamePadServerAddress = ia.getHostAddress();
			NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
			List<InterfaceAddress> lia =  ni.getInterfaceAddresses();
			InetAddress bia = null; // Broadcast InetAddress
		
			for (int i = 0; i < lia.size(); i++) {
				InterfaceAddress ifa= lia.get(i);
				if (ifa.getAddress().getHostAddress().equalsIgnoreCase(ia.getHostAddress())) {
					bia = ifa.getBroadcast();
				}
			}
		
			broadcastAddress = "255.255.255.255";	
			printConfig();
			config_ok = true;
		}
	}
	
	public String getBroadcastAddress() {
		return broadcastAddress;
	}



	public void setBroadcastAddress(String broadcastAddress) {
		this.broadcastAddress = broadcastAddress;
	}



	public String getGamePadServerAddress() {
		return gamePadServerAddress;
	}



	public void setGamePadServerAddress(String gamePadServerAddress) {
		this.gamePadServerAddress = gamePadServerAddress;
	}



	public int getBroadcastPort() {
		return broadcastPort;
	}



	public void setBroadcastPort(int broadcastPort) {
		this.broadcastPort = broadcastPort;
	}

	public int getGamePadServerPort() {
		return gamePadServerPort;
	}

	public void setGamePadServerPort(int gamePadServerPort) {
		this.gamePadServerPort = gamePadServerPort;
	}

	public boolean isConfig_ok() {
		return config_ok;
	}

	public void printConfig() {
		GUI.jtaLogArea.append("BroadCast Server IP: " + broadcastAddress +"\n"+
		"BroadCast Server PORT: " + broadcastPort +"\n"+
		"D3GAmePad Server IP: " + gamePadServerAddress+"\n"+
		"D3GAmePad Server PORT: " + gamePadServerPort+"\n");
	}
	
	public void saveConfig(){
		try {
			PrintWriter saveToFile = new PrintWriter("config.txt");
			saveToFile.println(broadcastAddress +"\r\n"+
					broadcastPort +"\r\n"+
					gamePadServerAddress+"\r\n"+
					gamePadServerPort+"\r\n");
			saveToFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("File config.txt nof found");
			e.printStackTrace();
		}
	}
	
}
