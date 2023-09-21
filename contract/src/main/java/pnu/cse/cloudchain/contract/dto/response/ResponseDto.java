package pnu.cse.cloudchain.contract.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto<T> {

    private String result;
    private String message;
    private T data;

    public static <T> ResponseDto<T> error(String message, T data){
        return new ResponseDto<>("FAIL", message , data );
    }

    public static ResponseDto<Void> success(){
        return new ResponseDto<Void>("SUCCESS", "성공!!",null);
    }

    public static ResponseDto<SuccessCodeDto> success(String message, SuccessCodeDto data){
        return new ResponseDto<SuccessCodeDto>("SUCCESS", message, data);
    }

    public static <T> ResponseDto<T> success(String message, T data){
        return new ResponseDto<>("SUCCESS", message ,data);
    }

}
