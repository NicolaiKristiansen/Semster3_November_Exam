package app.routes;

import app.controller.EntityTemplateController;
import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class EntityTemplateRoutes {
    EntityTemplateController controller = new EntityTemplateController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", controller::create, SecurityRoutes.Role.ADMIN);
            get("/{id}", controller::findByID, SecurityRoutes.Role.USER, SecurityRoutes.Role.ADMIN);
            put("/{id}", controller::update, SecurityRoutes.Role.ADMIN);
            delete("/{id}", controller::delete, SecurityRoutes.Role.ADMIN);
        };
    }
}
