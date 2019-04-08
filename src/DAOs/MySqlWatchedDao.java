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

import DTOs.Movie;
import DTOs.Watched;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;


public class MySqlWatchedDao extends Daos.MySqlDao implements WatchedDaoInterface{
    
    @Override
    public Watched watchMovie(String username, int id) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
            try {
            con = this.getConnection();
            
       
            
            String query = "INSERT INTO watched(username,movieId) VALUES(?,?) ";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, id);
            
            ps.execute();
            }
            catch (SQLException e) 
            {
                throw new DaoException("watchMovie() " + e.getMessage());
            }
            return null;
    }

}
