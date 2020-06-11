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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Admin} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.AdminResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /admins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdminCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter password;

    private LongFilter doctorsId;

    public AdminCriteria() {
    }

    public AdminCriteria(AdminCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.doctorsId = other.doctorsId == null ? null : other.doctorsId.copy();
    }

    @Override
    public AdminCriteria copy() {
        return new AdminCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public LongFilter getDoctorsId() {
        return doctorsId;
    }

    public void setDoctorsId(LongFilter doctorsId) {
        this.doctorsId = doctorsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdminCriteria that = (AdminCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(doctorsId, that.doctorsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        password,
        doctorsId
        );
    }

    @Override
    public String toString() {
        return "AdminCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (doctorsId != null ? "doctorsId=" + doctorsId + ", " : "") +
            "}";
    }

}
