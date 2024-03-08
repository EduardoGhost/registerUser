package com.eduardo.cadastro;

import org.junit.Test;
import static org.junit.Assert.*;
import com.eduardo.cadastro.model.database.local.ValidationUtil;

public class ValidationUtilTest {

    //testes de email
    @Test
    public void testValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("test@example.com"));
        assertTrue(ValidationUtil.isValidEmail("user1@gmail.com"));
        assertTrue(ValidationUtil.isValidEmail("test@company.co"));
        assertTrue(ValidationUtil.isValidEmail("user1@company.net"));
        assertTrue(ValidationUtil.isValidEmail("test@organization.org"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(ValidationUtil.isValidEmail("invalid-email"));
        assertFalse(ValidationUtil.isValidEmail("user@com"));
        assertFalse(ValidationUtil.isValidEmail("user@company"));
        assertFalse(ValidationUtil.isValidEmail("user@company."));
    }

    //Testes de nome
    @Test
    public void testIsValidNomeWithValidName() {
        // Arrange
        String nome = "NomeValido";

        // Act
        boolean result = ValidationUtil.isValidNome(nome);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidNomeWithNullName() {
        // Arrange
        String nome = null;

        // Act
        boolean result = ValidationUtil.isValidNome(nome);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidNomeWithLongName() {
        // Arrange
        String nome = "NomeMuitoPequenoNomeMuitoPequeno";

        // Act
        boolean result = ValidationUtil.isValidNome(nome);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidNomeWithEmptyName() {
        // Arrange
        String nome = "";

        // Act
        boolean result = ValidationUtil.isValidNome(nome);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidNomeWithNonLetterCharacters() {
        // Arrange
        String nome = "Ana123#";

        // Act
        boolean result = ValidationUtil.isValidNome(nome);

        // Assert
        assertFalse(result);
    }

}
