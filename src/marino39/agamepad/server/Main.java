package marino39.agamepad.server;
import java.awt.AWTException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) throws Exception{
		
			// Config
			ServerConfiguration sc = new ServerConfiguration();
			
			// GUi ;)
			new GUI(sc);
			
			//if (sc.isConfig_ok()) {
				// D3GamePad Server
				//EmulationServer s = new EmulationServer(sc);
				//s.start();
			
				// Broadcast Server
				//ServerInfoBroadcaster sib = new ServerInfoBroadcaster(sc);
				//sib.start();
			//}
	}
}
