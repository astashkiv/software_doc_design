package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "timings")
    private String timings;

    @Column(name = "medicine_count")
    private Integer medicineCount;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor signedBy;

    @ManyToMany
    @JoinTable(name = "prescription_medicines",
               joinColumns = @JoinColumn(name = "prescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "medicines_id", referencedColumnName = "id"))
    private Set<Medicine> medicines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("prescriptions")
    private Patient patient;

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

    public Prescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimings() {
        return timings;
    }

    public Prescription timings(String timings) {
        this.timings = timings;
        return this;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Integer getMedicineCount() {
        return medicineCount;
    }

    public Prescription medicineCount(Integer medicineCount) {
        this.medicineCount = medicineCount;
        return this;
    }

    public void setMedicineCount(Integer medicineCount) {
        this.medicineCount = medicineCount;
    }

    public Doctor getSignedBy() {
        return signedBy;
    }

    public Prescription signedBy(Doctor doctor) {
        this.signedBy = doctor;
        return this;
    }

    public void setSignedBy(Doctor doctor) {
        this.signedBy = doctor;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public Prescription medicines(Set<Medicine> medicines) {
        this.medicines = medicines;
        return this;
    }

    public Prescription addMedicines(Medicine medicine) {
        this.medicines.add(medicine);
        medicine.getPrescriptions().add(this);
        return this;
    }

    public Prescription removeMedicines(Medicine medicine) {
        this.medicines.remove(medicine);
        medicine.getPrescriptions().remove(this);
        return this;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Patient getPatient() {
        return patient;
    }

    public Prescription patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescription)) {
            return false;
        }
        return id != null && id.equals(((Prescription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", timings='" + getTimings() + "'" +
            ", medicineCount=" + getMedicineCount() +
            "}";
    }
}
