
package roommanagementproject;


public class Users {
    private String email;
    private int userID;
    private String resources;

    public Users(String email, int userID, String resources) {
        this.email = email;
        this.userID = userID;
        this.resources = resources;
    }
    
        @Override
    public String toString() {
        return email + "," + userID + "," + resources;
    }
    
    public String getEmail() {
        return email;
    }
    
    public int getUserID(){
        return userID;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setUserID(int userID){
        this.userID = userID;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }


    
}
