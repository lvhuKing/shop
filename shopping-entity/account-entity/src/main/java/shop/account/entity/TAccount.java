package shop.account.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TAccount implements Serializable {
    private Long id;

    /**
    * 余额
    */
    private Double accountValue;

    private static final long serialVersionUID = 1L;
}