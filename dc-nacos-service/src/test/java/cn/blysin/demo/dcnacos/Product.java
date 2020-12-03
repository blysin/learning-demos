package cn.blysin.demo.dcnacos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author daishaokun
 * @date 2020/11/24
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    private String name;
    private String desc;

    List<Detail> details;
}
