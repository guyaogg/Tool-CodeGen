package ${classPath};

import ${daoClassPath};
import ${orgClassPath};
import net.csibio.ssoclient.domain.exception.XException;
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
public class ${className} implements ${serviceClassName} {

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

