import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Pinnsvin
 * @description: 业务性错误信息
 */
@Data
@ApiModel(value = "业务性错误信息")
@NoArgsConstructor
@AllArgsConstructor
public class CommonBMessage {

    private String code;
    private String message;
    private int level;
}
