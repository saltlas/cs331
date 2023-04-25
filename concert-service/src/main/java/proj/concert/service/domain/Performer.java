package proj.concert.service.domain;
import proj.concert.common.types.Genre;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/** Represents a performer in the concert booking application. */
@Entity
@Table(name = "PERFORMERS")
public class Performer {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Column(name = "GENRE")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /** A brief synopsis of this performer. */
    @Lob // data is too long for a 255 character varchar so this marks it as a large object
    @Column(name = "BLURB", columnDefinition="CLOB") // character large object
    private String blurb;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageUri(String imageName) {
        this.imageName = imageName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    /** Performers are deemed equal if they have the same name. */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Performer)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Performer rhs = (Performer) obj;

        return new EqualsBuilder().
                append(name, rhs.name).
                isEquals();
    }

    /** The hash-code value of a performer is derived from their name. */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(name).hashCode();
    }
}
