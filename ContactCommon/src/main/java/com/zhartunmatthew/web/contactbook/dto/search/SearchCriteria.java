package com.zhartunmatthew.web.contactbook.dto.search;

import com.zhartunmatthew.web.contactbook.services.UtilService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchCriteria {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String sex;
    private int maritalStatus;
    private int nationality;
    private int country;
    private String postcode;
    private String city;
    private String street;
    private String house;
    private String flat;
    private Date fromDate;
    private Date toDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getNationality() {
        return nationality;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ArrayList<String> toArray() throws ServiceException {
        UtilService utilService = new UtilService();
        ArrayList<String> criteriaList = new ArrayList<>();
        addStringToList(criteriaList, "имя:", firstName);
        addStringToList(criteriaList, "фамилия:", lastName);
        addStringToList(criteriaList, "отчество:", patronymic);
        addStringToList(criteriaList, "пол:", sex);
        if (maritalStatus != 0) {
            criteriaList.add("сп: " + utilService.getMaritalStatus(maritalStatus).getName());
        }
        if (nationality != 0) {
            criteriaList.add("гр.: " + utilService.getNationality(nationality).getName());
        }
        if (country != 0) {
            criteriaList.add("страна: " + utilService.getCountry(country).getName());
        }
        addStringToList(criteriaList, "инд.:", postcode);
        addStringToList(criteriaList, "г.:", city);
        addStringToList(criteriaList, "ул.:", street);
        addStringToList(criteriaList, "д.:", house);
        addStringToList(criteriaList, "кв. :", flat);
        if (fromDate != null) {
            addStringToList(criteriaList, "от даты:", getDateString(fromDate));
        }
        if (toDate != null) {
            addStringToList(criteriaList,"до даты:", getDateString(toDate));
        }

        return criteriaList;
    }

    private void addStringToList(ArrayList<String> list, String name, String value) {
        if(value != null) {
            list.add(name + " " + value);
        }
    }

    private String getDateString(Date date) {
        if(date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int iDay = calendar.get(Calendar.DAY_OF_MONTH);
            int iMonth = calendar.get(Calendar.MONTH) + 1;
            int iYear = calendar.get(Calendar.YEAR);
            String day = iDay < 10 ? "0" + Integer.toString(iDay) : Integer.toString(iDay);
            String month = iMonth < 10 ? "0" + Integer.toString(iMonth) : Integer.toString(iMonth);
            String year = Integer.toString(iYear);

            return day + "." + month + "." + year;
        } else {
            return "";
        }
    }

}
