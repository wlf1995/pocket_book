package com.ibicn.hr.util;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.ibicnCloud.util.StringUtil;
import org.hibernate.collection.internal.PersistentSet;

import java.util.HashSet;
import java.util.Set;

/**
 * 主要用于过滤不需要序列化的属性，或者包含需要序列化的属性
 *
 * @author 孙宇
 */
public class FastjsonFilter implements PropertyFilter {

    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();
    private final Set<String> sets = new HashSet<String>();

    public Set<String> getSets() {
        return sets;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    @Override
    public boolean apply(Object source, String name, Object value) {
        name = StringUtil.format(name).toLowerCase();
        if (value != null && (value instanceof PersistentSet)) {// 避免hibernate对象循环引用，一切Set属性不予序列化
            if (sets.contains(name) || sets.contains(source.getClass().getSimpleName() + "." + name)) {
                return true; // 如果要强制包含某个集合，则给予序列化
            } else {
                return false;
            }
        }
        if (excludes.contains(name) || excludes.contains(source.getClass().getSimpleName() + "." + name)) {
            return false;
        }
        if (includes.size() == 0 || includes.contains(name) || includes.contains(source.getClass().getSimpleName() + "." + name)) {
            return true;
        }
        return false;
    }

}
