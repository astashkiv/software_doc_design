package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

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

    @OneToMany(mappedBy = "doctor")
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<DoctorCertificate> certificates = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("doctors")
    private Admin admin;

    @ManyToOne
    @JsonIgnoreProperties("doctors")
    private Department department;

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

    public Doctor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Doctor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public Doctor feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Doctor addFeedbacks(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setDoctor(this);
        return this;
    }

    public Doctor removeFeedbacks(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setDoctor(null);
        return this;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<DoctorCertificate> getCertificates() {
        return certificates;
    }

    public Doctor certificates(Set<DoctorCertificate> doctorCertificates) {
        this.certificates = doctorCertificates;
        return this;
    }

    public Doctor addCertificates(DoctorCertificate doctorCertificate) {
        this.certificates.add(doctorCertificate);
        doctorCertificate.setDoctor(this);
        return this;
    }

    public Doctor removeCertificates(DoctorCertificate doctorCertificate) {
        this.certificates.remove(doctorCertificate);
        doctorCertificate.setDoctor(null);
        return this;
    }

    public void setCertificates(Set<DoctorCertificate> doctorCertificates) {
        this.certificates = doctorCertificates;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Doctor payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Doctor addPayments(Payment payment) {
        this.payments.add(payment);
        payment.setDoctor(this);
        return this;
    }

    public Doctor removePayments(Payment payment) {
        this.payments.remove(payment);
        payment.setDoctor(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Doctor admin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Department getDepartment() {
        return department;
    }

    public Doctor department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
