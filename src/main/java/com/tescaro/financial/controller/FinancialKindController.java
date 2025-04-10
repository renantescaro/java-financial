package com.tescaro.financial.controller;

import com.tescaro.financial.model.FinancialKind;
import com.tescaro.financial.repository.FinancialKindRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/panel/financial-kind")
public class FinancialKindController {

  private final FinancialKindRepository financialKindRepository;

  public FinancialKindController(FinancialKindRepository financialKindRepository) {
    this.financialKindRepository = financialKindRepository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String listRender(Model model) {
    List<FinancialKind> financialkinds = financialKindRepository.findAll();
    model.addAttribute("financialkinds", financialkinds);
    return "financialKind/index";
  }

  @RequestMapping(path = "/new", method = RequestMethod.GET)
  public String newRender(Model model) {
    model.addAttribute("financialKind", new FinancialKind());
    return "financialKind/new";
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
  public String editRender(@PathVariable Long id, Model model) {
    Optional<FinancialKind> financialKind = financialKindRepository.findById(id);
    if (financialKind.isPresent()) {
      model.addAttribute("financialKind", financialKind.get());
      return "financialKind/edit";
    }
    return "redirect:/panel/financial-kind";
  }

  @RequestMapping(path = "/new", method = RequestMethod.POST)
  public String insert(@ModelAttribute FinancialKind financialKind) {
    financialKindRepository.save(financialKind);
    return "redirect:/panel/financial-kind";
  }

  @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
  public String update(@PathVariable Long id, @ModelAttribute FinancialKind financialKind) {
    Optional<FinancialKind> existing = financialKindRepository.findById(id);
    if (existing.isPresent()) {
      FinancialKind updated = existing.get();
      updated.setId(financialKind.getId());
      updated.setName(financialKind.getName());
      updated.setPeriodical(financialKind.getPeriodical());
      financialKindRepository.save(updated);
    }
    return "redirect:/panel/financial-kind";
  }

  @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
  public String delete(@PathVariable Long id) {
    financialKindRepository.deleteById(id);
    return "redirect:/panel/financial-kind";
  }
}
