package com.example.airlineinfosystem3.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorTitle", "Страница не найдена");
                model.addAttribute("errorMessage", "К сожалению, запрашиваемая страница не существует.");
                return "error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
                model.addAttribute("errorMessage", "Произошла ошибка на сервере. Пожалуйста, попробуйте позже.");
                return "error";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorTitle", "Доступ запрещен");
                model.addAttribute("errorMessage", "У вас нет доступа к этой странице.");
                return "error";
            }
        }

        model.addAttribute("errorCode", "Ошибка");
        model.addAttribute("errorTitle", "Произошла ошибка");
        model.addAttribute("errorMessage", "Что-то пошло не так. Пожалуйста, попробуйте позже.");
        return "error";
    }
}

