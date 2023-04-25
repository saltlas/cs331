package proj.concert.service.mapper;

import proj.concert.service.domain.Concert;
import proj.concert.service.domain.Performer;
import proj.concert.service.domain.ConcertDate;
import proj.concert.common.dto.ConcertDTO;
import proj.concert.common.dto.PerformerDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** 
 * Mapper class for converting between {@link ConcertDTO}s and
 * {@link Concert}s.
 */
public abstract class ConcertMapper {

	/** Converts a concert class into a DTO. */
	public static ConcertDTO convert(Concert concert) {
		ConcertDTO dto = new ConcertDTO(concert.getId(), concert.getTitle(), concert.getImageName(), concert.getBlurb());

		List<PerformerDTO> performers = new ArrayList<>();

		for (Performer performer: concert.getPerformers()) {
			performers.add(PerformerMapper.convert(performer));
		}
		
		dto.setPerformers(performers);

		List<LocalDateTime> dates = new ArrayList<>();

		for (ConcertDate date: concert.getDates()) {
			dates.add(date.getDate());
		}

		dto.setDates(dates);
		
		return dto;
	}
}