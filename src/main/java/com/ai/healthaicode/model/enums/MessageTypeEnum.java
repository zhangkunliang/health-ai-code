package com.ai.healthaicode.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息类型枚举
 *
 * @author <a href="https://github.com/zhangkunliang">张坤梁</a>
 */
public enum MessageTypeEnum {

    /**
     * 用户消息
     */
    USER("user", "用户消息"),

    /**
     * AI消息
     */
    AI("ai", "AI消息"),

    /**
     * 错误消息
     */
    ERROR("error", "错误消息");

    private final String value;

    private final String text;

    MessageTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static MessageTypeEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (MessageTypeEnum anEnum : MessageTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}