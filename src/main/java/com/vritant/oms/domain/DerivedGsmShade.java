package com.vritant.oms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DerivedGsmShade.
 */
@Entity
@Table(name = "derived_gsm_shade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DerivedGsmShade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "min_gsm", nullable = false)
    private Integer minGsm;
    
    @NotNull
    @Column(name = "max_gsm", nullable = false)
    private Integer maxGsm;
    
    @NotNull
    @Column(name = "shade", nullable = false)
    private String shade;
    
    @OneToOne
    private Formulae formulae;

    @ManyToOne
    @JoinColumn(name = "simple_gsm_shade_id")
    private SimpleGsmShade simpleGsmShade;

    @ManyToOne
    @JoinColumn(name = "mill_id")
    private Mill mill;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    @JsonIgnore
    private PriceList priceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinGsm() {
        return minGsm;
    }
    
    public void setMinGsm(Integer minGsm) {
        this.minGsm = minGsm;
    }

    public Integer getMaxGsm() {
        return maxGsm;
    }
    
    public void setMaxGsm(Integer maxGsm) {
        this.maxGsm = maxGsm;
    }

    public String getShade() {
        return shade;
    }
    
    public void setShade(String shade) {
        this.shade = shade;
    }

    public Formulae getFormulae() {
        return formulae;
    }

    public void setFormulae(Formulae formulae) {
        this.formulae = formulae;
    }

    public SimpleGsmShade getSimpleGsmShade() {
        return simpleGsmShade;
    }

    public void setSimpleGsmShade(SimpleGsmShade simpleGsmShade) {
        this.simpleGsmShade = simpleGsmShade;
    }

    public Mill getMill() {
        return mill;
    }

    public void setMill(Mill mill) {
        this.mill = mill;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DerivedGsmShade derivedGsmShade = (DerivedGsmShade) o;
        if(derivedGsmShade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, derivedGsmShade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DerivedGsmShade{" +
            "id=" + id +
            ", minGsm='" + minGsm + "'" +
            ", maxGsm='" + maxGsm + "'" +
            ", shade='" + shade + "'" +
            '}';
    }
}
