package com.fiap.tc.util;


public class CpfGenerator {

    private CpfGenerator() {
        //Do nothing
    }

    public static String execute(Boolean formatacao) {
        String iniciais = "";
        Integer numero;
        for (int i = 0; i < 9; i++) {
            numero = (int) (Math.random() * 10);
            iniciais += numero.toString();
        }
        String cpf = iniciais + calculateDigitVerified(iniciais);
        //Se for true, formata o valor
        if (formatacao) {
            cpf = format(cpf);
        }
        return cpf;
    }

    public static Boolean validate(String cpf) {
        if (cpf.length() != 11)
            return false;
        String numDig = cpf.substring(0, 9);
        return calculateDigitVerified(numDig).equals(cpf.substring(9, 11));
    }

    public static String format(String cpf) {
        String bloco1 = cpf.substring(0, 3);
        String bloco2 = cpf.substring(3, 6);
        String bloco3 = cpf.substring(6, 9);
        String bloco4 = cpf.substring(9, 11);

        return String.format("%s.%s.%s-%s", bloco1, bloco2, bloco3, bloco4);
    }

    private static String calculateDigitVerified(String num) {

        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = 0;
        else
            primDig = 11 - (soma % 11);

        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        soma += primDig * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = 0;
        else
            segDig = 11 - (soma % 11);

        return primDig.toString() + segDig.toString();
    }

}
