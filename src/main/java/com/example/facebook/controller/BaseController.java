package com.example.facebook.controller;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    protected ModelAndView send(String viewName) {
        return new ModelAndView(viewName);
    }

    protected ModelAndView send(String viewName, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView(viewName + ".html");
        modelAndView.addObject(objectName, object);
        return modelAndView;
    }

    protected ModelAndView redirect(String endpoint) {
        return new ModelAndView("redirect:" + endpoint);
    }

    protected ModelAndView redirect(String endpoint, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + endpoint);
        modelAndView.addObject(objectName, object);
        return modelAndView;
    }
}
