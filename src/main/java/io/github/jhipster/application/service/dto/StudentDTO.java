package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Student entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    private Boolean pareantSeparated;

    @NotNull
    private Boolean fatherIsAlive;

    @NotNull
    private Boolean livingWithFather;

    @NotNull
    private Boolean motherIsAlive;

    @NotNull
    private Boolean livingWithMother;


    private Long actorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPareantSeparated() {
        return pareantSeparated;
    }

    public void setPareantSeparated(Boolean pareantSeparated) {
        this.pareantSeparated = pareantSeparated;
    }

    public Boolean isFatherIsAlive() {
        return fatherIsAlive;
    }

    public void setFatherIsAlive(Boolean fatherIsAlive) {
        this.fatherIsAlive = fatherIsAlive;
    }

    public Boolean isLivingWithFather() {
        return livingWithFather;
    }

    public void setLivingWithFather(Boolean livingWithFather) {
        this.livingWithFather = livingWithFather;
    }

    public Boolean isMotherIsAlive() {
        return motherIsAlive;
    }

    public void setMotherIsAlive(Boolean motherIsAlive) {
        this.motherIsAlive = motherIsAlive;
    }

    public Boolean isLivingWithMother() {
        return livingWithMother;
    }

    public void setLivingWithMother(Boolean livingWithMother) {
        this.livingWithMother = livingWithMother;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (studentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", pareantSeparated='" + isPareantSeparated() + "'" +
            ", fatherIsAlive='" + isFatherIsAlive() + "'" +
            ", livingWithFather='" + isLivingWithFather() + "'" +
            ", motherIsAlive='" + isMotherIsAlive() + "'" +
            ", livingWithMother='" + isLivingWithMother() + "'" +
            ", actor=" + getActorId() +
            "}";
    }
}
