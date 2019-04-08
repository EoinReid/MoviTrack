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
import java.util.List;

public interface MovieDaoInterface {
    
    public List<Movie> findAllMovies() throws DaoException;
    public Movie findMovieById(int mid) throws DaoException ;
    public Movie findMovieByTitle(String mt) throws DaoException;
    public List<Movie> findMoviesByDirector(String md) throws DaoException;
    public void addMovie(String mtitle, String mgenre, String mdirector, String mruntime, String mplot, String mlocation, String mposter, String mrating, String mformat, String myear, String mstarring, int mcopies, String mbarcode, String muser_rating) throws DaoException;
    public void deleteMovie(int mid) throws DaoException;
    public void updateMovie(String mtitle, String mgenre, String mdirector, int mid) throws DaoException;
    // Reccomend
}
