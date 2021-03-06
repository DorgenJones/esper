/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.epl.expression.accessagg;

import com.espertech.esper.core.service.StatementExtensionSvcContext;
import com.espertech.esper.epl.agg.access.AggregationStateMinMaxByEverSpec;
import com.espertech.esper.epl.agg.access.AggregationStateSortedSpec;
import com.espertech.esper.epl.agg.service.AggregationStateFactory;
import com.espertech.esper.epl.core.engineimport.EngineImportService;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.epl.expression.core.ExprNodeUtility;
import com.espertech.esper.util.CollectionUtil;

import java.util.Comparator;

public class SortedAggregationStateFactoryFactory {

    private final EngineImportService engineImportService;
    private final StatementExtensionSvcContext statementExtensionSvcContext;
    private final ExprNode[] expressions;
    private final ExprEvaluator[] evaluators;
    private final boolean[] sortDescending;
    private final boolean ever;
    private final int streamNum;
    private final ExprAggMultiFunctionSortedMinMaxByNode parent;
    private final ExprEvaluator optionalFilter;

    public SortedAggregationStateFactoryFactory(EngineImportService engineImportService, StatementExtensionSvcContext statementExtensionSvcContext, ExprNode[] expressions, ExprEvaluator[] evaluators, boolean[] sortDescending, boolean ever, int streamNum, ExprAggMultiFunctionSortedMinMaxByNode parent, ExprEvaluator optionalFilter) {
        this.engineImportService = engineImportService;
        this.statementExtensionSvcContext = statementExtensionSvcContext;
        this.expressions = expressions;
        this.evaluators = evaluators;
        this.sortDescending = sortDescending;
        this.ever = ever;
        this.streamNum = streamNum;
        this.parent = parent;
        this.optionalFilter = optionalFilter;
    }

    public AggregationStateFactory makeFactory() {
        boolean sortUsingCollator = engineImportService.isSortUsingCollator();
        Comparator<Object> comparator = CollectionUtil.getComparator(expressions, evaluators, sortUsingCollator, sortDescending);
        Class[] criteraTypes = ExprNodeUtility.getExprResultTypes(expressions);

        if (ever) {
            AggregationStateMinMaxByEverSpec spec = new AggregationStateMinMaxByEverSpec(streamNum, evaluators, criteraTypes, parent.isMax(), comparator, null, optionalFilter);
            return engineImportService.getAggregationFactoryFactory().makeMinMaxEver(statementExtensionSvcContext, parent, spec);
        }

        AggregationStateSortedSpec spec = new AggregationStateSortedSpec(streamNum, evaluators, criteraTypes, comparator, null, optionalFilter);
        return engineImportService.getAggregationFactoryFactory().makeSorted(statementExtensionSvcContext, parent, spec);
    }
}
