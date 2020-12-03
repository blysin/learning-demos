package cn.blysin.demo.dcnacos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daishaokun
 * @date 2020/11/24
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Detail {
    private String title;
    private String desc;
    private Integer price;

}
