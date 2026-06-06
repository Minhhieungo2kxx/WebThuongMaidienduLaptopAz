package vn.ecornomere.ecornomereAZ.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private String error;
    private T data;

}
