package br.com.feedsync.FeedSync.dto;

import br.com.feedsync.FeedSync.entity.enums.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record UserResponse(

        @Schema(description = "ID do usuário", example = "84980e39-bdfa-4b9b-b50a-bed439bcbcc4", required = true) String id,

        @Schema(description = "Nome do usuário", example = "João da Silva", required = true)
        String name,

        @Schema(description = "Email do usuário", example = "joao@email.com", required = true)
        String email,

        @Schema(description = "Função/Perfil do usuário", example = "ADMIN", required = true)
        Profile profile,

        @Schema(description = "Data de criação do usuário", required = true)
        Date createdAt,

        @Schema(description = "Data da última atualização do usuário", required = true)
        Date lastUpdatedAt,

        @Schema(description = "Indica se o usuário está ativo", required = true)
        Boolean active,

        @Schema(description = "Lista de IDs dos cursos em que o aluno está matriculado", required = true)
        List<String> enrolledCourses
) {
}
