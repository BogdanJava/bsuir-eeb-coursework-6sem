package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.model.Phone;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.PhoneDTO;
import by.bsuir.eeb.rsoicoursework.service.PhoneService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phones")
public class PhoneRestController {

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity getPhone(@PathVariable long id) {
        Phone phone = phoneService.getById(id);
        return phone != null ? ResponseEntity.ok(phone) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPhone(@RequestBody PhoneDTO phoneDTO) {
        Phone savedPhone = phoneService.save(phoneDTO.getPhone(), phoneDTO.getUserId());
        return savedPhone != null ? ResponseEntity.ok(savedPhone) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity editPhone(@RequestBody PhoneDTO phoneDTO) {
        Phone fromDTO = phoneDTO.getPhone();
        fromDTO.setUser(new User(phoneDTO.getUserId()));
        Phone updatedPhone = phoneService.update(fromDTO);
        return updatedPhone != null ? ResponseEntity.ok(updatedPhone) :
                ResponseEntity.badRequest().body(ImmutableMap.of("error", "No phone with such id found"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deletePhone(@PathVariable long id) {
        return phoneService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
