package com.eshtio.preprocessor.core.directives.impl;

import com.eshtio.preprocessor.core.DefineProperties;
import com.eshtio.preprocessor.core.directives.Directive;
import com.eshtio.preprocessor.core.directives.DirectiveLineHandler;
import com.eshtio.preprocessor.core.directives.DirectiveFileState;
import com.eshtio.preprocessor.core.exception.DirectiveStateException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.eshtio.preprocessor.core.directives.Directive.*;

/**
 * #if-directive handler implementation, mutable
 *
 * Created by EshtIO on 2019-06-15.
 */
public class IfDirectivesLineHandler implements DirectiveLineHandler {

    private static final Set<Directive> SUPPORTED_DIRECTIVES = Stream.of(
            IF,
            ELIF,
            ELSE,
            ENDIF
    ).collect(Collectors.toSet());

    private final DefineProperties define;

    private final Map<Integer, Boolean> branchBlocks;

    private int currentBlock = 0;

    public IfDirectivesLineHandler(DefineProperties define) {
        this.define = define;
        this.branchBlocks = new HashMap<>();
    }

    @Override
    public boolean isSupportDirective(Directive directive) {
        return SUPPORTED_DIRECTIVES.contains(directive);
    }

    @Override
    public DirectiveFileState handleLine(Directive directive, String line) throws DirectiveStateException {
        switch (directive) {
            case IF:
                if (conditionIsTrue(line)) {
                    enterToBranchBlock(true);
                    return DirectiveFileState.DEFAULT;
                } else {
                    enterToBranchBlock(false);
                    return DirectiveFileState.SKIP_LINES;
                }
            case ELIF:
                checkBranchBlockEntrance("'" + IF.getKey() + "' directive not open");
                if (branchIsSelected()) {
                    return DirectiveFileState.SKIP_LINES;
                } else if (conditionIsTrue(line)) {
                    selectBranch();
                    return DirectiveFileState.DEFAULT;
                } else {
                    return DirectiveFileState.SKIP_LINES;
                }
            case ELSE:
                checkBranchBlockEntrance("'" + IF.getKey() + "' directive not open");
                if (branchIsSelected()) {
                    return DirectiveFileState.SKIP_LINES;
                } else {
                    selectBranch();
                    return DirectiveFileState.DEFAULT;
                }
            case ENDIF:
                checkBranchBlockEntrance("'" + IF.getKey() + "' directive not open");
                exitFromBranchBlock();
                return DirectiveFileState.DEFAULT;
            default:
                throw new RuntimeException("Illegal 'if' directive line: " + line);
        }
    }

    @Override
    public void checkFinalDirectiveState() throws DirectiveStateException {
        if (!branchBlocks.isEmpty()) {
            throw new DirectiveStateException(
                    "'" + IF.getKey() + "' directive not closed, use '" + ENDIF.getKey() + "'");
        }
    }

    private void checkBranchBlockEntrance(String message) throws DirectiveStateException {
        if (branchBlocks.isEmpty()) {
            throw new DirectiveStateException(message);
        }
    }

    private void enterToBranchBlock(boolean branchSelected) {
        branchBlocks.put(++currentBlock, branchSelected);
    }

    private void exitFromBranchBlock() {
        branchBlocks.remove(currentBlock--);
    }

    private boolean branchIsSelected() {
        return branchBlocks.get(currentBlock);
    }

    private void selectBranch() {
        branchBlocks.put(currentBlock, true);
    }

    private boolean conditionIsTrue(String directiveLine) throws DirectiveStateException {
        String[] parts = directiveLine.split(" ");
        if (parts.length != 2) {
            throw new DirectiveStateException("Illegal directive line condition: " + directiveLine);
        }
        String condition = parts[1];
        if (condition.startsWith("!")) {
            return !define.getValue(condition.substring(1));
        }
        return define.getValue(condition);
    }

}
