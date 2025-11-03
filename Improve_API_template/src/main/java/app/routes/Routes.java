package app.routes;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;


public class Routes {
    EntityTemplateRoutes entityTemplateRoutes = new EntityTemplateRoutes();

    public EndpointGroup getRoutes(){
        return ()->{
            get("/", ctx -> ctx.result("Welcome to mock exam"));
            path("/EntityTemplate", entityTemplateRoutes.getRoutes());
        };
    }
}
