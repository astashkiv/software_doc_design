package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_when")
    private ZonedDateTime when;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor toWho;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Patient byWho;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public Booking when(ZonedDateTime when) {
        this.when = when;
        return this;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }

    public Doctor getToWho() {
        return toWho;
    }

    public Booking toWho(Doctor doctor) {
        this.toWho = doctor;
        return this;
    }

    public void setToWho(Doctor doctor) {
        this.toWho = doctor;
    }

    public Patient getByWho() {
        return byWho;
    }

    public Booking byWho(Patient patient) {
        this.byWho = patient;
        return this;
    }

    public void setByWho(Patient patient) {
        this.byWho = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", when='" + getWhen() + "'" +
            "}";
    }
}
