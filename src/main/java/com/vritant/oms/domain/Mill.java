package com.vritant.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Mill.
 */
@Entity
@Table(name = "mill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @OneToOne
    private Addresses addresses;

    @OneToOne
    private NoteSet notes;

    @OneToMany(mappedBy = "mill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> pricess = new HashSet<>();

    @OneToMany(mappedBy = "mill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Quality> qualitiess = new ArrayList<>();

    @OneToMany(mappedBy = "mill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<SimpleGsmShade> simpleGsmShadess = new ArrayList<>();

	@OneToMany(mappedBy = "mill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerGroup> groupss = new HashSet<>();

    @OneToMany(mappedBy = "mill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DerivedGsmShade> derivedGsmShadess = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public NoteSet getNotes() {
        return notes;
    }

    public void setNotes(NoteSet noteSet) {
        this.notes = noteSet;
    }

    public Set<Price> getPricess() {
        return pricess;
    }

    public void setPricess(Set<Price> prices) {
        this.pricess = prices;
    }

    public List<Quality> getQualitiess() {
        return qualitiess;
    }

    public void setQualitiess(List<Quality> qualitys) {
        this.qualitiess = qualitys;
    }
    
 	public List<SimpleGsmShade> getSimpleGsmShadess() {
		return simpleGsmShadess;
	}

	public void setSimpleGsmShadess(List<SimpleGsmShade> simpleGsmShades) {
		this.simpleGsmShadess = simpleGsmShades;
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
        Mill mill = (Mill) o;
        if(mill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mill{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
