package pl.coderslab.entity;

public class User {
    private int id;
    private String email;
    private String userName;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        try {
            this.userName = userName;
        } catch (NullPointerException e){
            System.out.println("Błędny email");
        }
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

    @Override
    public String toString() {
        return "New User{" +
                "email=" + email +
                ", userName=" + userName +
                ", password=" + password +
                '}';
    }
}
