package kz.kaps.resort.entrypoints.rest.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    Environment env;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(MaxUploadSizeExceededException e,
                                                               HttpServletRequest req) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String maxUploadedFileSize = env.getProperty("spring.servlet.multipart.max-file-size");
        String message = messageSource.getMessage("error.uploaded.photo.size.is-too-big", new Object[]{maxUploadedFileSize}, currentLocale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionDto.builder()
                        .message(message)
                        .error(message)
                        .path(req.getRequestURI())
                        .build()
        );
    }

}
