package marino39.agamepad.server;
import java.awt.AWTException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) {
		try {
			// Config
			ServerConfiguration sc = new ServerConfiguration();

			if (sc.isConfig_ok()) {
				// D3GamePad Server
				EmulationServer s = new EmulationServer(sc);
				s.start();
			
				// Broadcast Server
				ServerInfoBroadcaster sib = new ServerInfoBroadcaster(sc);
				sib.start();
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}	
	}
}
