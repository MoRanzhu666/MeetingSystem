package com.gp.mts.bean.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoriesEnum {
    RELATION_ALL("relation", ""),
    RELATION_SPOUSE("relation", "配偶"),
    RELATION_PARENTS("relation", "父母"),
    RELATION_CHILDREN("relation", "子女"),
    RELATION_SIBLINGS("relation", "兄弟姐妹"),
    RELATION_OTHER_RELATIVES("relation", "其他亲属"),
    RELATION_FRIEND("relation", "朋友"),

    TIME_SCOPE_ALL("time_scope", ""),
    TIME_SCOPE_AM1("time_scope", "09:30-10:00"),
    TIME_SCOPE_AM2("time_scope", "10:15-10:45"),
    TIME_SCOPE_PM1("time_scope", "14:00-14:30"),
    TIME_SCOPE_PM2("time_scope", "15:00-15:30"),
    TIME_SCOPE_PM3("time_scope", "16:00-16:30"),


        ;
    private final String category;
    private final String describeInfo;

}
