package org.example.deltawebfacade.dto.path.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Сущность папки базы знаний")
public class KnowledgeBaseResponse {
    @Schema(description = "Имя вкладки")
    private String name;
    @Schema(description = "Ссылка на картинку")
    private String imageUrl;
    @Schema(description = "Страница")
    private String page;
    @Schema(description = "Id папки")
    private Long folderId;
}
