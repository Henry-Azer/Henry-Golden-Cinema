package henry.goldencinema.entity.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Integer status;

    private String timestamp;

    private String message;

    private Object body;

}
