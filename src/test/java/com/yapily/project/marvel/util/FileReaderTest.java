package com.yapily.project.marvel.util;

import com.yapily.project.marvel.util.config.TestApplicationConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
public class FileReaderTest {
    @Autowired
    FileReader fileReader;

    @Test
    public void testGetIds() {
        List<Integer> ids = fileReader.getIds();
        int expectedIds = 1493;
        int actualIds = ids.size();
        Assert.assertEquals(expectedIds, actualIds);
    }

}