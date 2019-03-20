package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TeacherMissingSession.
 */
@Entity
@Table(name = "teacher_missing_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "teachermissingsession")
public class TeacherMissingSession implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_justified", nullable = false)
    private Boolean isJustified;

    @ManyToOne
    @JsonIgnoreProperties("teacherMissingSessions")
    private ClassSession classSession;

    @ManyToOne
    @JsonIgnoreProperties("teacherMissingSessions")
    private Teacher student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsJustified() {
        return isJustified;
    }

    public TeacherMissingSession isJustified(Boolean isJustified) {
        this.isJustified = isJustified;
        return this;
    }

    public void setIsJustified(Boolean isJustified) {
        this.isJustified = isJustified;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public TeacherMissingSession classSession(ClassSession classSession) {
        this.classSession = classSession;
        return this;
    }

    public void setClassSession(ClassSession classSession) {
        this.classSession = classSession;
    }

    public Teacher getStudent() {
        return student;
    }

    public TeacherMissingSession student(Teacher teacher) {
        this.student = teacher;
        return this;
    }

    public void setStudent(Teacher teacher) {
        this.student = teacher;
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
        TeacherMissingSession teacherMissingSession = (TeacherMissingSession) o;
        if (teacherMissingSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacherMissingSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeacherMissingSession{" +
            "id=" + getId() +
            ", isJustified='" + isIsJustified() + "'" +
            "}";
    }
}
