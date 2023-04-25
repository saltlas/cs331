package proj.concert.service.domain;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

/** 
 * Represents a seat in a venue for a concert date. 
 * 
 * Seats are scoped only to their concert date, meaning a phsyical seat at a
 * venue can be represented by multiple seats in the database, one for each
 * concert date.
*/
@Entity
@Table(name = "SEATS")
public class Seat {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

	@Column(name = "LABEL")
	private String label;

	/** 
	 * Whether this seat has been booked by a user, if so a corresponding
	 * booking will exist.
	 */
	@Column(name = "IS_BOOKED")
	private boolean isBooked;

	/** The concert date this seat is for. */
	@ManyToOne
	@JoinColumn(name = "CONCERT_DATE", nullable = false)
	private ConcertDate concertDate;

	/** The cost to book this seat. */
	@Column(name = "COST")
	private BigDecimal cost;

	public Seat(String label, boolean isBooked, ConcertDate date, BigDecimal cost) {	
		this.label = label;
		this.isBooked = isBooked;
		this.concertDate = date;
		this.cost = cost;
	}
	
	public Seat() {}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public ConcertDate getConcertDate() {
		return concertDate;
	}

	public void setConcertDate(ConcertDate concertDate) {
		this.concertDate = concertDate;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/** Seats are deemed equal if they have the same label and concert date. */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Seat)) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Seat rhs = (Seat) obj;

		return new EqualsBuilder().
				append(label, rhs.label).
				append(concertDate, rhs.concertDate)
				.isEquals();
	}

	/** The hash-code value of a seat is derived from its label and concert date. */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(label).append(concertDate).hashCode();
	}
}
