/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.server.session;

import org.junit.Test;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.5
 */
public final class ThrottledUseAndTimeoutExpirationPolicyTests extends AbstractExpirationPolicyTests {

    public ThrottledUseAndTimeoutExpirationPolicyTests() {
        super(new ThrottledUseAndTimeoutExpirationPolicy(10, 5));
    }

    @Test
    public void testNotSoImmediateUsage() {
        final State state = new SimpleStateImpl();
        sleep(2);
        assertFalse(getExpirationPolicy().isExpired(state));
    }

    @Test
    public void testItShouldBeExpired() {
        final State state = new SimpleStateImpl();
        sleep(12);
        assertTrue(getExpirationPolicy().isExpired(state));
    }

    @Test
    public void testExpiredBecauseItWasUsedToSoon() {
        final State state = new SimpleStateImpl();
        state.updateState();
        assertTrue(getExpirationPolicy().isExpired(state));
    }

    @Test
    public void testNotExpiredBecauseWeWaitedLongEnough() {
        final State state = new SimpleStateImpl();
        state.updateState();
        sleep(7);
        assertFalse(getExpirationPolicy().isExpired(state));
    }
}