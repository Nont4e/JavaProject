// Name: Natchanon
// Student ID: 6188042
// Section: 1

import java.awt.BufferCapabilities.FlipContents;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SimpleMovieSearchEngine implements BaseMovieSearchEngine {
	public Map<Integer, Movie> movies;
	
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		movies = new HashMap<Integer, Movie>();
		// YOUR CODE GOES HERE
		File file = new File(movieFilename);
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(movieFilename));
			br.readLine();
			while((line = br.readLine()) != null) {	
				//Pattern doesn't have "
				Pattern p1 = Pattern.compile("([0-9]+),([\\S ]+)\\s\\(([0-9]+)\\),(.*)");
				Matcher m1 = p1.matcher(line);
				//Pattern has "
				Pattern p2 = Pattern.compile("([0-9]+),\"([\\S ]+)\\s\\(([0-9]+)\\)\",(.*)");
				Matcher m2 = p2.matcher(line);
				//Process Split Pattern 1
				if(m1.matches()) {
					int key = Integer.parseInt(m1.group(1));
					String name = m1.group(2);
					int year = Integer.parseInt(m1.group(3));
					movies.put(key, new Movie(key, name, year));
					String[] tag = m1.group(4).split("\\|");
					for(String i: tag) {
						movies.get(key).addTag(i);
					}
				}
				//Process Split Pattern 2
				else if(m2.matches()) {
					int key = Integer.parseInt(m2.group(1));
					String name = m2.group(2);
					int year = Integer.parseInt(m2.group(3));
					movies.put(key, new Movie(key, name, year));
					String[] tag = m2.group(4).split("\\|");
					for(String i: tag) {
						movies.get(key).addTag(i);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return movies;
	}

	@Override
	public void loadRating(String ratingFilename) {

		// YOUR CODE GOES HERE
		File file = new File(ratingFilename);
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(ratingFilename));
			br.readLine();
			while((line = br.readLine())!= null)
			{	
				//Process Split
				String[] part = line.split(",");
				int key = Integer.parseInt(part[1]);
				double rating = Double.parseDouble(part[2]);
				int uid = Integer.parseInt(part[0]);
				long time = Long.parseLong(part[3]);
				if(movies.get(key)!=null)
				movies.get(key).addRating(new User(uid), movies.get(key), rating, time);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void loadData(String movieFilename, String ratingFilename) {
	
		// YOUR CODE GOES HERE
		//Loading....
			loadMovies(movieFilename);
			loadRating(ratingFilename);
		//Done!
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {

		// YOUR CODE GOES HERE
		// Why do you want this data?
		return movies;
	}

	@Override
	public List<Movie> searchByTitle(String title, boolean exactMatch) {

		// YOUR CODE GOES HERE
		//Starto!!
		if(title != null) {
			List<Movie> pass = new ArrayList<Movie>();
			Iterator<Integer> key = movies.keySet().iterator();
			//Processing.. .
			while(key.hasNext()) {
				int index = key.next();
				String Mname = movies.get(index).getTitle();
				
				if(exactMatch) {
					if(Mname.toLowerCase().equals(title.toLowerCase()))
						pass.add(movies.get(index));
				}
				else {
					if(Mname.toLowerCase().contains(title.toLowerCase()))
						pass.add(movies.get(index));
					
				}
			}
			//Finish!	
			return pass;
		}
		//Why do you left the title blank?
		return null;
		
	}

	@Override
	public List<Movie> searchByTag(String tag) {

		// YOUR CODE GOES HERE
		//Start!!
		if(tag != null) {
			List<Movie> pass = new ArrayList<Movie>();
			Iterator<Integer> key = movies.keySet().iterator();
			//Processing.. . .
			while(key.hasNext()) {
				int index = key.next();
				Set<String> Mtag= movies.get(index).getTags();
				for(String i: Mtag) {
					if(i.toLowerCase().equals(tag.toLowerCase())) {
						pass.add(movies.get(index));
						break;
					}
				}
			}
			// Done!
			return pass;
		}
		//Don't Lied
		return null;
	}

	@Override
	public List<Movie>searchByYear(int year) {

		// YOUR CODE GOES HERE
		//Let's begin!
		List<Movie> pass = new ArrayList<Movie>();
		Iterator<Integer> key = movies.keySet().iterator();
		// Searching.....
		while(key.hasNext()) {
			int index = key.next();
			int Myear = movies.get(index).getYear();
			if(Myear == year)
				pass.add(movies.get(index));
		}
		// Done
		return pass;
		
	}

	@Override
	public List<Movie> advanceSearch(String title, String tag, int year) {

		// YOUR CODE GOES HERE
		// You're so picky.
		List<Movie> pass = new ArrayList<Movie>();
		Map<Integer, Movie> Mmap = new HashMap<Integer, Movie>();
		if((title != null) || (tag != null) || (year != -1)){
			
			for(Map.Entry<Integer, Movie> entry : movies.entrySet()) {
				Mmap.put(entry.getKey(), entry.getValue());
			}
			
			Iterator<Integer> a = Mmap.keySet().iterator();
			//Beginning...
			while(a.hasNext()){
				int index = a.next();
				//If you fill the title.
				if(title != null) {
					String Mname = Mmap.get(index).getTitle();
					if(!(Mname.toLowerCase().contains(title.toLowerCase()))){
						a.remove();
						continue;
					}
				}
				//If you fill the year
				if(year != -1) {
					int Myear = movies.get(index).getYear();
					if(Myear != year) {
						a.remove();
						continue;
					}
				}
				//If you .. nvm.
				if(tag != null) {
					Set<String> Mtag= movies.get(index).getTags();
					for(String i: Mtag) {
						if(i.toLowerCase().equals(tag.toLowerCase())) {
							pass.add(movies.get(index));
							break;
						}
					}
				}
			}
			//Finish!
			return pass;
		}
		//Why you bully me?
		return null;
	}

	@Override
	public List<Movie> sortByTitle(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		//Sorting...
		if(unsortedMovies != null) {
			unsortedMovies.sort(Comparator.comparing(Movie::getTitle));
			//Upside down
			if(!asc){
				Collections.reverse(unsortedMovies);
			}
			return unsortedMovies;
			//YAYY!!
		}
		return null;
	}

	@Override
	public List<Movie> sortByRating(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		//Sortiinng..
		if(unsortedMovies != null) {
			unsortedMovies.sort(Comparator.comparing(Movie::getMeanRating));
			//UPside umop
			if(!asc) {
				Collections.reverse(unsortedMovies);
			}
			return unsortedMovies;
		}
		//Just added the list man!!
		return null;
	}
	//Finale
}
