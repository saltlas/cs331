package proj.concert.service.mapper;

import proj.concert.service.domain.Concert;
import proj.concert.common.dto.ConcertSummaryDTO;


public abstract class ConcertSummaryMapper {

	public static ConcertSummaryDTO convert(Concert concert){
		ConcertSummaryDTO dto = new ConcertSummaryDTO(concert.getId(), concert.getTitle(), concert.getImageName());
		return dto;
	}
}