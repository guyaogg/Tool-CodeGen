package ${classPath};

import ${orgClassPath};
import ${queryClassPath};
import net.csibio.ssoclient.dao.BaseDAO;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ${author}
 * @version 1.0
 * @description: TODO
 * @date ${time}
 */
@Repository
public class ${className} extends BaseDAO<${orgClassName}, ${queryClassName}> {

    @Override
    protected boolean allowSort() {
    return false;
    }

    @Override
    protected Query buildQueryWithoutPage(${queryClassName} ${queryClassNameXiao}) {
        Query query = new Query();
        return query;
    }
}


