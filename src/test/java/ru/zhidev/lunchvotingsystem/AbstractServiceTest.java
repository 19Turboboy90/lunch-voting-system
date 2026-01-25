package ru.zhidev.lunchvotingsystem;

import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractServiceTest extends AbstractTest {
}
