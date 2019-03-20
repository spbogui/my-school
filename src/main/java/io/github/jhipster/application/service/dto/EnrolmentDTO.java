package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Enrolment entity.
 */
public class EnrolmentDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate enrolmentDate;

    private Double regimenRate;

    private Boolean repeating;

    @NotNull
    private String modernLanguage1;

    private String modernLanguage2;

    @NotNull
    private Boolean exemption;

    @NotNull
    private Boolean withInsurance;


    private Long schoolYearId;

    private Long actorId;

    private Long classRoomId;

    private Long regimenId;

    private Long previousSchoolId;

    private Long previousClassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(LocalDate enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public Double getRegimenRate() {
        return regimenRate;
    }

    public void setRegimenRate(Double regimenRate) {
        this.regimenRate = regimenRate;
    }

    public Boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(Boolean repeating) {
        this.repeating = repeating;
    }

    public String getModernLanguage1() {
        return modernLanguage1;
    }

    public void setModernLanguage1(String modernLanguage1) {
        this.modernLanguage1 = modernLanguage1;
    }

    public String getModernLanguage2() {
        return modernLanguage2;
    }

    public void setModernLanguage2(String modernLanguage2) {
        this.modernLanguage2 = modernLanguage2;
    }

    public Boolean isExemption() {
        return exemption;
    }

    public void setExemption(Boolean exemption) {
        this.exemption = exemption;
    }

    public Boolean isWithInsurance() {
        return withInsurance;
    }

    public void setWithInsurance(Boolean withInsurance) {
        this.withInsurance = withInsurance;
    }

    public Long getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(Long schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }

    public Long getRegimenId() {
        return regimenId;
    }

    public void setRegimenId(Long regimenId) {
        this.regimenId = regimenId;
    }

    public Long getPreviousSchoolId() {
        return previousSchoolId;
    }

    public void setPreviousSchoolId(Long schoolId) {
        this.previousSchoolId = schoolId;
    }

    public Long getPreviousClassId() {
        return previousClassId;
    }

    public void setPreviousClassId(Long classRoomId) {
        this.previousClassId = classRoomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrolmentDTO enrolmentDTO = (EnrolmentDTO) o;
        if (enrolmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrolmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrolmentDTO{" +
            "id=" + getId() +
            ", enrolmentDate='" + getEnrolmentDate() + "'" +
            ", regimenRate=" + getRegimenRate() +
            ", repeating='" + isRepeating() + "'" +
            ", modernLanguage1='" + getModernLanguage1() + "'" +
            ", modernLanguage2='" + getModernLanguage2() + "'" +
            ", exemption='" + isExemption() + "'" +
            ", withInsurance='" + isWithInsurance() + "'" +
            ", schoolYear=" + getSchoolYearId() +
            ", actor=" + getActorId() +
            ", classRoom=" + getClassRoomId() +
            ", regimen=" + getRegimenId() +
            ", previousSchool=" + getPreviousSchoolId() +
            ", previousClass=" + getPreviousClassId() +
            "}";
    }
}
