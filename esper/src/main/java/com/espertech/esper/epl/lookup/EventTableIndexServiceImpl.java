/*
 * *************************************************************************************
 *  Copyright (C) 2006-2015 EsperTech, Inc. All rights reserved.                       *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.table.*;

public class EventTableIndexServiceImpl implements EventTableIndexService {
    public boolean allowInitIndex(boolean isRecoveringResilient) {
        return true;
    }

    public EventTableFactory createSingleCoerceAll(int indexedStreamNum, EventType eventType, String indexProp, Class indexCoercionType, Object optionalSerde, boolean isFireAndForget) {
        return new PropertyIndexedEventTableSingleCoerceAllFactory(indexedStreamNum, eventType, indexProp, indexCoercionType);
    }

    public EventTableFactory createSingleCoerceAdd(int indexedStreamNum, EventType eventType, String indexProp, Class indexCoercionType, Object optionalSerde, boolean isFireAndForget) {
        return new PropertyIndexedEventTableSingleCoerceAddFactory(indexedStreamNum, eventType, indexProp, indexCoercionType);
    }

    public EventTableFactory createSingle(int indexedStreamNum, EventType eventType, String propertyName, boolean unique, String optionalIndexName, Object optionalSerde, boolean isFireAndForget) {
        return new PropertyIndexedEventTableSingleFactory(indexedStreamNum, eventType, propertyName, unique, optionalIndexName);
    }

    public EventTableFactory createUnindexed(int indexedStreamNum, Object optionalSerde, boolean isFireAndForget) {
        return new UnindexedEventTableFactory(indexedStreamNum);
    }

    public EventTableFactory createMultiKey(int indexedStreamNum, EventType eventType, String[] indexProps, boolean unique, String optionalIndexName, Object optionalSerde, boolean isFireAndForget) {
        return new PropertyIndexedEventTableFactory(indexedStreamNum, eventType, indexProps, unique, optionalIndexName);
    }

    public EventTableFactory createMultiKeyCoerceAdd(int indexedStreamNum, EventType eventType, String[] indexProps, Class[] indexCoercionTypes, boolean isFireAndForget) {
        return new PropertyIndexedEventTableCoerceAddFactory(indexedStreamNum, eventType, indexProps, indexCoercionTypes);
    }
}
