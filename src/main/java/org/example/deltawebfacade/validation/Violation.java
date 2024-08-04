package org.example.deltawebfacade.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Violation {
    private final String timestamp;
    private final String message;

}