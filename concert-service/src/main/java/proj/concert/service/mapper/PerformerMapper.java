package proj.concert.service.mapper;

import proj.concert.common.dto.PerformerDTO;
import proj.concert.service.domain.Performer;

/** 
 * Mapper class for converting between {@link PerformerDTO}s and
 * {@link Performer}s.
 */
public abstract class PerformerMapper {

    /** Converts a performer class into a DTO. */
    public static PerformerDTO convert(Performer performer) {
        return new PerformerDTO(performer.getId(), performer.getName(), performer.getImageName(), performer.getGenre(), performer.getBlurb());
    }
}
