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
 * Criteria class for the {@link com.stashkiv.pharmacy.domain.Query} entity. This class is used
 * in {@link com.stashkiv.pharmacy.web.rest.QueryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /queries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QueryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter query;

    private StringFilter answer;

    private LongFilter answeredById;

    private LongFilter askedById;

    public QueryCriteria() {
    }

    public QueryCriteria(QueryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.query = other.query == null ? null : other.query.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.answeredById = other.answeredById == null ? null : other.answeredById.copy();
        this.askedById = other.askedById == null ? null : other.askedById.copy();
    }

    @Override
    public QueryCriteria copy() {
        return new QueryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuery() {
        return query;
    }

    public void setQuery(StringFilter query) {
        this.query = query;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public LongFilter getAnsweredById() {
        return answeredById;
    }

    public void setAnsweredById(LongFilter answeredById) {
        this.answeredById = answeredById;
    }

    public LongFilter getAskedById() {
        return askedById;
    }

    public void setAskedById(LongFilter askedById) {
        this.askedById = askedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QueryCriteria that = (QueryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(query, that.query) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(answeredById, that.answeredById) &&
            Objects.equals(askedById, that.askedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        query,
        answer,
        answeredById,
        askedById
        );
    }

    @Override
    public String toString() {
        return "QueryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (query != null ? "query=" + query + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (answeredById != null ? "answeredById=" + answeredById + ", " : "") +
                (askedById != null ? "askedById=" + askedById + ", " : "") +
            "}";
    }

}
