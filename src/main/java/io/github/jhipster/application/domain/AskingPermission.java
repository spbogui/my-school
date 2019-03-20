package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AskingPermission.
 */
@Entity
@Table(name = "asking_permission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "askingpermission")
public class AskingPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "asking_date", nullable = false)
    private LocalDate askingDate;

    @NotNull
    @Column(name = "number_of_day", nullable = false)
    private Integer numberOfDay;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne
    @JsonIgnoreProperties("askingPermissions")
    private SchoolSchoolYear schoolSchoolYear;

    @ManyToOne
    @JsonIgnoreProperties("askingPermissions")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAskingDate() {
        return askingDate;
    }

    public AskingPermission askingDate(LocalDate askingDate) {
        this.askingDate = askingDate;
        return this;
    }

    public void setAskingDate(LocalDate askingDate) {
        this.askingDate = askingDate;
    }

    public Integer getNumberOfDay() {
        return numberOfDay;
    }

    public AskingPermission numberOfDay(Integer numberOfDay) {
        this.numberOfDay = numberOfDay;
        return this;
    }

    public void setNumberOfDay(Integer numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public String getReason() {
        return reason;
    }

    public AskingPermission reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SchoolSchoolYear getSchoolSchoolYear() {
        return schoolSchoolYear;
    }

    public AskingPermission schoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
        return this;
    }

    public void setSchoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
    }

    public Student getStudent() {
        return student;
    }

    public AskingPermission student(Student student) {
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
        AskingPermission askingPermission = (AskingPermission) o;
        if (askingPermission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), askingPermission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AskingPermission{" +
            "id=" + getId() +
            ", askingDate='" + getAskingDate() + "'" +
            ", numberOfDay=" + getNumberOfDay() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
