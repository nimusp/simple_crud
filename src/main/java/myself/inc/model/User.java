/*
 * Developed by Sumin Pavel on 5/2/19 11:23 PM
 */

package myself.inc.model;

import java.util.Date;

public class User {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final String email;

    public User(int userId, String firstName, String lastName, Date dateOfBirth, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                '}';
    }
}
