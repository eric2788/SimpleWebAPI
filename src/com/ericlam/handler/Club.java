package com.ericlam.handler;

import com.ericlam.utils.Level;

public class Club {
    private String familyname;
    private String givenname;
    private String sex;
    private int phone;
    private String sport;
    private Level level;

    public Club(String familyname, String givenname, String sex, int phone, String sport, Level level) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.sex = sex;
        this.phone = phone;
        this.sport = sport;
        this.level = level;
    }

    public String getFamilyname() {
        return familyname;
    }

    public String getGivenname() {
        return givenname;
    }

    public String getSex() {
        return sex;
    }

    public int getPhone() {
        return phone;
    }

    public String getSport() {
        return sport;
    }

    public Level getLevel() {
        return level;
    }
}
