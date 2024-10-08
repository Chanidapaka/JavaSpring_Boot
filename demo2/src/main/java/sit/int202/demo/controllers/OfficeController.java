package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repositories.OfficeRepository;
import sit.int202.demo.services.OfficeService;

import java.util.List;

@Controller
@RequestMapping("/offices") //รับrequest ทุกชนิด --เราจะ/ไปทางนี้ก่อน1
public class OfficeController {
    private final OfficeService service;

    public OfficeController(OfficeService service) {
        this.service = service;
    }

    @GetMapping("/all") //เอาแค่get   --เราจะ/ไปทางนี้ทีหลัง2
    public String allOffices(Model model) {
        List<Office> officeList = service.getAllOffices();
        model.addAttribute("offices", officeList);
        return "office_list";
    }
}
