package pl.arczynskiadam.core.model.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
		return Optional.ofNullable(entityValue).map(t -> Timestamp.valueOf(t)).orElse(null);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
        return Optional.ofNullable(databaseValue).map(t -> t.toLocalDateTime()).orElse(null);
    }
}