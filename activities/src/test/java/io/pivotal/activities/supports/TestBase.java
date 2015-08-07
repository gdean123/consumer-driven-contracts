package io.pivotal.activities.supports;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import javax.transaction.Transactional;

@Transactional
public class TestBase {

    @Before
    public void testBaseSetup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
}