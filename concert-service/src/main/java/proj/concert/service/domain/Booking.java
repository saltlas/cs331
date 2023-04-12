package proj.concert.service.domain;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "BOOKINGS")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "DATE_ID", nullable = false)
    private ConcertDate date;

    @OneToMany(mappedBy = "booking")
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Booking() {
    }

    public void setDate(ConcertDate date) {
        this.date = date;
    }

    public ConcertDate getDate() {
        return date;
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
                append(date, rhs.date).
                append(user, rhs.user)
                .append(seats, rhs.seats)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(date).append(user).append(seats).hashCode();
    }
    
}
