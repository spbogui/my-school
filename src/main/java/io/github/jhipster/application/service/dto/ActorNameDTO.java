package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActorName entity.
 */
public class ActorNameDTO implements Serializable {

    private Long id;

    @NotNull
    private String civility;

    @NotNull
    private String familyName;

    @NotNull
    private String givenName1;

    private String givenName2;

    private String givenName3;

    private String maidenName;


    private Long actorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName1() {
        return givenName1;
    }

    public void setGivenName1(String givenName1) {
        this.givenName1 = givenName1;
    }

    public String getGivenName2() {
        return givenName2;
    }

    public void setGivenName2(String givenName2) {
        this.givenName2 = givenName2;
    }

    public String getGivenName3() {
        return givenName3;
    }

    public void setGivenName3(String givenName3) {
        this.givenName3 = givenName3;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
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

        ActorNameDTO actorNameDTO = (ActorNameDTO) o;
        if (actorNameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actorNameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActorNameDTO{" +
            "id=" + getId() +
            ", civility='" + getCivility() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", givenName1='" + getGivenName1() + "'" +
            ", givenName2='" + getGivenName2() + "'" +
            ", givenName3='" + getGivenName3() + "'" +
            ", maidenName='" + getMaidenName() + "'" +
            ", actor=" + getActorId() +
            "}";
    }
}
