package basePackage.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class LoginDTO {

    private String email;

    private String senha;
}
