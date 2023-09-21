package pnu.cse.cloudchain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pnu.cse.cloudchain.review.dto.ReviewDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Response<T> {

    private String result;
    private String message;
    private T data;

    public static <T> Response<T> error(String message, T data){
        return new Response<>("FAIL", message , data );
    }

    public static Response<Void> success(){
        return new Response<Void>("SUCCESS", "성공!!",null);
    }

    public static Response<SuccessCodeDto> success(String message, SuccessCodeDto data){
        return new Response<SuccessCodeDto>("SUCCESS", message, data);
    }

    public static Response<List<ReviewDto>> success(String message, List<ReviewDto> data){
        return new Response<>("SUCCESS", message ,data);
    }

}
