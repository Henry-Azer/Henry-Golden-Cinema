package henry.goldencinema.service;

import henry.goldencinema.entity.review.Message;
import henry.goldencinema.entity.review.Rate;

import java.util.Optional;

public interface ReviewServices {

    Optional<Rate> addRate(Rate rate);

    Optional<Message> addMessage(Message message);
}
