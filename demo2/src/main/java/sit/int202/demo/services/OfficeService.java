package sit.int202.demo.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repositories.OfficeRepository;

import java.util.List;

@Service  //บอก spring boot ว่ามันคือ service นะ
public class OfficeService {
    private OfficeRepository officeRepository;
    public List<Office> getAllOffices() { // เมธอดเพื่อดึงข้อมูลออฟฟิศทั้งหมด
        return officeRepository.findAll(); // คืนค่าข้อมูลออฟฟิศทั้งหมด
    }

    public Office getOffice(String officeCode) { // เมธอดเพื่อดึงข้อมูลออฟฟิศตาม ID
        return officeRepository.findById(officeCode).orElse(null);  // คืนค่าออฟฟิศหรือ null ถ้าไม่พบ
    }

    public Office addOffice(Office office) { //เมธอดที่เพิ่มข้อมูลออฟฟิศ
        // การเช็คว่ามีออฟฟิศนี้อยู่ในฐานข้อมูลหรือไม่ และตรวจสอบว่า officeCode เป็น null หรือไม่
        if (office.getOfficeCode()==null || officeRepository.existsById(office.getOfficeCode())) {
            // ถ้ามีหรือ officeCode เป็น null ให้โยนข้อผิดพลาด HTTP 400 พร้อม"Office id '%s' already exists"
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,
                   String.format("Office id '%s' already exists",
                           office.getOfficeCode()));
        }
        // ถ้าไม่มี ให้บันทึกและคืนค่าออฟฟิศที่ถูกบันทึก
        return officeRepository.save(office); // คืนค่าออฟฟิศโดยการบันทึก
    }
    public Office updateOffice(Office office) { //เมธอดที่อัปเดตข้อมูลออฟฟิศ

        if (office.getOfficeCode()==null || !officeRepository.existsById(office.getOfficeCode())) {
            // ถ้ามีหรือ officeCode เป็น null ให้โยนข้อผิดพลาด HTTP 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,
                    String.format("Can't update, Office id '%s' does not exists ",
                            office.getOfficeCode()));
        }
        // ถ้าไม่เข้าเงื่อนไขก็ให้บันทึก
        return officeRepository.save(office); // คืนค่าออฟฟิศโดยการบันทึก
    }
    public Office deleteOffice(String officeCode) {
        Office office = getOffice(officeCode);
        if (office.getOfficeCode()==null) {  // เช็คว่า office มีค่าหรือไม่
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,
                    String.format("Can't delete, Office id '%s' does not exists ",
                            officeCode));
        }
        // ลบออฟฟิศจากฐานข้อมูล
        officeRepository.save(office);  // ลบออฟฟิศจากฐานข้อมูล
        return office; // คืนค่าออฟฟิศที่ถูกลบ
    }
}
