package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.Message;
import henry.goldencinema.entity.Rate;
import henry.goldencinema.repository.MessageRepository;
import henry.goldencinema.repository.RateRepository;
import henry.goldencinema.service.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServicesImpl implements ReviewServices {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Rate addRate(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }
}
