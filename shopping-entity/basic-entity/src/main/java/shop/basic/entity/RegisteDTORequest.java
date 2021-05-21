package shop.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteDTORequest implements Serializable {
    private static final long serialVersionUID = 468669030810301206L;
    private String phone;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    /**验证码*/
    private String verificationCode;
}
