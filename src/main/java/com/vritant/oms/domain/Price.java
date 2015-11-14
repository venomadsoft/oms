package com.vritant.oms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @OneToOne    private Quality quality;

    @OneToOne    private SimpleGsmShade simpleGsmShade;

    @ManyToOne
    private Mill mill;

    @ManyToOne
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

        if ( ! Objects.equals(id, price.id)) return false;

        return true;
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
}
