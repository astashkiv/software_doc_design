package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MedicalCondition.
 */
@Entity
@Table(name = "medical_condition")
public class MedicalCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "comments")
    private String comments;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "blood_sugar_level")
    private Double bloodSugarLevel;

    @Column(name = "pressure")
    private String pressure;

    @Column(name = "pulse")
    private Integer pulse;

    @ManyToOne
    @JsonIgnoreProperties("conditions")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public MedicalCondition age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public MedicalCondition height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public MedicalCondition weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getComments() {
        return comments;
    }

    public MedicalCondition comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getTemperature() {
        return temperature;
    }

    public MedicalCondition temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public MedicalCondition bloodSugarLevel(Double bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
        return this;
    }

    public void setBloodSugarLevel(Double bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getPressure() {
        return pressure;
    }

    public MedicalCondition pressure(String pressure) {
        this.pressure = pressure;
        return this;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Integer getPulse() {
        return pulse;
    }

    public MedicalCondition pulse(Integer pulse) {
        this.pulse = pulse;
        return this;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Patient getPatient() {
        return patient;
    }

    public MedicalCondition patient(Patient patient) {
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
        if (!(o instanceof MedicalCondition)) {
            return false;
        }
        return id != null && id.equals(((MedicalCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalCondition{" +
            "id=" + getId() +
            ", age=" + getAge() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", comments='" + getComments() + "'" +
            ", temperature=" + getTemperature() +
            ", bloodSugarLevel=" + getBloodSugarLevel() +
            ", pressure='" + getPressure() + "'" +
            ", pulse=" + getPulse() +
            "}";
    }
}
