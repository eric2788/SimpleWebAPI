package com.ericlam.handler;

import com.ericlam.utils.Level;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/club")
public class ClubHandler extends HttpServlet {

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-Type", "application/json, charset=UTF-8");
        System.out.println("Request Received.");
        System.out.println("Method: " + req.getMethod());
        System.out.println("Parameters: " + req.getParameterMap().size());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        doHead(req, response);
        System.out.println("Handling Delete Method....");
        String text = req.getParameter("phone");
        System.out.println("DEBUG: " + text);
        long phone = Long.parseLong(text);
        response.getWriter().println(DataBaseHandler.removeMember(phone).toJSONString());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHead(request, response);
        System.out.println("Handling Get Method....");
        response.getWriter().println(JSONArray.toJSONString(DataBaseHandler.getAllNumbers()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHead(request, response);
        System.out.println("Handling Post Method....");
        Map requestBody = request.getParameterMap();
        String familyname = (String) requestBody.get("familyname");
        String givename = (String) requestBody.get("givenname");
        String sex = (String) requestBody.get("sex");
        int phones = (int) requestBody.get("phone");
        String sport = (String) requestBody.get("sport");
        Level level = Level.valueOf((String) requestBody.get("level"));
        System.out.println("DEBUG: " + familyname + " " + givename + " " + sex + " " + phones + " " + sport + " " + level);
        response.getWriter().println(DataBaseHandler.addMembers(new Club(familyname, givename, sex, phones, sport, level)).toJSONString());
    }


}
