package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActorRelationship.
 */
@Entity
@Table(name = "actor_relationship")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "actorrelationship")
public class ActorRelationship implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnoreProperties("actorRelationships")
    private Responsible responsible;

    @ManyToOne
    @JsonIgnoreProperties("actorRelationships")
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties("actorRelationships")
    private RelationshipType relationshipType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public ActorRelationship isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public ActorRelationship responsible(Responsible responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public Student getStudent() {
        return student;
    }

    public ActorRelationship student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public ActorRelationship relationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
        return this;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
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
        ActorRelationship actorRelationship = (ActorRelationship) o;
        if (actorRelationship.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorRelationship.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorRelationship{" +
            "id=" + getId() +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
