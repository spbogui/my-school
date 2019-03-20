package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TeacherSubjectSchoolYear.
 */
@Entity
@Table(name = "teacher_subject_school_year")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "teachersubjectschoolyear")
public class TeacherSubjectSchoolYear implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_principal")
    private Boolean isPrincipal;

    @ManyToOne
    @JsonIgnoreProperties("teacherSubjectSchoolYears")
    private Actor actor;

    @ManyToOne
    @JsonIgnoreProperties("teacherSubjectSchoolYears")
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties("teacherSubjectSchoolYears")
    private SchoolSchoolYear schoolSchoolYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsPrincipal() {
        return isPrincipal;
    }

    public TeacherSubjectSchoolYear isPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
        return this;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public Actor getActor() {
        return actor;
    }

    public TeacherSubjectSchoolYear actor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public TeacherSubjectSchoolYear teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SchoolSchoolYear getSchoolSchoolYear() {
        return schoolSchoolYear;
    }

    public TeacherSubjectSchoolYear schoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
        return this;
    }

    public void setSchoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
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
        TeacherSubjectSchoolYear teacherSubjectSchoolYear = (TeacherSubjectSchoolYear) o;
        if (teacherSubjectSchoolYear.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacherSubjectSchoolYear.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeacherSubjectSchoolYear{" +
            "id=" + getId() +
            ", isPrincipal='" + isIsPrincipal() + "'" +
            "}";
    }
}
