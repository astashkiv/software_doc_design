package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "translation")
    private String translation;

    @ManyToMany(mappedBy = "languagesIns")
    @JsonIgnore
    private Set<Medicine> medicines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public Language translation(String translation) {
        this.translation = translation;
        return this;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public Language medicines(Set<Medicine> medicines) {
        this.medicines = medicines;
        return this;
    }

    public Language addMedicines(Medicine medicine) {
        this.medicines.add(medicine);
        medicine.getLanguagesIns().add(this);
        return this;
    }

    public Language removeMedicines(Medicine medicine) {
        this.medicines.remove(medicine);
        medicine.getLanguagesIns().remove(this);
        return this;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return id != null && id.equals(((Language) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", translation='" + getTranslation() + "'" +
            "}";
    }
}
