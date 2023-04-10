package proj.concert.service.mapper;

import proj.concert.service.domain.Concert;
import proj.concert.service.domain.ConcertDate;
import proj.concert.common.dto.ConcertDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ConcertMapper {

	public ConcertMapper(){
	}

	public ConcertDTO convert(Concert concert){
		ConcertDTO dto = new ConcertDTO(concert.getId(), concert.getTitle(), concert.getImageName(), concert.getBlurb());
		dto.setPerformers(concert.getPerformers());
		List<ConcertDate> dates = new ArrayList<>();
		for(ConcertDate date: concert.getDates()){
			dates.add(date.getDate());
		}
		dto.setDates(dates);
		return dto;
	}
}