package proj.concert.service.mapper;

import proj.concert.common.dto.SeatDTO;
import proj.concert.service.domain.Seat;

public class SeatMapper {
    public SeatDTO convert(Seat seat) {
        return new SeatDTO(seat.getLabel(), seat.getCost());
    }
}
