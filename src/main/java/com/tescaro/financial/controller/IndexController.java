package com.tescaro.financial.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {

    return "index";
  }

  @GetMapping("/panel")
  public String hello(Model model) {
    return "panel";
  }
}
