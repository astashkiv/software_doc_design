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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Medicine} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.MedicineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /medicines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MedicineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter instruction;

    private LongFilter languagesInId;

    private LongFilter prescriptionsId;

    public MedicineCriteria() {
    }

    public MedicineCriteria(MedicineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.instruction = other.instruction == null ? null : other.instruction.copy();
        this.languagesInId = other.languagesInId == null ? null : other.languagesInId.copy();
        this.prescriptionsId = other.prescriptionsId == null ? null : other.prescriptionsId.copy();
    }

    @Override
    public MedicineCriteria copy() {
        return new MedicineCriteria(this);
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

    public StringFilter getInstruction() {
        return instruction;
    }

    public void setInstruction(StringFilter instruction) {
        this.instruction = instruction;
    }

    public LongFilter getLanguagesInId() {
        return languagesInId;
    }

    public void setLanguagesInId(LongFilter languagesInId) {
        this.languagesInId = languagesInId;
    }

    public LongFilter getPrescriptionsId() {
        return prescriptionsId;
    }

    public void setPrescriptionsId(LongFilter prescriptionsId) {
        this.prescriptionsId = prescriptionsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MedicineCriteria that = (MedicineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(instruction, that.instruction) &&
            Objects.equals(languagesInId, that.languagesInId) &&
            Objects.equals(prescriptionsId, that.prescriptionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        instruction,
        languagesInId,
        prescriptionsId
        );
    }

    @Override
    public String toString() {
        return "MedicineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (instruction != null ? "instruction=" + instruction + ", " : "") +
                (languagesInId != null ? "languagesInId=" + languagesInId + ", " : "") +
                (prescriptionsId != null ? "prescriptionsId=" + prescriptionsId + ", " : "") +
            "}";
    }

}
