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
 * A StudentEvaluation.
 */
@Entity
@Table(name = "student_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "studentevaluation")
public class StudentEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Double grade;

    @ManyToOne
    @JsonIgnoreProperties("studentEvaluations")
    private Actor actor;

    @ManyToOne
    @JsonIgnoreProperties("studentEvaluations")
    private Evaluation evaluation;

    @ManyToOne
    @JsonIgnoreProperties("studentEvaluations")
    private EvaluationMode evaluationMode;

    @ManyToOne
    @JsonIgnoreProperties("studentEvaluations")
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public StudentEvaluation grade(Double grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Actor getActor() {
        return actor;
    }

    public StudentEvaluation actor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public StudentEvaluation evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public EvaluationMode getEvaluationMode() {
        return evaluationMode;
    }

    public StudentEvaluation evaluationMode(EvaluationMode evaluationMode) {
        this.evaluationMode = evaluationMode;
        return this;
    }

    public void setEvaluationMode(EvaluationMode evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public Subject getSubject() {
        return subject;
    }

    public StudentEvaluation subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        StudentEvaluation studentEvaluation = (StudentEvaluation) o;
        if (studentEvaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentEvaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentEvaluation{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            "}";
    }
}
