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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Booking} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.BookingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bookings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter when;

    private LongFilter toWhoId;

    private LongFilter byWhoId;

    public BookingCriteria() {
    }

    public BookingCriteria(BookingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.when = other.when == null ? null : other.when.copy();
        this.toWhoId = other.toWhoId == null ? null : other.toWhoId.copy();
        this.byWhoId = other.byWhoId == null ? null : other.byWhoId.copy();
    }

    @Override
    public BookingCriteria copy() {
        return new BookingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTimeFilter when) {
        this.when = when;
    }

    public LongFilter getToWhoId() {
        return toWhoId;
    }

    public void setToWhoId(LongFilter toWhoId) {
        this.toWhoId = toWhoId;
    }

    public LongFilter getByWhoId() {
        return byWhoId;
    }

    public void setByWhoId(LongFilter byWhoId) {
        this.byWhoId = byWhoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookingCriteria that = (BookingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(when, that.when) &&
            Objects.equals(toWhoId, that.toWhoId) &&
            Objects.equals(byWhoId, that.byWhoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        when,
        toWhoId,
        byWhoId
        );
    }

    @Override
    public String toString() {
        return "BookingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (when != null ? "when=" + when + ", " : "") +
                (toWhoId != null ? "toWhoId=" + toWhoId + ", " : "") +
                (byWhoId != null ? "byWhoId=" + byWhoId + ", " : "") +
            "}";
    }

}
