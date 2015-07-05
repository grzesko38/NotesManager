package pl.arczynskiadam.core.model.converters;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
    public Timestamp convertToDatabaseColumn(LocalDate entityValue) {
		LocalDateTime val = LocalDateTime.of(entityValue, LocalTime.MIDNIGHT);
		return Timestamp.valueOf(val);
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp databaseValue) {
        return LocalDate.from(databaseValue.toLocalDateTime());
    }
}