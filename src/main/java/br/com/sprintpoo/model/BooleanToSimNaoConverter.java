package br.com.sprintpoo.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToSimNaoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return "N";
        }
        // Traduz true -> 'S' e false -> 'N' para satisfazer o Oracle da Sprint 3
        return attribute ? "S" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        // Converte 'S' do banco de volta para true no Java
        return "S".equalsIgnoreCase(dbData);
    }
}