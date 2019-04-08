/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author tiarn
 */
public class Watched {
    
    private String username;
    private int id;
    //private Timestamp;

    public Watched(String username, int id) {
        this.username = username;
        this.id = id;
    }
    
    public Watched(){
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Watched{" + "username=" + username + ", id=" + id + '}';
    }
    
    
    
}
