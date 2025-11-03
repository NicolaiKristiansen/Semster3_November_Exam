package app;

import app.config.ApplicationConfig;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello SP2");


        ApplicationConfig
                .getInstance()
                .initiateServer()
//            .checkSecurityRoles() // check for role when route is called
//            .setRoute(SecurityRoutes.getSecurityRoutes())
//            .setRoute(SecurityRoutes.getSecuredRoutes())
                .setRoute(()->{
                    path("/index",()->{
                        get("/",ctx->ctx.render("index.html"));
                    });
                })
                .startServer(7007)
                .setCORS()
                .setGeneralExceptionHandling();
    }

}
