package cn.sixlab.minesoft.singte.core.dao;

import cn.hutool.core.util.StrUtil;
import cn.sixlab.minesoft.singte.core.common.config.BaseDao;
import cn.sixlab.minesoft.singte.core.common.pager.PageResult;
import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.models.StMenu;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StMenuDao extends BaseDao<StMenu> {

    @Override
    public Class<StMenu> entityClass() {
        return StMenu.class;
    }


    public StMenu selectByMenuCode(String menuCode) {
        Query query = new Query(Criteria.where("menuCode").is(menuCode));
        return mongoTemplate.findOne(query, entityClass());
    }

    @Override
    public StMenu selectExist(StMenu record) {
        Query query = new Query(Criteria.where("menuCode").is(record.getMenuCode()));
        return mongoTemplate.findOne(query, entityClass());
    }

    /**
     * 查询分组下所有菜单，根据权重排序
     *
     * @param menuGroup 分组
     * @return 菜单列表
     */
    public List<StMenu> selectGroupMenus(String menuGroup) {
        Criteria criteria = Criteria.where("menuGroup").is(menuGroup)
                .and("status").is(StConst.YES);

        Query query = new Query(criteria).with(Sort.by("weight", "id"));
        return mongoTemplate.find(query, StMenu.class);
    }

    @Override
    public PageResult<StMenu> queryData(String keyword, String status, Integer pageNum, Integer pageSize) {
        Criteria criteria = new Criteria();
        if (StrUtil.isNotEmpty(status)) {
            criteria = criteria.and("status").is(status);
        }

        if (StrUtil.isNotEmpty(keyword)) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("menuCode").regex(keyword),
                    Criteria.where("menuLink").regex(keyword),
                    Criteria.where("menuGroup").regex(keyword),
                    Criteria.where("intro").regex(keyword)
            );

            criteria = criteria.andOperator(keywordCriteria);
        }
        Sort sort = Sort.by("weight", "id");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, StMenu.class, pageNum, pageSize);
    }
}