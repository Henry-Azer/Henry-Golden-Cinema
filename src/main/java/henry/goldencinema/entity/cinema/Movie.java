package henry.goldencinema.entity.cinema;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Movie {

    @Id
    private String id;

    @Field
    private Double rate;

    @Field
    private Boolean nowPlaying;

    @Field
    private Integer duration, year;

    @Field
    private String title, description, language, imgURL, ytTrailerId, director;

    @Field
    private Collection<String> actors, writers;

}
