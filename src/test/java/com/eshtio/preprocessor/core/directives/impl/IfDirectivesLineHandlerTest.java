package com.eshtio.preprocessor.core.directives.impl;

import com.eshtio.preprocessor.TestUtils;
import com.eshtio.preprocessor.core.DefineProperties;
import com.eshtio.preprocessor.core.directives.Directive;
import com.eshtio.preprocessor.core.exception.DirectiveStateException;
import org.junit.Before;
import org.junit.Test;

import static com.eshtio.preprocessor.core.directives.DirectiveFileState.DEFAULT;
import static com.eshtio.preprocessor.core.directives.DirectiveFileState.SKIP_LINES;
import static org.junit.Assert.assertEquals;

/**
 * Created by EshtIO on 2019-06-16.
 */
public class IfDirectivesLineHandlerTest {

    private static final DefineProperties DEFINE_PROPERTIES = TestUtils.getTestDefineProperties();

    private IfDirectivesLineHandler handler;

    @Before
    public void setUp() {
        handler = new IfDirectivesLineHandler(DEFINE_PROPERTIES);
    }

    @Test
    public void handleSupportedDirectiveFullBlock() {
        assertEquals(DEFAULT, handler.handleLine(Directive.IF, "#if " + TestUtils.DP_TRUE_KEY));
        assertEquals(SKIP_LINES, handler.handleLine(Directive.ELIF, "#elif !" + TestUtils.DP_TRUE_KEY));
        assertEquals(SKIP_LINES, handler.handleLine(Directive.ELSE, "#else"));
        assertEquals(DEFAULT, handler.handleLine(Directive.ENDIF, "#endif"));
        handler.checkFinalDirectiveState();
    }

    @Test
    public void handleNestedBlock() {
        assertEquals(DEFAULT, handler.handleLine(Directive.IF, "#if " + TestUtils.DP_TRUE_KEY));
        assertEquals(SKIP_LINES, handler.handleLine(Directive.IF, "#if " + TestUtils.DP_FALSE_KEY));
        assertEquals(DEFAULT, handler.handleLine(Directive.ELSE, "#else"));
        assertEquals(DEFAULT, handler.handleLine(Directive.ENDIF, "#endif"));
        assertEquals(DEFAULT, handler.handleLine(Directive.ENDIF, "#endif"));
        handler.checkFinalDirectiveState();
    }

    @Test
    public void handleDirectiveWithNotFoundBranch() {
        assertEquals(SKIP_LINES, handler.handleLine(Directive.IF, "#if " + TestUtils.DP_FALSE_KEY));
        assertEquals(SKIP_LINES, handler.handleLine(Directive.ELIF, "#elif " + TestUtils.DP_FALSE_KEY));
        assertEquals(DEFAULT, handler.handleLine(Directive.ENDIF, "#endif"));
        handler.checkFinalDirectiveState();
    }

    @Test(expected = DirectiveStateException.class)
    public void handleIllegalIfCondition() {
        handler.handleLine(Directive.IF, "#if illegal condition");
    }

    @Test(expected = DirectiveStateException.class)
    public void checkDirectiveStateWhenDirectiveNotClosed() {
        assertEquals(SKIP_LINES, handler.handleLine(Directive.IF, "#if !" + TestUtils.DP_TRUE_KEY));
        handler.checkFinalDirectiveState();
    }

    @Test(expected = DirectiveStateException.class)
    public void handleElseDirectiveFirst() {
        handler.handleLine(Directive.ELSE, "#else");
    }

    @Test(expected = DirectiveStateException.class)
    public void handleElifDirectiveFirst() {
        handler.handleLine(Directive.ELIF, "#elif");
    }

    @Test(expected = DirectiveStateException.class)
    public void handleEndifDirectiveFirst() {
        handler.handleLine(Directive.ENDIF, "#endif");
    }

}
