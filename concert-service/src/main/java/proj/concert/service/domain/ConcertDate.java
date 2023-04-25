package proj.concert.service.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** 
 * Represents the date of a concert.
 * 
 * This is different from a normal date for the following reasons:
 * 
 * - It references the concert (bidirectional).
 * - It contains the seats for this concert date.
 */
@Entity
@Table(name = "CONCERT_DATES")
public class ConcertDate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /** The concert that this date is for. */
    @ManyToOne
    @JoinColumn(name = "CONCERT_ID", nullable = false)
    private Concert concert;

    /** The underlying date/time this concert date is scheduled on. */
	  @Column(name="DATE", nullable=false)
    private LocalDateTime date;

    /** All the seats of the venue for this concert date. */
    @OneToMany(mappedBy = "concertDate", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Seat> seats;

    public ConcertDate() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    /** 
     * Concert dates are deemed equal if they have the same underlying date and
     * are for the same concert.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConcertDate)) {
            return false;
        }


        if (obj == this) {
            return true;
        }

        ConcertDate rhs = (ConcertDate) obj;

        return new EqualsBuilder().
                append(date, rhs.date).
                append(concert, rhs.concert).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(date).append(concert).hashCode();
    }


}