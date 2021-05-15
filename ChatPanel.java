package socket;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

public class ChatPanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTextArea ChatArea;
    private JButton SEND;
    private JLabel Chat;
    private JLabel History;
    private JTextArea HistoryArea;
    Socket socket = null;
    String sender;
    String receiver;
    BufferedReader bf = null;
    DataOutputStream os = null;
    private JScrollPane scrollPane_1;
    


    @Override
    public void run() {
	while (true) {
	    try {
		if (socket != null) {
		    String message = "";
		    while ((message = bf.readLine()) != null) {

			HistoryArea.append(message + "\n");
		    }
		}
	    } catch (Exception e) {

	    }
	}
    }

    public ChatPanel(Socket s, String sender, String receiver) {
	initComponents();
	socket = s;
	this.sender = sender;
	this.receiver = receiver;
	try {
	    
	    JScrollPane scrollPane = new JScrollPane();

	    GroupLayout groupLayout = new GroupLayout(this);
	    groupLayout.setHorizontalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addGap(332)
	    			.addComponent(getLblHistory(), GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
	    			.addGap(339))
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    				.addGroup(groupLayout.createSequentialGroup()
	    					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    						.addGroup(groupLayout.createSequentialGroup()
	    							.addGap(180)
	    							.addComponent(getLblNewLabel(), GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
	    							.addGap(269))
	    						.addGroup(groupLayout.createSequentialGroup()
	    							.addContainerGap()
	    							.addComponent(getScrollPane_1(), GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
	    							.addGap(18)))
	    					.addGap(8)
	    					.addComponent(getBtnNewButton(), GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
	    				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
	    					.addContainerGap()
	    					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 762, GroupLayout.PREFERRED_SIZE)))
	    			.addContainerGap(21, Short.MAX_VALUE))
	    );
	    groupLayout.setVerticalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addComponent(getLblHistory(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    				.addGroup(groupLayout.createSequentialGroup()
	    					.addComponent(getLblNewLabel(), GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
	    					.addPreferredGap(ComponentPlacement.RELATED)
	    					.addComponent(getScrollPane_1(), GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
	    				.addComponent(getBtnNewButton(), GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
	    			.addGap(13))
	    );
	    scrollPane.setViewportView(getTextArea_1());
	    bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    os = new DataOutputStream(socket.getOutputStream());

	    setLayout(groupLayout);
	    (new Thread(this)).start();
	    
	} catch (Exception e) {
	    System.out.println("Error while create Main Panel");
	}

    }

    private void initComponents() {
    }

    public JTextArea getTextArea() {
	if (ChatArea == null) {
	    ChatArea = new JTextArea();
	    ChatArea.setBackground(Color.WHITE);
	    ChatArea.setFont(new Font("Arial", Font.PLAIN, 26));
	}
	return ChatArea;
    }

    public JButton getBtnNewButton() {
	if (SEND == null) {
	    SEND = new JButton("Gửi");
	    SEND.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    if (ChatArea.getText().isEmpty()) return;
		    try {
			os.writeBytes(sender + ": " + ChatArea.getText() + "\n");
			os.flush();
			HistoryArea.append(sender + ": " + ChatArea.getText() + "\n");
			ChatArea.setText("");
		    } catch (Exception e) {
			System.out.println("Error while sendding messeger");
		    }
		}
	    });
	    SEND.setBackground(Color.ORANGE);
	    SEND.setForeground(Color.RED);
	    SEND.setFont(new Font("Tahoma", Font.BOLD, 26));
	}
	return SEND;
    }

    public JLabel getLblNewLabel() {
	if (Chat == null) {
	    Chat = new JLabel("Chat");
	    Chat.setHorizontalAlignment(SwingConstants.CENTER);
	    Chat.setForeground(Color.RED);
	    Chat.setFont(new Font("Arial", Font.ITALIC, 18));
	}
	return Chat;
    }

    public JLabel getLblHistory() {
	if (History == null) {
	    History = new JLabel("Lịch sử");
	    History.setHorizontalAlignment(SwingConstants.CENTER);
	    History.setForeground(Color.MAGENTA);
	    History.setFont(new Font("Arial", Font.ITALIC, 18));
	}
	return History;
    }

    public JTextArea getTextArea_1() {
	if (HistoryArea == null) {
	    HistoryArea = new JTextArea();
	    HistoryArea.setFont(new Font("Arial", Font.PLAIN, 30));
	}
	return HistoryArea;
    }
    
    public void connect(String name) {
	    try {
		os.writeBytes(name +": " + "1" +"\n");
		os.flush();
	    } catch (Exception e) {
		System.out.println("Error while sendding messeger");
	    }
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTextArea());
		}
		return scrollPane_1;
	}
}