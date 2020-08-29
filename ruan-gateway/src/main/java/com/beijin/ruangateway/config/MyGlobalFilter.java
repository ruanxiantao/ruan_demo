package com.beijin.ruangateway.config;

import com.hilife.internal.common.util.JsonUtil;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ruanxiantao
 * @Description:
 * @Date: 2020/8/1 16:15
 */
//@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        URI uri = serverHttpRequest.getURI();
        String cachedRequestBodyObject = exchange.getAttribute("cachedRequestBodyObject");
//        Request payload = JsonUtil.readValue(cachedRequestBodyObject, Request.class);
        Map<String, String> map = new HashMap<>();
        map.put("name","cc");
        map.put("sex","cc");
        String param = JsonUtil.toJSon(map);
//        Map<String, Object> attributes = exchange.getAttributes();
//        attributes.put("cachedRequestBodyObject",param);
//                if (verify) {
        //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
        ServerHttpRequest request = serverHttpRequest.mutate()
                .uri(uri)
                .build();
        DataBuffer bodyDataBuffer = stringBuffer(param != null ? param : "");
//                    DataBuffer bodyDataBuffer = stringBuffer(cachedRequestBodyObject != null ? cachedRequestBodyObject : "");
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

        request = new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };

        ServerWebExchange webExchange = exchange.mutate()
                .request(request)
                .response(exchange.getResponse())
                .build();
//        Map<String, Object> attributes = exchange.getAttributes();
//        attributes.put("cachedRequestBodyObject",param);
        return chain.filter(webExchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
