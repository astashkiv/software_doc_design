package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Medicine.
 */
@Entity
@Table(name = "medicine")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "instruction")
    private String instruction;

    @ManyToMany
    @JoinTable(name = "medicine_languages_in",
               joinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "languages_in_id", referencedColumnName = "id"))
    private Set<Language> languagesIns = new HashSet<>();

    @ManyToMany(mappedBy = "medicines")
    @JsonIgnore
    private Set<Prescription> prescriptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Medicine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public Medicine instruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Set<Language> getLanguagesIns() {
        return languagesIns;
    }

    public Medicine languagesIns(Set<Language> languages) {
        this.languagesIns = languages;
        return this;
    }

    public Medicine addLanguagesIn(Language language) {
        this.languagesIns.add(language);
        language.getMedicines().add(this);
        return this;
    }

    public Medicine removeLanguagesIn(Language language) {
        this.languagesIns.remove(language);
        language.getMedicines().remove(this);
        return this;
    }

    public void setLanguagesIns(Set<Language> languages) {
        this.languagesIns = languages;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Medicine prescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public Medicine addPrescriptions(Prescription prescription) {
        this.prescriptions.add(prescription);
        prescription.getMedicines().add(this);
        return this;
    }

    public Medicine removePrescriptions(Prescription prescription) {
        this.prescriptions.remove(prescription);
        prescription.getMedicines().remove(this);
        return this;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicine)) {
            return false;
        }
        return id != null && id.equals(((Medicine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Medicine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", instruction='" + getInstruction() + "'" +
            "}";
    }
}
