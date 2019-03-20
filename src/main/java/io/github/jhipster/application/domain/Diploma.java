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
 * A Diploma.
 */
@Entity
@Table(name = "diploma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "diploma")
public class Diploma implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "diploma_label", nullable = false)
    private String diplomaLabel;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("diplomas")
    private Cycle cycle;

    @ManyToOne
    @JsonIgnoreProperties("diplomas")
    private Diploma parentDiploma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiplomaLabel() {
        return diplomaLabel;
    }

    public Diploma diplomaLabel(String diplomaLabel) {
        this.diplomaLabel = diplomaLabel;
        return this;
    }

    public void setDiplomaLabel(String diplomaLabel) {
        this.diplomaLabel = diplomaLabel;
    }

    public String getDescription() {
        return description;
    }

    public Diploma description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public Diploma cycle(Cycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Diploma getParentDiploma() {
        return parentDiploma;
    }

    public Diploma parentDiploma(Diploma diploma) {
        this.parentDiploma = diploma;
        return this;
    }

    public void setParentDiploma(Diploma diploma) {
        this.parentDiploma = diploma;
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
        Diploma diploma = (Diploma) o;
        if (diploma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diploma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diploma{" +
            "id=" + getId() +
            ", diplomaLabel='" + getDiplomaLabel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
