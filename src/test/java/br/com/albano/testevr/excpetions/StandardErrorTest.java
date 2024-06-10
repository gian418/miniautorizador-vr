package br.com.albano.testevr.excpetions;

import br.com.albano.testevr.exceptions.StandardError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StandardErrorTest {

    @Test
    public void testStandardErrorConstructor() {
        Integer status = 404;
        String msg = "Not Found";
        Long timestamp = System.currentTimeMillis();

        StandardError standardError = new StandardError(status, msg, timestamp);

        Assertions.assertEquals(status, standardError.getStatus());
        Assertions.assertEquals(msg, standardError.getMsg());
        Assertions.assertEquals(timestamp, standardError.getTimestamp());
    }

    @Test
    public void testSetStatus() {
        StandardError standardError = new StandardError(500, "Internal Server Error", System.currentTimeMillis());
        Integer newStatus = 400;
        standardError.setStatus(newStatus);

        Assertions.assertEquals(newStatus, standardError.getStatus());
    }

    @Test
    public void testSetMsg() {
        StandardError standardError = new StandardError(500, "Internal Server Error", System.currentTimeMillis());
        String newMsg = "Bad Request";
        standardError.setMsg(newMsg);

        Assertions.assertEquals(newMsg, standardError.getMsg());
    }

    @Test
    public void testSetTimestamp() {
        StandardError standardError = new StandardError(500, "Internal Server Error", System.currentTimeMillis());
        Long newTimestamp = System.currentTimeMillis();
        standardError.setTimestamp(newTimestamp);

        Assertions.assertEquals(newTimestamp, standardError.getTimestamp());
    }
}