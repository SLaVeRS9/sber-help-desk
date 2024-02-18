package ru.sberbank.edu.ticketservice.profile;

public enum UserGender {
    MALE("MALE"),
    FEMALE("FEMALE");
    private String displayValue;

    private UserGender(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        if ( displayValue == null ) {
            displayValue = "MALE";
        }
        return displayValue;
    }

}
