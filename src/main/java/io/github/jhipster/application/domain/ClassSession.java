package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ClassSession.
 */
@Entity
@Table(name = "class_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "classsession")
public class ClassSession implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_hour", nullable = false)
    private Instant startHour;

    @NotNull
    @Column(name = "end_hour", nullable = false)
    private Instant endHour;

    @NotNull
    @Column(name = "detail", nullable = false)
    private String detail;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JsonIgnoreProperties("classSessions")
    private ClassSessionType classSessionType;

    @ManyToOne
    @JsonIgnoreProperties("classSessions")
    private Program program;

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

    public ClassSession startHour(Instant startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return endHour;
    }

    public ClassSession endHour(Instant endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public String getDetail() {
        return detail;
    }

    public ClassSession detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ClassSession createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ClassSessionType getClassSessionType() {
        return classSessionType;
    }

    public ClassSession classSessionType(ClassSessionType classSessionType) {
        this.classSessionType = classSessionType;
        return this;
    }

    public void setClassSessionType(ClassSessionType classSessionType) {
        this.classSessionType = classSessionType;
    }

    public Program getProgram() {
        return program;
    }

    public ClassSession program(Program program) {
        this.program = program;
        return this;
    }

    public void setProgram(Program program) {
        this.program = program;
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
        ClassSession classSession = (ClassSession) o;
        if (classSession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassSession{" +
            "id=" + getId() +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", detail='" + getDetail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
