package cn.sixlab.minesoft.singte.core.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.sixlab.minesoft.singte.core.common.config.BaseController;
import cn.sixlab.minesoft.singte.core.common.pager.PageResult;
import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.common.utils.StErr;
import cn.sixlab.minesoft.singte.core.common.vo.ModelResp;
import cn.sixlab.minesoft.singte.core.dao.SteAncientSectionDao;
import cn.sixlab.minesoft.singte.core.models.SteAncientSection;
import cn.sixlab.minesoft.singte.core.service.AncientService;
import cn.sixlab.minesoft.singte.core.common.utils.Callback;
import org.nlpcn.commons.lang.jianfan.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/admin/ancient/section")
public class AdminAncientSectionController extends BaseController {

    @Autowired
    private SteAncientSectionDao ancientSectionDao;

    @Autowired
    private AncientService ancientService;

    @ResponseBody
    @PostMapping(value = "/reload")
    public ModelResp reload() {

        ancientService.iterSections(new Callback<SteAncientSection>() {
            @Override
            public void call(SteAncientSection param) {
                param.setAuthor(Converter.SIMPLIFIED.convert(param.getAuthor()));
                param.setBookName(Converter.SIMPLIFIED.convert(param.getBookName()));
                param.setSectionName(Converter.SIMPLIFIED.convert(param.getSectionName()));
                param.setContentHtml(Converter.SIMPLIFIED.convert(param.getContentHtml()));
                param.setContentText(Converter.SIMPLIFIED.convert(param.getContentText()));

                ancientSectionDao.save(param);
            }
        });

        return ModelResp.success();
    }

    @GetMapping(value = "/list")
    public String list() {
        return "admin/ancient/sectionList";
    }

    @PostMapping(value = "/listData")
    public String listData(ModelMap modelMap, String keyword, String status,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "20") Integer pageSize) {

        PageResult<SteAncientSection> pageResult = ancientSectionDao.queryData(keyword, status, pageNum, pageSize);

        modelMap.put("result", pageResult);

        return "admin/ancient/sectionListData";
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public ModelResp submit(SteAncientSection params) {
        SteAncientSection nextInfo;

        if (StrUtil.isNotEmpty(params.getId())) {
            nextInfo = ancientSectionDao.selectById(params.getId());

            if (null == nextInfo) {
                return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
            }

            nextInfo.setUpdateTime(new Date());
        } else {
            SteAncientSection checkExist = ancientSectionDao.selectExist(params);
            if (null != checkExist) {
                return ModelResp.error(StErr.EXIST_SAME, "common.same.found");
            }

            nextInfo = new SteAncientSection();
            nextInfo.setViewCount(0);
            nextInfo.setThumbCount(0);
            nextInfo.setStatus(StConst.YES);
            nextInfo.setCreateTime(new Date());
        }

        nextInfo.setAncientSet(params.getAncientSet());
        nextInfo.setAncientCategory(params.getAncientCategory());
        nextInfo.setBookName(params.getBookName());
        nextInfo.setSectionName(params.getSectionName());
        nextInfo.setAuthor(params.getAuthor());
        nextInfo.setContentHtml(params.getContentHtml());
        nextInfo.setContentText(params.getContentText());
        nextInfo.setWeight(params.getWeight());
        nextInfo.setIntro(params.getIntro());

        ancientSectionDao.save(nextInfo);
        return ModelResp.success();
    }

    @ResponseBody
    @RequestMapping(value = "/status")
    public ModelResp status(String id, String status) {
        SteAncientSection record = ancientSectionDao.selectById(id);

        if (null == record) {
            return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
        }

        record.setStatus(status);
        ancientSectionDao.save(record);
        return ModelResp.success();
    }

    @ResponseBody
    @RequestMapping(value = "/get")
    public ModelResp get(String id) {
        SteAncientSection record = ancientSectionDao.selectById(id);

        if (null == record) {
            return ModelResp.error(StErr.NOT_EXIST, "common.not.found");
        }

        return ModelResp.success(record);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public ModelResp delete(String id) {
        ancientSectionDao.deleteById(id);

        return ModelResp.success();
    }

}
