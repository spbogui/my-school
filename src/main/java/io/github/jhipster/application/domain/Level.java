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
 * A Level.
 */
@Entity
@Table(name = "level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "level")
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @Column(name = "short_form")
    private String shortForm;

    @ManyToOne
    @JsonIgnoreProperties("levels")
    private Level parentLevel;

    @ManyToOne
    @JsonIgnoreProperties("levels")
    private Cycle cycle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Level label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortForm() {
        return shortForm;
    }

    public Level shortForm(String shortForm) {
        this.shortForm = shortForm;
        return this;
    }

    public void setShortForm(String shortForm) {
        this.shortForm = shortForm;
    }

    public Level getParentLevel() {
        return parentLevel;
    }

    public Level parentLevel(Level level) {
        this.parentLevel = level;
        return this;
    }

    public void setParentLevel(Level level) {
        this.parentLevel = level;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public Level cycle(Cycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
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
        Level level = (Level) o;
        if (level.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), level.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Level{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", shortForm='" + getShortForm() + "'" +
            "}";
    }
}
