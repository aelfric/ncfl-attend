package org.ncfl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "school", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Attendee> attendees;

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public List<Attendee> getAttendees() {
        return attendees;
    }

    @SuppressWarnings("unused")
    public void setAttendees(List<Attendee> attendees) {
        attendees.forEach(a ->a.setSchool(this));
        this.attendees = attendees;
    }
}
