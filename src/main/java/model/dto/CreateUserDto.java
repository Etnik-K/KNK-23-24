package model.dto;

public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private String passwordHash;
    private String selectedRole;

    public CreateUserDto(String firstName, String lastName, String email, String salt, String passwordHash, String selectedRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.selectedRole= selectedRole;
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
    public String getSelectedRole() {return selectedRole; }
}
