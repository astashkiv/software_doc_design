package com.stashkiv.pharmacy.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.MedicalCondition} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.MedicalConditionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /medical-conditions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MedicalConditionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter age;

    private DoubleFilter height;

    private DoubleFilter weight;

    private StringFilter comments;

    private DoubleFilter temperature;

    private DoubleFilter bloodSugarLevel;

    private StringFilter pressure;

    private IntegerFilter pulse;

    private LongFilter patientId;

    public MedicalConditionCriteria() {
    }

    public MedicalConditionCriteria(MedicalConditionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.bloodSugarLevel = other.bloodSugarLevel == null ? null : other.bloodSugarLevel.copy();
        this.pressure = other.pressure == null ? null : other.pressure.copy();
        this.pulse = other.pulse == null ? null : other.pulse.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
    }

    @Override
    public MedicalConditionCriteria copy() {
        return new MedicalConditionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public DoubleFilter getHeight() {
        return height;
    }

    public void setHeight(DoubleFilter height) {
        this.height = height;
    }

    public DoubleFilter getWeight() {
        return weight;
    }

    public void setWeight(DoubleFilter weight) {
        this.weight = weight;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public DoubleFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(DoubleFilter temperature) {
        this.temperature = temperature;
    }

    public DoubleFilter getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(DoubleFilter bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public StringFilter getPressure() {
        return pressure;
    }

    public void setPressure(StringFilter pressure) {
        this.pressure = pressure;
    }

    public IntegerFilter getPulse() {
        return pulse;
    }

    public void setPulse(IntegerFilter pulse) {
        this.pulse = pulse;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MedicalConditionCriteria that = (MedicalConditionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(age, that.age) &&
            Objects.equals(height, that.height) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(bloodSugarLevel, that.bloodSugarLevel) &&
            Objects.equals(pressure, that.pressure) &&
            Objects.equals(pulse, that.pulse) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        age,
        height,
        weight,
        comments,
        temperature,
        bloodSugarLevel,
        pressure,
        pulse,
        patientId
        );
    }

    @Override
    public String toString() {
        return "MedicalConditionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (bloodSugarLevel != null ? "bloodSugarLevel=" + bloodSugarLevel + ", " : "") +
                (pressure != null ? "pressure=" + pressure + ", " : "") +
                (pulse != null ? "pulse=" + pulse + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
