package henry.goldencinema.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Hall {

    @Id
    private String id;

    @Field
    @Indexed(unique = true)
    private String name;

    @Field
    private Integer capacity;

    @Field
    @DocumentReference
    private Collection<Show> shows;

}