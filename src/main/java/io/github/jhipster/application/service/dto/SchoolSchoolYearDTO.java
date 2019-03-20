package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SchoolSchoolYear entity.
 */
public class SchoolSchoolYearDTO implements Serializable {

    private Long id;

    private String description;


    private Long schoolNameId;

    private Long schoolYearlabelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSchoolNameId() {
        return schoolNameId;
    }

    public void setSchoolNameId(Long schoolId) {
        this.schoolNameId = schoolId;
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

        SchoolSchoolYearDTO schoolSchoolYearDTO = (SchoolSchoolYearDTO) o;
        if (schoolSchoolYearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolSchoolYearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolSchoolYearDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", schoolName=" + getSchoolNameId() +
            ", schoolYearlabel=" + getSchoolYearlabelId() +
            "}";
    }
}
