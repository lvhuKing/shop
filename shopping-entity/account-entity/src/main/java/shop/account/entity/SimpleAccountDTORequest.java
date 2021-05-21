package shop.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleAccountDTORequest implements Serializable {
    private static final long serialVersionUID = 2290655544213722456L;
    private String username;
    private Double money;
}
