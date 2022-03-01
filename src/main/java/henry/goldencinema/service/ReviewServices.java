package henry.goldencinema.service;

import henry.goldencinema.entity.Message;
import henry.goldencinema.entity.Rate;

public interface ReviewServices {

    Rate addRate(Rate rate);

    Message addMessage(Message message);
}
