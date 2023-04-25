package proj.concert.service.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "CONCERT_DATES")
public class ConcertDate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "CONCERT_ID", nullable = false)
    private Concert concert;

	@Column(name="DATE", nullable=false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "concertDate", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Seat> seats;

    protected ConcertDate() {
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConcertDate))
            return false;
        if (obj == this)
            return true;

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