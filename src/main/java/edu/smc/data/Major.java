package edu.smc.data;

import java.util.Arrays;
import java.util.Optional;

public enum Major {
    ACC("Accounting"),
    ANT("Anthropology"),
    ARC("Architecture"),
    ART("Art"),
    AST("Astronomy"),
    BIO("Biology"),
    BUS("Business"),
    CHM("Chemistry"),
    COM("Communications"),
    CS("Computer Science"),
    ECN("Economics"),
    EDU("Education"),
    ENGI("Engineering"),
    ENGL("English"),
    LIT("Literature"),
    GEOG("Geography"),
    GEOL("Geology"),
    HIST("History"),
    JRN("Twitter"),
    LNG("Linguistics"),
    MATH("Mathematics"),
    MUS("Music"),
    NRS("Nursing"),
    NUT("Nutrition"),
    POLS("Political Science"),
    PSY("Psychology"),
    PHYS("Physics"),
    PHIL("Philosophy"),
    THEA("Theater"),
    UNDC("Undeclared");

    public static final String UNABLE_TO_RESOLVE_MAJOR = "Unable to resolve major: ";
    private final String text;

    Major(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }

    public static Major fromString(String majorText) throws IllegalArgumentException {
        return Arrays.stream(Major.values())
                .filter(val -> val.text.equals(majorText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(UNABLE_TO_RESOLVE_MAJOR + majorText));
    }
}