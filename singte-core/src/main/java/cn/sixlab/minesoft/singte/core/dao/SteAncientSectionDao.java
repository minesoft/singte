package cn.sixlab.minesoft.singte.core.dao;

import cn.hutool.core.util.StrUtil;
import cn.sixlab.minesoft.singte.core.common.config.BaseDao;
import cn.sixlab.minesoft.singte.core.common.pager.PageResult;
import cn.sixlab.minesoft.singte.core.models.SteAncientBook;
import cn.sixlab.minesoft.singte.core.models.SteAncientSection;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SteAncientSectionDao extends BaseDao<SteAncientSection> {

    @Override
    public Class<SteAncientSection> entityClass() {
        return SteAncientSection.class;
    }

    @Override
    public SteAncientSection selectExist(SteAncientSection record) {
        Criteria criteria = Criteria.where("ancientSet").is(record.getAncientSet())
                .and("ancientCategory").is(record.getAncientCategory())
                .and("bookName").is(record.getBookName())
                .and("sectionName").is(record.getSectionName());

        Sort sort = Sort.by("id");

        Query query = new Query(criteria).with(sort);

        return mongoTemplate.findOne(query, entityClass());
    }

    public List<SteAncientSection> listBookSections(String bookName) {
        Criteria criteria = Criteria.where("bookName").is(bookName);

        Sort sort = Sort.by("weight", "id");

        Query query = new Query(criteria).with(sort);

        return mongoTemplate.find(query, entityClass());
    }

    public List<String> listBookAuthor(String bookName) {
        Criteria criteria = Criteria.where("bookName").is(bookName);

        Sort sort = Sort.by("weight", "id");

        Query query = new Query(criteria).with(sort);

        return mongoTemplate.findDistinct(query, "author", entityClass(), String.class);
    }

    public PageResult<SteAncientSection> querySection(SteAncientBook ancientBook, String keyword, Integer pageNum, Integer pageSize) {
        Criteria criteria = new Criteria();
        if (null != ancientBook) {
            if (StrUtil.isNotEmpty(ancientBook.getAncientSet())) {
                criteria = criteria.and("ancientSet").is(ancientBook.getAncientSet());
            }
            if (StrUtil.isNotEmpty(ancientBook.getAncientCategory())) {
                criteria = criteria.and("ancientCategory").is(ancientBook.getAncientCategory());
            }
            if (StrUtil.isNotEmpty(ancientBook.getBookName())) {
                criteria = criteria.and("bookName").is(ancientBook.getBookName());
            }
        }

        if (StrUtil.isNotEmpty(keyword)) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("ancientSet").regex(keyword),
                    Criteria.where("ancientCategory").regex(keyword),
                    Criteria.where("bookName").regex(keyword),
                    Criteria.where("author").regex(keyword),
                    Criteria.where("contentText").regex(keyword),
                    Criteria.where("intro").regex(keyword)
            );

            criteria = criteria.andOperator(keywordCriteria);
        }
        Sort sort = Sort.by("weight", "id");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, entityClass(), pageNum, pageSize);
    }

    public PageResult<SteAncientSection> queryData(String keyword, String status, Integer pageNum, Integer pageSize) {
        Criteria criteria = new Criteria();
        if (StrUtil.isNotEmpty(status)) {
            criteria = criteria.and("status").is(status);
        }

        if (StrUtil.isNotEmpty(keyword)) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("ancientSet").regex(keyword),
                    Criteria.where("ancientCategory").regex(keyword),
                    Criteria.where("bookName").regex(keyword),
                    Criteria.where("author").regex(keyword),
                    Criteria.where("contentText").regex(keyword),
                    Criteria.where("intro").regex(keyword)
            );

            criteria = criteria.andOperator(keywordCriteria);
        }

        Sort sort = Sort.by("weight", "id");

        Query query = new Query(criteria).with(sort);

        return pageQuery(query, entityClass(), pageNum, pageSize);
    }

}