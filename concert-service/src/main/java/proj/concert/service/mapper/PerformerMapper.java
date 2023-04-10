package proj.concert.service.mapper;

import proj.concert.common.dto.ConcertDTO;
import proj.concert.common.dto.PerformerDTO;
import proj.concert.service.domain.Concert;
import proj.concert.service.domain.ConcertDate;
import proj.concert.service.domain.Performer;

import java.util.ArrayList;
import java.util.List;

public class PerformerMapper {

    public PerformerMapper(){
    }

    public PerformerDTO convert(Performer performer){
        PerformerDTO dto = new PerformerDTO(performer.getId(), performer.getName(), performer.getImageName(), performer.getGenre(), performer.getBlurb());
        return dto;
    }
}
