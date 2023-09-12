package ${classPath};

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.csibio.ssoclient.domain.base.MongoBaseDO;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ${author}
 * @version 1.0
 * @description: TODO
 * @date ${time}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "${className}")
public class ${className} extends MongoBaseDO {

}
