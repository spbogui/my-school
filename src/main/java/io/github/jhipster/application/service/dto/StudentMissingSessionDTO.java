package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentMissingSession entity.
 */
public class StudentMissingSessionDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isJustified;


    private Long classSessionId;

    private Long studentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsJustified() {
        return isJustified;
    }

    public void setIsJustified(Boolean isJustified) {
        this.isJustified = isJustified;
    }

    public Long getClassSessionId() {
        return classSessionId;
    }

    public void setClassSessionId(Long classSessionId) {
        this.classSessionId = classSessionId;
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

        StudentMissingSessionDTO studentMissingSessionDTO = (StudentMissingSessionDTO) o;
        if (studentMissingSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentMissingSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentMissingSessionDTO{" +
            "id=" + getId() +
            ", isJustified='" + isIsJustified() + "'" +
            ", classSession=" + getClassSessionId() +
            ", student=" + getStudentId() +
            "}";
    }
}
