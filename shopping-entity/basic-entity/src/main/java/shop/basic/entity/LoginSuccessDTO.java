package shop.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDTO implements Serializable {
    private static final long serialVersionUID = -1277927743798414542L;
    private Long userId;
    private String username;
    private String phone;
    private String onlineIP;
    private Date loginTime;
    private String token;
}
