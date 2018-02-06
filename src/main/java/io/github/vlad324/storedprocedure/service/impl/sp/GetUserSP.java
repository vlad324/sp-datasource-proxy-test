package io.github.vlad324.storedprocedure.service.impl.sp;

import io.github.vlad324.storedprocedure.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
@Slf4j
public class GetUserSP extends StoredProcedure {

    private static final String NAME_PARAM = "name_param";
    private static final String SELECTED_COUNT = "selected_count";
    private static final String RESULT_SET = "RESULT_SET";

    public GetUserSP(final DataSource ds, final String name) {
        super(ds, name);
        declareParameters();
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers(final String name) {
        Map<String, Object> inParameters = new HashMap<>();
        inParameters.put(NAME_PARAM, name);

        log.debug("Executing SP {} with parameters={}", getSql(), inParameters);

        Map<String, Object> outParameters = super.execute(inParameters);

        log.info("SP {} executed. Out parameters={}", getSql(), outParameters);

        return (List<User>) outParameters.get(RESULT_SET);
    }

    private void declareParameters() {
        declareParameter(new SqlReturnResultSet(RESULT_SET, (ResultSet rs, int rowNum) ->
            User.builder().id(rs.getInt("id")).name(rs.getString("name")).build()
        ));

        declareParameter(new SqlParameter(NAME_PARAM, Types.VARCHAR));
        declareParameter(new SqlOutParameter(SELECTED_COUNT, Types.INTEGER));

        compile();
    }
}
