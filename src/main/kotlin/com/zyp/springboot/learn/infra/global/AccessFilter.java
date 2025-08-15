package com.zyp.springboot.learn.infra.global;

import com.zyp.springboot.learn.util.StrUtils;
import com.zyp.springboot.learn.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

// 借鉴了AbstractRequestLoggingFilter
@Slf4j
public class AccessFilter extends OncePerRequestFilter {
    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 1024;
    private static final int DEFAULT_MAX_RESPONSE_LENGTH = 1024;

    private int maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH;
    private int maxResponseLength = DEFAULT_MAX_RESPONSE_LENGTH;
    private boolean includeHeaders = false;
    private boolean includePayload = false;
    private boolean includeResponse = false;
    private boolean includeQueryString = false;
    private final Set<String> maskHeaders = new HashSet<>();
    private final HandlerExceptionResolver resolver;

    public AccessFilter(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        var startTime = System.nanoTime();
        MDC.put("traceId", WebUtils.getRequestId(req)); // set traceId to MDC, you can see the usage in the logback_spring.xml(search traceId)
        req = tryWrapReq(req);
        resp = tryWrapResp(resp);
        log.info(createIncomeMsg(req));

        try {
            filterChain.doFilter(req, resp);
        } catch (Throwable throwable) {
            // 错误处理统一委托给HandlerExceptionResolver
            if (throwable instanceof Exception ex) {
                resolver.resolveException(req, resp, null, ex);
            } else {
                resolver.resolveException(req, resp, null, new Exception(throwable));
            }
        } finally {
            log.info(createOutcomeMsg(req, resp, startTime));
        }
    }

    private String createIncomeMsg(HttpServletRequest request) {
        StringBuilder msg = new StringBuilder("http_start|").append(request.getRequestURI());
        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append("|query_string=?").append(queryString);
            }
        }
        if (isIncludeHeaders()) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            if (getMaskHeaders().size() > 0) {
                Enumeration<String> names = request.getHeaderNames();
                while (names.hasMoreElements()) {
                    String header = names.nextElement();
                    if (getMaskHeaders().contains(header)) {
                        headers.set(header, "******");
                    }
                }
            }
            msg.append("|headers=").append(headers);
        }
        return msg.toString();
    }

    private String createOutcomeMsg(HttpServletRequest req, HttpServletResponse resp, long startTime) throws IOException {
        var elapsedInMillis = (System.nanoTime() - startTime) / 1_000_000;
        StringBuilder msg = new StringBuilder(String.format("http_end|%s|%d|%d", req.getRequestURI(), resp.getStatus(), elapsedInMillis));
        if (isIncludePayload()) {
            var reqBody = WebUtils.getMessagePayload(req, getMaxPayloadLength());
            msg.append("|req=").append(StrUtils.removeNewLines(reqBody));
        }
        if (isIncludeResponse()) {
            var respBody = getResponseBody(req, resp);
            msg.append("|resp=").append(StrUtils.removeNewLines(respBody));
        }
        return msg.toString();
    }

    private HttpServletRequest tryWrapReq(HttpServletRequest req) {
        if (!isIncludePayload()) {
            return req;
        }
        if (req.getContentLength() > getMaxPayloadLength()) {
            return req;
        }
        boolean isFirstRequest = !isAsyncDispatch(req);
        if (isFirstRequest && !(req instanceof ContentCachingRequestWrapper)) {
            req = new ContentCachingRequestWrapper(req);
        }
        return req;
    }

    private HttpServletResponse tryWrapResp(HttpServletResponse resp) {
        if (!isIncludeResponse()) {
            return resp;
        }
        return new ContentCachingResponseWrapper(resp);
    }

    private String getResponseBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!(response instanceof ContentCachingResponseWrapper responseWrapper)) {
            return "-";
        }
        // 一定要在copyBodyToResponse调用之前
        var body = responseWrapper.getContentAsByteArray();
        if (request.isAsyncStarted()) {
            request.getAsyncContext().addListener(new AsyncListener() {
                public void onComplete(AsyncEvent asyncEvent) throws IOException {
                    // 不管有没有读body，都要调用这个方法，否则输出流里不会有数据
                    responseWrapper.copyBodyToResponse();
                }

                public void onTimeout(AsyncEvent asyncEvent) {
                }

                public void onError(AsyncEvent asyncEvent) {
                }

                public void onStartAsync(AsyncEvent asyncEvent) {
                }
            });
        } else {
            // 不管有没有读body，都要调用这个方法，否则输出流里不会有数据
            responseWrapper.copyBodyToResponse();
        }
        if (body.length > getMaxResponseLength()) {
            return "-";
        }
        return new String(body, responseWrapper.getCharacterEncoding());
    }

    public void setMaxPayloadLength(int maxPayloadLength) {
        this.maxPayloadLength = maxPayloadLength;
    }

    public int getMaxPayloadLength() {
        return this.maxPayloadLength;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    public boolean isIncludePayload() {
        return includePayload;
    }

    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    public boolean isIncludeResponse() {
        return includeResponse;
    }

    public void setIncludeResponse(boolean includeResponse) {
        this.includeResponse = includeResponse;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    public int getMaxResponseLength() {
        return maxResponseLength;
    }

    public void setMaxResponseLength(int maxResponseLength) {
        this.maxResponseLength = maxResponseLength;
    }

    public Set<String> getMaskHeaders() {
        return maskHeaders;
    }

    public void setMaskHeaders(Set<String> maskHeaders) {
        // 全转为小写，保证大小写无关
        maskHeaders.forEach(x -> this.maskHeaders.add(x.toLowerCase()));
    }
}

