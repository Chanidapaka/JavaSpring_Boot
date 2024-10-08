package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repositories.OfficeRepository;
import sit.int202.demo.services.OfficeService;

import java.util.List;

@Controller
@RequestMapping("/offices") // กำหนด URL ที่จะใช้สำหรับ Controller นี้
public class OfficeController {
    private final OfficeService service;

    // Constructor Injection เพื่อฉีด OfficeService
    public OfficeController(OfficeService service) {
        this.service = service;
    }
    @GetMapping("/delete")
    public String deleteOffice(@RequestParam("id") String officeCode, Model model) {
        Office office = service.deleteOffice(officeCode);
        model.addAttribute("office", office);
        model.addAttribute("message", "Office deleted successfully");
        return "offices_details";
    }

    @GetMapping("") // เมธอดที่จะรับคำขอ GET ที่ /offices
    public String getOfficeById(@RequestParam String officeCode, Model model) {
        // ดึงข้อมูลออฟฟิศตาม officeCode
        Office office = service.getOffice(officeCode);
        model.addAttribute("office", office);
        return "office_details";
    }


    @GetMapping("/all") //เอาแค่get   --เราจะ/ไปทางนี้ทีหลัง2
    public String allOffices(Model model) {
        List<Office> officeList = service.getAllOffices();
        model.addAttribute("offices", officeList);
        return "office_list";
    }
}
