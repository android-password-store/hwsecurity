/*
 * Copyright (C) 2018-2021 Confidential Technologies GmbH
 *
 * You can purchase a commercial license at https://hwsecurity.dev.
 * Buying such a license is mandatory as soon as you develop commercial
 * activities involving this program without disclosing the source code
 * of your own applications.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.cotech.hw.fido.internal.async;


import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import android.os.Looper;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 24)
public class FidoAsyncOperationManagerTest {

    private FidoAsyncOperationManager fidoAsyncOperationManager;

    @Before
    public void setup() {
        fidoAsyncOperationManager = new FidoAsyncOperationManager();
    }

    @Test
    public void startAsyncOperation() throws Exception {
        TestFidoOperationThread thread = new TestFidoOperationThread();
        fidoAsyncOperationManager.startAsyncOperation(null, thread);
        FidoAsyncOperationManagerUtil.joinRunningThread(fidoAsyncOperationManager);
        final ShadowLooper looper = shadowOf(Looper.getMainLooper());
        assertNotEquals(looper.getNextScheduledTaskTime(), Duration.ZERO);
        looper.idle();
        assertEquals(looper.getNextScheduledTaskTime(), Duration.ZERO);
        thread.assertLatchOk();
    }

    @Test
    public void startAsyncOperation_thenClear() throws Exception {
        CountDownLatch delayLatch = new CountDownLatch(1);
        TestFidoOperationThread thread = new TestFidoOperationThread(delayLatch);
        fidoAsyncOperationManager.startAsyncOperation(null, thread);
        fidoAsyncOperationManager.clearAsyncOperation();
        FidoAsyncOperationManagerUtil.joinRunningThread(fidoAsyncOperationManager);
        final ShadowLooper looper = shadowOf(Looper.getMainLooper());
        assertEquals(looper.getNextScheduledTaskTime(), Duration.ZERO);
        looper.idle();
        assertEquals(looper.getNextScheduledTaskTime(), Duration.ZERO);
    }


}