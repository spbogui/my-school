package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PermissionAgreement entity.
 */
public class PermissionAgreementDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean permissionAllowed;

    private LocalDate allowanceDate;

    private LocalDate permissionStartDate;

    private LocalDate permissionEndDate;

    private LocalDate returnDate;

    private LocalDate effectiveReturnDate;

    private String memo;


    private Long askingPermissionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPermissionAllowed() {
        return permissionAllowed;
    }

    public void setPermissionAllowed(Boolean permissionAllowed) {
        this.permissionAllowed = permissionAllowed;
    }

    public LocalDate getAllowanceDate() {
        return allowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public LocalDate getPermissionStartDate() {
        return permissionStartDate;
    }

    public void setPermissionStartDate(LocalDate permissionStartDate) {
        this.permissionStartDate = permissionStartDate;
    }

    public LocalDate getPermissionEndDate() {
        return permissionEndDate;
    }

    public void setPermissionEndDate(LocalDate permissionEndDate) {
        this.permissionEndDate = permissionEndDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getEffectiveReturnDate() {
        return effectiveReturnDate;
    }

    public void setEffectiveReturnDate(LocalDate effectiveReturnDate) {
        this.effectiveReturnDate = effectiveReturnDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getAskingPermissionId() {
        return askingPermissionId;
    }

    public void setAskingPermissionId(Long askingPermissionId) {
        this.askingPermissionId = askingPermissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PermissionAgreementDTO permissionAgreementDTO = (PermissionAgreementDTO) o;
        if (permissionAgreementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), permissionAgreementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PermissionAgreementDTO{" +
            "id=" + getId() +
            ", permissionAllowed='" + isPermissionAllowed() + "'" +
            ", allowanceDate='" + getAllowanceDate() + "'" +
            ", permissionStartDate='" + getPermissionStartDate() + "'" +
            ", permissionEndDate='" + getPermissionEndDate() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", effectiveReturnDate='" + getEffectiveReturnDate() + "'" +
            ", memo='" + getMemo() + "'" +
            ", askingPermission=" + getAskingPermissionId() +
            "}";
    }
}
