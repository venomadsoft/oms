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
 * A SimpleGsmShade.
 */
@Entity
@Table(name = "simple_gsm_shade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SimpleGsmShade implements Serializable {

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

    @ManyToOne
    private Mill mill;

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

    public Mill getMill() {
        return mill;
    }

    public void setMill(Mill mill) {
        this.mill = mill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleGsmShade simpleGsmShade = (SimpleGsmShade) o;

        if ( ! Objects.equals(id, simpleGsmShade.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SimpleGsmShade{" +
            "id=" + id +
            ", minGsm='" + minGsm + "'" +
            ", maxGsm='" + maxGsm + "'" +
            ", shade='" + shade + "'" +
            '}';
    }
}
