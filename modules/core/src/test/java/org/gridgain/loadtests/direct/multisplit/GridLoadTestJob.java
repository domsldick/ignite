/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.loadtests.direct.multisplit;

import org.apache.ignite.*;
import org.apache.ignite.compute.*;
import org.apache.ignite.resources.*;
import org.gridgain.grid.*;

import java.io.*;

/**
 * Load test job.
 */
public class GridLoadTestJob extends ComputeJobAdapter {
    /** */
    @IgniteInstanceResource
    private Ignite ignite;

    /**
     * Constructor.
     * @param arg Argument.
     */
    public GridLoadTestJob(Integer arg) {
        super(arg);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override public Serializable execute() throws GridException {
        Integer i = this.<Integer>argument(0);

        assert i != null && i > 0;

        if (i == 1)
            return new GridLoadTestJobTarget().executeLoadTestJob(1);

        assert ignite != null;

        ignite.compute().localDeployTask(GridLoadTestTask.class, GridLoadTestTask.class.getClassLoader());

        return (Integer) ignite.compute().execute(GridLoadTestTask.class.getName(), i);
    }
}
