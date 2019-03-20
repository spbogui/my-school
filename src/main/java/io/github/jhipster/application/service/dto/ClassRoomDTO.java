package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClassRoom entity.
 */
public class ClassRoomDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long levelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassRoomDTO classRoomDTO = (ClassRoomDTO) o;
        if (classRoomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classRoomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassRoomDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevelId() +
            "}";
    }
}
