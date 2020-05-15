package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A PatientCertificate.
 */
@Entity
@Table(name = "patient_certificate")
public class PatientCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @ManyToOne
    @JsonIgnoreProperties("certificates")
    private Patient doctor;

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

    public PatientCertificate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public PatientCertificate receivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Patient getDoctor() {
        return doctor;
    }

    public PatientCertificate doctor(Patient patient) {
        this.doctor = patient;
        return this;
    }

    public void setDoctor(Patient patient) {
        this.doctor = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientCertificate)) {
            return false;
        }
        return id != null && id.equals(((PatientCertificate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientCertificate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", receivedDate='" + getReceivedDate() + "'" +
            "}";
    }
}
