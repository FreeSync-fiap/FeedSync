package br.com.feedsync.FeedSync.dto;

import br.com.feedsync.FeedSync.entity.enums.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "UserRequest", description = "DTO para criação de um usuário")
public record UserRequest(

        @Schema(description = "Nome do usuário", example = "João da Silva", required = true)
        @NotBlank(message = "Name must not be blank")
        String name,

        @Schema(description = "Email do usuário", example = "joao@email.com", required = true)
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email must not be blank")
        String email,

        @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "123456", required = true)
        @Size(min = 6, message = "Password must be at least 6 characters")
        @NotBlank(message = "Password must not be blank")
        String password,

        @Schema(description = "Função/Perfil do usuário", example = "DOCTOR", required = true)
        @NotNull(message = "Profile must not be null") Profile profile
) {}
