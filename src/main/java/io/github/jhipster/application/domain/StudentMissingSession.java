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
 * A StudentMissingSession.
 */
@Entity
@Table(name = "student_missing_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "studentmissingsession")
public class StudentMissingSession implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_justified", nullable = false)
    private Boolean isJustified;

    @ManyToOne
    @JsonIgnoreProperties("studentMissingSessions")
    private ClassSession classSession;

    @ManyToOne
    @JsonIgnoreProperties("studentMissingSessions")
    private Student student;

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

    public StudentMissingSession isJustified(Boolean isJustified) {
        this.isJustified = isJustified;
        return this;
    }

    public void setIsJustified(Boolean isJustified) {
        this.isJustified = isJustified;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public StudentMissingSession classSession(ClassSession classSession) {
        this.classSession = classSession;
        return this;
    }

    public void setClassSession(ClassSession classSession) {
        this.classSession = classSession;
    }

    public Student getStudent() {
        return student;
    }

    public StudentMissingSession student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        StudentMissingSession studentMissingSession = (StudentMissingSession) o;
        if (studentMissingSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentMissingSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentMissingSession{" +
            "id=" + getId() +
            ", isJustified='" + isIsJustified() + "'" +
            "}";
    }
}
