package org.example.deltawebfacade.dto.file;

import lombok.*;
import org.springframework.http.MediaType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseUponReceipt extends FileBaseDto {
    private Long size;
}
