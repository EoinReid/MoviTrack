/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

/**
 *
 * @author tiarn
 */
import DTOs.Watched;
import Exceptions.DaoException;
import DAOs.MovieDaoInterface;


public interface WatchedDaoInterface {
    
    public Watched watchMovie(String username,int id) throws DaoException; 
}
