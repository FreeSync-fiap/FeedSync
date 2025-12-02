package br.com.feedsync.FeedSync.controller.gateway;


import br.com.feedsync.FeedSync.service.ReportGatewayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportGatewayService service;

    public ReportController(ReportGatewayService service) {
        this.service = service;
    }

    @GetMapping
    public String teste () {
        return service.teste();
    }

}
