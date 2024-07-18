package com.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/403")
    public ModelAndView handle403() {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorMessage", "접근 권한이 없습니다.");
        return modelAndView;
    }

    @RequestMapping("/404")
    public ModelAndView handle404() {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorMessage", "페이지를 찾을 수 없습니다.");
        return modelAndView;
    }

    @RequestMapping("/500")
    public ModelAndView handle500() {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorMessage", "서버 내부 오류가 발생했습니다.");
        return modelAndView;
    }

    @RequestMapping("/400")
    public ModelAndView handle400() {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorMessage", "잘못된 요청입니다.");
        return modelAndView;
    }

    @RequestMapping("/general")
    public ModelAndView handleGeneralError() {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("errorMessage", "예기치 않은 오류가 발생했습니다.");
        return modelAndView;
    }
}
