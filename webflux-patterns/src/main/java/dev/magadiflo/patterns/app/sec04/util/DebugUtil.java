package dev.magadiflo.patterns.app.sec04.util;

import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Slf4j
@RequiredArgsConstructor
@Component
public class DebugUtil {

    private final ObjectMapper objectMapper;

    public void print(OrchestrationRequestContext context) {
        String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(context);
        log.info("{}", json);
    }
}
