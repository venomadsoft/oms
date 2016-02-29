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
 * A CustomerGroup.
 */
@Entity
@Table(name = "customer_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "freight")
    private Float freight;
    
    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @ManyToOne
    @JoinColumn(name = "mill_id")
    private Mill mill;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "customer_group_customers",
               joinColumns = @JoinColumn(name="customer_groups_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="customerss_id", referencedColumnName="ID"))
    private Set<Customer> customerss = new HashSet<>();

    @OneToOne
    private NoteSet notes;

    @OneToOne
    private Formulae formulae;

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

    public Float getFreight() {
        return freight;
    }
    
    public void setFreight(Float freight) {
        this.freight = freight;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public Mill getMill() {
        return mill;
    }

    public void setMill(Mill mill) {
        this.mill = mill;
    }

    public Set<Customer> getCustomerss() {
        return customerss;
    }

    public void setCustomerss(Set<Customer> customers) {
        this.customerss = customers;
    }

    public NoteSet getNotes() {
        return notes;
    }

    public void setNotes(NoteSet noteSet) {
        this.notes = noteSet;
    }

    public Formulae getFormulae() {
        return formulae;
    }

    public void setFormulae(Formulae formulae) {
        this.formulae = formulae;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerGroup customerGroup = (CustomerGroup) o;
        if(customerGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerGroup{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", freight='" + freight + "'" +
            '}';
    }
}
