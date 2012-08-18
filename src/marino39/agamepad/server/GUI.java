package marino39.agamepad.server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


import javax.swing.*;


public class GUI extends JFrame{
	
	JLabel label1 = new JLabel("Log:");
	JLabel label2 = new JLabel("Broadcast Port:");
	JLabel label3 = new JLabel("Server Port:");
	JLabel label4 = new JLabel("Server Name:");
	JLabel label5 = new JLabel("Status:");
	JLabel label6 = new JLabel("Broadcast Server:");
	JLabel label7 = new JLabel("Offline");
	JLabel label8 = new JLabel("Remote Control Server:");
	JLabel label9 = new JLabel("Offline");
	
	static JTextField jtfBroadcastPort = new JTextField();
	static JTextField jtfServerPort = new JTextField();
	JTextField jtfServerName = new JTextField();
	
	static JTextArea jtaLogArea = new JTextArea();
	JScrollPane jspLogArea = new JScrollPane(jtaLogArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	JButton jbStartBroadcast = new JButton("Start Broadcast");
	JButton jbStartServer = new JButton("Start Server");
	
	private static ServerInfoBroadcaster sib;
	private static EmulationServer s;
	
	boolean flagaBroadcast = true;
	boolean flagaServer = true;
	
	File fileToRead = new File("config.txt");
	
	public GUI() throws Exception {
		super("Serwer");
		
		final ServerConfiguration sc = new ServerConfiguration();
		final TrayIcon trayIcon;
		
		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(null);
		
		
		serverPanel.add(label1);
		label1.setBounds(7,5,40,20);
		
		serverPanel.add(label2);
		label2.setBounds(7,310,90,20);
		
		serverPanel.add(label3);
		label3.setBounds(7,340,90,20);
		
		serverPanel.add(label4);
		label4.setBounds(230,310,90,20);
		
		serverPanel.add(label5);
		label5.setBounds(320,115,60,20);
		
		serverPanel.add(label6);
		label6.setBounds(320,140,150,20);
		
		serverPanel.add(label7);
		label7.setBounds(320,160,100,20);
		
		serverPanel.add(label8);
		label8.setBounds(320,185,150,20);
		
		serverPanel.add(label9);
		label9.setBounds(320,205,100,20);
		
		
		serverPanel.add(jtfBroadcastPort);
		jtfBroadcastPort.setBounds(105,310,100,20);
		jtfBroadcastPort.setEditable(true);
		jtfBroadcastPort.setText(Integer.toString(sc.getBroadcastPort()));
		
		serverPanel.add(jtfServerPort);
		jtfServerPort.setBounds(105,340,100,20);
		jtfServerPort.setEditable(true);
		jtfServerPort.setText(Integer.toString(sc.getGamePadServerPort()));
		
		serverPanel.add(jtfServerName);
		jtfServerName.setBounds(315,310,100,20);
		jtfServerName.setEditable(false);
		jtfServerName.setText(sc.getGamePadServerAddress());
		
		
		serverPanel.add(jbStartBroadcast);
		jbStartBroadcast.setBounds(320,25,150,30);
		
		serverPanel.add(jbStartServer);
		jbStartServer.setBounds(320,65,150,30);
		
		
		jspLogArea.setBounds(7,25,300,270);
		jtaLogArea.setEditable(false);
		jtaLogArea.setCaretPosition(jtaLogArea.getDocument().getLength()); 
		serverPanel.add(jspLogArea);
		
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
			
			jtfBroadcastPort.setText(list.get(1).toString());
			jtfServerPort.setText(list.get(3).toString());
			jtaLogArea.append("\n:: Server and Broadcast Ports loaded from file");
		}else{
			jtaLogArea.append("\n:: Server and Broadcast Ports are default");
		}
		
		// Broadcast Server
		jbStartBroadcast.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sc.isConfig_ok() && flagaBroadcast == true){
					sib = new ServerInfoBroadcaster(sc);
					sib.start();
					if((jtfBroadcastPort != null)){
						sc.broadcastPort = Integer.parseInt(jtfBroadcastPort.getText());
					}
					label7.setText("Online");
					label7.setForeground(Color.GREEN);
					jbStartBroadcast.setLabel("Stop Broadcast");
					jtaLogArea.append("\n:: Broadcast started");
					flagaBroadcast = false;
				}else if(sc.isConfig_ok() && flagaBroadcast == false){
					sib.main.stop();
					sib.bSocket.close();
					label7.setText("Offline");
					label7.setForeground(Color.RED);
					jbStartBroadcast.setLabel("Start Broadcast");
					jtaLogArea.append("\n:: Broadcast stopped");
					flagaBroadcast = true;
				}
			}
		});
		
		// D3GamePad Server
		jbStartServer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (sc.isConfig_ok() && flagaServer == true){
					if((jtfServerName != null)){
						sc.gamePadServerPort = Integer.parseInt(jtfServerPort.getText());
					}
					sc.saveConfig();
					s = new EmulationServer(sc);
					try {
						s.start();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
					label9.setText("Online");
					label9.setForeground(Color.GREEN);
					flagaServer = false;
					jbStartServer.setLabel("Stop Server");
				}else if(sc.isConfig_ok() && flagaServer == false){
					s.threadA.stop();
					s.threadB.stop();
					try {
						s.server.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					label9.setText("Offline");
					label9.setForeground(Color.RED);
					jbStartServer.setLabel("Start Server");
					
					flagaServer = true;
					jtaLogArea.append("\n:: Server stoped");
					jtaLogArea.append("\n");
					
				}
			}
		});
		
		
		add(serverPanel);
		setLocation(400,150);
		setPreferredSize(new Dimension(500,410));
		setResizable(false);
		pack();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
		
		
		//Tray icon
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = Toolkit.getDefaultToolkit().getImage("ticon2.gif");

		    ActionListener exitListener = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            System.exit(0);
		        }
		    };
		    ActionListener maxListener = new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		setVisible(true);
		    	}
		    };
		            
		    PopupMenu popup = new PopupMenu();
		    MenuItem exitItem = new MenuItem("Exit");
		    MenuItem maxItem = new MenuItem("Open");
		    maxItem.addActionListener(maxListener);
		    exitItem.addActionListener(exitListener);
		    popup.add(maxItem);
		    popup.add(exitItem);

		    trayIcon = new TrayIcon(image, "Android Gamepad Server", popup);

		    ActionListener actionListener = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            trayIcon.displayMessage("Android Gamepad Server", 
		                "This is Android Gamepad Server app.\nHave fun!",
		                TrayIcon.MessageType.INFO);
		            	setVisible(true);
		        }
		    };
		            
		    trayIcon.setImageAutoSize(true);
		    trayIcon.addActionListener(actionListener);

		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println("TrayIcon could not be added.");
		    }

		} else {
			System.err.println("System Tray is not supported");
		}
	}
}
