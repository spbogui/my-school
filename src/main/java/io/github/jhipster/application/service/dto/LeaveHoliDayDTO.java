package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LeaveHoliDay entity.
 */
public class LeaveHoliDayDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private String memo;

    @NotNull
    private LocalDate createdAt;


    private Long schoolYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(Long schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaveHoliDayDTO leaveHoliDayDTO = (LeaveHoliDayDTO) o;
        if (leaveHoliDayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveHoliDayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveHoliDayDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", memo='" + getMemo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", schoolYear=" + getSchoolYearId() +
            "}";
    }
}
