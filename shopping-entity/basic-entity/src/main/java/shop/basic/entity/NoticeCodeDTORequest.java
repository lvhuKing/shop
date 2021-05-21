package shop.basic.entity;

import com.common.validation.IsPhone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCodeDTORequest implements Serializable {
    private static final long serialVersionUID = -6880144095403716676L;
    @NotBlank(message = "邮箱不可以为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotBlank(message = "手机号不可以为空")
    @IsPhone(message = "手机号不正确")
    private String phone;
    private String oper;
}
