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
 * A Formula.
 */
@Entity
@Table(name = "formula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "operator", length = 1, nullable = false)
    private String operator;
    
    @NotNull
    @Column(name = "operand", nullable = false)
    private Float operand;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Formulae parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Float getOperand() {
        return operand;
    }
    
    public void setOperand(Float operand) {
        this.operand = operand;
    }

    public Formulae getParent() {
        return parent;
    }

    public void setParent(Formulae formulae) {
        this.parent = formulae;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formula formula = (Formula) o;
        if(formula.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, formula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Formula{" +
            "id=" + id +
            ", operator='" + operator + "'" +
            ", operand='" + operand + "'" +
            '}';
    }
}
