package io.renren.modules.xj.utils;

import io.renren.common.utils.R;
import io.renren.modules.xj.entity.XjDataSourceEntity;
import io.renren.modules.xj.entity.XjKtrEntity;
import io.renren.modules.xj.service.XjDataSourceService;
import io.renren.modules.xj.service.XjKtrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component(value = "ktrRun")
public class KtrRun {

    @Autowired
    private XjKtrService xjKtrService;
    @Autowired
    private XjDataSourceService xjDataSourceService;

    public void run(@RequestParam String ktrId) throws Exception {
        XjKtrEntity xk = xjKtrService.selectById(ktrId);
        xk.setKtrStatus("1");
        xjKtrService.updateById(xk);
        XjDataSourceEntity ds = xjDataSourceService.selectById(xk.getKtrDsid());
        xjKtrService.kettleJob(xk,ds);
    }
}
