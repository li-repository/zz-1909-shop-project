package com.qf.mapper;

import com.qf.entity.TProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author li
 * @date 2020/3/14 0014 16:46
 */
@Repository
public interface CartMapper {

    /**
     * 根据ID查询商品详细信息
     * @param pid
     * @return
     */
    //@Select("select * from t_product where pid = #{pid}")
    public TProduct findProductById(@Param("pid") Long pid);

}
