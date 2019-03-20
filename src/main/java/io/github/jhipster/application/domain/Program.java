package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Program.
 */
@Entity
@Table(name = "program")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "program")
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_hour")
    private Instant startHour;

    @Column(name = "end_hour")
    private Instant endHour;

    @ManyToOne
    @JsonIgnoreProperties("programs")
    private Subject subject;

    @ManyToOne
    @JsonIgnoreProperties("programs")
    private ClassRoom classRoom;

    @ManyToOne
    @JsonIgnoreProperties("programs")
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties("programs")
    private Days days;

    @ManyToOne
    @JsonIgnoreProperties("programs")
    private SchoolYear schoolYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartHour() {
        return startHour;
    }

    public Program startHour(Instant startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return endHour;
    }

    public Program endHour(Instant endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public Subject getSubject() {
        return subject;
    }

    public Program subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public Program classRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
        return this;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Room getRoom() {
        return room;
    }

    public Program room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Days getDays() {
        return days;
    }

    public Program days(Days days) {
        this.days = days;
        return this;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public Program schoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
        return this;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Program program = (Program) o;
        if (program.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), program.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Program{" +
            "id=" + getId() +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            "}";
    }
}
