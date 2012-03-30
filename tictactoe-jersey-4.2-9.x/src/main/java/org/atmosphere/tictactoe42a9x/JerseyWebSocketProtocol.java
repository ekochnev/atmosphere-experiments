package org.atmosphere.tictactoe42a9x;

import com.sun.jersey.api.uri.UriComponent;
import org.atmosphere.cpr.*;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketProcessor;
import org.atmosphere.websocket.WebSocketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JerseyWebSocketProtocol implements WebSocketProtocol, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(JerseyWebSocketProtocol.class);

    private String contentType;
    private String methodType;
    private String delimiter = "@@";
    private boolean destroyable;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(AtmosphereConfig config) {
        String contentType = config.getInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE);
        if (contentType == null) {
            contentType = "text/html";
        }
        this.contentType = contentType;

        String methodType = config.getInitParameter(ApplicationConfig.WEBSOCKET_METHOD);
        if (methodType == null) {
            methodType = "POST";
        }
        this.methodType = methodType;

        String delimiter = config.getInitParameter(ApplicationConfig.WEBSOCKET_PATH_DELIMITER);
        if (delimiter == null) {
            delimiter = "@@";
        }
        this.delimiter = delimiter;

        String s = config.getInitParameter(ApplicationConfig.RECYCLE_ATMOSPHERE_REQUEST_RESPONSE);
        if (s != null && Boolean.valueOf(s)) {
            destroyable = true;
        } else {
            destroyable = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AtmosphereRequest> onMessage(WebSocket webSocket, String d) {
        AtmosphereResourceImpl resource = (AtmosphereResourceImpl) webSocket.resource();
        if (resource == null) {
            logger.error("Invalid state. No AtmosphereResource has been suspended");
            return null;
        }
        String pathInfo = resource.getRequest().getPathInfo();
        if (d.startsWith(delimiter)) {
            String[] token = d.split(delimiter);
            pathInfo = token[1];
            d = token[2];
        }
        Map<String,Object> m = new HashMap<String, Object>();
        m.put(FrameworkConfig.WEBSOCKET_SUBPROTOCOL, FrameworkConfig.SIMPLE_HTTP_OVER_WEBSOCKET);
        // Propagate the original attribute to WebSocket message.
        m.putAll(resource.getRequest().attributes());

        List<AtmosphereRequest> list = new ArrayList<AtmosphereRequest>();

        AtmosphereRequest initialRequest = resource.getRequest();

        String initialServletPath = initialRequest.getServletPath();
        String initialRequestURI = initialRequest.getRequestURI();
        String initialContextPath = initialRequest.getContextPath();

//        UriBuilder absoluteUriBuilder = UriBuilder.fromUri(
//                requestURL.toString());
//
//        final String decodedBasePath = (pathInfo != null)
//                ? initialContextPath + initialServletPath + "/"
//                : initialContextPath + "/";
//
//        final String encodedBasePath = UriComponent.encode(decodedBasePath,
//                UriComponent.Type.PATH);
//
//        final URI baseUri = absoluteUriBuilder.replacePath(encodedBasePath).
//                build();

        UriBuilder pathInfoUriBuilder = UriBuilder.fromUri(pathInfo);
        URI pathInfoUri = pathInfoUriBuilder.build();
        String requestURI = pathInfoUri.getPath();

        // We need to create a new AtmosphereRequest as WebSocket message may arrive concurrently on the same connection.
        list.add(new AtmosphereRequest.Builder()
                .request(initialRequest)
                .method(methodType)
                .contentType(contentType)
                .body(d)
                .attributes(m)
                .pathInfo(pathInfo)
                .requestURI(requestURI)
                .destroyable(destroyable)
                .headers(resource.getRequest().headersMap())
                .build());

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AtmosphereRequest> onMessage(WebSocket webSocket, byte[] d, final int offset, final int length) {
        return onMessage(webSocket, new String(d, offset, length));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(WebSocket webSocket) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClose(WebSocket webSocket) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onError(WebSocket webSocket, WebSocketProcessor.WebSocketException t) {
        logger.warn(t.getMessage() + " Status {} Message {}", t.response().getStatus(), t.response().getStatusMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean inspectResponse() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handleResponse(AtmosphereResponse res, String message) {
        // Should never be called
        return message;
    }

    @Override
    public byte[] handleResponse(AtmosphereResponse res, byte[] message, int offset, int length) {
        // Should never be called
        return message;
    }
}
