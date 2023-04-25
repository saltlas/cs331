package proj.concert.service.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import proj.concert.common.dto.BookingDTO;
import proj.concert.common.dto.SeatDTO;
import proj.concert.service.domain.Booking;
import proj.concert.service.domain.ConcertDate;
import proj.concert.service.domain.Seat;

/** 
 * Mapper class for converting between {@link BookingDTO}s and
 * {@link Booking}s.
 */
public abstract class BookingMapper {

    /** Converts a booking class into a DTO. */
    public static BookingDTO convert(Booking booking) {
        ConcertDate concertDate = booking.getDate();

        long concertId = concertDate.getConcert().getId();
        LocalDateTime date = concertDate.getDate();
        List<SeatDTO> seats = new ArrayList<>();

        for (Seat seat : booking.getSeats()) {
            seats.add(SeatMapper.convert(seat));
        }

        return new BookingDTO(concertId, date, seats);
    }
}
