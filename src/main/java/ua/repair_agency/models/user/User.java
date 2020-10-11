package ua.repair_agency.models.user;

import ua.repair_agency.constants.Role;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int password;
    private String language;
    private Role role;

    private User(){}

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

    public int getPassword() {
        return password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public Role getRole() {
        return role;
    }

    public static class UserBuilder{
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private int password;
        private String language;
        private Role role;

        public UserBuilder setId(int id){
            this.id = id;
            return this;
        }

        public UserBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(int password){
            this.password = password;
            return this;
        }

        public UserBuilder setLanguage(String language){
            this.language = language;
            return this;
        }

        public UserBuilder setRole(Role role){
            this.role = role;
            return this;
        }

        public User build(){
            User user = new User();
            user.id = id;
            user.firstName = firstName;
            user.lastName = lastName;
            user.email = email;
            user.password = password;
            user.language = language;
            user.role = role;
            return user;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", language='" + language + '\'' +
                ", role=" + role +
                '}';
    }
}