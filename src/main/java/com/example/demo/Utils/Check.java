package com.example.demo.Utils;

public class Check {
    public static boolean checkCPF(String cpf) {
        if (cpf == null || cpf.length() != 11)
            return false;

        char str_digit;
        int first_digit;
        int second_digit;
        int rem;
        int sum;

        sum = 0;

        for (int i = 0; i < 9; i++)
            sum += (10 - i) * Character.getNumericValue(cpf.charAt(i));

        rem = sum % 11;

        switch (rem) {
            case 0:
            case 1:
                first_digit = 0;
                break;
            default:
                first_digit = 11 - rem;
                break;
        }

        str_digit = Character.forDigit(first_digit, 10);

        if (Character.compare(cpf.charAt(9), str_digit) != 0)
            return false;
        sum = 0;

        for (int i = 0; i < 10; i++)
            sum += (11-i) * Character.getNumericValue(cpf.charAt(i));

        rem = sum % 11;

        switch (rem) {
            case 0:
            case 1:
                second_digit = 0;
                break;
            default:
                second_digit = 11 - rem;
        }

        str_digit = Character.forDigit(second_digit, 10);

        if (Character.compare(cpf.charAt(10), str_digit) != 0)
            return false;

        return true;
    }
}
