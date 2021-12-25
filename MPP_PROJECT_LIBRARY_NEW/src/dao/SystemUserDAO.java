/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SystemUser;

/**
 *
 * @author mauro
 */
public class SystemUserDAO {

    static final String OUTPUT_DIR = IStoragePath.OUTPUT_DIR + "user";

    private SystemUser user;

    public SystemUserDAO() {};
    
    public SystemUserDAO(String username) {
        user = getUser(username);
    }
    
    public boolean isNull() {
        if (user == null)
            return true;
        
        return false;
    }
    
    public SystemUser getUser() {
        return user;
    }
    

    public void setUser(SystemUser user) {
        this.user = user;
    }
    
    public void addUser (SystemUser user) throws IOException {     
        DataAccessUtil.saveObject(OUTPUT_DIR, user.getUsername(), user);
    }
    
    public List<SystemUser> getAllUsers(){
        return DataAccessUtil.readAllObject(OUTPUT_DIR);
    }
    
        //if addBook implemented by save based on Isbn -> then method is ok 
    public SystemUser getUser(String username){
        return DataAccessUtil.readGenericObject(OUTPUT_DIR, username);
    }
    
    public boolean isValidUser(SystemUser user) {
        //Recovering the users from database
        SystemUserDAO userDAO = new SystemUserDAO();
        List<SystemUser> usersList = userDAO.getAllUsers();
        
        boolean isNull = usersList.isEmpty();
        boolean invalidUsername = true;
        boolean invalidPassword = true;
        
        if (isNull) {
            return false;
        }
        else {
            for (SystemUser userOnArray : usersList ) {
                if (userOnArray != null) {
                    invalidUsername = ! userOnArray.getUsername().equals(user.getUsername());
                    invalidPassword = ! userOnArray.getPassword().equals(user.getPassword());
                }
                if (!invalidUsername && !invalidPassword) {
                    break;
                }
            }
        }
        
        return (!invalidUsername && !invalidPassword);
                    
    }
}
