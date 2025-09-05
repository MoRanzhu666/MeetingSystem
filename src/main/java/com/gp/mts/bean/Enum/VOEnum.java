package com.gp.mts.bean.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VOEnum {
    ADMIN_LOGIN_VO("loginVO"),
    QUERY_VO("queryVO"),
    REGISTER_VO("registerVO"),
    ;

    private final String voName;
}
