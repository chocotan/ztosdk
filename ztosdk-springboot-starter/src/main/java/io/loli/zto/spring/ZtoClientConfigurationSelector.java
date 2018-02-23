package io.loli.zto.spring;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * Created by yewenlin on 2016-8-16.
 */
public class ZtoClientConfigurationSelector extends AdviceModeImportSelector<EnableZtoClient> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[]{ZtoClientAutoConfiguration.class.getName()};
    }
}