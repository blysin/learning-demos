package blysin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2021/4/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarCome {
    private Integer id;
    private String billId;
    private String carNo;
    private Date carTime;
    private String lotCode;
}
