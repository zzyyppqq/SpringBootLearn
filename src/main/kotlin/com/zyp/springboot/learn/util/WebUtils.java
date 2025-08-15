package com.zyp.springboot.learn.util;

import com.zyp.springboot.learn.constant.HttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class WebUtils {

    public static String getMessagePayload(HttpServletRequest request, int maxPayloadLength) {
        ContentCachingRequestWrapper wrapper =
                org.springframework.web.util.WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper == null) {
            return "";
        }
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            int length = Math.min(buf.length, maxPayloadLength);
            if (length < 0) {
                length = buf.length;
            }
            try {
                return new String(buf, 0, length, wrapper.getCharacterEncoding());
            } catch (UnsupportedEncodingException ex) {
                return "[unknown]";
            }
        }
        return "";
    }

    public static String getRequestId(HttpServletRequest request) {
        // 外部传入的优先，这样就可以跟外部服务串起来
        String requestId = request.getHeader(HttpHeader.X_REQUEST_ID);
        // 调用方没有设置，就直接用UUID作为trace id
        if (StrUtils.hasNoLength(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        return requestId;
    }
}
