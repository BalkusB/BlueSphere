import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInPage implements ActionListener
{
	//Component fields
	private JFrame frame;
	private ImageIcon logoImage = new ImageIcon("BlueSphereSmall.png");
	private ImageIcon movieImage = new ImageIcon("Jaws.jpg");
	private JButton logInButton;
	private JButton createAccountButton;
	private JButton searchButton;
	private JButton searchButton2;
	private JButton logOutButton;
	private JButton watchMovie1;
	private JButton watchMovie2;
	private JButton watchMovie3;
	private JButton watchMovie4;
	private JButton watchMovie5;
	private JButton mainMenuButton;
	private JButton likedMovie;
	private JButton dislikedMovie;
	private JButton comedyButton;
	private JButton actionButton;
	private JButton horrorButton;
	private JButton romanceButton;
	private JButton scifiButton;
	private JTextField username;
	private JTextField password;
	private JTextField searchBox;
	
	//track current user information
	private User currentUser;
	
	//holds movie currently being played
	private Movie currentMovie;
	
	//List holding all movies
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	
	//List holding displayed movies
	private ArrayList<Movie> moviesDisplayed = new ArrayList<Movie>();
	
	//movie value holding movie being played
	private Movie movieBeingPlayed;
	
	//class constructor
	public LogInPage()
	{
		createMovies();
		createWindow();
		createLogInPage();
		frame.repaint();
	}
	
	//create user object from given file
	public void createUser(String user)
	{
		try
		{
			File file = new File(user + ".txt");
		    Scanner myReader = new Scanner(file);
		    String username = myReader.nextLine();
		    String password = myReader.nextLine();
		    ArrayList<String> watchedMovies = new ArrayList<String>();
		    ArrayList<String> favoriteGenres = new ArrayList<String>();
		    while (myReader.hasNextLine()) 
		    {
		    	watchedMovies.add(myReader.nextLine());
		    }
		    for(int i = 0; i < watchedMovies.size(); i++)
			{
		    	if(watchedMovies.get(i).equals("Comedy") || watchedMovies.get(i).equals("Action") || 
		    			watchedMovies.get(i).equals("Horror") || watchedMovies.get(i).equals("Romance") || 
		    			watchedMovies.get(i).equals("Scifi"))
		    	{
		    		favoriteGenres.add(watchedMovies.get(i));
		    		watchedMovies.remove(i);
		    		i--;
		    	}
			}
		    currentUser = new User(username, password, watchedMovies, favoriteGenres);
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	//reads movies from file and sends them to array list
	public void createMovies()
	{
		try
		{
			File file = new File("Movies.txt");
		    Scanner myReader = new Scanner(file);
		    while (myReader.hasNextLine()) 
		    {
		    	String title = myReader.nextLine();
		    	ArrayList<String> genres = parseGenres(myReader.nextLine());
		    	movies.add(new Movie(title, genres));
		    }
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	//parses genres from file
	public ArrayList<String> parseGenres(String s)
	{
		
		String[] genres = s.split(" ");
		
		ArrayList<String> toRet = new ArrayList<String>();
		for(int i = 0; i < genres.length; i++)
		{
			toRet.add(genres[i]);
		}
		return toRet;
	}
	
	//algorithm to recommend movies and puts them in displayed movies
	public void recommendMovies()
	{
		moviesDisplayed.clear();
		Collections.shuffle(movies);
		currentUser.printUser();
		ArrayList<Movie> tempMovies = (ArrayList<Movie>) movies.clone();
		for(int i = 0; i < tempMovies.size(); i++)
		{
			for(String title : currentUser.getWatchedMovies())
				if(tempMovies.get(i).getTitle().equals(title))
				{
					tempMovies.remove(i);
					i--;
					break;
				}
		}
		for(int i = 0; i < tempMovies.size(); i++)
		{
			boolean breaker = false;
			for(String genre : tempMovies.get(i).getGenres())
			{
				
				for(String genre2 : currentUser.getfavoriteGenres())
				{
					if(genre.equals(genre2))
					{
						moviesDisplayed.add(tempMovies.get(i));
						tempMovies.remove(i);
						i--;
						breaker = true;
						break;
					}
					if(breaker)
						break;
				}
				if(breaker)
					break;
			}
			if(moviesDisplayed.size() == 5)
				return;
		}
		if(moviesDisplayed.size() < 5)
		{
			tempMovies = (ArrayList<Movie>) movies.clone();
			for(int i = 0; i < tempMovies.size(); i++)
			{
				for(Movie movie : moviesDisplayed)
					if(tempMovies.get(i).equals(movie))
					{
						tempMovies.remove(i);
						i--;
						break;
					}
			}
			for(int i = 0; i < tempMovies.size(); i++)
			{
				for(String genre : tempMovies.get(i).getGenres())
				{
					boolean breaker = false;
					for(String genre2 : currentUser.getfavoriteGenres())
					{
						if(genre.equals(genre2))
						{
							moviesDisplayed.add(tempMovies.get(i));
							tempMovies.remove(i);
							breaker = true;
							i--;
							break;
						}
						if(breaker)
							break;
					}
					if(breaker)
						break;
				}
				if(moviesDisplayed.size() == 5)
					return;
			}
		}
		int i = 0;
		while(moviesDisplayed.size() < 5)
		{
			boolean unadded = true;
			for(Movie movie : moviesDisplayed)
				if(movie.equals(movies.get(i)))
					unadded = false;
			if(unadded)
				moviesDisplayed.add(movies.get(i));
			i++;
		}
	}
	
	//searches for movies of a genre then displays them
	public void searchMovies(String genre)
	{
		moviesDisplayed.clear();
		ArrayList<Movie> tempMovies = (ArrayList<Movie>) movies.clone();
		for(int i = 0; i < 4; i++)
		{
			for(int i2 = 0; i2 < tempMovies.size(); i2++)
			{
				boolean breaker = false;
				for(String genre2 : tempMovies.get(i2).getGenres())
				{
					if(genre.equals(genre2))
					{
						moviesDisplayed.add(tempMovies.get(i2));
						tempMovies.remove(i2);
						breaker = true;
						break;
					}
				}
				if(breaker)
					break;
			}
		}
	}
	
	public void searchMovies2(String search)
	{
		moviesDisplayed.clear();
		ArrayList<Movie> tempMovies = (ArrayList<Movie>) movies.clone();
		for(int i = 0; i < 4; i++)
		{
			for(int i2 = 0; i2 < tempMovies.size(); i2++)
			{
				if(tempMovies.get(i2).getTitle().contains(search))
				{
					moviesDisplayed.add(tempMovies.get(i2));
					tempMovies.remove(i2);
					break;
				}
			}
		}
	}
	
	//create frame and panel
	public void createWindow()
	{
		frame = new JFrame();
		
		//panel = new JPanel();
		//panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 500, 1000));
		//panel.setLayout(null);
		
		//frame.add(panel);
		frame.setLayout(null);
		frame.setSize(1000, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setIconImage(logoImage.getImage());
		//panel.setBackground(Color.WHITE);
	}
	
	//create log in page
	public void createLogInPage()
	{
		createLogInComponents();
		frame.setTitle("Blue Sphere: Log In");
	}
	
	//create main menu
	public void createMainMenu()
	{
		recommendMovies();
		createMainMenuComponents();
		frame.setTitle("Blue Sphere: Main Menu");
	}
	
	//create search page
	public void createSearchPage()
	{
		createSearchPageComponents();
		frame.setTitle("Blue Sphere: Search Page");
	}
	
	//create search page
	public void playMovie(Movie movie)
	{
		createMovieComponents(movie);
		frame.setTitle("Blue Sphere: " + movie.getTitle());
		movieBeingPlayed = movie;
	}
	
	//create watching movie page
	public void createMoviePage(String Movie)
	{
		createSearchPageComponents();
		frame.setTitle("Blue Sphere: " + Movie);
	}
	
	//create log in page components
	public void createLogInComponents()
	{
		JLabel logo = new JLabel();
		logo.setBounds(440, 60, 100, 100);
		logo.setIcon(logoImage);
		frame.add(logo);
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(350, 175, 80, 25);
		frame.add(userLabel);
		
		username = new JTextField(20);
		username.setBounds(440, 175, 165, 25);
		frame.add(username);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(350, 205, 80, 25);
		frame.add(passLabel);
		
		password = new JPasswordField(20);
		password.setBounds(440, 205, 165, 25);
		frame.add(password);
		
		logInButton = new JButton("Log In");
		logInButton.setBounds(350, 235, 255, 25);
		logInButton.addActionListener(this);
		frame.add(logInButton);
		
		createAccountButton = new JButton("Create Account");
		createAccountButton.setBounds(350, 265, 255, 25);
		createAccountButton.addActionListener(this);
		frame.add(createAccountButton);
	}
	
	//create main menu components
	public void createMainMenuComponents()
	{
		JLabel logo = new JLabel();
		logo.setBounds(10, 10, 100, 100);
		logo.setIcon(logoImage);
		frame.add(logo);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(120, 15, 810, 50);
		searchButton.addActionListener(this);
		frame.add(searchButton);
		
		JLabel recommendedMovies = new JLabel("Recommended Movies For You");
		recommendedMovies.setFont(new Font("",Font.PLAIN, 40));
		recommendedMovies.setBounds(230, 50, 880, 80);
		frame.add(recommendedMovies);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setBounds(10, 410, 90, 40);
		logOutButton.addActionListener(this);
		frame.add(logOutButton);
		
		watchMovie1 = new JButton("Watch");
		watchMovie1.setBounds(10, 360, 180, 40);
		watchMovie1.addActionListener(this);
		frame.add(watchMovie1);
		
		watchMovie2 = new JButton("Watch");
		watchMovie2.setBounds(200, 360, 180, 40);
		watchMovie2.addActionListener(this);
		frame.add(watchMovie2);
		
		watchMovie3 = new JButton("Watch");
		watchMovie3.setBounds(390, 360, 180, 40);
		watchMovie3.addActionListener(this);
		frame.add(watchMovie3);
		
		watchMovie4 = new JButton("Watch");
		watchMovie4.setBounds(580, 360, 180, 40);
		watchMovie4.addActionListener(this);
		frame.add(watchMovie4);
		
		
		watchMovie5 = new JButton("Watch");
		watchMovie5.setBounds(770, 360, 180, 40);
		watchMovie5.addActionListener(this);
		frame.add(watchMovie5);
		
		JLabel movie1 = new JLabel(moviesDisplayed.get(0).getTitle(), JLabel.CENTER);
		movie1.setBounds(10, 120, 180, 20);
		movie1.setFont(new Font("",Font.PLAIN, 20));
		frame.add(movie1);
		
		JLabel movie1Image = new JLabel();
		movie1Image.setBounds(10, 140, 180, 220);
		movie1Image.setIcon(movieImage);
		frame.add(movie1Image);
		
		JLabel movie2 = new JLabel(moviesDisplayed.get(1).getTitle(), JLabel.CENTER);
		movie2.setBounds(200, 120, 180, 20);
		movie2.setFont(new Font("",Font.PLAIN, 20));
		frame.add(movie2);
		
		JLabel movie2Image = new JLabel();
		movie2Image.setBounds(200, 140, 180, 220);
		movie2Image.setIcon(movieImage);
		frame.add(movie2Image);
		
		JLabel movie3 = new JLabel(moviesDisplayed.get(2).getTitle(), JLabel.CENTER);
		movie3.setBounds(390, 120, 180, 20);
		movie3.setFont(new Font("",Font.PLAIN, 20));
		frame.add(movie3);
		
		JLabel movie3Image = new JLabel();
		movie3Image.setBounds(390, 140, 180, 220);
		movie3Image.setIcon(movieImage);
		frame.add(movie3Image);
		
		JLabel movie4 = new JLabel(moviesDisplayed.get(3).getTitle(), JLabel.CENTER);
		movie4.setBounds(580, 120, 180, 20);
		movie4.setFont(new Font("",Font.PLAIN, 20));
		frame.add(movie4);
		
		JLabel movie4Image = new JLabel();
		movie4Image.setBounds(580, 140, 180, 220);
		movie4Image.setIcon(movieImage);
		frame.add(movie4Image);
		
		JLabel movie5 = new JLabel(moviesDisplayed.get(4).getTitle(), JLabel.CENTER);
		movie5.setBounds(770, 120, 180, 20);
		movie5.setFont(new Font("",Font.PLAIN, 20));
		frame.add(movie5);
		
		JLabel movie5Image = new JLabel();
		movie5Image.setBounds(770, 140, 180, 220);
		movie5Image.setIcon(movieImage);
		frame.add(movie5Image);
	}

	//create search page components
	public void createSearchPageComponents()
	{
		JLabel logo = new JLabel();
		logo.setBounds(10, 10, 100, 100);
		logo.setIcon(logoImage);
		frame.add(logo);
		
		searchBox = new JTextField();
		searchBox.setBounds(120, 15, 700, 50);
		frame.add(searchBox);
		
		searchButton2 = new JButton("Search");
		searchButton2.setBounds(830, 15, 100, 50);
		searchButton2.addActionListener(this);
		frame.add(searchButton2);
		
		JLabel searchResults = new JLabel("Seach Results:");
		searchResults.setFont(new Font("",Font.PLAIN, 40));
		searchResults.setBounds(200, 50, 880, 80);
		frame.add(searchResults);
		
		mainMenuButton = new JButton("Return to Main Menu");
		mainMenuButton.setBounds(10, 410, 180, 40);
		mainMenuButton.addActionListener(this);
		frame.add(mainMenuButton);
		
		comedyButton = new JButton("Comedy");
		comedyButton.setBounds(10, 160, 180, 40);
		comedyButton.addActionListener(this);
		frame.add(comedyButton);
		
		actionButton = new JButton("Action");
		actionButton.setBounds(10, 210, 180, 40);
		actionButton.addActionListener(this);
		frame.add(actionButton);
		
		horrorButton = new JButton("Horror");
		horrorButton.setBounds(10, 260, 180, 40);
		horrorButton.addActionListener(this);
		frame.add(horrorButton);
		
		romanceButton = new JButton("Romance");
		romanceButton.setBounds(10, 310, 180, 40);
		romanceButton.addActionListener(this);
		frame.add(romanceButton);
		
		scifiButton = new JButton("Sci-Fi");
		scifiButton.setBounds(10, 360, 180, 40);
		scifiButton.addActionListener(this);
		frame.add(scifiButton);
		
		JLabel searchGenre = new JLabel("Search by Genre", JLabel.CENTER);
		searchGenre.setBounds(10, 120, 180, 25);
		searchGenre.setFont(new Font("",Font.PLAIN, 20));
		frame.add(searchGenre);
	}
	
	//create searched movies
	public void createSearchedMovies()
	{
		int x = 200;
		for(Movie movie : moviesDisplayed)
		{
			JLabel movie1 = new JLabel(movie.getTitle(), JLabel.CENTER);
			movie1.setBounds(x, 120, 180, 20);
			movie1.setFont(new Font("",Font.PLAIN, 20));
			frame.add(movie1);
			
			JLabel movie1Image = new JLabel();
			movie1Image.setBounds(x, 140, 180, 220);
			movie1Image.setIcon(movieImage);
			frame.add(movie1Image);
			x += 190;
		}
		
		if(moviesDisplayed.size() > 0)
		{
			watchMovie1 = new JButton("Watch");
			watchMovie1.setBounds(200, 360, 180, 40);
			watchMovie1.addActionListener(this);
			frame.add(watchMovie1);
		}
		if(moviesDisplayed.size() > 1)
		{
			watchMovie2 = new JButton("Watch");
			watchMovie2.setBounds(390, 360, 180, 40);
			watchMovie2.addActionListener(this);
			frame.add(watchMovie2);
		}
		
		if(moviesDisplayed.size() > 2)
		{
			watchMovie3 = new JButton("Watch");
			watchMovie3.setBounds(580, 360, 180, 40);
			watchMovie3.addActionListener(this);
			frame.add(watchMovie3);
		}
		if(moviesDisplayed.size() > 3)
		{
			watchMovie4 = new JButton("Watch");
			watchMovie4.setBounds(770, 360, 180, 40);
			watchMovie4.addActionListener(this);
			frame.add(watchMovie4);
		}
		frame.repaint();
	}
	
	//create placeholder movie
	public void createMovieComponents(Movie movie)
	{
		JLabel moviePlaceHolder = new JLabel("Movie Place Holder");
		moviePlaceHolder.setFont(new Font("",Font.PLAIN, 60));
		moviePlaceHolder.setBounds(220, 50, 900, 100);
		frame.add(moviePlaceHolder);
		
		JLabel enjoyedMovie = new JLabel("Did you enjoy this movie?");
		enjoyedMovie.setBounds(10, 390, 200, 15);
		frame.add(enjoyedMovie);
		
		likedMovie = new JButton("Yes");
		likedMovie.setBounds(10, 420, 100, 30);
		likedMovie.addActionListener(this);
		frame.add(likedMovie);
		
		dislikedMovie = new JButton("No");
		dislikedMovie.setBounds(120, 420, 100, 30);
		dislikedMovie.addActionListener(this);
		frame.add(dislikedMovie);
	}
	
	//method handling when buttons are clicked
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == logInButton)
			logInButton();
		if(e.getSource() == createAccountButton)
			createAccountButton();
		if(e.getSource() == logOutButton)
			logOutButton();
		if(e.getSource() == searchButton)
			searchButton();
		if(e.getSource() == mainMenuButton)
			mainMenuButton();
		if(e.getSource() == watchMovie1)
			playMovieButton(0);
		if(e.getSource() == watchMovie2)
			playMovieButton(1);
		if(e.getSource() == watchMovie3)
			playMovieButton(2);
		if(e.getSource() == watchMovie4)
			playMovieButton(3);
		if(e.getSource() == watchMovie5)
			playMovieButton(4);
		if(e.getSource() == searchButton2)
			searchButton2();
		if(e.getSource() == comedyButton)
			comedyButton();
		if(e.getSource() == actionButton)
			actionButton();
		if(e.getSource() == horrorButton)
			horrorButton();
		if(e.getSource() == romanceButton)
			romanceButton();
		if(e.getSource() == scifiButton)
			scifiButton();
		if(e.getSource() == likedMovie)
			likedMovieButton();
		if(e.getSource() == dislikedMovie)
			dislikedMovieButton();
	}
	
	//log in button was clicked
	public void logInButton()
	{
		try 
		{
			File file = new File(username.getText() + ".txt");
			if(file.exists()) 
			{
				Scanner myReader = new Scanner(file);
			    String user = myReader.nextLine();
			    String pass = myReader.nextLine();
			    if(username.getText().equals(user) && password.getText().equals(pass))
			    {
			    	createUser(user);
			    	frame.getContentPane().removeAll();
			    	frame.repaint();
			    	createMainMenu();
			    }
			    else
			    {
			    	frame.getContentPane().removeAll();
					frame.repaint();
					createLogInPage();
					JLabel errorLabel = new JLabel("Incorrect password");
					errorLabel.setBounds(350, 300, 300, 25);
					frame.add(errorLabel);
					frame.repaint();
			    }
		    } 
			else 
		    {
				frame.getContentPane().removeAll();
				frame.repaint();
				createLogInPage();
				JLabel errorLabel = new JLabel("Username does not exist");
				errorLabel.setBounds(350, 300, 300, 25);
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
	
	public void logOutButton()
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		createLogInPage();
	}
	
	//create account button was clicked
	public void createAccountButton()
	{
		CreateAccountWindow popup = new CreateAccountWindow();
	}
	
	//search button was clicked
	public void searchButton()
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		createSearchPage();
	}
	
	//main menu button was clicked
	public void mainMenuButton()
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		createMainMenu();
	}
	
	//watch movie button was clicked
	public void playMovieButton(int i)
	{
		frame.getContentPane().removeAll();
		frame.repaint();
		currentMovie = moviesDisplayed.get(i);
		playMovie(moviesDisplayed.get(i));
	}
	
	//search button 2 was clicked
	public void searchButton2()
	{
		String temp = searchBox.getText();
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies2(temp);
		createSearchedMovies();
	}
	
	//comedy search button was clicked
	public void comedyButton()
	{
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies("Comedy");
		createSearchedMovies();
	}
	
	//action search button was clicked
	public void actionButton()
	{
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies("Action");
		createSearchedMovies();
	}
	
	//horror search button was clicked
	public void horrorButton()
	{
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies("Horror");
		createSearchedMovies();
	}
	
	//romance search button was clicked
	public void romanceButton()
	{
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies("Romance");
		createSearchedMovies();
	}
	
	//scifi search button was clicked
	public void scifiButton()
	{
		frame.getContentPane().removeAll();
		createSearchPage();
		searchMovies("Scifi");
		createSearchedMovies();
	}
	
	//liked movie button was clicked
	public void likedMovieButton()
	{
		currentUser.addWatchedMovie(currentMovie.getTitle());
		for(String genre : currentMovie.getGenres())
			currentUser.addFavoriteGenre(genre);
		frame.getContentPane().removeAll();
		frame.repaint();
		createMainMenu();
	}
	
	//disliked movie button was clicked
	public void dislikedMovieButton()
	{
		currentUser.addWatchedMovie(currentMovie.getTitle());
		frame.getContentPane().removeAll();
		frame.repaint();
		createMainMenu();
	}
}
