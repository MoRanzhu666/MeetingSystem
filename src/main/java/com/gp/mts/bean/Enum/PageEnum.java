package com.gp.mts.bean.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PageEnum {
    REGISTER("register"),
    QUERY("query"),
    ADMIN_LOGIN("admin/login"),
    ADMIN_DASHBOARD("admin/dashboard"),
    ;

    private final String pageName;
}
