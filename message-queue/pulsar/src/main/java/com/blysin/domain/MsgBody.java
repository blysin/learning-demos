package com.blysin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daishaokun
 * @date 2021/1/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgBody {
    private String name;
    private Integer age;
}
