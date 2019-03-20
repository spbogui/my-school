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
 * A RubricAmount.
 */
@Entity
@Table(name = "rubric_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rubricamount")
public class RubricAmount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JsonIgnoreProperties("rubricAmounts")
    private Rubric rubric;

    @ManyToOne
    @JsonIgnoreProperties("rubricAmounts")
    private Level level;

    @ManyToOne
    @JsonIgnoreProperties("rubricAmounts")
    private SchoolYear schoolYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public RubricAmount amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public RubricAmount paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Rubric getRubric() {
        return rubric;
    }

    public RubricAmount rubric(Rubric rubric) {
        this.rubric = rubric;
        return this;
    }

    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }

    public Level getLevel() {
        return level;
    }

    public RubricAmount level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public RubricAmount schoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
        return this;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
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
        RubricAmount rubricAmount = (RubricAmount) o;
        if (rubricAmount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubricAmount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RubricAmount{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
