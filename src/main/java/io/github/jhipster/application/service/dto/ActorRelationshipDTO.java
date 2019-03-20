package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActorRelationship entity.
 */
public class ActorRelationshipDTO implements Serializable {

    private Long id;

    private Boolean isActive;


    private Long responsibleId;

    private Long studentId;

    private Long relationshipTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(Long relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActorRelationshipDTO actorRelationshipDTO = (ActorRelationshipDTO) o;
        if (actorRelationshipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorRelationshipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorRelationshipDTO{" +
            "id=" + getId() +
            ", isActive='" + isIsActive() + "'" +
            ", responsible=" + getResponsibleId() +
            ", student=" + getStudentId() +
            ", relationshipType=" + getRelationshipTypeId() +
            "}";
    }
}
