package io.github.bty834.infrastructure.repository.mapper;

import io.github.bty834.infrastructure.repository.mapper.po.SamplePO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SampleMapper {

    List<SamplePO> selectById(@Param("id") Long id);
}
