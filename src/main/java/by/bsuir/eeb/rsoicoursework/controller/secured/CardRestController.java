package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.dto.CardDTO;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {

    @Autowired
    private CardManagementService cardManagementService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity getCard(@PathVariable long id) {
        Card card = cardManagementService.getCardById(id);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCard(@RequestBody CardDTO cardDTO) {
        cardDTO.getCard().setUser(userService.findById(cardDTO.getUserId()));
        Card savedCard = cardManagementService.save(cardDTO.getCard());
        return savedCard != null ? ResponseEntity.ok(savedCard) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllCards(@RequestParam long userId) {
        boolean exists = userService.exists(userId);
        return exists ? ResponseEntity.ok(cardManagementService.getCardsByUserId(userId)) : ResponseEntity.notFound().build();
    }
}
