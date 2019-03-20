package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Responsible entity.
 */
public class ResponsibleDTO implements Serializable {

    private Long id;

    private String profession;


    private Long actorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResponsibleDTO responsibleDTO = (ResponsibleDTO) o;
        if (responsibleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responsibleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResponsibleDTO{" +
            "id=" + getId() +
            ", profession='" + getProfession() + "'" +
            ", actor=" + getActorId() +
            "}";
    }
}
