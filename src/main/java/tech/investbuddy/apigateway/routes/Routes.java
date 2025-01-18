package tech.investbuddy.apigateway.routes;

import org.apache.catalina.filters.RequestFilter;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("/api/v1/users/**"), HandlerFunctions.http("http://user-service:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userProfileServiceRoute(){
        return GatewayRouterFunctions.route("user-profile-service")
                .route(RequestPredicates.path("/api/v1/profiles/**"), HandlerFunctions.http("http://user-profile-service:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> kycServiceRoute(){
        return GatewayRouterFunctions.route("kyc-service")
                .route(RequestPredicates.path("/api/v1/kyc/**"), HandlerFunctions.http("http://kyc-service:8084"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> PredictionServiceRoute() {
        return GatewayRouterFunctions.route("prediction-service")
                .route(RequestPredicates.path("/api/v1/prediction/**"), HandlerFunctions.http("http://prediction-service:8085"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> discoveryServerRoute() {
        return GatewayRouterFunctions.route("discovery-server")
                .route(RequestPredicates.path("/"), HandlerFunctions.http("http://discovery-server:8761"))
                .build();
    }
}
