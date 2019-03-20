package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActorIdentifierNumber.
 */
@Entity
@Table(name = "actor_identifier_number")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "actoridentifiernumber")
public class ActorIdentifierNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @ManyToOne
    @JsonIgnoreProperties("actorIdentifierNumbers")
    private IdentifierType identifierType;

    @ManyToOne
    @JsonIgnoreProperties("actorIdentifierNumbers")
    private Actor actor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ActorIdentifierNumber identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public ActorIdentifierNumber identifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
        return this;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public Actor getActor() {
        return actor;
    }

    public ActorIdentifierNumber actor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActorIdentifierNumber actorIdentifierNumber = (ActorIdentifierNumber) o;
        if (actorIdentifierNumber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorIdentifierNumber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorIdentifierNumber{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
