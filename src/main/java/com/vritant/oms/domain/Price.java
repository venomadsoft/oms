package com.vritant.oms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
/**
 * A Price.
 */
@Entity
@Table(name = "price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Price implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private Float value;
    
    @ManyToOne
    @JoinColumn(name = "quality_id")
    private Quality quality;

    @ManyToOne
    @JoinColumn(name = "simple_gsm_shade_id")
    private SimpleGsmShade simpleGsmShade;

    @Transient
    private DerivedGsmShade derivedGsmShade;

    @ManyToOne
    @JoinColumn(name = "mill_id")
    private Mill mill;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    @JsonProperty(access = Access.WRITE_ONLY)
    private PriceList priceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }
    
    public void setValue(Float value) {
        this.value = value;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public SimpleGsmShade getSimpleGsmShade() {
        return simpleGsmShade;
    }

    public void setSimpleGsmShade(SimpleGsmShade simpleGsmShade) {
        this.simpleGsmShade = simpleGsmShade;
    }

    @JsonProperty("derivedGsmShade")
    public DerivedGsmShade getDerivedGsmShade() {
        return derivedGsmShade;
    }

    @JsonProperty("derivedGsmShade")
    public void setDerivedGsmShade(DerivedGsmShade derivedGsmShade) {
        this.derivedGsmShade = derivedGsmShade;
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
        Price price = (Price) o;
        if(price.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, price.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Price{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }

    public Price cloneDerivedPrice() {
        Price clonedPrice = new Price();
        clonedPrice.setMill(mill);
        clonedPrice.setQuality(quality);
        clonedPrice.setPriceList(priceList);
        return clonedPrice;
    }
}
