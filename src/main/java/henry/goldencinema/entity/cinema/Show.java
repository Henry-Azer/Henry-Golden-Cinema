package henry.goldencinema.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Show {

    @Id
    private String id;

    @Field
    @DocumentReference
    private Movie movie;

    @Field
    private LocalDate showDate;

    @Field
    private LocalTime showTime;

    @Field
    private Set<Integer> seats = new HashSet<>();

}
