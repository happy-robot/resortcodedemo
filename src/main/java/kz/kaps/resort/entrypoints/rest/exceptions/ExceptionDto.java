package kz.kaps.resort.entrypoints.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@Setter
public class ExceptionDto {

    private String message;
    private String error;
    private String path;

}
