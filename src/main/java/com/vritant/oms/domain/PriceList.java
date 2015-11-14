package com.vritant.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PriceList.
 */
@Entity
@Table(name = "price_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PriceList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "wef_date_from", nullable = false)
    private LocalDate wefDateFrom;

    @NotNull
    @Column(name = "wef_date_to", nullable = false)
    private LocalDate wefDateTo;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "priceList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> pricess = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tax> taxess = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerGroup> groupss = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DerivedGsmShade> derivedGsmShadess = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWefDateFrom() {
        return wefDateFrom;
    }

    public void setWefDateFrom(LocalDate wefDateFrom) {
        this.wefDateFrom = wefDateFrom;
    }

    public LocalDate getWefDateTo() {
        return wefDateTo;
    }

    public void setWefDateTo(LocalDate wefDateTo) {
        this.wefDateTo = wefDateTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Price> getPricess() {
        return pricess;
    }

    public void setPricess(Set<Price> prices) {
        this.pricess = prices;
    }

    public Set<Tax> getTaxess() {
        return taxess;
    }

    public void setTaxess(Set<Tax> taxs) {
        this.taxess = taxs;
    }

    public Set<CustomerGroup> getGroupss() {
        return groupss;
    }

    public void setGroupss(Set<CustomerGroup> customerGroups) {
        this.groupss = customerGroups;
    }

    public Set<DerivedGsmShade> getDerivedGsmShadess() {
        return derivedGsmShadess;
    }

    public void setDerivedGsmShadess(Set<DerivedGsmShade> derivedGsmShades) {
        this.derivedGsmShadess = derivedGsmShades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceList priceList = (PriceList) o;

        if ( ! Objects.equals(id, priceList.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PriceList{" +
            "id=" + id +
            ", wefDateFrom='" + wefDateFrom + "'" +
            ", wefDateTo='" + wefDateTo + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
