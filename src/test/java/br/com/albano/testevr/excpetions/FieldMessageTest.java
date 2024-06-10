package br.com.albano.testevr.excpetions;

import br.com.albano.testevr.exceptions.FieldMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldMessageTest {

    @Test
    public void testFieldMessageDefaultConstructor() {
        FieldMessage fieldMessage = new FieldMessage();
        Assertions.assertNull(fieldMessage.getFieldName());
        Assertions.assertNull(fieldMessage.getMessage());
    }

    @Test
    public void testFieldMessageParameterizedConstructor() {
        String fieldName = "usuario";
        String message = "usuario eh obrigatorio";
        FieldMessage fieldMessage = new FieldMessage(fieldName, message);

        Assertions.assertEquals(fieldName, fieldMessage.getFieldName());
        Assertions.assertEquals(message, fieldMessage.getMessage());
    }

    @Test
    public void testSetFieldName() {
        FieldMessage fieldMessage = new FieldMessage();
        String fieldName = "senha";
        fieldMessage.setFieldName(fieldName);

        Assertions.assertEquals(fieldName, fieldMessage.getFieldName());
    }

    @Test
    public void testSetMessage() {
        FieldMessage fieldMessage = new FieldMessage();
        String message = "senha eh obrigatoria";
        fieldMessage.setMessage(message);

        Assertions.assertEquals(message, fieldMessage.getMessage());
    }
}
