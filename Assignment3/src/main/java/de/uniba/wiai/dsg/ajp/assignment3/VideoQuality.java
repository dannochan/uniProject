package de.uniba.wiai.dsg.ajp.assignment3;

public enum VideoQuality {

    QUALITY_4K("4K"),
    QUALITY_HD("HD");

    private String value;

    public String getValue(){
        return value;
    }

    VideoQuality(String value) {
        this.value=value;
    }
}
