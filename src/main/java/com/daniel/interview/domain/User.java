package com.daniel.interview.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Daniel on 2018/9/29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseDomain {
    private String id;
    private String userName;
    private int userAge;
}
