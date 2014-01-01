/*
 * Copyright 2010 Werner Guttmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.castor.cpa.jpa.processors.classprocessors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.core.annotationprocessing.AnnotationTargetException;
import org.castor.core.nature.BaseNature;
import org.castor.cpa.jpa.natures.JPAClassNature;
import org.castor.cpa.jpa.processors.BaseJPAAnnotationProcessor;

import javax.persistence.NamedNativeQuery;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wguttmn AT codehaus DOT org">Werner Guttmann</a>
 * @version $Revision$ $Date$
 */
public class JPANamedNativeQueryProcessor extends BaseJPAAnnotationProcessor {
    //-----------------------------------------------------------------------------------
    
    /**
     * The <a href="http://jakarta.apache.org/commons/logging/">Jakarta Commons
     * Logging</a> instance used for all logging.
     */
    private static final Log LOG = LogFactory.getFactory().getInstance(
            JPANamedNativeQueryProcessor.class);

    //-----------------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    public final Class<? extends Annotation> forAnnotationClass() {
        return NamedNativeQuery.class;
    }

    public final <I extends BaseNature,
            A extends Annotation> boolean processAnnotation(
            final I info, final A annotation, final AnnotatedElement target)
            throws AnnotationTargetException {
        if ((info instanceof JPAClassNature)
                && (annotation instanceof NamedNativeQuery)
                && (target instanceof Class<?>)
                && (target.isAnnotationPresent(NamedNativeQuery.class))) {
            LOG.debug("Processing class annotation " + annotation.toString());

            final JPAClassNature jpaClassNature = (JPAClassNature) info;
            final NamedNativeQuery namedNativeQuery = (NamedNativeQuery) annotation;
            final Map<String, String> namedNativeQueryMap = new HashMap<String, String>();
            namedNativeQueryMap.put(namedNativeQuery.name(), namedNativeQuery.query());
            jpaClassNature.setNamedNativeQuery(namedNativeQueryMap);
            return true;
        }
        return false;
    }

    //-----------------------------------------------------------------------------------
}