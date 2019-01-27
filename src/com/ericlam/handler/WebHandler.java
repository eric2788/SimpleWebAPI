package com.ericlam.handler;

import com.ericlam.utils.Level;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WebHandler extends AbstractHandler {
    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type","application/json, charset=UTF-8");

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        switch (s){
            case "/getAll":
                writer.println(JSONArray.toJSONString(DataBaseHandler.getAllNumbers()));
                break;
            case "/delete":
                if (httpServletRequest.getMethod().equalsIgnoreCase("get")) {
                    writer.println(DataBaseHandler.error("Cannot use get to request this link").toJSONString());
                    break;
                }
                String text = httpServletRequest.getParameter("phone");
                int phone = Integer.parseInt(text);
                writer.println(DataBaseHandler.removeMember(phone).toJSONString());
                break;
            case "/create":
                if (httpServletRequest.getMethod().equalsIgnoreCase("get")) {
                    writer.println(DataBaseHandler.error("Cannot use get to request this link").toJSONString());
                    break;
                }
                Map requestBody = httpServletRequest.getParameterMap();
                String familyname = (String) requestBody.get("familyname");
                String givename = (String) requestBody.get("givenname");
                String sex = (String) requestBody.get("sex");
                int phones = (int) requestBody.get("phone");
                String sport = (String) requestBody.get("sport");
                Level level = Level.valueOf((String)requestBody.get("Level"));
                writer.println(DataBaseHandler.addMembers(new Club(familyname,givename,sex,phones,sport,level)).toJSONString());
                break;
            default:
                writer.println(DataBaseHandler.error("Unknown Request").toJSONString());
                break;
        }

        request.setHandled(true);
        writer.close();
    }
}
