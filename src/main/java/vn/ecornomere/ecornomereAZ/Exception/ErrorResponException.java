package vn.ecornomere.ecornomereAZ.Exception;



import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponException<T> {
      private int statusCode;
      private String message;

      private String timestamp;
      private String error;

      T data;

}
