package proj.concert.service.domain;

import proj.concert.common.types.Genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERFORMERS")
public class Performer {

    @Id
    @Column(name = "ID")
    private Long Id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Column(name = "GENRE")
    private Genre genre;

    @Column(name = "BLURB")
    private String blurb;

}
