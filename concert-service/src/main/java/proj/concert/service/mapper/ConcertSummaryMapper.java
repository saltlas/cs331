package proj.concert.service.mapper;

import proj.concert.service.domain.Concert;
import proj.concert.common.dto.ConcertSummaryDTO;


/**
 * Mapper class for converting between {@link ConcertSummaryDTO}s and
 * {@link Concert}s.
 */
public abstract class ConcertSummaryMapper {

	/** Converts a concert class into a DTO. */
	public static ConcertSummaryDTO convert(Concert concert) {
		return new ConcertSummaryDTO(concert.getId(), concert.getTitle(), concert.getImageName());
	}
}