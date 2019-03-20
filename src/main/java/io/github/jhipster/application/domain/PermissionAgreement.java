package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PermissionAgreement.
 */
@Entity
@Table(name = "permission_agreement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "permissionagreement")
public class PermissionAgreement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "permission_allowed", nullable = false)
    private Boolean permissionAllowed;

    @Column(name = "allowance_date")
    private LocalDate allowanceDate;

    @Column(name = "permission_start_date")
    private LocalDate permissionStartDate;

    @Column(name = "permission_end_date")
    private LocalDate permissionEndDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "effective_return_date")
    private LocalDate effectiveReturnDate;

    @Column(name = "memo")
    private String memo;

    @OneToOne
    @JoinColumn(unique = true)
    private AskingPermission askingPermission;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPermissionAllowed() {
        return permissionAllowed;
    }

    public PermissionAgreement permissionAllowed(Boolean permissionAllowed) {
        this.permissionAllowed = permissionAllowed;
        return this;
    }

    public void setPermissionAllowed(Boolean permissionAllowed) {
        this.permissionAllowed = permissionAllowed;
    }

    public LocalDate getAllowanceDate() {
        return allowanceDate;
    }

    public PermissionAgreement allowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
        return this;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public LocalDate getPermissionStartDate() {
        return permissionStartDate;
    }

    public PermissionAgreement permissionStartDate(LocalDate permissionStartDate) {
        this.permissionStartDate = permissionStartDate;
        return this;
    }

    public void setPermissionStartDate(LocalDate permissionStartDate) {
        this.permissionStartDate = permissionStartDate;
    }

    public LocalDate getPermissionEndDate() {
        return permissionEndDate;
    }

    public PermissionAgreement permissionEndDate(LocalDate permissionEndDate) {
        this.permissionEndDate = permissionEndDate;
        return this;
    }

    public void setPermissionEndDate(LocalDate permissionEndDate) {
        this.permissionEndDate = permissionEndDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public PermissionAgreement returnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getEffectiveReturnDate() {
        return effectiveReturnDate;
    }

    public PermissionAgreement effectiveReturnDate(LocalDate effectiveReturnDate) {
        this.effectiveReturnDate = effectiveReturnDate;
        return this;
    }

    public void setEffectiveReturnDate(LocalDate effectiveReturnDate) {
        this.effectiveReturnDate = effectiveReturnDate;
    }

    public String getMemo() {
        return memo;
    }

    public PermissionAgreement memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public AskingPermission getAskingPermission() {
        return askingPermission;
    }

    public PermissionAgreement askingPermission(AskingPermission askingPermission) {
        this.askingPermission = askingPermission;
        return this;
    }

    public void setAskingPermission(AskingPermission askingPermission) {
        this.askingPermission = askingPermission;
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
        PermissionAgreement permissionAgreement = (PermissionAgreement) o;
        if (permissionAgreement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), permissionAgreement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PermissionAgreement{" +
            "id=" + getId() +
            ", permissionAllowed='" + isPermissionAllowed() + "'" +
            ", allowanceDate='" + getAllowanceDate() + "'" +
            ", permissionStartDate='" + getPermissionStartDate() + "'" +
            ", permissionEndDate='" + getPermissionEndDate() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", effectiveReturnDate='" + getEffectiveReturnDate() + "'" +
            ", memo='" + getMemo() + "'" +
            "}";
    }
}
