package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.Message;
import henry.goldencinema.entity.Rate;
import henry.goldencinema.repository.MessageRepository;
import henry.goldencinema.repository.RateRepository;
import henry.goldencinema.service.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServicesImpl implements ReviewServices {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Optional<Rate> addRate(Rate rate) {
        return Optional.of(rateRepository.save(rate));
    }

    @Override
    public Optional<Message> addMessage(Message message) {
        return Optional.of(messageRepository.save(message));
    }
}
