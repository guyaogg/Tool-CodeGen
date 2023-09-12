package ${classPath};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${daoClassPath};
import ${orgClassPath};
import net.csibio.tanzhi.exception.XException;
import ${mapperClassPath};
import ${serviceClassPath};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ${author}
 * @version 1.0
 * @description: TODO
 * @date ${time}
 */
@Service
public class ${className} extends ServiceImpl<${mapperClassName}, ${orgClassName}> implements ${serviceClassName} {

    @Autowired
    private ${daoClassName} ${daoClassNameXiao};

    @Override
    public ${daoClassName} getBaseDAO() {
        return ${daoClassNameXiao};
    }

    @Override
    public void beforeInsert(${orgClassName} ${orgClassNameXiao}) throws XException {

    }

    @Override
    public void beforeUpdate(${orgClassName} ${orgClassNameXiao}) throws XException {

    }

    @Override
    public void beforeRemove(String id) throws XException {

    }
}
