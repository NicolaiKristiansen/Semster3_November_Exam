package app.utils.externalAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class Stats {

    private String id;
    private String slug;
    private String name;

    private String categoryKey;

    private String description;
    private int popularityScore;
    private int averageSalary;

    private ZonedDateTime updatedAt;
}
