package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Evaluation.
 */
@Entity
@Table(name = "evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evaluation")
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "planned_date", nullable = false)
    private LocalDate plannedDate;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JsonIgnoreProperties("evaluations")
    private EvaluationType evaluationType;

    @ManyToOne
    @JsonIgnoreProperties("evaluations")
    private SchoolSchoolYear schoolSchoolYear;

    @ManyToOne
    @JsonIgnoreProperties("evaluations")
    private Period period;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "evaluation_class_room",
               joinColumns = @JoinColumn(name = "evaluation_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "class_room_id", referencedColumnName = "id"))
    private Set<ClassRoom> classRooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public Evaluation plannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
        return this;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Boolean isIsDone() {
        return isDone;
    }

    public Evaluation isDone(Boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public Evaluation evaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
        return this;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public Evaluation duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public Evaluation evaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
        return this;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public SchoolSchoolYear getSchoolSchoolYear() {
        return schoolSchoolYear;
    }

    public Evaluation schoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
        return this;
    }

    public void setSchoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
    }

    public Period getPeriod() {
        return period;
    }

    public Evaluation period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Set<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public Evaluation classRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
        return this;
    }

    public Evaluation addClassRoom(ClassRoom classRoom) {
        this.classRooms.add(classRoom);
        classRoom.getEvaluations().add(this);
        return this;
    }

    public Evaluation removeClassRoom(ClassRoom classRoom) {
        this.classRooms.remove(classRoom);
        classRoom.getEvaluations().remove(this);
        return this;
    }

    public void setClassRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
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
        Evaluation evaluation = (Evaluation) o;
        if (evaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + getId() +
            ", plannedDate='" + getPlannedDate() + "'" +
            ", isDone='" + isIsDone() + "'" +
            ", evaluationDate='" + getEvaluationDate() + "'" +
            ", duration=" + getDuration() +
            "}";
    }
}
