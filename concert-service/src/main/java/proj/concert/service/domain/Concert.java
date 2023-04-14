package proj.concert.service.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "CONCERTS")
public class Concert {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Lob //data is too long for a 255 character varchar so this marks it as a large object
    @Column(name = "BLURB", columnDefinition="CLOB") //character large object
    private String blurb;

    @OneToMany(mappedBy="concert", cascade = CascadeType.ALL)
    private Set<ConcertDate> dates = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "CONCERT_PERFORMER",
           joinColumns = { @JoinColumn(name = "CONCERT_ID") },
           inverseJoinColumns = { @JoinColumn(name = "PERFORMER_ID") })
    private Set<Performer> performers = new HashSet<>();

    public Concert() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Set<ConcertDate> getDates() {
        return dates;
    }

    public void setDates(Set<ConcertDate> dates) {
        this.dates = dates;
    }

    public Set<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(Set<Performer> performers) {
        this.performers = performers;
    }

    @Override
    public boolean equals(Object obj) {
        // Implement value-equality based on a Concert's title alone. ID isn't
        // included in the equality check because two Concert objects could
        // represent the same real-world Concert, where one is stored in the
        // database (and therefore has an ID - a primary key) and the other
        // doesn't (it exists only in memory).

        //taken from week 5 lab
        if (!(obj instanceof Concert))
            return false;
        if (obj == this)
            return true;

        Concert rhs = (Concert) obj;
        return new EqualsBuilder().
                append(title, rhs.title).
                isEquals();
    }

    @Override
    public int hashCode() {
        // Hash-code value is derived from the value of the title field. It's
        // good practice for the hash code to be generated based on a value
        // that doesn't change.
        return new HashCodeBuilder(17, 31).append(title).hashCode();
    }

}
