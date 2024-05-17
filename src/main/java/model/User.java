package model;

public class User {
    // nese nuk e trashegon as ni klas klasen user, i kishim bo final qito
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private String passwordHash;
    private boolean selectedRole;
    private String userType; // Added userType property

    // e kom shtu n konstruktor selected role se su kan
    public User(int id, String firstName, String lastName, String email, String salt, String passwordHash, String userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.selectedRole = selectedRole;
        this.userType = userType; // Initialize userType
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isSelectedRole() {
        return selectedRole;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void print(){
        System.out.println("Emri: " + this.firstName + " " + this.lastName);
        System.out.println("Email: " + this.email);
        System.out.println("isSelectedRole(): " + this.isSelectedRole());
    }
}
