package henry.goldencinema.service;

import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.cinema.Show;

import java.util.Collection;
import java.util.Optional;

public interface HallServices {

    Optional<Collection<Hall>> getAllHalls();

    Optional<Collection<Hall>> getAllShowHalls();

    Optional<Collection<Hall>> getAllIdleHalls();

    Optional<Hall> getHallByName(String name);

    Optional<Hall> getHallByShow(Show show);

    Optional<Hall> getHallById(String id);

    Optional<Hall> addHall(Hall hall);

    Optional<Hall> updateHall(Hall hall);

    void deleteHallById(String id);

}
