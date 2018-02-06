package io.github.vlad324.storedprocedure.service.impl.sp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.asserts.CallableExecution;
import net.ttddyy.dsproxy.asserts.ParameterKeyValue;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import javax.sql.DataSource;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class GetUserSPTest {

    private static final String SP_NAME = "SP_NAME";

    @Mock
    private CallableStatement callableStatement;

    @Mock
    private Connection connection;

    @Mock
    private DataSource dataSource;


    private ProxyTestDataSource proxyTestDataSource;

    private GetUserSP sp;

    @Before
    public void init() throws SQLException {
        // Prevent cycle
        when(callableStatement.getMoreResults()).thenReturn(false);
        when(callableStatement.getUpdateCount()).thenReturn(-1);

        // mock to avoid NullPointerException
        when(connection.prepareCall(anyString())).thenReturn(callableStatement);
        when(dataSource.getConnection()).thenReturn(connection);

        // proxy datasource
        this.proxyTestDataSource = new ProxyTestDataSource(dataSource);
        this.sp = new GetUserSP(this.proxyTestDataSource, SP_NAME);
    }

    @Test
    public void getUsers() {
        // Setup
        final int allParametersCount = 2;
        final int inParametersCount = 1;

        final String name = "some_name";

        final Map<Integer, Object> expectedValues = new HashMap<>();
        expectedValues.put(1, name);

        // Execute
        sp.getUsers(name);

        // Validate
        CallableExecution spExecution = proxyTestDataSource.getFirstCallable();

        String query = spExecution.getQuery();
        assertTrue(query.contains(SP_NAME)); // check query contains name

        assertEquals(allParametersCount, spExecution.getAllParameters().size()); // check count of all params

        SortedSet<ParameterKeyValue> inParams = spExecution.getSetParams();
        assertEquals(inParametersCount, inParams.size()); // check count of in params


        inParams.forEach((ParameterKeyValue param) -> {
            int paramIndex = param.getKey().getIndex();

            Object expectedValue = expectedValues.get(paramIndex);

            assertEquals(expectedValue, param.getValue()); // check param value
        });

    }

    @After
    public void reset() {
        this.proxyTestDataSource.reset();
    }
}