package shop.basic.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TUser implements Serializable {
    private Long id;

    private String userName;

    private String phone;

    private String password;

    private String email;

    private String remark;

    private static final long serialVersionUID = 1L;
}