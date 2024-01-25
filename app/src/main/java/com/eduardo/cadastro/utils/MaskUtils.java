package com.eduardo.cadastro.utils;

public class MaskUtils {

    public static String formatCnpj(String cnpj) {
        // Remove caracteres não numéricos do CNPJ
        String digits = cnpj.replaceAll("[^0-9]", "");

        // Aplica a máscara do CNPJ (##.###.###/####-##)
        return String.format("%s.%s.%s/%s-%s",
                digits.substring(0, 2),
                digits.substring(2, 5),
                digits.substring(5, 8),
                digits.substring(8, 12),
                digits.substring(12));
    }

    // Método para definir a máscara do CPF
    public static String formatCpf(String cpf) {
        // Remove caracteres não numéricos do CPF
        String digits = cpf.replaceAll("[^0-9]", "");

        // Aplica a máscara do CPF (###.###.###-##)
        return String.format("%s.%s.%s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 9),
                digits.substring(9));
    }

    public static String unmaskCnpj(String cnpj) {
        // Remove caracteres não numéricos do CNPJ
        return cnpj.replaceAll("[^0-9]", "");
    }

    // Método para verificar se é CPF
    public static boolean isCpf(String documento) {
        return documento != null && documento.length() == 11;
    }
}




