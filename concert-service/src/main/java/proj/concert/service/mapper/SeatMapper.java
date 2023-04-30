package proj.concert.service.mapper;

import proj.concert.common.dto.SeatDTO;
import proj.concert.service.domain.Seat;

/**
 * Mapper class for converting between {@link SeatDTO}s and
 * {@link Seat}s.
 */
public abstract class SeatMapper {

    /** Converts a seat class into a DTO. */
    public static SeatDTO convert(Seat seat) {
        return new SeatDTO(seat.getLabel(), seat.getCost());
    }
}
