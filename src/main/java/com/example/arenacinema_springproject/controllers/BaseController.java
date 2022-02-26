package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.exceptions.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    public static final String ADMIN = "admin";

    public void validateLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isNewSession = session.isNew();
        boolean isLogged = session.getAttribute(LOGGED)!=null && ((Boolean)session.getAttribute(LOGGED));
        boolean isSameIP = request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)); //this checks IP and is used against hijacking
        if(isNewSession || !isLogged || !isSameIP ) {
            throw new UnauthorizedException("You have to login.");
        }
    }

    protected void adminLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isAdminSession = (Boolean) session.getAttribute(ADMIN);
        if(!isAdminSession) {
            throw new UnauthorizedException("You should have admin rights.");
        }
    }
}
