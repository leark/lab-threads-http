import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * YOUR TASK: Add comments explaining how this code works!
 * 
 * @author Joel Ross & Kyungmin Lee
 */
public class MovieDownloader {

	public static String[] downloadMovieData(String movie) {

		//construct the url for the omdbapi API
		String urlString = "";
		try {
			urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(movie, "UTF-8") + "&type=movie";
		}catch(UnsupportedEncodingException uee){
			return null;
		}

		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;

		String movies[] = null;

		try {

			URL url = new URL(urlString);

			// open connection to url
			urlConnection = (HttpURLConnection) url.openConnection();
			// use GET request
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			// get input stream (data)
			InputStream inputStream = urlConnection.getInputStream();
			// create stringbuffer (to easily modify, add what we read from source)
			StringBuffer buffer = new StringBuffer();
			// check if anything returns
			if (inputStream == null) {
				return null;
			}
			// creating buffered reader
			reader = new BufferedReader(new InputStreamReader(inputStream));

			// read the first line
			String line = reader.readLine();
			// while there are more lines
			while (line != null) {
				// appending lines to buffer
				buffer.append(line + "\n");
				line = reader.readLine();
			}

			// if there's no content'
			if (buffer.length() == 0) {
				return null;
			}
			// change buffer to string
			String results = buffer.toString();
			
			results = results.replace("{\"Search\":[","");
			results = results.replace("]}","");
			results = results.replace("},", "},\n");

			movies = results.split("\n");
		} 
		// input output execption
		catch (IOException e) {
			return null;
		} 
		finally {
			// if urlconnection is open, close
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			// if reader is open, close
			if (reader != null) {
				try {
					reader.close();
				} 
				catch (IOException e) {
				}
			}
		}

		return movies;
	}


	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);

		boolean searching = true;

		while(searching) {					
			System.out.print("Enter a movie name to search for or type 'q' to quit: ");
			String searchTerm = sc.nextLine().trim();
			if(searchTerm.toLowerCase().startsWith("q")){
				searching = false;
			}
			else {
				String[] movies = downloadMovieData(searchTerm);
				for(String movie : movies) {
					System.out.println(movie);
				}
			}
		}
		sc.close();
	}
}
