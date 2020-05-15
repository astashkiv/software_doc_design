package com.stashkiv.pharmacy.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @Column(name = "phone", unique = true)
    private String phone;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @OneToMany(mappedBy = "patient")
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Prescription> prescriptions = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<PatientCertificate> certificates = new HashSet<>();

    @OneToMany(mappedBy = "askedBy")
    private Set<Query> queries = new HashSet<>();

    @OneToMany(mappedBy = "byWho")
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<MedicalCondition> conditions = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Payment> payments = new HashSet<>();

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

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Patient phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Patient address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public Patient feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Patient addFeedbacks(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setPatient(this);
        return this;
    }

    public Patient removeFeedbacks(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setPatient(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Patient prescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public Patient addPrescriptions(Prescription prescription) {
        this.prescriptions.add(prescription);
        prescription.setPatient(this);
        return this;
    }

    public Patient removePrescriptions(Prescription prescription) {
        this.prescriptions.remove(prescription);
        prescription.setPatient(null);
        return this;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<PatientCertificate> getCertificates() {
        return certificates;
    }

    public Patient certificates(Set<PatientCertificate> patientCertificates) {
        this.certificates = patientCertificates;
        return this;
    }

    public Patient addCertificates(PatientCertificate patientCertificate) {
        this.certificates.add(patientCertificate);
        patientCertificate.setDoctor(this);
        return this;
    }

    public Patient removeCertificates(PatientCertificate patientCertificate) {
        this.certificates.remove(patientCertificate);
        patientCertificate.setDoctor(null);
        return this;
    }

    public void setCertificates(Set<PatientCertificate> patientCertificates) {
        this.certificates = patientCertificates;
    }

    public Set<Query> getQueries() {
        return queries;
    }

    public Patient queries(Set<Query> queries) {
        this.queries = queries;
        return this;
    }

    public Patient addQueries(Query query) {
        this.queries.add(query);
        query.setAskedBy(this);
        return this;
    }

    public Patient removeQueries(Query query) {
        this.queries.remove(query);
        query.setAskedBy(null);
        return this;
    }

    public void setQueries(Set<Query> queries) {
        this.queries = queries;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Patient bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Patient addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setByWho(this);
        return this;
    }

    public Patient removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setByWho(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<MedicalCondition> getConditions() {
        return conditions;
    }

    public Patient conditions(Set<MedicalCondition> medicalConditions) {
        this.conditions = medicalConditions;
        return this;
    }

    public Patient addConditions(MedicalCondition medicalCondition) {
        this.conditions.add(medicalCondition);
        medicalCondition.setPatient(this);
        return this;
    }

    public Patient removeConditions(MedicalCondition medicalCondition) {
        this.conditions.remove(medicalCondition);
        medicalCondition.setPatient(null);
        return this;
    }

    public void setConditions(Set<MedicalCondition> medicalConditions) {
        this.conditions = medicalConditions;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Patient payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Patient addPayments(Payment payment) {
        this.payments.add(payment);
        payment.setPatient(this);
        return this;
    }

    public Patient removePayments(Payment payment) {
        this.payments.remove(payment);
        payment.setPatient(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
