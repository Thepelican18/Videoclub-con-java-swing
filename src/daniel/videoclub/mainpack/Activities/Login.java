package daniel.videoclub.mainpack.Activities;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import daniel.videoclub.mainpack.ActivityManager;
import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Frame;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPasswordField;

public class Login extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	boolean isChief = false;

	public Login() {

		setBackground(SystemColor.activeCaption);
		setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(379, 199, 183, 31);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(426, 342, 96, 31);
		btnNewButton.addActionListener(this);
		add(btnNewButton);

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblLogin.setBounds(379, 125, 183, 23);
		add(lblLogin);

		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsername.setBounds(273, 200, 96, 31);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(273, 270, 89, 31);
		add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(379, 270, 183, 31);
		add(passwordField);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		

		if (!isRegistered(textField.getText(), passwordField.getText())) {//en vez de usar getText deberia ser getAccsesible context porque te lo cifran, pero en este caso utilizo este metodo aunque este deprecated

			System.out.println("Incorrect username or password");
			textField.setText("");
			passwordField.setText("");
		} else {

			ActivityManager.setActualActivity(CatalogManager.getInstance(isChief, textField.getText(), "PRODUCT"));
			Frame.refresh();
			this.setVisible(false);

		}

	}

	public boolean isRegistered(String username, String password) {

		List<String> dataList = FileManager.loadFileDataIntoList(FileList.USERLOGIN);

		String login = username + "#" + password + "#";

		for (String data : dataList) {

			if (data.substring(5).equals(login)) {

				if (login.equals(dataList.get(0).substring(5))) {
					isChief = true;
					JOptionPane.showMessageDialog(null, "you have logged as chief");
				} else {
					JOptionPane.showMessageDialog(null, "you have logged as employee");
				}

				return true;
			}

		}
		System.out.println(isChief);
		return false;

	}

}
