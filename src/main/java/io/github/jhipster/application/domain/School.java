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
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "openning_date")
    private LocalDate openningDate;

    @NotNull
    @Column(name = "phone_number_1", nullable = false)
    private String phoneNumber1;

    @Column(name = "phone_number_2")
    private String phoneNumber2;

    @Column(name = "email")
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "web_site_link")
    private String webSiteLink;

    @Column(name = "address")
    private String address;

    @Column(name = "town")
    private String town;

    @Column(name = "region")
    private String region;

    @Column(name = "municipality")
    private String municipality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public School schoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSlogan() {
        return slogan;
    }

    public School slogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public LocalDate getOpenningDate() {
        return openningDate;
    }

    public School openningDate(LocalDate openningDate) {
        this.openningDate = openningDate;
        return this;
    }

    public void setOpenningDate(LocalDate openningDate) {
        this.openningDate = openningDate;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public School phoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
        return this;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public School phoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
        return this;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getEmail() {
        return email;
    }

    public School email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public School fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebSiteLink() {
        return webSiteLink;
    }

    public School webSiteLink(String webSiteLink) {
        this.webSiteLink = webSiteLink;
        return this;
    }

    public void setWebSiteLink(String webSiteLink) {
        this.webSiteLink = webSiteLink;
    }

    public String getAddress() {
        return address;
    }

    public School address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public School town(String town) {
        this.town = town;
        return this;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRegion() {
        return region;
    }

    public School region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMunicipality() {
        return municipality;
    }

    public School municipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
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
        School school = (School) o;
        if (school.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), school.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", schoolName='" + getSchoolName() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", openningDate='" + getOpenningDate() + "'" +
            ", phoneNumber1='" + getPhoneNumber1() + "'" +
            ", phoneNumber2='" + getPhoneNumber2() + "'" +
            ", email='" + getEmail() + "'" +
            ", fax='" + getFax() + "'" +
            ", webSiteLink='" + getWebSiteLink() + "'" +
            ", address='" + getAddress() + "'" +
            ", town='" + getTown() + "'" +
            ", region='" + getRegion() + "'" +
            ", municipality='" + getMunicipality() + "'" +
            "}";
    }
}
