package io.loli.zto;

import io.loli.zto.client.ZtoApiClient;
import io.loli.zto.exception.ZtoNoPermissionException;
import org.junit.Test;

public class ClientTest {
    @Test(expected = ZtoNoPermissionException.class)
    public void testZtoNoPermission(){
        ZtoClientProperties account = new ZtoClientProperties("http://japi.zto.cn/", "test","test", 2000L);
        ZtoApiClient client = new ZtoApiClient(account);
        String commonOrder = client.postApi("commonOrder", "{}");
        System.out.println(commonOrder);
    }
}
