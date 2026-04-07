package com.dog_feliz.user_service.shared.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "traceId";
    private static final String START_TIME = "startTime";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        MDC.put(TRACE_ID, traceId);
        MDC.put(START_TIME, String.valueOf(startTime));

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {

        try {
            long startTime = Long.parseLong(MDC.get(START_TIME));
            long duration = System.currentTimeMillis() - startTime;

            String traceId = MDC.get(TRACE_ID);

            String method = request.getMethod();
            String uri = request.getRequestURI();
            int status = response.getStatus();

            if (ex != null) {
                log.error(
                        "[HTTP_REQUEST] traceId={} method={} uri={} status={} durationMs={} error={}",
                        traceId, method, uri, status, duration, ex.getMessage()
                );
            } else {
                log.info(
                        "[HTTP_REQUEST] traceId={} method={} uri={} status={} durationMs={}",
                        traceId, method, uri, status, duration
                );
            }

        } finally {
            MDC.clear();
        }
    }
}