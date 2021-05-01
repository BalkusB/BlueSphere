import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountWindow implements ActionListener
{
	//component fields
	private JFrame frame;
	private ImageIcon logoImage = new ImageIcon("BlueSphereSmall.png");
	private JButton createAccountButton;
	private JTextField username;
	private JTextField password;
	
	public CreateAccountWindow()
	{
		createWindow();
		createComponents();
		frame.repaint();
	}
	
	public void createWindow()
	{
		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(300, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Blue Sphere: Create Account");
		frame.setIconImage(logoImage.getImage());
	}
	
	public void createComponents()
	{
		JLabel logo = new JLabel();
		logo.setBounds(90, 10, 100, 100);
		logo.setIcon(logoImage);
		frame.add(logo);
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(10, 125, 80, 25);
		frame.add(userLabel);
		
		username = new JTextField(20);
		username.setBounds(100, 125, 165, 25);
		frame.add(username);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(10, 155, 80, 25);
		frame.add(passLabel);
		
		password = new JPasswordField(20);
		password.setBounds(100, 155, 165, 25);
		frame.add(password);
		
		createAccountButton = new JButton("Create Account");
		createAccountButton.setBounds(10, 185, 255, 25);
		createAccountButton.addActionListener(this);
		frame.add(createAccountButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(username.getText().length() > 0 && password.getText().length() > 0)
		{
			try 
			{
				File file = new File(username.getText() + ".txt");
				if (file.createNewFile()) 
				{
					FileWriter myWriter = new FileWriter(username.getText() + ".txt");
					myWriter.write(username.getText() + "\n");
			    	myWriter.write(password.getText() + "\n");
			    	myWriter.close();
			    	frame.dispose();
			    } 
				else 
			    {
					JLabel errorLabel = new JLabel("Username already exists");
					errorLabel.setBounds(10, 215, 300, 25);
					frame.add(errorLabel);
					frame.repaint();
			    }
			    
			} 
			catch (IOException exception) 
			{
			      System.out.println("An error occurred.");
			      exception.printStackTrace();
			}
		}
		else
		{
			JLabel errorLabel = new JLabel("Please enter both a username and a password");
			errorLabel.setBounds(10, 215, 300, 25);
			frame.add(errorLabel);
			frame.repaint();
	    }
	}
	
}
