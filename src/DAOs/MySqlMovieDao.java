/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

/**
 *
 * @author Administrator
 */
import DTOs.Movie;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;




public class MySqlMovieDao extends Daos.MySqlDao implements MovieDaoInterface {
 
    @Override
    
    public List<Movie> findAllMovies() throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        List<Movie> movies = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM MOVIES";
            ps = con.prepareStatement(query);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                int movieID = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                plot = plot.replaceAll("\"","*");
                plot = plot.trim();
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                int copies = rs.getInt("Copies");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                m = new Movie(movieID, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                movies.add(m);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllMovies() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllMovies() " + e.getMessage());
            }
        }
        return movies;     // may be empty
    }
    
    @Override
    public Movie findMovieById(int mid) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();
            
            String query = "SELECT * FROM MOVIES WHERE ID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, mid);
            
            
            rs = ps.executeQuery();
            if (rs.next()) 
            {
            	int movieID = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                   plot = plot.replaceAll("\"","*");
                plot = plot.trim();
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                int copies = rs.getInt("Copies");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                m = new Movie(movieID, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findMovieById() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findMovieById() " + e.getMessage());
            }
        }
        return m;     // u may be null 
    } 
    
     @Override
    public Movie findMovieByTitle(String mt) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();
            
            String query = "SELECT * FROM MOVIES WHERE TITLE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, mt);
            
            
            rs = ps.executeQuery();
            if (rs.next()) 
            {
            	int movieID = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                plot = plot.replaceAll("\"","*");
                plot = plot.trim();
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                int copies = rs.getInt("Copies");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                m = new Movie(movieID, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findMovieByTitle() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findMovieByTitle() " + e.getMessage());
            }
        }
        return m;     // u may be null 
    } 
    
    
    
        
     @Override
    public List<Movie> findMoviesByDirector(String md) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> m = new ArrayList<>();
        Movie movie = null;
        
        
        try {
            con = this.getConnection();
            
            String query = "SELECT * FROM MOVIES WHERE director = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, md);
                      
            rs = ps.executeQuery();
           while (rs.next()) 
            {
            	int movieID = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                plot = plot.replaceAll("\"","*");
                plot = plot.trim();
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                int copies = rs.getInt("Copies");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                movie = new Movie(movieID, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                m.add(movie);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findMovieByDirector() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findMoviesByDirector() " + e.getMessage());
            }
        }
        return m;     // u may be null 
    }
    @Override
    public void addMovie(String mtitle, String mgenre, String mdirector, String mruntime, String mplot, String mlocation, String mposter, String mrating, String mformat, String myear, String mstarring, int mcopies, String mbarcode, String muser_rating) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();
            
            String query = "INSERT INTO movies(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            ps = con.prepareStatement(query);
            ps.setString(1, mtitle);
            ps.setString(2, mgenre);
            ps.setString(3, mdirector);
            ps.setString(4, mruntime);
            ps.setString(5, mplot);
            ps.setString(6, mlocation);
            ps.setString(7, mposter);
            ps.setString(8, mrating);
            ps.setString(9, mformat);
            ps.setString(10, myear);
            ps.setString(11, mstarring);
            ps.setInt(12, mcopies);
            ps.setString(13, mbarcode);
            ps.setString(14, muser_rating);
            
            ps.execute();
        }
        catch (SQLException e) 
            {
                throw new DaoException("addMovie() " + e.getMessage());
            }
    }
    
    @Override
    public void updateMovie(String mtitle, String mgenre, String mdirector, int mid) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();
            
            String query = "UPDATE Movies SET title=?, genre=?, director=? WHERE id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, mtitle);
            ps.setString(2, mgenre);
            ps.setString(3, mdirector);
            ps.setInt(4, mid);
            
            ps.execute();
        }
        catch (SQLException e) 
            {
                throw new DaoException("updateMovie() " + e.getMessage());
            }
    }
    
    
    @Override
    public void deleteMovie(int mid) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try {
            con = this.getConnection();
            
            String query = "DELETE FROM movies WHERE id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, mid);
         
            ps.execute();
        }
        catch (SQLException e) 
            {
                throw new DaoException("deleteMovie() " + e.getMessage());
            }
        
    }
}
