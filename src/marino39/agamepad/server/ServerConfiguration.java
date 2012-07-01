package marino39.agamepad.server;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;


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
	public ServerConfiguration() throws UnknownHostException, SocketException {
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

	private void printConfig() {
		System.out.println("BroadCast Server IP: " + broadcastAddress);
		System.out.println("BroadCast Server PORT: " + broadcastPort);
		System.out.println("D3GAmePad Server IP: " + gamePadServerAddress);
		System.out.println("D3GAmePad Server PORT: " + gamePadServerPort);
	}
	
}
