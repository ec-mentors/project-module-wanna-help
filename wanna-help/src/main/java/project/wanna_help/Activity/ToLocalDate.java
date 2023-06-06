package project.wanna_help.Activity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class ToLocalDate implements Converter<String, LocalDate> {


    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }

}
