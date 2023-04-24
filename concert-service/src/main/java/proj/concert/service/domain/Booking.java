package proj.concert.service.domain;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Represents a user's booking of seats for a concert at a given date. */
@Entity
@Table(name = "BOOKINGS")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /** 
     * The seats included by this booking. The `isBooked` property of these
     * should be `true`.
     */
    @OneToMany
    private List<Seat> seats;

    /** The user who made this booking. */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /** 
     * The concert date this booking is for.
     * 
     * It may seem redundant to store the concert date here since the seats
     * already have a reference to it, but it allows us to fetch the concert
     * date of a booking without also having to fetch all of its seats.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONCERT_DATE_ID", nullable = false)
    private ConcertDate date;

    public Booking() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDate(ConcertDate date) {
        this.date = date;
    }

    public ConcertDate getDate() {
        return date;
    }

    /** 
     * Bookings are deemed equal if they are made by the same user, and contain
     * the same seats.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Booking rhs = (Booking) obj;

        return new EqualsBuilder().
                append(user, rhs.user)
                .append(seats, rhs.seats)
                .isEquals();
    }

    /** The hash-code value of a booking is derived from it's user and seats. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(user).append(seats).hashCode();
    }
    
}
