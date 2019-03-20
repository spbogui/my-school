package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TeacherSubjectSchoolYear entity.
 */
public class TeacherSubjectSchoolYearDTO implements Serializable {

    private Long id;

    private Boolean isPrincipal;


    private Long actorId;

    private Long teacherId;

    private Long schoolSchoolYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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

        TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO = (TeacherSubjectSchoolYearDTO) o;
        if (teacherSubjectSchoolYearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacherSubjectSchoolYearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeacherSubjectSchoolYearDTO{" +
            "id=" + getId() +
            ", isPrincipal='" + isIsPrincipal() + "'" +
            ", actor=" + getActorId() +
            ", teacher=" + getTeacherId() +
            ", schoolSchoolYear=" + getSchoolSchoolYearId() +
            "}";
    }
}
