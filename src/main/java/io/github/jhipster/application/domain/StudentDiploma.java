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
 * A StudentDiploma.
 */
@Entity
@Table(name = "student_diploma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "studentdiploma")
public class StudentDiploma implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mention", nullable = false)
    private String mention;

    @NotNull
    @Column(name = "graduation_date", nullable = false)
    private LocalDate graduationDate;

    @ManyToOne
    @JsonIgnoreProperties("studentDiplomas")
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties("studentDiplomas")
    private Diploma diploma;

    @ManyToOne
    @JsonIgnoreProperties("studentDiplomas")
    private SchoolSchoolYear schoolSchoolYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMention() {
        return mention;
    }

    public StudentDiploma mention(String mention) {
        this.mention = mention;
        return this;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public StudentDiploma graduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
        return this;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Student getStudent() {
        return student;
    }

    public StudentDiploma student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Diploma getDiploma() {
        return diploma;
    }

    public StudentDiploma diploma(Diploma diploma) {
        this.diploma = diploma;
        return this;
    }

    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }

    public SchoolSchoolYear getSchoolSchoolYear() {
        return schoolSchoolYear;
    }

    public StudentDiploma schoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
        return this;
    }

    public void setSchoolSchoolYear(SchoolSchoolYear schoolSchoolYear) {
        this.schoolSchoolYear = schoolSchoolYear;
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
        StudentDiploma studentDiploma = (StudentDiploma) o;
        if (studentDiploma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDiploma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDiploma{" +
            "id=" + getId() +
            ", mention='" + getMention() + "'" +
            ", graduationDate='" + getGraduationDate() + "'" +
            "}";
    }
}
