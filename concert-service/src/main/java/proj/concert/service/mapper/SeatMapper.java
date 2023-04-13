package proj.concert.service.mapper;

import proj.concert.common.dto.SeatDTO;
import proj.concert.service.domain.Seat;

public abstract class SeatMapper {
    public static SeatDTO convert(Seat seat) {
        return new SeatDTO(seat.getLabel(), seat.getCost());
    }
}
