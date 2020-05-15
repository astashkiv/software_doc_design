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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Doctor} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.DoctorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doctors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DoctorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private StringFilter address;

    private LongFilter feedbacksId;

    private LongFilter certificatesId;

    private LongFilter paymentsId;

    private LongFilter adminId;

    private LongFilter departmentId;

    public DoctorCriteria() {
    }

    public DoctorCriteria(DoctorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.feedbacksId = other.feedbacksId == null ? null : other.feedbacksId.copy();
        this.certificatesId = other.certificatesId == null ? null : other.certificatesId.copy();
        this.paymentsId = other.paymentsId == null ? null : other.paymentsId.copy();
        this.adminId = other.adminId == null ? null : other.adminId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
    }

    @Override
    public DoctorCriteria copy() {
        return new DoctorCriteria(this);
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

    public LongFilter getCertificatesId() {
        return certificatesId;
    }

    public void setCertificatesId(LongFilter certificatesId) {
        this.certificatesId = certificatesId;
    }

    public LongFilter getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(LongFilter paymentsId) {
        this.paymentsId = paymentsId;
    }

    public LongFilter getAdminId() {
        return adminId;
    }

    public void setAdminId(LongFilter adminId) {
        this.adminId = adminId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DoctorCriteria that = (DoctorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(feedbacksId, that.feedbacksId) &&
            Objects.equals(certificatesId, that.certificatesId) &&
            Objects.equals(paymentsId, that.paymentsId) &&
            Objects.equals(adminId, that.adminId) &&
            Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        address,
        feedbacksId,
        certificatesId,
        paymentsId,
        adminId,
        departmentId
        );
    }

    @Override
    public String toString() {
        return "DoctorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (feedbacksId != null ? "feedbacksId=" + feedbacksId + ", " : "") +
                (certificatesId != null ? "certificatesId=" + certificatesId + ", " : "") +
                (paymentsId != null ? "paymentsId=" + paymentsId + ", " : "") +
                (adminId != null ? "adminId=" + adminId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            "}";
    }

}
