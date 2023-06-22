package org.enroll.exception;

public class ReadExcelException extends RuntimeException {//读取Excel遇到错误时抛出异常

    public ReadExcelException (String message) {
        super(message);
    }
}
