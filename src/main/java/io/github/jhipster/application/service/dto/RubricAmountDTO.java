package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RubricAmount entity.
 */
public class RubricAmountDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private String paymentMethod;


    private Long rubricId;

    private Long levelId;

    private Long schoolYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getRubricId() {
        return rubricId;
    }

    public void setRubricId(Long rubricId) {
        this.rubricId = rubricId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(Long schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RubricAmountDTO rubricAmountDTO = (RubricAmountDTO) o;
        if (rubricAmountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubricAmountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RubricAmountDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", rubric=" + getRubricId() +
            ", level=" + getLevelId() +
            ", schoolYear=" + getSchoolYearId() +
            "}";
    }
}
