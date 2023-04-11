package proj.concert.service.mapper;

import proj.concert.service.domain.Concert;
import proj.concert.common.dto.ConcertSummaryDTO;

import java.util.ArrayList;
import java.util.List;


public class ConcertSummaryMapper {

	public ConcertSummaryMapper(){
	}

	public ConcertSummaryDTO convert(Concert concert){
		ConcertSummaryDTO dto = new ConcertSummaryDTO(concert.getId(), concert.getTitle(), concert.getImageName());
		return dto;
	}
}