/**
 * SERVER                                                   February 2019 DL 08/03/19
 *
 * Server accepts client connections, creates a ClientHandler to handle the
 * Client communication, creates a socket and passes the socket to the handler,
 * runs the handler in a separate Thread.
 *
 *
 * The handler reads requests from clients, and sends replies to clients, all in
 * accordance with the rules of the protocol. as specified in
 * "ClientServerBasic" sample program
 *
 * The following PROTOCOL is implemented:
 *
 * If ( the Server receives the request "Time", from a Client ) then : the
 * server will send back the current time
 *
 * If ( the Server receives the request "Echo message", from a Client ) then :
 * the server will send back the message
 *
 * If ( the Server receives the request it does not recognize ) then : the
 * server will send back the message "Sorry, I don't understand"
 *
 * This is an example of a simple protocol, where the server's response is based
 * on the client's request.
 *
 *
 */
package BusinessObjects;

import DAOs.MovieDaoInterface;
import DAOs.WatchedDaoInterface;
import DAOs.MySqlMovieDao;
import DTOs.Movie;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    MovieDaoInterface movieDao = null;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            movieDao = new MySqlMovieDao();

            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection, 
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, movieDao, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                                 // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable // each ClientHandler communicates with one Client
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        MovieDaoInterface dao;

        public ClientHandler(Socket clientSocket, MovieDaoInterface IMovieDao, int clientNumber) {
            this.dao = IMovieDao;

            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;

            try {
                while ((message = socketReader.readLine()) != null) // listen at socket for message from client (wait)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);
                    if (message.startsWith("GetAllMovies")) {
                        List<Movie> ml = movieDao.findAllMovies();
                        String jsonStr = convertToJsonGroup(ml);
                        socketWriter.println(jsonStr);  // send message to client
                    }
                    if (message.startsWith("GetMovieById")) {
                        String[] tokens = message.split(" ");
                        int id = Integer.parseInt(tokens[1]);
                        Movie m = movieDao.findMovieById(id); //Need to parse what user enters in the String and the ID they are looking for.
                        String jsonStr = convertToJson(m);
                        socketWriter.println(jsonStr);

                    } else if (message.startsWith("GetMovieByTitle")) {
                        String s = message.substring(16);
                        String title = s;
                        Movie m = movieDao.findMovieByTitle(title);
                        String jsonStr = convertToJson(m);
                        socketWriter.println(jsonStr);  // send message to client

                    } else if (message.startsWith("GetMoviesByDirector")) {
                        String s = message.substring(20);
                        String director = s;
                        List<Movie> ml = movieDao.findMoviesByDirector(director);

                        String jsonStr = convertToJsonGroup(ml);
                        socketWriter.println(jsonStr);  // send message to client

                    } else if (message.startsWith("deleteMovie")){
                        String s = message.substring(12);
                        int id = Integer.parseInt(s);
                        movieDao.deleteMovie(id);
                        
                        socketWriter.println("This movie has been deleted from the database.");
                        
                    } else if(message.startsWith("updateMovie")){
                        String[] tokens = message.split(",");
                        String title = tokens[1];
                        String genre = tokens[2];
                        String director = tokens[3];
                        int id = Integer.parseInt(tokens[4]);
                        
                        movieDao.updateMovie(title, genre, director, id);
                        socketWriter.println("Movie with id: "+id+ " has been updated");
                        
                    } else if(message.startsWith("addMovie")){                
                        String[] tokens = message.split(",", -1);
                        String title = tokens[1];
                        String genre = tokens[2];
                        String director = tokens[3];
                        String runtime = tokens[4];
                        String plot = tokens[5];
                        String location = tokens[6];
                        String poster = tokens[7];
                        String rating = tokens[8];
                        String format = tokens[9];
                        String year = tokens[10];
                        String starring = tokens[11];
                        int copies = Integer.parseInt(tokens[12]);
                        String barcode = tokens[13];
                        String user_rating = tokens[14];
                        
                        movieDao.addMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                        socketWriter.println("Your movie has been added to the database");
                        
                    }
                    else {
                        socketWriter.println("I'm sorry I don't understand :( ");
                    }

                }

                socket.close();

            } catch (DaoException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }

        public String convertToJsonGroup(List<Movie> m) throws DaoException {
            String jsonstr = "{\"movies\":[";
            int moviecount = 0;

            for (Movie movie : m) {
                if (moviecount > 0 && moviecount < m.size()) {
                    jsonstr += ",";
                }
                moviecount++;

                jsonstr += "{\"Id\":\"" + movie.getId() + "\","
                        + "\"Title\":\"" + movie.getTitle() + "\","
                        + "\"Genre\":\"" + movie.getGenre() + "\","
                        + "\"Director\":\"" + movie.getDirector() + "\","
                        + "\"RunTime\":\"" + movie.getRuntime() + "\","
                        + "\"Plot\":\"" + movie.getPlot() + "\","
                        + "\"Location\":\"" + movie.getLocation() + "\","
                        + "\"Poster\":\"" + movie.getPoster() + "\","
                        + "\"Rating\":\"" + movie.getRating() + "\","
                        + "\"Format\":\"" + movie.getFormat() + "\","
                        + "\"Year\":\"" + movie.getYear() + "\","
                        + "\"Starring\":\"" + movie.getStarring() + "\","
                        + "\"Copies\":\"" + movie.getCopies() + "\","
                        + "\"Barcode\":\"" + movie.getBarcode() + "\","
                        + "\"User_Rating\":\"" + movie.getUser_rating() + "\"}";

            }
            jsonstr += "] }";
            return jsonstr;

        }

        public String convertToJson(Movie m) throws DaoException {
            Movie movie = m;

            String jsonStr = "{\"movies\":";

            jsonStr += "{\"Id\":\"" + movie.getId() + "\","
                    + "\"Title\":\"" + movie.getTitle() + "\","
                    + "\"Genre\":\"" + movie.getGenre() + "\","
                    + "\"Director\":\"" + movie.getDirector() + "\","
                    + "\"RunTime\":\"" + movie.getRuntime() + "\","
                    + "\"Plot\":\"" + movie.getPlot() + "\","
                    + "\"Location\":\"" + movie.getLocation() + "\","
                    + "\"Poster\":\"" + movie.getPoster() + "\","
                    + "\"Rating\":\"" + movie.getRating() + "\","
                    + "\"Format\":\"" + movie.getFormat() + "\","
                    + "\"Year\":\"" + movie.getYear() + "\","
                    + "\"Starring\":\"" + movie.getStarring() + "\","
                    + "\"Copies\":\"" + movie.getCopies() + "\","
                    + "\"Barcode\":\"" + movie.getBarcode() + "\","
                    + "\"User_Rating\":\"" + movie.getUser_rating() + "\"}";

            jsonStr += " }";

            System.out.println(jsonStr);

            return jsonStr;
        }
    }

}
