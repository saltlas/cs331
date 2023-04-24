package proj.concert.service.mapper;

import proj.concert.service.domain.Seat;
import proj.concert.service.domain.ConcertDate;
import proj.concert.common.dto.ConcertInfoNotificationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between {@link ConcertInfoNotificationDTO}s and
 * {@link ConcertDate}s.
 */
public abstract class ConcertInfoMapper {

	/** Converts a concert date into a info notification DTO. */
	public static ConcertInfoNotificationDTO convert(ConcertDate concertDate) {
		List<Seat> seats = new ArrayList<Seat>(concertDate.getSeats());

		int unbooked = 0;

		for (Seat seat: seats) {
			if (!(seat.isBooked())) {
				unbooked += 1;
			}
		}

		return new ConcertInfoNotificationDTO(unbooked);
	}
}