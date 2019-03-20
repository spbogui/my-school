package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentDiploma entity.
 */
public class StudentDiplomaDTO implements Serializable {

    private Long id;

    @NotNull
    private String mention;

    @NotNull
    private LocalDate graduationDate;


    private Long studentId;

    private Long diplomaId;

    private Long schoolSchoolYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getDiplomaId() {
        return diplomaId;
    }

    public void setDiplomaId(Long diplomaId) {
        this.diplomaId = diplomaId;
    }

    public Long getSchoolSchoolYearId() {
        return schoolSchoolYearId;
    }

    public void setSchoolSchoolYearId(Long schoolSchoolYearId) {
        this.schoolSchoolYearId = schoolSchoolYearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentDiplomaDTO studentDiplomaDTO = (StudentDiplomaDTO) o;
        if (studentDiplomaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDiplomaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDiplomaDTO{" +
            "id=" + getId() +
            ", mention='" + getMention() + "'" +
            ", graduationDate='" + getGraduationDate() + "'" +
            ", student=" + getStudentId() +
            ", diploma=" + getDiplomaId() +
            ", schoolSchoolYear=" + getSchoolSchoolYearId() +
            "}";
    }
}
