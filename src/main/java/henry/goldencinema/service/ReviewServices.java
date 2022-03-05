package henry.goldencinema.service;

import henry.goldencinema.entity.Message;
import henry.goldencinema.entity.Rate;

import java.util.Optional;

public interface ReviewServices {

    Optional<Rate> addRate(Rate rate);

    Optional<Message> addMessage(Message message);
}
