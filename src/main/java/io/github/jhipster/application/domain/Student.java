package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pareant_separated")
    private Boolean pareantSeparated;

    @NotNull
    @Column(name = "father_is_alive", nullable = false)
    private Boolean fatherIsAlive;

    @NotNull
    @Column(name = "living_with_father", nullable = false)
    private Boolean livingWithFather;

    @NotNull
    @Column(name = "mother_is_alive", nullable = false)
    private Boolean motherIsAlive;

    @NotNull
    @Column(name = "living_with_mother", nullable = false)
    private Boolean livingWithMother;

    @OneToOne
    @JoinColumn(unique = true)
    private Actor actor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPareantSeparated() {
        return pareantSeparated;
    }

    public Student pareantSeparated(Boolean pareantSeparated) {
        this.pareantSeparated = pareantSeparated;
        return this;
    }

    public void setPareantSeparated(Boolean pareantSeparated) {
        this.pareantSeparated = pareantSeparated;
    }

    public Boolean isFatherIsAlive() {
        return fatherIsAlive;
    }

    public Student fatherIsAlive(Boolean fatherIsAlive) {
        this.fatherIsAlive = fatherIsAlive;
        return this;
    }

    public void setFatherIsAlive(Boolean fatherIsAlive) {
        this.fatherIsAlive = fatherIsAlive;
    }

    public Boolean isLivingWithFather() {
        return livingWithFather;
    }

    public Student livingWithFather(Boolean livingWithFather) {
        this.livingWithFather = livingWithFather;
        return this;
    }

    public void setLivingWithFather(Boolean livingWithFather) {
        this.livingWithFather = livingWithFather;
    }

    public Boolean isMotherIsAlive() {
        return motherIsAlive;
    }

    public Student motherIsAlive(Boolean motherIsAlive) {
        this.motherIsAlive = motherIsAlive;
        return this;
    }

    public void setMotherIsAlive(Boolean motherIsAlive) {
        this.motherIsAlive = motherIsAlive;
    }

    public Boolean isLivingWithMother() {
        return livingWithMother;
    }

    public Student livingWithMother(Boolean livingWithMother) {
        this.livingWithMother = livingWithMother;
        return this;
    }

    public void setLivingWithMother(Boolean livingWithMother) {
        this.livingWithMother = livingWithMother;
    }

    public Actor getActor() {
        return actor;
    }

    public Student actor(Actor actor) {
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", pareantSeparated='" + isPareantSeparated() + "'" +
            ", fatherIsAlive='" + isFatherIsAlive() + "'" +
            ", livingWithFather='" + isLivingWithFather() + "'" +
            ", motherIsAlive='" + isMotherIsAlive() + "'" +
            ", livingWithMother='" + isLivingWithMother() + "'" +
            "}";
    }
}
