package henry.goldencinema.controller;

import henry.goldencinema.entity.Message;
import henry.goldencinema.entity.Rate;
import henry.goldencinema.service.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewServices reviewServices;

    @PostMapping("/rate")
    public ResponseEntity<Rate> addRate(@RequestBody Rate rate) throws URISyntaxException {
        Rate savedRate = reviewServices.addRate(rate);

        return ResponseEntity.created(new URI("/rate" + savedRate.getId())).body(rate);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) throws URISyntaxException {
        Message savedMessage = reviewServices.addMessage(message);

        return ResponseEntity.created(new URI("/message" + savedMessage.getId())).body(message);
    }
}

