package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Diploma entity.
 */
public class DiplomaDTO implements Serializable {

    private Long id;

    @NotNull
    private String diplomaLabel;

    private String description;


    private Long cycleId;

    private Long parentDiplomaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiplomaLabel() {
        return diplomaLabel;
    }

    public void setDiplomaLabel(String diplomaLabel) {
        this.diplomaLabel = diplomaLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public Long getParentDiplomaId() {
        return parentDiplomaId;
    }

    public void setParentDiplomaId(Long diplomaId) {
        this.parentDiplomaId = diplomaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiplomaDTO diplomaDTO = (DiplomaDTO) o;
        if (diplomaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diplomaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiplomaDTO{" +
            "id=" + getId() +
            ", diplomaLabel='" + getDiplomaLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", cycle=" + getCycleId() +
            ", parentDiploma=" + getParentDiplomaId() +
            "}";
    }
}
