/*
 * Copyright 2015-2023 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.console;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.junit.platform.console.CommandResult.SUCCESS;

import org.apiguardian.api.API;
import org.junit.platform.console.options.CommandLineOptions;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

/**
 * @since 1.10
 */
@API(status = INTERNAL, since = "1.10")
public class ExecutionCommandResultFactory {

	/**
	 * Exit code indicating test failure(s)
	 */
	private static final int TEST_FAILED = 1;

	/**
	 * Exit code indicating no tests found
	 */
	private static final int NO_TESTS_FOUND = 2;

	public static int computeExitCode(TestExecutionSummary summary, CommandLineOptions options) {
		if (options.isFailIfNoTests() && summary.getTestsFoundCount() == 0) {
			return NO_TESTS_FOUND;
		}
		return summary.getTotalFailureCount() == 0 ? SUCCESS : TEST_FAILED;
	}

	static CommandResult<TestExecutionSummary> forSummary(TestExecutionSummary summary, CommandLineOptions options) {
		int exitCode = computeExitCode(summary, options);
		return CommandResult.create(exitCode, summary);
	}
}