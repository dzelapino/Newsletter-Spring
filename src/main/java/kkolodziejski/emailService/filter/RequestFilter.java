package kkolodziejski.emailService.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
@WebFilter(filterName = "RequestFilter", urlPatterns = "/api/*")
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Date requestDate = new Date();
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        FileWriter writer = new FileWriter("requestLogs.log", true);
        writer.append("DATE = ")
                .append(String.valueOf(requestDate))
                .append(" METHOD = ")
                .append(request.getMethod())
                .append(" REQUESTURI = ")
                .append(request.getRequestURI())
                .append(" REQUEST BODY = ")
                .append(requestBody)
                .append(" RESPONSE CODE = ")
                .append(String.valueOf(response.getStatus()))
                .append(" RESPONSE BODY = ")
                .append(responseBody)
                .append(" \n");
        writer.close();

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
