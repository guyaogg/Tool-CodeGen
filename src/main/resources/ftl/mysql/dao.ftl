package ${classPath};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${orgClassPath};
import ${queryClassPath};
import ${mapperClassPath};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ${author}
 * @version 1.0
 * @description: TODO
 * @date ${time}
 */
@Repository
public class ${className} extends BaseDAO<${orgClassName}, ${queryClassName}> {
    @Autowired
    ${mapperClassName} ${mapperClassNameXiao};

    @Override
    protected ${mapperClassName} getBaseMapper() {
        return ${mapperClassNameXiao};
    }

    @Override
    protected QueryWrapper<${orgClassName}> buildWrapperWithoutPage(${queryClassName} query) {
    QueryWrapper<${orgClassName}> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }
}
