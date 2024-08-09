package org.example.deltawebfacade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Ответ с информацией о пользователе")
public class UserResponse {
    @Schema(description = "Email пользователя")
    private String email;
}
