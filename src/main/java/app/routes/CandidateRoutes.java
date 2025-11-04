package app.routes;

import app.controller.CandidateController;
import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class CandidateRoutes {
    CandidateController controller = new CandidateController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", controller::create, SecurityRoutes.Role.ADMIN);
            get("/{id}", controller::findByID, SecurityRoutes.Role.USER, SecurityRoutes.Role.ADMIN);
            get("/", controller::getAll);
            get("/reports/candidates/top-by-popularity", controller::getTopByPopularity);
            put("/{id}", controller::update, SecurityRoutes.Role.ADMIN);
            delete("/{id}", controller::delete, SecurityRoutes.Role.ADMIN);
            put("/{id}/skills/{skillId}", controller::linkCandidateToSkill, SecurityRoutes.Role.ADMIN);
        };
    }
}
