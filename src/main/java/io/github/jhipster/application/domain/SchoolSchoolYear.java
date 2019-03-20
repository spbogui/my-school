package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SchoolSchoolYear.
 */
@Entity
@Table(name = "school_school_year")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "schoolschoolyear")
public class SchoolSchoolYear implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("schoolSchoolYears")
    private School schoolName;

    @ManyToOne
    @JsonIgnoreProperties("schoolSchoolYears")
    private SchoolYear schoolYearlabel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public SchoolSchoolYear description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public School getSchoolName() {
        return schoolName;
    }

    public SchoolSchoolYear schoolName(School school) {
        this.schoolName = school;
        return this;
    }

    public void setSchoolName(School school) {
        this.schoolName = school;
    }

    public SchoolYear getSchoolYearlabel() {
        return schoolYearlabel;
    }

    public SchoolSchoolYear schoolYearlabel(SchoolYear schoolYear) {
        this.schoolYearlabel = schoolYear;
        return this;
    }

    public void setSchoolYearlabel(SchoolYear schoolYear) {
        this.schoolYearlabel = schoolYear;
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
        SchoolSchoolYear schoolSchoolYear = (SchoolSchoolYear) o;
        if (schoolSchoolYear.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolSchoolYear.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolSchoolYear{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
