package br.com.feedsync.FeedSync.controller.docs;

import br.com.feedsync.FeedSync.dto.UserRequest;
import br.com.feedsync.FeedSync.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(
        name = "Users",
        description = "Endpoints para gerenciamento de usuários"
)
public interface UserControllerDoc {

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<UserResponse> createUser(
            @Validated
            @RequestBody UserRequest user
    );

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário com base no seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UserResponse> findUserById(
            @Parameter(
                    description = "ID do usuário",
                    example = "a1b2c3d4"
            )
            @PathVariable String id
    );

    @Operation(
            summary = "Listar usuários",
            description = "Lista todos os usuários, podendo filtrar por status ativo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            )
    })
    ResponseEntity<List<UserResponse>> findAllUsers(
            @Parameter(
                    description = "Filtra usuários ativos ou inativos",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active
    );

    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> deleteUserById(
            @Parameter(
                    description = "ID do usuário",
                    example = "a1b2c3d4"
            )
            @PathVariable String id
    );

    @Operation(
            summary = "Matricular usuário em curso",
            description = "Realiza a matrícula de um usuário em um curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário matriculado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou curso não encontrado")
    })
    ResponseEntity<Void> enrollUser(
            @Parameter(
                    description = "ID do usuário",
                    example = "user123"
            )
            @PathVariable String userId,

            @Parameter(
                    description = "ID do curso",
                    example = "course456"
            )
            @PathVariable String courseId
    );
}
