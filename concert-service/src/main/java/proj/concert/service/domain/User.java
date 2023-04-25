package proj.concert.service.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/** Represents a user in the concert booking application. */
@Entity
@Table(name="USERS")
public class User {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /** List of bookings made by this user. */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getVersion() {
        return version;
    }

    protected void setVersion(Long version) {
        this.version = version;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    /** Users are deemed equal if they have the same username and password. */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        User rhs = (User) obj;
        
        return new EqualsBuilder().
                append(username, rhs.username).
                append(password, rhs.password).
                isEquals();
    }

    /** The hash-code value of a user is derived from their username and password. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(username).append(password).hashCode();
    }
}
