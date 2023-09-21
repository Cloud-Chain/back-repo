package pnu.cse.cloudchain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseProfileDto {
    protected String result;
    protected String message;
    protected ProfileDto data;
}

