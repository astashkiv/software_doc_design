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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Prescription} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.PrescriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prescriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrescriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter timings;

    private IntegerFilter medicineCount;

    private LongFilter signedById;

    private LongFilter medicinesId;

    private LongFilter patientId;

    public PrescriptionCriteria() {
    }

    public PrescriptionCriteria(PrescriptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.timings = other.timings == null ? null : other.timings.copy();
        this.medicineCount = other.medicineCount == null ? null : other.medicineCount.copy();
        this.signedById = other.signedById == null ? null : other.signedById.copy();
        this.medicinesId = other.medicinesId == null ? null : other.medicinesId.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
    }

    @Override
    public PrescriptionCriteria copy() {
        return new PrescriptionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getTimings() {
        return timings;
    }

    public void setTimings(StringFilter timings) {
        this.timings = timings;
    }

    public IntegerFilter getMedicineCount() {
        return medicineCount;
    }

    public void setMedicineCount(IntegerFilter medicineCount) {
        this.medicineCount = medicineCount;
    }

    public LongFilter getSignedById() {
        return signedById;
    }

    public void setSignedById(LongFilter signedById) {
        this.signedById = signedById;
    }

    public LongFilter getMedicinesId() {
        return medicinesId;
    }

    public void setMedicinesId(LongFilter medicinesId) {
        this.medicinesId = medicinesId;
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
        final PrescriptionCriteria that = (PrescriptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(timings, that.timings) &&
            Objects.equals(medicineCount, that.medicineCount) &&
            Objects.equals(signedById, that.signedById) &&
            Objects.equals(medicinesId, that.medicinesId) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        timings,
        medicineCount,
        signedById,
        medicinesId,
        patientId
        );
    }

    @Override
    public String toString() {
        return "PrescriptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (timings != null ? "timings=" + timings + ", " : "") +
                (medicineCount != null ? "medicineCount=" + medicineCount + ", " : "") +
                (signedById != null ? "signedById=" + signedById + ", " : "") +
                (medicinesId != null ? "medicinesId=" + medicinesId + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
