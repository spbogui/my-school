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
 * A ActorName.
 */
@Entity
@Table(name = "actor_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "actorname")
public class ActorName implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "civility", nullable = false)
    private String civility;

    @NotNull
    @Column(name = "family_name", nullable = false)
    private String familyName;

    @NotNull
    @Column(name = "given_name_1", nullable = false)
    private String givenName1;

    @Column(name = "given_name_2")
    private String givenName2;

    @Column(name = "given_name_3")
    private String givenName3;

    @Column(name = "maiden_name")
    private String maidenName;

    @ManyToOne
    @JsonIgnoreProperties("actorNames")
    private Actor actor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCivility() {
        return civility;
    }

    public ActorName civility(String civility) {
        this.civility = civility;
        return this;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getFamilyName() {
        return familyName;
    }

    public ActorName familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName1() {
        return givenName1;
    }

    public ActorName givenName1(String givenName1) {
        this.givenName1 = givenName1;
        return this;
    }

    public void setGivenName1(String givenName1) {
        this.givenName1 = givenName1;
    }

    public String getGivenName2() {
        return givenName2;
    }

    public ActorName givenName2(String givenName2) {
        this.givenName2 = givenName2;
        return this;
    }

    public void setGivenName2(String givenName2) {
        this.givenName2 = givenName2;
    }

    public String getGivenName3() {
        return givenName3;
    }

    public ActorName givenName3(String givenName3) {
        this.givenName3 = givenName3;
        return this;
    }

    public void setGivenName3(String givenName3) {
        this.givenName3 = givenName3;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public ActorName maidenName(String maidenName) {
        this.maidenName = maidenName;
        return this;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public Actor getActor() {
        return actor;
    }

    public ActorName actor(Actor actor) {
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
        ActorName actorName = (ActorName) o;
        if (actorName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorName{" +
            "id=" + getId() +
            ", civility='" + getCivility() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", givenName1='" + getGivenName1() + "'" +
            ", givenName2='" + getGivenName2() + "'" +
            ", givenName3='" + getGivenName3() + "'" +
            ", maidenName='" + getMaidenName() + "'" +
            "}";
    }
}
