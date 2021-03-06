package cn.sixlab.minesoft.singte.core.poetry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.sixlab.minesoft.singte.core.common.utils.JsonUtils;
import cn.sixlab.minesoft.singte.core.models.SteAncientSection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("importqianziwen")
public class ImportQianZiWen extends PoetryImportApi {

    @Override
    public List<PoetryModel> paths() {
        List<PoetryModel> list = new ArrayList<>();

        list.add(new PoetryModel("mengxue/qianziwen.json", "经部", "蒙学类", "千字文", "周兴嗣"));

        return list;
    }

    /**
     * mengxue/qianziwen.json
     * 
     */
    @Override
    public void parseJson(String resp, SteAncientSection param) {
        SteAncientSection ancientSection = new SteAncientSection();
        BeanUtil.copyProperties(param, ancientSection);

        int sectionCount = 1;
        Map map = JsonUtils.toBean(resp, Map.class);

        ancientSection.setId(null);
        ancientSection.setWeight(sectionCount);

        String title = MapUtil.getStr(map, "title");
        if (StrUtil.isEmpty(title)) {
            title = MapUtil.getStr(map, "chapter");
        }

        ancientSection.setSectionName(title);

        String author = MapUtil.getStr(map, "author");
        if (StrUtil.isNotEmpty(author)) {
            ancientSection.setAuthor(author);
        }

        List<String> qianziwenContent = (List<String>) map.get("paragraphs");
        StringBuilder qianziwenSb = new StringBuilder();
        for (int i = 0; i < qianziwenContent.size(); i++) {
            qianziwenSb.append(qianziwenContent.get(i));
            if (i % 2 == 0) {
                qianziwenSb.append("，");
            } else {
                qianziwenSb.append("。");
            }
        }
        String qianziwen = qianziwenSb.toString();
        ancientSection.setContentHtml(StrUtil.replace(qianziwen, "。", "。<br />"));
        ancientSection.setContentText(qianziwen);

        ancientSectionDao.save(ancientSection);
    }
}
