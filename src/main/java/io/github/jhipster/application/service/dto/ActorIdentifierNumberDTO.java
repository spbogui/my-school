package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActorIdentifierNumber entity.
 */
public class ActorIdentifierNumberDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;


    private Long identifierTypeId;

    private Long actorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getIdentifierTypeId() {
        return identifierTypeId;
    }

    public void setIdentifierTypeId(Long identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
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

        ActorIdentifierNumberDTO actorIdentifierNumberDTO = (ActorIdentifierNumberDTO) o;
        if (actorIdentifierNumberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorIdentifierNumberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorIdentifierNumberDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", identifierType=" + getIdentifierTypeId() +
            ", actor=" + getActorId() +
            "}";
    }
}
