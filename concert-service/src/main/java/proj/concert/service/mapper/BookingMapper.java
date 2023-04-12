package proj.concert.service.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import proj.concert.common.dto.BookingDTO;
import proj.concert.common.dto.SeatDTO;
import proj.concert.service.domain.Booking;
import proj.concert.service.domain.Seat;

public class BookingMapper {
    public BookingDTO convert(Booking booking) {
        long concertId = booking.getDate().getConcert().getId();
        LocalDateTime date = booking.getDate().getDate();
        List<SeatDTO> seats = new ArrayList<>();

        SeatMapper mapper = new SeatMapper();

        for (Seat seat : booking.getSeats()) {
            seats.add(mapper.convert(seat));
        }

        return new BookingDTO(concertId, date, seats);
    }
}
