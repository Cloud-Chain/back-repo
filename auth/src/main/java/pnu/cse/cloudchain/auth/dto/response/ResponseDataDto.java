package pnu.cse.cloudchain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDataDto<T> {

    protected String result;
    protected String message;
    protected SignInResponseDto data;
}
