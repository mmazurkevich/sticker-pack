package org.sticker.pack.model.converter;

import org.sticker.pack.model.AuthType;

import javax.persistence.AttributeConverter;

import static org.sticker.pack.model.AuthType.FACEBOOK;
import static org.sticker.pack.model.AuthType.GOOGLE;

/**
 * Created by Mikhail on 01.07.2017.
 */
public class AuthTypeConverter implements AttributeConverter<AuthType, String> {
    @Override
    public String convertToDatabaseColumn(AuthType authType) {
        switch (authType) {
            case GOOGLE:
                return "google";
            case FACEBOOK:
                return "facebook";
            case DEFAULT:
                return "default";
            default:
                throw new IllegalArgumentException("Unknown" + authType);
        }
    }

    @Override
    public AuthType convertToEntityAttribute(String authType) {
        switch (authType) {
            case "google":
                return GOOGLE;
            case "facebook":
                return FACEBOOK;
            case "default":
                return FACEBOOK;
            default:
                throw new IllegalArgumentException("Unknown" + authType);
        }
    }
}