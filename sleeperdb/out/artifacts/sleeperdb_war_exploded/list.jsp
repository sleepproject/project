<%--
  Created by IntelliJ IDEA.
  User: o0tmd
  Date: 2021-10-20
  Time: 오후 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="Statistics.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("UTF-8");

  String returns="";
  String type = request.getParameter("type");
  String parameter = request.getParameter("userId");

%>
<%
  if (type == null) {
    return;
  }else if (type.equals("vision_write")) {
    System.out.println("값을받았습니다."+parameter);
    Vision_Write vision_board = Vision_Write.getWrite();
    //returns = vision_board.write(parameter);
    out.println(returns);
  }else if (type.equals("read_data")) {
    System.out.println("값을 리턴합니다.");
    Vision_Board vision_board = Vision_Board.getVision_Board();
    returns=vision_board.select(parameter);
    out.println(returns);
  }
%>