// Name: Natchanon
// Student ID: 6188042
// Section: 1

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Movie {
	private int mid;
	private String title;
	private int year;
	private Set<String> tags;
	private Map<Integer, Rating> ratings;	//mapping userID -> rating
	private Double avgRating;
	//additional
	
	public Movie(int _mid, String _title, int _year){
		// YOUR CODE GOES HERE
		//Constructing...
		mid = _mid;
		title = _title;
		year = _year;
		tags = new HashSet<String>();
		ratings = new HashMap<Integer, Rating>();
		
	}
	
	public int getID() {
		
		// YOUR CODE GOES HERE
		return mid;
	}
	public String getTitle(){
		
		// YOUR CODE GOES HERE
		if(title != null)
			return title;
		
		return null;
	}
	
	public Set<String> getTags() {
		
		// YOUR CODE GOES HERE
		if(tags != null)
			return tags;
		
		return null;
	}
	
	public void addTag(String tag){
		
		// YOUR CODE GOES HERE
		tags.add(tag);
	}
	
	public int getYear(){
		
		// YOUR CODE GOES HERE
		return year;
	}
	
	public String toString()
	{
		return "[mid: "+mid+":"+title+" ("+year+") "+tags+"] -> avg rating: " + avgRating;
	}
	
	
	public double calMeanRating(){
		// YOUR CODE GOES HERE
		//get rating and divide by size
		double mean = 0;
		for(Integer key: ratings.keySet()) {
			mean+= ratings.get(key).rating;
		}
		mean /= ratings.size();
		return mean;
	}
	
	public Double getMeanRating(){
		
		// YOUR CODE GOES HERE
		return avgRating;
	}
	
	public void addRating(User user, Movie movie, double rating, long timestamp) {
		// YOUR CODE GOES HERE
		//Rotten Tomato 98%
		ratings.put(user.uid, new Rating(user, movie, rating, timestamp));
		avgRating = calMeanRating();
	}
	
	public Map<Integer, Rating> getRating(){
		
		// YOUR CODE GOES HERE
		return ratings;
	}
	
}
