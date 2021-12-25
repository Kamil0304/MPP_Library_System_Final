package model;
 
//SystemUser can be Admin or Librarian

import java.io.Serializable;

public class SystemUser implements Serializable {    
    
    public enum AuthorizationLevel {ADMINISTRATOR, LIBRARIAN, BOTH};
    
    private AuthorizationLevel role;
    private String username;//will be used as id
    private String password;

    
    public AuthorizationLevel getRole() {
        return role;
    }

    public void setRole(AuthorizationLevel role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}