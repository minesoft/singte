package cn.sixlab.minesoft.singte.core.controller.admin;

import cn.sixlab.minesoft.singte.core.common.config.BaseController;
import cn.sixlab.minesoft.singte.core.common.utils.ConfigUtils;
import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.common.utils.StErr;
import cn.sixlab.minesoft.singte.core.common.vo.ModelResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/admin/server")
public class AdminServerController extends BaseController {
    private static final Date startTime = new Date();

    @Autowired
    private ConfigUtils configUtils;

    @GetMapping(value = "/info")
    public String info(ModelMap modelMap) {
        modelMap.put("startTime", startTime);

        return "admin/server/info";
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public ModelResp update() {
        runShell("update.sh");
        return runShell("kill.sh");
    }

    @ResponseBody
    @RequestMapping(value = "/reboot")
    public ModelResp reboot() {
        return runShell("kill.sh");
    }

    private ModelResp runShell(String file) {
        String path = configUtils.getConfig(StConst.SERVER_SH_PATH);

        Process exec;
        try {
            String[] cmd = {"/bin/sh", "-c", path + file};
            exec = Runtime.getRuntime().exec(cmd, null, new File(path));

            InputStreamReader streamReader = new InputStreamReader(exec.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String line;

            log.info("??????????????????\n");
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            streamReader = new InputStreamReader(exec.getErrorStream());
            reader = new BufferedReader(streamReader);
            log.error("????????????????????????\n");
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            exec.waitFor();
        } catch (IOException e) {
            log.error("??????????????????", e);
            return ModelResp.error(StErr.UNKNOWN, "??????????????????");
        } catch (InterruptedException e) {
            log.error("??????????????????", e);
            return ModelResp.error(StErr.UNKNOWN, "??????????????????");
        }

        return ModelResp.success();
    }

}
