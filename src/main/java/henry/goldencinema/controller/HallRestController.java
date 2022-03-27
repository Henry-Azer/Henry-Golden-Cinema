package henry.goldencinema.controller;


import henry.goldencinema.entity.cinema.Hall;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.service.HallServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/hall")
public class HallRestController {

    @Autowired
    private HallServices hallServices;

    @PostMapping
    public ResponseEntity<?> addHall(@RequestBody Hall hall) {
        Optional<Hall> existedHall = hallServices.getHallByName(hall.getName());

        if (existedHall.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Hall already exist for Name: " + hall.getName(), ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Hall Added successfully", hallServices.addHall(hall)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHall(@RequestBody Hall hall) {
        Optional<Hall> existedHall = hallServices.getHallByName(hall.getId());

        if (existedHall.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Hall not found", hall));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Hall updated successfully", hallServices.updateHall(hall)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable String id) {
        Optional<Hall> existedHall = hallServices.getHallByName(id);

        if (existedHall.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "Hall not found for id: " + id, ""));

        hallServices.deleteHallById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "Hall deleted successfully", ""));
    }

}
