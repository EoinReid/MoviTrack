/**                                                 OOP Feb 2019
 * This App demonstrates the use of a Data Access Object (DAO) 
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 * 
 * "Use a Data Access Object (DAO) to abstract and encapsulate all 
 * access to the data source. The DAO manages the connection with 
 * the data source to obtain and store data" Ref: oracle.com
 * 
 * Here we use one DAO per database table.
 * 
 * Use the SQL script included with this project to create the 
 * required MySQL user_database and user table
 */
package BusinessObjects;

import DTOs.User;
import Daos.MySqlUserDao;
import Daos.UserDaoInterface;
import DTOs.Movie;
import DAOs.MySqlMovieDao;
import DAOs.MovieDaoInterface;
import Exceptions.DaoException;
import java.util.List;

public class MainApp
{
    public static void main( String [] args)
    {
        UserDaoInterface IUserDao = new MySqlUserDao();
        MovieDaoInterface IMovieDao = new MySqlMovieDao();
        //"IUse..." -> "I" for Interface
        // Notice that the userDao reference is an Interface type.
        // This allows for the use of different concrete implementations.
        // e.g. we could replace the MySqlUserDao with an OracleUserDao 
        // (accessing an Oracle Database) 
        // without changing anything in the Interface. 
        // If the Interface doesn't change, then none of the
        // code below that uses the interface needs to change.
        // The 'contract' defined by the interface will not be broken.
        // This means that this code is independent of the code
        // used to access the database. (Reduced coupling).
        
        // The Business Objects require that all User DAOs implement
        // the interface called "UserDaoInterface", as the code uses
        // only references of the interface type to access the DAO methods.
        
        
        try
        {

            List<Movie> movies = IMovieDao.findAllMovies();
            
            if( movies.isEmpty() )
                System.out.println("There are no Movies");
            
            // for( Movie movie : movies )
            //   System.out.println("Movie: " + movie.toString());
            
            String jsonStr = "{\"movies\":[";
            
            int moviecount = 0;
            for(Movie movie: movies)
            {
                if(moviecount>0 && moviecount <movies.size()){
                    jsonStr+=",";
                }
                moviecount++;
                
                jsonStr +="{\"Id\":\""+movie.getId()+"\","
                        +"\"Title\":\""+movie.getTitle()+"\","
                        +"\"Genre\":\""+movie.getGenre()+"\","
                        +"\"Director\":\""+movie.getDirector()+"\","
                        +"\"Runtime\":\""+movie.getRuntime()+"\","
                        +"\"Plot\":\""+movie.getPlot()+"\","
                        +"\"Location\":\""+movie.getLocation()+"\","
                        +"\"Poster\":\""+movie.getPoster()+"\","
                        +"\"Rating\":\""+movie.getRating()+"\","
                        +"\"Format\":\""+movie.getFormat()+"\","
                        +"\"Year\":\""+movie.getYear()+"\","
                        +"\"Starring\":\""+movie.getStarring()+"\","
                        +"\"Copies\":\""+movie.getCopies()+"\","
                        +"\"Barcode\":\""+movie.getBarcode()+"\","
                        +"\"User_Rating\":\""+movie.getUser_rating()+"\"}";
                
                
            }
            //jsonStr = jsonStr.substring(0,jsonStr.length()-1); //removes the last comma.
            jsonStr += "] }";
            System.out.println(jsonStr);
            
            
            // test dao - with good id
            Movie movie = IMovieDao.findMovieById(16);
            if(movie != null)
                System.out.println("Movie found: " + movie);
            else
                System.out.println("Movie with that id was not found");
            
            // test dao - with bad id
            movie = IMovieDao.findMovieById(2);
            if(movie != null)
                System.out.println("Movie found: " + movie);
            else
                System.out.println("Movie with that id was not found");
            
        }
        catch( DaoException e )
        {
          e.printStackTrace();         
        }       
    }
}
