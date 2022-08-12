package com.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Builder
@AllArgsConstructor
public class LoggingBean {
    private ApiType apiType;
    private String className;
    private String method;
    private Object[] arguments;
    private String[] parameters;
    private long durationMs;
    private String detailMessage;
    private String returnStatusCode;
    private String stackTrace;
    private Object returnValue;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (null != apiType) {
            sb.append(apiType).append("\t=\t");
        }
        sb.append("{");
        sb.append("className =\"")
                .append(className).append("\"")
                .append(" | \tmethod =\"")
                .append(method).append("\"");

        if (null != parameters) {
            sb.append(" | \tparameters =\"")
                    .append(Arrays.toString(parameters)).append("\"")
                    .append(" | \targuments =\"")
                    .append(Arrays.toString(arguments)).append("\"");
        }

        if (null != returnValue) {
            sb.append("| \treturnValue =\"").append(returnValue).append("\"");
        }

        if (StringUtils.hasLength(returnStatusCode)) {
            sb.append(" | \treturnStatusCode =\"").append(returnStatusCode).append("\"");
        }

        sb.append(" | \tdurationMs =\"").append(durationMs).append("\"");

        if (StringUtils.hasLength(stackTrace)) {
            sb.append(" | \tstacktrace =\"")
                    .append(stackTrace).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    @Getter
    public enum ApiType {
        EXTERNAL("EXTERNAL"),
        CONTROLLER("CONTROLLER"),
        SERVICE("SERVICE"),
        REPOSITORY("REPOSITORY"),
        EXCEPTIONHANDLER("EXCEPTIONHANDLER"),
        SCHEDULER("SCHEDULER"),
        ERROR("ERROR");

        private final String type;

        ApiType(String type) {
            this.type = type;
        }
    }

}
