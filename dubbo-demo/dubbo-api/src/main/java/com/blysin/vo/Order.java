package com.blysin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daishaokun
 * @date 2020/12/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Integer id;
    private String orderNo;
    private String carNo;
}
