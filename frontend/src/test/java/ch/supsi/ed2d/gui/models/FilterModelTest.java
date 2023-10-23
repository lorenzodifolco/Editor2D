package ch.supsi.ed2d.gui.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterModelTest {

    private static FilterModel filterModel;

    @BeforeAll
    static void beforeAll()
    {
        filterModel = new FilterModel("testName","testPath");
    }

    @Test
    void getIconPath() {
        assertEquals("testPath", filterModel.getIconPath());
    }

    @Test
    void getFilterName() {
        assertEquals("testName", filterModel.getFilterName());
    }
}