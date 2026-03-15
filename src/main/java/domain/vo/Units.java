package domain.vo;

public enum Units {
    GRAM("г"),
    MILLILITER("мл"),
    PIECE("шт"),
    ;
    private String value;

    Units(String value) {
        this.value = value;
    }
}
