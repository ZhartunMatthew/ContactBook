package com.zhartunmatthew.web.contactbook.dto.search;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SearchCriteriaBuilder {

    private SearchCriteria criteria = null;
    private final static String START_QUERY =
            "SELECT contacts.id AS id, first_name, last_name, patronymic, " +
            "birth_date, sex, marital_status.id_marital_status AS marital_status, " +
            "nationality.id_nationality AS nationality, " +
            "countries.id_country AS country, addresses.city AS city, " +
            "addresses.street AS street, addresses.house_number AS house, " +
            "addresses.flat AS flat, addresses.postcode AS postcode, " +
            "website, email, photo_path, job " +
            "FROM contacts " +
            "LEFT JOIN nationality ON nationality.id_nationality = contacts.nationality_id " +
            "LEFT JOIN marital_status ON marital_status.id_marital_status = contacts.marital_status_id " +
            "LEFT JOIN countries ON countries.id_country = contacts.country_id " +
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id WHERE TRUE";

    public SearchCriteriaBuilder(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
        int position = 1;
        PreparedStatement statement = connection.prepareStatement(buildQuery());
        position = setString(statement, position, criteria.getFirstName());
        position = setString(statement, position, criteria.getLastName());
        position = setString(statement, position, criteria.getPatronymic());
        position = setString(statement, position, criteria.getSex());
        position = setInt(statement, position, criteria.getMaritalStatus());
        position = setInt(statement, position, criteria.getNationality());
        position = setInt(statement, position, criteria.getCountry());
        position = setString(statement, position, criteria.getPostcode());
        position = setString(statement, position, criteria.getCity());
        position = setString(statement, position, criteria.getStreet());
        position = setString(statement, position, criteria.getHouse());
        position = setString(statement, position, criteria.getFlat());
        position = setDate(statement, position, criteria.getFromDate());
        position = setDate(statement, position, criteria.getToDate());

        return statement;
    }

    private String buildQuery() {
        StringBuilder stringBuilder = new StringBuilder(START_QUERY);
        stringBuilder.append(stringQueryPart("first_name", criteria.getFirstName()));
        stringBuilder.append(stringQueryPart("last_name", criteria.getLastName()));
        stringBuilder.append(stringQueryPart("patronymic", criteria.getPatronymic()));
        stringBuilder.append(stringQueryPart("sex", criteria.getSex()));
        stringBuilder.append(intQueryPart("marital_status.id_marital_status", criteria.getMaritalStatus()));
        stringBuilder.append(intQueryPart("nationality.id_nationality", criteria.getNationality()));
        stringBuilder.append(intQueryPart("countries.id_country", criteria.getCountry()));
        stringBuilder.append(stringQueryPart("postcode", criteria.getPostcode()));
        stringBuilder.append(stringQueryPart("city", criteria.getCity()));
        stringBuilder.append(stringQueryPart("street", criteria.getStreet()));
        stringBuilder.append(stringQueryPart("house_number", criteria.getHouse()));
        stringBuilder.append(stringQueryPart("flat", criteria.getFlat()));
        stringBuilder.append(dateQueryPart(criteria.getFromDate(), ">="));
        stringBuilder.append(dateQueryPart(criteria.getToDate(), "<="));
        stringBuilder.append(" ORDER BY last_name;");

        return stringBuilder.toString();
    }

    private String str2Pattern(String str) {
        return "%" + str + "%";
    }

    private String stringQueryPart(String parameter, String value) {
        return !StringUtils.isEmpty(value) ? String.format(" AND %s LIKE ?", parameter) : "";
    }

    private String intQueryPart(String parameter, int value) {
        return value != 0 ? String.format(" AND %s LIKE ?", parameter) : "";
    }

    private String dateQueryPart(Date date, String sign) {
        return  date != null ? " AND birth_date " + sign + " ?" : "";
    }

    private int setString(PreparedStatement statement, int index, String value) throws SQLException {
        if(!StringUtils.isEmpty(value)) {
            statement.setString(index, str2Pattern(value));
            return index + 1;
        } else {
            return index;
        }
    }

    private int setInt(PreparedStatement statement, int index, int value) throws SQLException {
        if(value != 0) {
            statement.setInt(index, value);
            return index + 1;
        } else {
            return index;
        }
    }

    private int setDate(PreparedStatement statement, int index, Date value) throws SQLException {
        if(value != null) {
            statement.setDate(index, value);
            return index + 1;
        } else {
            return index;
        }
    }
}

