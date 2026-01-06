package br.com.feedsync.FeedSync.controller.docs;

import br.com.feedsync.FeedSync.dto.FeedbackRequest;
import br.com.feedsync.FeedSync.dto.FeedbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(
        name = "Feedback",
        description = "Endpoints para criação, listagem, consulta e remoção de feedbacks"
)
public interface FeedbackControllerDoc {

    @Operation(
            summary = "Criar feedback",
            description = "Cria um novo feedback associado ao usuário autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback criado com sucesso",
                    content = @Content(schema = @Schema(implementation = FeedbackResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<FeedbackResponse> create(
            @RequestBody FeedbackRequest feedback,
            @AuthenticationPrincipal UserDetails user
    );

    @Operation(
            summary = "Listar feedbacks",
            description = "Lista todos os feedbacks, podendo filtrar por status ativo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de feedbacks retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = FeedbackResponse.class))
            )
    })
    ResponseEntity<List<FeedbackResponse>> list(
            @Parameter(
                    description = "Filtra feedbacks ativos ou inativos",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active
    );

    @Operation(
            summary = "Buscar feedback por ID",
            description = "Retorna um feedback específico pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback encontrado",
                    content = @Content(schema = @Schema(implementation = FeedbackResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    ResponseEntity<FeedbackResponse> findFeedbackById(
            @Parameter(
                    description = "ID do feedback",
                    example = "a1b2c3d4"
            )
            @PathVariable String id
    );

    @Operation(
            summary = "Excluir feedback",
            description = "Remove um feedback pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    ResponseEntity<Void> deleteFeedbackById(
            @Parameter(
                    description = "ID do feedback",
                    example = "a1b2c3d4"
            )
            @PathVariable String id
    );
}
