package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SchoolYear entity.
 */
public class SchoolYearDTO implements Serializable {

    private Long id;

    @NotNull
    private String schoolYearlabel;

    @NotNull
    private LocalDate courseStartDate;

    @NotNull
    private LocalDate courseEndDate;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Boolean isBlankYear;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolYearlabel() {
        return schoolYearlabel;
    }

    public void setSchoolYearlabel(String schoolYearlabel) {
        this.schoolYearlabel = schoolYearlabel;
    }

    public LocalDate getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public LocalDate getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isIsBlankYear() {
        return isBlankYear;
    }

    public void setIsBlankYear(Boolean isBlankYear) {
        this.isBlankYear = isBlankYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolYearDTO schoolYearDTO = (SchoolYearDTO) o;
        if (schoolYearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolYearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolYearDTO{" +
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
