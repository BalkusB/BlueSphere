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
	private ImageIcon logoImage = new ImageIcon(LogInPage.class.getResource("/images/BlueSphereSmall.png"));
	private JButton createAccountButton;
	private JTextField username;
	private JTextField password;
	
	//class constructor
	public CreateAccountWindow()
	{
		createWindow(); //create the pop up window
		createComponents();
		frame.repaint();
	}
	
	//create window's frame
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
	
	//create window's components
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
	public void actionPerformed(ActionEvent e) //is called if create account button is clicked
	{
		if(username.getText().length() > 0 && password.getText().length() > 0) //check to see if a username and password are entered
		{
			try 
			{
				File file = new File(username.getText() + ".txt");
				if (file.createNewFile()) //try to create a file using user name
				{
					FileWriter myWriter = new FileWriter(username.getText() + ".txt"); //create new file if username available
					myWriter.write(username.getText() + "\n"); //add username to user file
			    	myWriter.write(password.getText() + "\n"); //add password to user file
			    	myWriter.close();
			    	frame.dispose(); //delete pop up screen
			    } 
				else 
			    {
					JLabel errorLabel = new JLabel("Username already exists"); //tell user file already exists if a file is on record
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
			JLabel errorLabel = new JLabel("Please enter both a username and a password"); //ask user to enter both a username and password if they failed to do so
			errorLabel.setBounds(10, 215, 300, 25);
			frame.add(errorLabel);
			frame.repaint();
	    }
	}
	
}
