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
public class LoginDTORequest implements Serializable {
    private static final long serialVersionUID = -8299502012747932421L;
    private String phone;
    private String password;
}
