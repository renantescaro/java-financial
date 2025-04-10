package com.tescaro.financial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tescaro.financial.dto.BillsDTO;
import com.tescaro.financial.enums.KindEnum;
import com.tescaro.financial.model.Bills;
import com.tescaro.financial.repository.BillsRepository;
import com.tescaro.financial.repository.FinancialKindRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/panel/bills")
public class BillsController {

    private final BillsRepository billsRepository;
    private final FinancialKindRepository financialKindRepository;

    public BillsController(
        BillsRepository billsRepository,
        FinancialKindRepository financialKindRepository) {
        this.billsRepository = billsRepository;
        this.financialKindRepository = financialKindRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listRender(Model model) {
        try {
            List<Bills> bills = billsRepository.findAll();

            List<BillsDTO> billsDto = bills.stream().map(b -> {
                BillsDTO dto = new BillsDTO();
                dto.id = b.getId();
                dto.name = b.getName();
                dto.kind = b.getKind().getDescription();
                dto.time = b.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                dto.value = b.getValue();
                dto.financialKindId = b.getFinancialKind().getId();
                dto.financialKindName = b.getFinancialKind().getName();
                return dto;
            }).toList();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            model.addAttribute("bills", mapper.writeValueAsString(billsDto));
            return "bills/index";
        } catch (Exception e) {
            System.err.println(e);
            return "bills/index";
        }
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newRender(Model model) {
        model.addAttribute("bills", new Bills());
        model.addAttribute("kinds", KindEnum.values());
        model.addAttribute("financialKinds", financialKindRepository.findAll());
        return "bills/new";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String editRender(@PathVariable Long id, Model model) {
        Optional<Bills> bills = billsRepository.findById(id);
        if (bills.isPresent()) {
            model.addAttribute("bills", bills.get());
            model.addAttribute("kinds", KindEnum.values());
            model.addAttribute("financialKinds", financialKindRepository.findAll());
            return "bills/edit";
        }
        return "redirect:/panel/bills";
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String insert(@ModelAttribute Bills bills) {
        billsRepository.save(bills);
        return "redirect:/panel/bills";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute Bills bills) {
        Optional<Bills> existing = billsRepository.findById(id);
        if (existing.isPresent()) {
            Bills updated = existing.get();
            updated.setId(bills.getId());
            updated.setKind(bills.getKind());
            updated.setValue(bills.getValue());
            updated.setTime(bills.getTime());
            billsRepository.save(updated);
        }
        return "redirect:/panel/bills";
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        billsRepository.deleteById(id);
        return "redirect:/panel/bills";
    }
}
