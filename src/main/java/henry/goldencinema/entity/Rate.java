package henry.goldencinema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Rate {

    @Id
    private String id;

    @Field
    private Boolean firstTime;

    @Field
    private Integer rate;

    @Field
    private String review;
}
