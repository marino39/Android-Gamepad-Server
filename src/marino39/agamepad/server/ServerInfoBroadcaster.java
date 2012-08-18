package marino39.agamepad.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ServerInfoBroadcaster implements Runnable {
	
	Thread main;
	DatagramSocket bSocket;
	private ServerConfiguration serverConfig;
	
	public ServerInfoBroadcaster(ServerConfiguration sc) {
		this.serverConfig = sc;
	}
	
	public void start() {
		try {
			SocketAddress address = new InetSocketAddress(serverConfig.getBroadcastAddress(), serverConfig.getBroadcastPort());
			bSocket = new DatagramSocket();
			bSocket.setBroadcast(true);
			bSocket.connect(address);
			main = new Thread(this);
			main.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (bSocket) {
					String server = serverConfig.getGamePadServerAddress() + ":" + serverConfig.getGamePadServerPort();
					bSocket.send(new DatagramPacket(server.getBytes(), server.getBytes().length));
				}
				Thread.sleep(1000);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	public String getGamepadServerAddress() throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		return localHost.getHostAddress();
	}
	
}
