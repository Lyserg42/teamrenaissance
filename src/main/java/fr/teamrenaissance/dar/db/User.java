package fr.teamrenaissance.dar.db;

public class User {

    private Integer userID; //Long ?
    private String name;
    private String firstname;
    private String username;
    private String email;
    private String password; //?
    private String address;
    private String avatar;

    public User(String name, String firstname, String username, String email, String password, String address, String avatar){
        this.name = name;
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
    }

    public User(){}

    public Integer getUserID() {
        return userID;
    }

    private void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
