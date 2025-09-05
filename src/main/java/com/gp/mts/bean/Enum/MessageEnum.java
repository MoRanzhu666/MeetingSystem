package com.gp.mts.bean.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {
    ERROR("errorMsg"),
    SUCCESS("successMsg"),
    Global("globalMsg"),
    IS_SUCCESS("isSuccessMsg"),
    ;

    private final String messageName;
}
