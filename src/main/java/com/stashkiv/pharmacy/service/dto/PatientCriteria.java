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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Patient} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.PatientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private StringFilter address;

    private LongFilter feedbacksId;

    private LongFilter prescriptionsId;

    private LongFilter certificatesId;

    private LongFilter queriesId;

    private LongFilter bookingsId;

    private LongFilter conditionsId;

    private LongFilter paymentsId;

    public PatientCriteria() {
    }

    public PatientCriteria(PatientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.feedbacksId = other.feedbacksId == null ? null : other.feedbacksId.copy();
        this.prescriptionsId = other.prescriptionsId == null ? null : other.prescriptionsId.copy();
        this.certificatesId = other.certificatesId == null ? null : other.certificatesId.copy();
        this.queriesId = other.queriesId == null ? null : other.queriesId.copy();
        this.bookingsId = other.bookingsId == null ? null : other.bookingsId.copy();
        this.conditionsId = other.conditionsId == null ? null : other.conditionsId.copy();
        this.paymentsId = other.paymentsId == null ? null : other.paymentsId.copy();
    }

    @Override
    public PatientCriteria copy() {
        return new PatientCriteria(this);
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

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public LongFilter getFeedbacksId() {
        return feedbacksId;
    }

    public void setFeedbacksId(LongFilter feedbacksId) {
        this.feedbacksId = feedbacksId;
    }

    public LongFilter getPrescriptionsId() {
        return prescriptionsId;
    }

    public void setPrescriptionsId(LongFilter prescriptionsId) {
        this.prescriptionsId = prescriptionsId;
    }

    public LongFilter getCertificatesId() {
        return certificatesId;
    }

    public void setCertificatesId(LongFilter certificatesId) {
        this.certificatesId = certificatesId;
    }

    public LongFilter getQueriesId() {
        return queriesId;
    }

    public void setQueriesId(LongFilter queriesId) {
        this.queriesId = queriesId;
    }

    public LongFilter getBookingsId() {
        return bookingsId;
    }

    public void setBookingsId(LongFilter bookingsId) {
        this.bookingsId = bookingsId;
    }

    public LongFilter getConditionsId() {
        return conditionsId;
    }

    public void setConditionsId(LongFilter conditionsId) {
        this.conditionsId = conditionsId;
    }

    public LongFilter getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(LongFilter paymentsId) {
        this.paymentsId = paymentsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PatientCriteria that = (PatientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(feedbacksId, that.feedbacksId) &&
            Objects.equals(prescriptionsId, that.prescriptionsId) &&
            Objects.equals(certificatesId, that.certificatesId) &&
            Objects.equals(queriesId, that.queriesId) &&
            Objects.equals(bookingsId, that.bookingsId) &&
            Objects.equals(conditionsId, that.conditionsId) &&
            Objects.equals(paymentsId, that.paymentsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        address,
        feedbacksId,
        prescriptionsId,
        certificatesId,
        queriesId,
        bookingsId,
        conditionsId,
        paymentsId
        );
    }

    @Override
    public String toString() {
        return "PatientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (feedbacksId != null ? "feedbacksId=" + feedbacksId + ", " : "") +
                (prescriptionsId != null ? "prescriptionsId=" + prescriptionsId + ", " : "") +
                (certificatesId != null ? "certificatesId=" + certificatesId + ", " : "") +
                (queriesId != null ? "queriesId=" + queriesId + ", " : "") +
                (bookingsId != null ? "bookingsId=" + bookingsId + ", " : "") +
                (conditionsId != null ? "conditionsId=" + conditionsId + ", " : "") +
                (paymentsId != null ? "paymentsId=" + paymentsId + ", " : "") +
            "}";
    }

}
