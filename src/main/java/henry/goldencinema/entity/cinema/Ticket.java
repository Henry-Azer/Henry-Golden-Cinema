package henry.goldencinema.entity.cinema;

import henry.goldencinema.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Ticket {

    @Id
    private String id;

    @Field
    @DocumentReference
    private User user;

    @Field
    @DocumentReference
    private Movie movie;

    @Field
    @DocumentReference
    private Hall hall;

    @Field
    private LocalDate date;

    @Field
    private LocalTime time;

    @Field
    private Integer seat;

    @Field
    private byte[] image;
}
