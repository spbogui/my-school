package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AskingPermission entity.
 */
public class AskingPermissionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate askingDate;

    @NotNull
    private Integer numberOfDay;

    @NotNull
    private String reason;


    private Long schoolSchoolYearId;

    private Long studentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAskingDate() {
        return askingDate;
    }

    public void setAskingDate(LocalDate askingDate) {
        this.askingDate = askingDate;
    }

    public Integer getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(Integer numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getSchoolSchoolYearId() {
        return schoolSchoolYearId;
    }

    public void setSchoolSchoolYearId(Long schoolSchoolYearId) {
        this.schoolSchoolYearId = schoolSchoolYearId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AskingPermissionDTO askingPermissionDTO = (AskingPermissionDTO) o;
        if (askingPermissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), askingPermissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AskingPermissionDTO{" +
            "id=" + getId() +
            ", askingDate='" + getAskingDate() + "'" +
            ", numberOfDay=" + getNumberOfDay() +
            ", reason='" + getReason() + "'" +
            ", schoolSchoolYear=" + getSchoolSchoolYearId() +
            ", student=" + getStudentId() +
            "}";
    }
}
