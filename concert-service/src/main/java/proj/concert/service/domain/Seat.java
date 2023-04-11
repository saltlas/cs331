package proj.concert.service.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SEATS")
public class Seat {
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
}
