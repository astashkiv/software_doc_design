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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Language} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.LanguageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /languages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LanguageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter translation;

    private LongFilter medicinesId;

    public LanguageCriteria() {
    }

    public LanguageCriteria(LanguageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.translation = other.translation == null ? null : other.translation.copy();
        this.medicinesId = other.medicinesId == null ? null : other.medicinesId.copy();
    }

    @Override
    public LanguageCriteria copy() {
        return new LanguageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTranslation() {
        return translation;
    }

    public void setTranslation(StringFilter translation) {
        this.translation = translation;
    }

    public LongFilter getMedicinesId() {
        return medicinesId;
    }

    public void setMedicinesId(LongFilter medicinesId) {
        this.medicinesId = medicinesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LanguageCriteria that = (LanguageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(translation, that.translation) &&
            Objects.equals(medicinesId, that.medicinesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        translation,
        medicinesId
        );
    }

    @Override
    public String toString() {
        return "LanguageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (translation != null ? "translation=" + translation + ", " : "") +
                (medicinesId != null ? "medicinesId=" + medicinesId + ", " : "") +
            "}";
    }

}
