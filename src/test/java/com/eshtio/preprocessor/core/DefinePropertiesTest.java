package com.eshtio.preprocessor.core;

import com.eshtio.preprocessor.TestUtils;
import com.eshtio.preprocessor.core.exception.IllegalDefinePropertyException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by EshtIO on 2019-06-15.
 */
public class DefinePropertiesTest {

    @Test
    public void readDefineProperties() {
        DefineProperties symbols = TestUtils.getTestDefineProperties();

        assertTrue(symbols.getValue(TestUtils.DP_TRUE_KEY));
        assertFalse(symbols.getValue(TestUtils.DP_FALSE_KEY));
        assertFalse(symbols.getValue(TestUtils.DP_NOT_BOOLEAN_KEY));
        assertFalse(symbols.getValue(TestUtils.DP_NOT_DEFINED_KEY));
    }

    @Test(expected = IllegalDefinePropertyException.class)
    public void getUnknownProperty() {
        DefineProperties symbols = TestUtils.getTestDefineProperties();
        symbols.getValue("UNKNOWN_KEY");
    }

}
