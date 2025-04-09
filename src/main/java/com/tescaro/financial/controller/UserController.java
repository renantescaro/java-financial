package com.tescaro.financial.controller;

import com.tescaro.financial.model.User;
import com.tescaro.financial.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/panel/user")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String listRender(Model model) {
    List<User> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "user/index";
  }

  @RequestMapping(path = "/new", method = RequestMethod.GET)
  public String newRender(Model model) {
    model.addAttribute("user", new User());
    return "user/new";
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
  public String editRender(@PathVariable Long id, Model model) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      model.addAttribute("user", user.get());
      return "user/edit";
    }
    return "redirect:/panel/user";
  }

  @RequestMapping(path = "/new", method = RequestMethod.POST)
  public String insert(@ModelAttribute User user) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));

    userRepository.save(user);
    return "redirect:/panel/user";
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
  public String update(@PathVariable Long id, @ModelAttribute User user) {
    Optional<User> existingUser = userRepository.findById(id);

    if (existingUser.isPresent()) {
      User updatedUser = existingUser.get();
      if (user.getPassword() != updatedUser.getPassword()) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updatedUser.setPassword(encoder.encode(user.getPassword()));
      }

      updatedUser.setUsername(user.getUsername());
      updatedUser.setEmail(user.getEmail());
      userRepository.save(updatedUser);
    }
    return "redirect:/panel/user";
  }

  @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
  public String delete(@PathVariable Long id) {
    userRepository.deleteById(id);
    return "redirect:/panel/user";
  }
}
