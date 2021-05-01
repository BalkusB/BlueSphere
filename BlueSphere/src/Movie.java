import java.util.ArrayList;

public class Movie 
{
	//Movie Fields
	private String title;
	private ArrayList<String> genres;
	
	//Class Constructor
	public Movie(String title, ArrayList<String> genres)
	{
		this.title = title;
		this.genres = genres;
	}
	
	//prints info for debugging purposes
	public void printMovie()
	{
		System.out.print(title + ": ");
		for(String s : genres)
		{
			System.out.print(s + ", ");
		}
		System.out.println();
	}
	
	//getters, no need to set, movies aren't subject to change
	public String getTitle()
	{
		return title;
	}
	
	public ArrayList<String> getGenres()
	{
		return genres;
	}
}
