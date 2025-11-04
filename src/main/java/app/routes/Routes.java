package app.routes;

import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;


public class Routes {
    SkillRoutes skillRoutes = new SkillRoutes();
    CandidateRoutes candidateRoutes = new CandidateRoutes();
    SecurityRoutes securityRoutes = new SecurityRoutes();

    public EndpointGroup getRoutes(){
        return ()->{
            get("/", ctx -> ctx.result("Welcome to mock exam"));
            path("/security", securityRoutes.getSecurityRoutes());
            path("/candidates", candidateRoutes.getRoutes());
            path("/skills", skillRoutes.getRoutes());
        };
    }
}
