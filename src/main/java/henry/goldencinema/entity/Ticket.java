package henry.goldencinema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Ticket {

    @Id
    private String id;

    @Field
    private Date showDateAndTime;

    @Field
    private String hall;

    @Field
    private Integer seat;

    @Field
    @DocumentReference
    private String movieTitle;

    @Field
    @DocumentReference
    private User user;

}
