package proj.concert.service.mapper;

import proj.concert.common.dto.PerformerDTO;
import proj.concert.service.domain.Performer;

public abstract class PerformerMapper {

    public static PerformerDTO convert(Performer performer){
        PerformerDTO dto = new PerformerDTO(performer.getId(), performer.getName(), performer.getImageName(), performer.getGenre(), performer.getBlurb());
        return dto;
    }
}
