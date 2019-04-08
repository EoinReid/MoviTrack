/** CLIENT                                                  February 2019 DL 08/03/19
 *
 * This Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 *
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */
package BusinessObjects;

import DAOs.MovieDaoInterface;
import DAOs.MySqlMovieDao;
import DTOs.Movie;
import Exceptions.DaoException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket, and open new socket

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client: This Client is running and has connected to the server");

            System.out.println("Commands are as follows: ");
            System.out.println("GetAllMovies -> (Returns all Movies)");
            System.out.println("GetMovieById (ID) -> Returns a movie with your entered ID");
            System.out.println("GetMovieByTitle (Title) -> Returns a movie with your entered title");
            System.out.println("GetMovieByDirector (Director) -> Returns a list of movies that were made by your entered director");
            System.out.println("deleteMovie (ID) -> This will delete the movie that has the ID you entered.");
            System.out.println("updateMovie, (title,genre,director,id(of moving being updated)) -> This will update the title,genre and director of the movie that has the ID you have entered.");
            System.out.println("addMovie (title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies(number of),barcode,user_rating) -> This will add the movie and all the criteria you entered.");
            System.out.println("watchMovie (username,id) -> This will allow you to watch a Movie by entering your username and the movie ID");
            System.out.println("Please enter a command: ");

            String command = in.nextLine();  // read a command from the user

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            socketWriter.println(command);  // write command to socket

            Scanner socketReader = new Scanner(socket.getInputStream());

            if (command.startsWith("GetAllMovies")) // we expect the server to return all movies in the database.
            {
               
                String response = socketReader.nextLine();               
                System.out.println(response);
                             
                JsonReader jr = Json.createReader(new StringReader(response));
                JsonObject jo = jr.readObject();
                JsonArray ja = jo.getJsonArray("movies");
                List<Movie> movies = new ArrayList<>();   
                
                    for (int i = 0; i < ja.size(); i++) {
                             
                JsonObject object = ja.getJsonObject(i);
                String Id = object.getString("Id");
                int id = Integer.parseInt(Id);               
                String title = object.getString("Title");           
                String genre = object.getString("Genre");
                String director = object.getString("Director");
                String runtime = object.getString("RunTime");
                String plot = object.getString("Plot");
                String location = object.getString("Location");
                String poster = object.getString("Poster");
                String rating = object.getString("Rating");
                String format = object.getString("Format");
                String year = object.getString("Year");
                String starring = object.getString("Starring");
                String Copies = object.getString("Copies");
                int copies = Integer.parseInt(Copies);
                String barcode = object.getString("Barcode");
                String user_rating = object.getString("User_Rating");
                Movie m = new Movie(id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                movies.add(m);
                    }
                    
                for(Movie m : movies){                                   
                
                    System.out.println("Client: Response from server: Movies: " +m.toString());
                }                                                                

            } else if (command.startsWith("GetMovieById")) // we expect the server to return a movie with a matching ID.
            {
                
              String response = socketReader.nextLine();
                
                System.out.println(response);
                
                if(response.equals("{}")){
                    System.out.println("Cannot find Movie with this Id");
                }
                else{
                JsonReader jr = Json.createReader(new StringReader(response));
                JsonObject jo = jr.readObject();
                
                JsonObject object = jo.getJsonObject("movies");
                String Id = object.getString("Id");
                int id = Integer.parseInt(Id);               
                String title = object.getString("Title");                
                String genre = object.getString("Genre");
                String director = object.getString("Director");
                String runtime = object.getString("RunTime");
                String plot = object.getString("Plot");
                String location = object.getString("Location");
                String poster = object.getString("Poster");
                String rating = object.getString("Rating");
                String format = object.getString("Format");
                String year = object.getString("Year");
                String starring = object.getString("Starring");
                String Copies = object.getString("Copies");
                int copies = Integer.parseInt(Copies);
                String barcode = object.getString("Barcode");
                String user_rating = object.getString("User_Rating");
                
                Movie m = new Movie(id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                
                    System.out.println("Client: Response from server: Movie: " + m.toString());
                }
            }else if (command.startsWith("GetMovieByTitle")) // we expect the server to return a movie with a matching Title.
            {
                
                String response = socketReader.nextLine();               
                System.out.println(response);
                
                if(response.equals("{}")){
                    System.out.println("Cannot find Movie with this title");
                }
                else{
                JsonReader jr = Json.createReader(new StringReader(response));
                JsonObject jo = jr.readObject();
                
                JsonObject object = jo.getJsonObject("movies");
                String Id = object.getString("Id");
                int id = Integer.parseInt(Id);               
                String title = object.getString("Title");                
                String genre = object.getString("Genre");
                String director = object.getString("Director");
                String runtime = object.getString("RunTime");
                String plot = object.getString("Plot");
                String location = object.getString("Location");
                String poster = object.getString("Poster");
                String rating = object.getString("Rating");
                String format = object.getString("Format");
                String year = object.getString("Year");
                String starring = object.getString("Starring");
                String Copies = object.getString("Copies");
                int copies = Integer.parseInt(Copies);
                String barcode = object.getString("Barcode");
                String user_rating = object.getString("User_Rating");
                
                Movie m = new Movie(id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                
                    System.out.println("Client: Response from server: Movie: " + m.toString());
                }
            } else if (command.startsWith("GetMoviesByDirector")) // we expect the server to return all movies by a certain director.
            {                                 
                String response = socketReader.nextLine();               
                System.out.println(response);
                
                if(response.equals("{}")){
                    System.out.println("Cannot find Movie with this Director");
                }
                else{
                JsonReader jr = Json.createReader(new StringReader(response));
                JsonObject jo = jr.readObject();
                JsonArray ja = jo.getJsonArray("movies");
                List<Movie> movies = new ArrayList<>();   
                
                    for (int i = 0; i < ja.size(); i++) {
                    
                  //jo = ja.getJsonObject(i);
                JsonObject object = ja.getJsonObject(i);
                String Id = object.getString("Id");
                int id = Integer.parseInt(Id);               
                String title = object.getString("Title");           
                String genre = object.getString("Genre");
                String director = object.getString("Director");
                String runtime = object.getString("RunTime");
                String plot = object.getString("Plot");
                String location = object.getString("Location");
                String poster = object.getString("Poster");
                String rating = object.getString("Rating");
                String format = object.getString("Format");
                String year = object.getString("Year");
                String starring = object.getString("Starring");
                String Copies = object.getString("Copies");
                int copies = Integer.parseInt(Copies);
                String barcode = object.getString("Barcode");
                String user_rating = object.getString("User_Rating");
                
                movies.add(new Movie (id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating));
                    }
                
                
                for(Movie m : movies){                                   
                
                    System.out.println("Client: Response from server: Movie: " + m.toString());
                }
                
                }
            }else if(command.startsWith("deleteMovie")){
                
                String response = socketReader.nextLine();               
                System.out.println(response);
            
            }else if (command.startsWith("updateMovie")){
                
                String response = socketReader.nextLine();               
                System.out.println(response);
                
            }else if (command.startsWith("addMovie")){
                
                String response = socketReader.nextLine();               
                System.out.println(response);
                
            }
            else // the user has entered an invalid command
            {
                String input = socketReader.nextLine();// wait for, and retrieve the echo ( or other message)
                System.out.println("Client: Response from server: \"" + input + "\"");
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        } 
       
    }
}
