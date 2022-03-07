package henry.goldencinema.entity.review;

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
public class Message {

    @Id
    private String id;

    @Field
    private String email;

    @Field
    private String subject;

    @Field
    private String message;
}
