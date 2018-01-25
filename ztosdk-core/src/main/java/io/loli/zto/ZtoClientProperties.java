package io.loli.zto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ZtoClientProperties {
    private String url;
    private String companyId;
    private String key;
    private Long timeout = 3000L;
}
