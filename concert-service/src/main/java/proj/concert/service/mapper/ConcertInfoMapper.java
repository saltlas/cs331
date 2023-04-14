package proj.concert.service.mapper;

import proj.concert.service.domain.Seat;
import proj.concert.service.domain.ConcertDate;
import proj.concert.common.dto.ConcertInfoNotificationDTO;

import java.util.ArrayList;
import java.util.List;



public abstract class ConcertInfoMapper {


	public static ConcertInfoNotificationDTO convert(ConcertDate concertDate){
		List<Seat> seats = new ArrayList(concertDate.getSeats());
		int unbooked = 0;
		for(Seat seat: seats){
			if(!(seat.isBooked())){
				unbooked += 1;
			}
		}
		ConcertInfoNotificationDTO dto = new ConcertInfoNotificationDTO(unbooked);

		return dto;
	}
}