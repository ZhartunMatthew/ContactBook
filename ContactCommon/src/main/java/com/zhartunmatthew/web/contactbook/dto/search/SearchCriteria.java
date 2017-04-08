package com.zhartunmatthew.web.contactbook.dto.search;

import com.zhartunmatthew.web.contactbook.services.UtilService;
import com.zhartunmatthew.web.contactbook.services.exception.ServiceException;

import java.nio.charset.Charset;
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
        Charset charset = Charset.forName("UTF-8");
        ArrayList<String> criteriaList = new ArrayList<>();
        addStringToList(criteriaList, new String("имя:".getBytes(), charset), firstName);
        addStringToList(criteriaList, new String("фамилия:".getBytes(), charset), lastName);
        addStringToList(criteriaList, new String("отчество:".getBytes(), charset), patronymic);
        addStringToList(criteriaList, new String("пол:".getBytes(), charset), sex);
        if(maritalStatus != 0) {
            criteriaList.add(new String("сп: ".getBytes(), charset) + utilService.getMaritalStatus(maritalStatus).getName());
        }
        if(nationality != 0) {
            criteriaList.add(new String("гр.: ".getBytes(), charset) + utilService.getNationality(nationality).getName());
        }
        if(country != 0) {
            criteriaList.add(new String("страна: ".getBytes(), charset) + utilService.getCountry(country).getName());
        }
        addStringToList(criteriaList, new String("инд.:".getBytes(), charset), postcode);
        addStringToList(criteriaList, new String("г.:".getBytes(), charset), city);
        addStringToList(criteriaList, new String("ул.:".getBytes(), charset), street);
        addStringToList(criteriaList, new String("д.:".getBytes(), charset), house);
        addStringToList(criteriaList, new String("кв. :".getBytes(), charset), flat);
        if(fromDate != null) {
            addStringToList(criteriaList, new String("от даты:".getBytes(), charset), getDateString(fromDate));
        }
        if(toDate != null) {
            addStringToList(criteriaList, new String("до даты:".getBytes(), charset), getDateString(toDate));
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
