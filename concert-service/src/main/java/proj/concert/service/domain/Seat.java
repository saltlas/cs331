package proj.concert.service.domain;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "SEATS")
public class Seat {
	@Id
    @Column(name = "ID")
    private long id;

	@Column(name = "LABEL")
	private String label;

	@Column(name = "IS_BOOKED")
	private boolean isBooked;

	@ManyToOne
	@JoinColumn(name = "CONCERT_DATE", nullable = false)
	private ConcertDate concertDate;

	@Column(name = "COST")
	private BigDecimal cost;

	public Seat(String label, boolean isBooked, ConcertDate date, BigDecimal cost) {	
		this.label = label;
		this.isBooked = isBooked;
		this.concertDate = date;
		this.cost = cost;
	}
	
	public Seat() {}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(label).append(concertDate).hashCode();
	}
}
