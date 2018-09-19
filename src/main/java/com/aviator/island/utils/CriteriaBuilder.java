package com.aviator.island.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hibernate.criterion.*;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/7.
 */
public class CriteriaBuilder {

    public static DetachedCriteriaBuilder newDetachedCriteriaBuilder(Class entityClass) {
        return new DetachedCriteriaBuilder(entityClass);
    }

    public static class DetachedCriteriaBuilder {
        private Class entityClass;

        private Map<SearchConditional, Map<String, Object>> orSearchConditional = Maps.newHashMap();

        private Map<SearchConditional, Map<String, Object>> andSearchConditional = Maps.newHashMap();

        private Map<SearchConditional, Map<String, Object>> notSearchConditional = Maps.newHashMap();

        public DetachedCriteriaBuilder(Class entityClass) {
            this.entityClass = entityClass;
        }

        public DetachedCriteriaBuilder orConditional(String property, Object value, SearchConditional conditional) {
            Map<String, Object> params = this.orSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(params)) {
                params = Maps.newHashMap();
                this.orSearchConditional.put(conditional, params);
            }
            params.put(property, value);
            return this;
        }

        public DetachedCriteriaBuilder orConditional(Map<String, Object> params, SearchConditional conditional) {
            Map<String, Object> allParams = this.orSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(allParams)) {
                allParams = Maps.newHashMap();
                this.orSearchConditional.put(conditional, allParams);
            }
            allParams.putAll(params);
            return this;
        }

        public DetachedCriteriaBuilder andConditional(String property, Object value, SearchConditional conditional) {
            Map<String, Object> params = this.andSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(params)) {
                params = Maps.newHashMap();
                this.andSearchConditional.put(conditional, params);
            }
            params.put(property, value);
            return this;
        }

        public DetachedCriteriaBuilder andConditional(Map<String, Object> params, SearchConditional conditional) {
            Map<String, Object> allParams = this.andSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(allParams)) {
                allParams = Maps.newHashMap();
                this.andSearchConditional.put(conditional, allParams);
            }
            allParams.putAll(params);
            return this;
        }

        public DetachedCriteriaBuilder notConditional(String property, Object value, SearchConditional conditional) {
            Map<String, Object> params = this.notSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(params)) {
                params = Maps.newHashMap();
                this.notSearchConditional.put(conditional, params);
            }
            params.put(property, value);
            return this;
        }

        public DetachedCriteriaBuilder notConditional(Map<String, Object> params, SearchConditional conditional) {
            Map<String, Object> allParams = this.notSearchConditional.get(conditional);
            if (CollectionUtils.isEmpty(allParams)) {
                allParams = Maps.newHashMap();
                this.notSearchConditional.put(conditional, allParams);
            }
            allParams.putAll(params);
            return this;
        }

        public DetachedCriteria build() {
            DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
            if (!CollectionUtils.isEmpty(this.andSearchConditional)) {
                criteria.add(assemblyJunction(this.andSearchConditional, Junction.Nature.AND));
            }
            if (!CollectionUtils.isEmpty(this.orSearchConditional)) {
                criteria.add(assemblyJunction(this.orSearchConditional, Junction.Nature.OR));
            }
            if (!CollectionUtils.isEmpty(this.notSearchConditional)) {
                assemblyNot(this.notSearchConditional).forEach(criterionT -> criteria.add(criterionT));
            }
            return criteria;
        }

        private Junction assemblyJunction(Map<SearchConditional, Map<String, Object>> searchConditional, Junction.Nature nature) {
            Junction junctionS = Restrictions.disjunction();
            if (Junction.Nature.AND.getOperator().equals(nature.getOperator())) {
                junctionS = Restrictions.conjunction();
            }
            final Junction junction = junctionS;
            if (!CollectionUtils.isEmpty(searchConditional)) {
                searchConditional.forEach((conditional, params) -> {
                    if (SearchConditional.EQ.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.eq(property, value)));
                    }
                    if (SearchConditional.GT.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.gt(property, value)));
                    }
                    if (SearchConditional.GE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.ge(property, value)));
                    }
                    if (SearchConditional.LT.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.lt(property, value)));
                    }
                    if (SearchConditional.LE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.le(property, value)));
                    }
                    if (SearchConditional.IS_NULL.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.isNull(property)));
                    }
                    if (SearchConditional.IS_NOT_NULL.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.isNotNull(property)));
                    }
                    if (SearchConditional.IS_EMPTY.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.isEmpty(property)));
                    }
                    if (SearchConditional.IS_NOT_EMPTY.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> junction.add(Restrictions.isNotEmpty(property)));
                    }
                    if (SearchConditional.LIKE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> {
                            if (value != null) {
                                junction.add(Restrictions.like(property, value.toString(), MatchMode.ANYWHERE));
                            }
                        });
                    }
                    if (SearchConditional.IN.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> {
                            if (value instanceof Collection) {
                                junction.add(Restrictions.in(property, (Collection) value));
                            } else {
                                junction.add(Restrictions.in(property, value));
                            }
                        });
                    }
                    if (SearchConditional.BETWEEN.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> {
                            Object[] arr = (Object[]) value;
                            junction.add(Restrictions.between(property, arr[0], arr[1]));
                        });
                    }
                });
            }
            return junction;
        }

        private List<Criterion> assemblyNot(Map<SearchConditional, Map<String, Object>> searchConditional) {
            if (!CollectionUtils.isEmpty(searchConditional)) {
                List<Criterion> criterionList = Lists.newArrayList();
                searchConditional.forEach((conditional, params) -> {
                    if (SearchConditional.EQ.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.eq(property, value))));
                    }
                    if (SearchConditional.GT.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.gt(property, value))));
                    }
                    if (SearchConditional.GE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.ge(property, value))));
                    }
                    if (SearchConditional.LT.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.lt(property, value))));
                    }
                    if (SearchConditional.LE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.le(property, value))));
                    }
                    if (SearchConditional.IS_NULL.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.isNull(property))));
                    }
                    if (SearchConditional.IS_NOT_NULL.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.isNotNull(property))));
                    }
                    if (SearchConditional.IS_EMPTY.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.isEmpty(property))));
                    }
                    if (SearchConditional.IS_NOT_EMPTY.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.isNotEmpty(property))));
                    }
                    if (SearchConditional.LIKE.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> {
                            if (value != null) {
                                criterionList.add(Restrictions.not(Restrictions.like(property, value.toString(), MatchMode.ANYWHERE)));
                            }
                        });
                    }
                    if (SearchConditional.IN.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> criterionList.add(Restrictions.not(Restrictions.in(property, value))));
                    }
                    if (SearchConditional.BETWEEN.conditionalEqual(conditional)) {
                        params.forEach((property, value) -> {
                            Object[] arr = (Object[]) value;
                            criterionList.add(Restrictions.between(property, arr[0], arr[1]));
                        });
                    }
                });
                return criterionList;
            }
            return null;
        }
    }

}
