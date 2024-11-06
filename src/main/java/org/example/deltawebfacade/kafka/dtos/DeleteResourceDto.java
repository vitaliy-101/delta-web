package org.example.deltawebfacade.kafka.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResourceDto {
    private String type;
    private Long id;
}
