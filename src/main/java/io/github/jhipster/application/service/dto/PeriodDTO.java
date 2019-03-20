package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Period entity.
 */
public class PeriodDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalDate createdAt;


    private Long periodLabelId;

    private Long schoolYearlabelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPeriodLabelId() {
        return periodLabelId;
    }

    public void setPeriodLabelId(Long periodTypeId) {
        this.periodLabelId = periodTypeId;
    }

    public Long getSchoolYearlabelId() {
        return schoolYearlabelId;
    }

    public void setSchoolYearlabelId(Long schoolYearId) {
        this.schoolYearlabelId = schoolYearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeriodDTO periodDTO = (PeriodDTO) o;
        if (periodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", periodLabel=" + getPeriodLabelId() +
            ", schoolYearlabel=" + getSchoolYearlabelId() +
            "}";
    }
}
