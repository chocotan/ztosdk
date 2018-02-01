package io.loli.zto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ZtoClientProperties {
    private String url;
    private String companyId;
    private String key;
    private Boolean async = false;
    private Long timeout = 3000L;
}
