package proj.concert.service.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class ConcertDate {

	@Column(name="DATE", nullable=false)
    private LocalDateTime date;

    protected ConcertDate() {
    }

    public ConcertDate(LocalDateTime date){
    	this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(date).hashCode();
    }


}