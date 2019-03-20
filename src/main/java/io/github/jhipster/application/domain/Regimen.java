package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Regimen.
 */
@Entity
@Table(name = "regimen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "regimen")
public class Regimen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "regimen_label", nullable = false)
    private String regimenLabel;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegimenLabel() {
        return regimenLabel;
    }

    public Regimen regimenLabel(String regimenLabel) {
        this.regimenLabel = regimenLabel;
        return this;
    }

    public void setRegimenLabel(String regimenLabel) {
        this.regimenLabel = regimenLabel;
    }

    public String getDescription() {
        return description;
    }

    public Regimen description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Regimen regimen = (Regimen) o;
        if (regimen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regimen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regimen{" +
            "id=" + getId() +
            ", regimenLabel='" + getRegimenLabel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
