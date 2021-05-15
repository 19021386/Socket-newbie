package socket;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField textField;
    private JTabbedPane tabbedPane;
    private JButton btnNewButton;
    // _________________________________________
    ServerGUI thisServer;
    ServerSocket socket;
    BufferedReader br;
    Thread t;
    private JTextArea txtrAwaiting;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    ServerGUI frame = new ServerGUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public ServerGUI() {
	initComponents();
	thisServer = this;
    }

    private void initComponents() {
	setTitle("Server");
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 835, 674);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	contentPane.add(getLblNewLabel());
	contentPane.add(getTextField());
	contentPane.add(getTabbedPane());
	contentPane.add(getBtnNewButton());
    }

    public JLabel getLblNewLabel() {
	if (lblNewLabel == null) {
	    lblNewLabel = new JLabel("Port");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 18));
	    lblNewLabel.setBounds(103, 26, 55, 46);
	}
	return lblNewLabel;
    }

    public JTextField getTextField() {
	if (textField == null) {
	    textField = new JTextField();
	    textField.setFont(new Font("Arial", Font.PLAIN, 22));
	    textField.setBounds(181, 27, 329, 46);
	    textField.setColumns(10);
	}
	return textField;
    }

    public JTabbedPane getTabbedPane() {
	if (tabbedPane == null) {
	    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setFont(new Font("Arial", Font.PLAIN, 20));
	    tabbedPane.setBorder(null);
	    tabbedPane.setBounds(10, 73, 797, 545);
	    tabbedPane.addTab("Online", null, getTxtrAwaiting(), null);
	}
	return tabbedPane;
    }

    @Override
    public void run() {
	while (true)

	    try {

		Socket ClientSocket = socket.accept();
		if (ClientSocket != null) {

		    br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		    String ClientName = br.readLine();
		    ClientName = ClientName.substring(0, ClientName.indexOf(":"));


		    ChatPanel chatPanel = new ChatPanel(ClientSocket, "Server", ClientName);
		    tabbedPane.add(ClientName, chatPanel);
		    txtrAwaiting.append(ClientName +" has connected" + "\n");
		    chatPanel.updateUI();




		    Thread t = new Thread(chatPanel);
		    t.start();

		}



	    } catch (Exception e) {

	    }
    }

    public JButton getBtnNewButton() {
	if (btnNewButton == null) {
	    btnNewButton = new JButton("START SERVER");
	    btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
	    btnNewButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {

		    int port = 8;
		    try {

			port = Integer.parseInt(textField.getText());
		    } catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane,
			                "Port invalid, using the default port=8\nDetails: " + e,
			                "Error while read Port", JOptionPane.ERROR_MESSAGE);
		    }
		    try {

			socket = new ServerSocket(port);
			JOptionPane.showMessageDialog(contentPane, "Server is running at port: " + port, "Started server",
			                JOptionPane.INFORMATION_MESSAGE);

		    } catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Details: " + e, "Cant start server",
			                JOptionPane.ERROR_MESSAGE);
		    }


		    Thread t = new Thread(thisServer);
		    t.start();
		}
	    });
	    btnNewButton.setFont(new Font("Ink Free", Font.BOLD, 18));
	    btnNewButton.setBounds(538, 28, 201, 47);
	}
	return btnNewButton;
    }
	private JTextArea getTxtrAwaiting() {
		if (txtrAwaiting == null) {
			txtrAwaiting = new JTextArea();
			txtrAwaiting.setFont(new Font("Monospaced", Font.PLAIN, 27));
			txtrAwaiting.setText("Awaiting..." +"\n");
		}
		return txtrAwaiting;
	}
}