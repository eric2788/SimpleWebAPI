package com.ericlam.handler;

import com.ericlam.utils.Level;
import com.ericlam.utils.SQLDataSourceManager;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class DataBaseHandler {
    static ArrayList<JSONObject> getAllNumbers(){
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        try(Connection connection = SQLDataSourceManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `CLUB`")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Map<String,Object> maps = new LinkedHashMap<>();
                String familyname = resultSet.getString("familyname");
                String givenname = resultSet.getString("givenname");
                String sex = resultSet.getString("sex");
                int phone = resultSet.getInt("phone");
                String sport = resultSet.getString("sport");
                Level level = Level.valueOf(resultSet.getString("level"));
                maps.put("familyname",familyname);
                maps.put("givename",givenname);
                maps.put("sex",sex);
                maps.put("phone",phone);
                maps.put("sport",sport);
                maps.put("level",level.toString());
                arrayList.add(new JSONObject(maps));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    static JSONObject removeMember(int phone){
        boolean success;
        try(Connection connection = SQLDataSourceManager.getInstance().getConnection();PreparedStatement statement = connection.prepareStatement("DELETE FROM `CLUB` WHERE `phone`=?")){
            statement.setInt(1,phone);
            success = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        Map<String,Boolean> result = new HashMap<>();
        result.put("success",success);
        return new JSONObject(result);
    }

    static JSONObject addMembers(Club club){
        boolean success;
        try(Connection connection = SQLDataSourceManager.getInstance().getConnection();PreparedStatement statement = connection.prepareStatement("INSERT INTO `CLUB` VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `familyname`=?, `givenname`=?, `sex`=?, `sport`=?, `level`=?")){
            statement.setString(1,club.getFamilyname());
            statement.setString(7,club.getFamilyname());
            statement.setString(2,club.getGivenname());
            statement.setString(8,club.getGivenname());
            statement.setString(3,club.getSex());
            statement.setString(9,club.getSex());
            statement.setInt(4,club.getPhone());
            statement.setString(5,club.getSport());
            statement.setString(10,club.getSport());
            statement.setString(6,club.getLevel().toString());
            statement.setString(11,club.getLevel().toString());
            success = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        Map<String,Boolean> result = new HashMap<>();
        result.put("success",success);
        return new JSONObject(result);
    }

    static JSONObject error(String errorMSG){
        Map<String,String> error = new HashMap<>();
        error.put("Error",errorMSG);
        return new JSONObject(error);
    }
}
