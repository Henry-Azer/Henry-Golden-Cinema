package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Show;
import henry.goldencinema.repository.HallRepository;
import henry.goldencinema.service.HallServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HallServicesImpl implements HallServices {

    @Autowired
    private HallRepository hallRepository;

    @Override
    public Optional<Collection<Hall>> getAllHalls() {
        return Optional.ofNullable(hallRepository.findAll());
    }

    @Override
    public Optional<Collection<Hall>> getAllShowHalls() {
        Collection<Hall> halls = hallRepository.findAll();
        Predicate<Hall> showHallsPredicate = hall -> hall.getShows() != null;

        if (!halls.isEmpty())
            return Optional.of(halls.stream().filter(showHallsPredicate).collect(Collectors.toList()));

        return Optional.empty();
    }

    @Override
    public Optional<Collection<Hall>> getAllIdleHalls() {
        Collection<Hall> halls = hallRepository.findAll();
        Predicate<Hall> idleHallsPredicate = hall -> hall.getShows() == null;

        if (!halls.isEmpty())
            return Optional.of(halls.stream().filter(idleHallsPredicate).collect(Collectors.toList()));

        return Optional.empty();
    }

    @Override
    public Optional<Hall> getHallById(String id) {
        return Optional.ofNullable(hallRepository.findHallById(id));
    }

    @Override
    public Optional<Hall> getHallByName(String name) {
        return Optional.ofNullable(hallRepository.findHallByName(name));
    }

    @Override
    public Optional<Hall> getHallByShow(Show show) {
        Collection<Hall> halls = hallRepository.findAll();

        for (var hall : halls) {
            for (var tempShow : hall.getShows()) {
                if (Objects.equals(tempShow.getId(), show.getId()))
                    return Optional.of(hall);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Hall> addHall(Hall hall) {
        return Optional.of(hallRepository.save(hall));
    }

    @Override
    public Optional<Hall> updateHall(Hall hall) {
        return Optional.of(hallRepository.save(hall));
    }

    @Override
    public void deleteHallById(String id) {
        hallRepository.deleteById(id);
    }
}
