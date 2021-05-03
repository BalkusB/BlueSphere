import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;

public class User 
{
	//User Fields
	private String username;
	private String password;
	private ArrayList<String> watchedMovies = new ArrayList<String>();
	private ArrayList<String> favoriteGenres = new ArrayList<String>();
	
	//class constructor
	public User(String username, String password, ArrayList<String> watchedMovies, ArrayList<String> favoriteGenres)
	{
		this.username = username;
		this.password = password;
		this.watchedMovies = watchedMovies;
		this.favoriteGenres = favoriteGenres;
	}
	
	//prints info for debugging purposes
	public void printUser()
	{
		System.out.println(username + ": ");
		System.out.print("Watched movies: ");
		for(String s : watchedMovies)
		{
			System.out.print(s + ", ");
		}
		System.out.println();
		System.out.print("Favorite Genres: ");
		for(String s : favoriteGenres)
		{
			System.out.print(s + ", ");
		}
		System.out.println();
	}
	
	//setters
	public void addWatchedMovie(String movie)
	{
		for(String title : watchedMovies) //check if movie is already on record
			if(movie.equals(title))
				return; //if movie is already on watched list, leave method and do not add again
		watchedMovies.add(movie); //add movie if it is not alreadt on list
		try //add movie to user's file
		{
			File file = new File(username + ".txt");
			if(file.exists()) 
			{
				FileWriter myWriter = new FileWriter(username + ".txt", true);
				myWriter.write(movie + "\n");
				myWriter.close();
		    } 
			else 
		    {
				System.out.println("Error");
		    }
		}
		catch (IOException exception) 
		{
		      System.out.println("An error occurred.");
		      exception.printStackTrace();
		}
	}
	
	public void addFavoriteGenre(String genre)
	{
		for(String likedGenre : favoriteGenres) //check to see if genres is already on user's liked genre
			if(genre.equals(likedGenre))
				return; //leave method and do not add ganres if already on list
		favoriteGenres.add(genre); //add genre if not already on faborite genres list
		try //write new favorite genre to user's file
		{
			File file = new File(username + ".txt");
			if(file.exists()) 
			{
				FileWriter myWriter = new FileWriter(username + ".txt", true);
				myWriter.write(genre + "\n");
				myWriter.close();
		    } 
			else 
		    {
				System.out.println("Error");
		    }
		}
		catch (IOException exception) 
		{
		      System.out.println("An error occurred.");
		      exception.printStackTrace();
		}
	}
	
	//getters
	public ArrayList<String> getWatchedMovies()
	{
		return watchedMovies;
	}
	
	public ArrayList<String> getfavoriteGenres()
	{
		return favoriteGenres;
	}
}
