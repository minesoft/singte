package cn.sixlab.minesoft.singte.core.controller.admin;

import cn.hutool.extra.spring.SpringUtil;
import cn.sixlab.minesoft.singte.core.common.config.BaseController;
import cn.sixlab.minesoft.singte.core.common.utils.StErr;
import cn.sixlab.minesoft.singte.core.common.vo.ModelResp;
import cn.sixlab.minesoft.singte.core.poetry.PoetryImportApi;
import cn.sixlab.minesoft.singte.core.poetry.PoetryModel;
import cn.sixlab.minesoft.singte.core.service.MessageTranslate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/admin/utils")
public class AdminUtilsController extends BaseController {

    @ResponseBody
    @PostMapping(value = "/importAncient")
    public ModelResp importAncient(String type) throws IOException {

        PoetryImportApi poetryApi = SpringUtil.getBean(type, PoetryImportApi.class);
        if (null != poetryApi) {
            poetryApi.parsePoetry();
            return ModelResp.success();
        }else{
            return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
        }
    }

    @ResponseBody
    @PostMapping(value = "/importOneAncient")
    public ModelResp importOneAncient(String type, PoetryModel model) throws IOException {
        log.info("type>>>>>>>>");
        PoetryImportApi poetryApi = SpringUtil.getBean(type, PoetryImportApi.class);
        if (null != poetryApi) {
            poetryApi.parseOnePoetry(model);
            return ModelResp.success();
        }else{
            return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
        }
    }

    @ResponseBody
    @PostMapping(value = "/translate")
    public ModelResp translate(String lang, String baiduLang) throws IOException {

        MessageTranslate translate = SpringUtil.getBean(MessageTranslate.class);
        if (null != translate) {
            translate.trans(lang, baiduLang);
            return ModelResp.success();
        } else {
            return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
        }
    }
}
