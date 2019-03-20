package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Enrolment.
 */
@Entity
@Table(name = "enrolment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enrolment")
public class Enrolment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "enrolment_date", nullable = false)
    private LocalDate enrolmentDate;

    @Column(name = "regimen_rate")
    private Double regimenRate;

    @Column(name = "repeating")
    private Boolean repeating;

    @NotNull
    @Column(name = "modern_language_1", nullable = false)
    private String modernLanguage1;

    @Column(name = "modern_language_2")
    private String modernLanguage2;

    @NotNull
    @Column(name = "exemption", nullable = false)
    private Boolean exemption;

    @NotNull
    @Column(name = "with_insurance", nullable = false)
    private Boolean withInsurance;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private SchoolYear schoolYear;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private Actor actor;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private ClassRoom classRoom;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private Regimen regimen;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private School previousSchool;

    @ManyToOne
    @JsonIgnoreProperties("enrolments")
    private ClassRoom previousClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEnrolmentDate() {
        return enrolmentDate;
    }

    public Enrolment enrolmentDate(LocalDate enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
        return this;
    }

    public void setEnrolmentDate(LocalDate enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public Double getRegimenRate() {
        return regimenRate;
    }

    public Enrolment regimenRate(Double regimenRate) {
        this.regimenRate = regimenRate;
        return this;
    }

    public void setRegimenRate(Double regimenRate) {
        this.regimenRate = regimenRate;
    }

    public Boolean isRepeating() {
        return repeating;
    }

    public Enrolment repeating(Boolean repeating) {
        this.repeating = repeating;
        return this;
    }

    public void setRepeating(Boolean repeating) {
        this.repeating = repeating;
    }

    public String getModernLanguage1() {
        return modernLanguage1;
    }

    public Enrolment modernLanguage1(String modernLanguage1) {
        this.modernLanguage1 = modernLanguage1;
        return this;
    }

    public void setModernLanguage1(String modernLanguage1) {
        this.modernLanguage1 = modernLanguage1;
    }

    public String getModernLanguage2() {
        return modernLanguage2;
    }

    public Enrolment modernLanguage2(String modernLanguage2) {
        this.modernLanguage2 = modernLanguage2;
        return this;
    }

    public void setModernLanguage2(String modernLanguage2) {
        this.modernLanguage2 = modernLanguage2;
    }

    public Boolean isExemption() {
        return exemption;
    }

    public Enrolment exemption(Boolean exemption) {
        this.exemption = exemption;
        return this;
    }

    public void setExemption(Boolean exemption) {
        this.exemption = exemption;
    }

    public Boolean isWithInsurance() {
        return withInsurance;
    }

    public Enrolment withInsurance(Boolean withInsurance) {
        this.withInsurance = withInsurance;
        return this;
    }

    public void setWithInsurance(Boolean withInsurance) {
        this.withInsurance = withInsurance;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public Enrolment schoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
        return this;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Actor getActor() {
        return actor;
    }

    public Enrolment actor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public Enrolment classRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
        return this;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public Enrolment regimen(Regimen regimen) {
        this.regimen = regimen;
        return this;
    }

    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    public School getPreviousSchool() {
        return previousSchool;
    }

    public Enrolment previousSchool(School school) {
        this.previousSchool = school;
        return this;
    }

    public void setPreviousSchool(School school) {
        this.previousSchool = school;
    }

    public ClassRoom getPreviousClass() {
        return previousClass;
    }

    public Enrolment previousClass(ClassRoom classRoom) {
        this.previousClass = classRoom;
        return this;
    }

    public void setPreviousClass(ClassRoom classRoom) {
        this.previousClass = classRoom;
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
        Enrolment enrolment = (Enrolment) o;
        if (enrolment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrolment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enrolment{" +
            "id=" + getId() +
            ", enrolmentDate='" + getEnrolmentDate() + "'" +
            ", regimenRate=" + getRegimenRate() +
            ", repeating='" + isRepeating() + "'" +
            ", modernLanguage1='" + getModernLanguage1() + "'" +
            ", modernLanguage2='" + getModernLanguage2() + "'" +
            ", exemption='" + isExemption() + "'" +
            ", withInsurance='" + isWithInsurance() + "'" +
            "}";
    }
}
