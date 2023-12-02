package com.project.unicomer.util;

public final class CardUtils {
    private CardUtils() {
    }

    public static String getCardNumber() {

        String cardNumber = "";

        for (int i = 1; i <= 4; i++){

            int numberSection = (int)Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);

            String section = "";

            if(i < 4) {
                section = Integer.toString(numberSection) + " ";
            } else{
                section = Integer.toString(numberSection);
            }

            cardNumber += section;

        }
        return cardNumber;
    }
}
