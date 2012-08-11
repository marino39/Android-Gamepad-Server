package marino39.agamepad.server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	
	JTextField jtfBroadcastPort = new JTextField();
	JTextField jtfServerPort = new JTextField();
	JTextField jtfServerName = new JTextField();
	
	static JTextArea jtaLogArea = new JTextArea();
	JScrollPane jspLogArea = new JScrollPane(jtaLogArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JButton jbStartBroadcast = new JButton("Start Broadcast");
	JButton jbStartServer = new JButton("Start Server");
	
	public GUI(final ServerConfiguration sc){
		super("Serwer");
		
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
		jtfBroadcastPort.setEditable(false);
		jtfBroadcastPort.setText(Integer.toString(sc.getBroadcastPort()));
		
		serverPanel.add(jtfServerPort);
		jtfServerPort.setBounds(105,340,100,20);
		jtfServerPort.setEditable(false);
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
		serverPanel.add(jspLogArea);
		
		// Broadcast Server
		jbStartBroadcast.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sc.isConfig_ok()){
					ServerInfoBroadcaster sib = new ServerInfoBroadcaster(sc);
					sib.start();
					label7.setText("Online");
					label7.setForeground(Color.RED);
				}
			}
		});
		
		// D3GamePad Server
		jbStartServer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (sc.isConfig_ok()){
					EmulationServer s = new EmulationServer(sc);
					try {
						s.start();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
					label9.setText("Online");
					label9.setForeground(Color.RED);
				}
			}
		});
		
		
		add(serverPanel);
		setLocation(400,150);
		setPreferredSize(new Dimension(500,410));
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
