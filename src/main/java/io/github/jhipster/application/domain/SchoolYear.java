package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SchoolYear.
 */
@Entity
@Table(name = "school_year")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "schoolyear")
public class SchoolYear implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "school_yearlabel", nullable = false)
    private String schoolYearlabel;

    @NotNull
    @Column(name = "course_start_date", nullable = false)
    private LocalDate courseStartDate;

    @NotNull
    @Column(name = "course_end_date", nullable = false)
    private LocalDate courseEndDate;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "is_blank_year", nullable = false)
    private Boolean isBlankYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolYearlabel() {
        return schoolYearlabel;
    }

    public SchoolYear schoolYearlabel(String schoolYearlabel) {
        this.schoolYearlabel = schoolYearlabel;
        return this;
    }

    public void setSchoolYearlabel(String schoolYearlabel) {
        this.schoolYearlabel = schoolYearlabel;
    }

    public LocalDate getCourseStartDate() {
        return courseStartDate;
    }

    public SchoolYear courseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
        return this;
    }

    public void setCourseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public LocalDate getCourseEndDate() {
        return courseEndDate;
    }

    public SchoolYear courseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
        return this;
    }

    public void setCourseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public SchoolYear startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SchoolYear endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isIsBlankYear() {
        return isBlankYear;
    }

    public SchoolYear isBlankYear(Boolean isBlankYear) {
        this.isBlankYear = isBlankYear;
        return this;
    }

    public void setIsBlankYear(Boolean isBlankYear) {
        this.isBlankYear = isBlankYear;
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
        SchoolYear schoolYear = (SchoolYear) o;
        if (schoolYear.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolYear.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolYear{" +
            "id=" + getId() +
            ", schoolYearlabel='" + getSchoolYearlabel() + "'" +
            ", courseStartDate='" + getCourseStartDate() + "'" +
            ", courseEndDate='" + getCourseEndDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isBlankYear='" + isIsBlankYear() + "'" +
            "}";
    }
}
