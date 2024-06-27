/*
 * Copyright 2000-2021 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.annotations

/**
 * An annotation which allows to specify for integral type (byte, char, short, int, long) an allowed values range.
 * Applying this annotation to other types is not correct.
 *
 *
 * Example:
 * <pre>`public @Range(from = 0, to = Integer.MAX_VALUE) int length() {
 * return this.length; // returns a non-negative integer
 * }`</pre>
 *
 * @since 17.0.0
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.TYPE)
@kotlin.jvm.ImplicitlyActualizedByJvmDeclaration
expect annotation class Range(
    /**
     * @return minimal allowed value (inclusive)
     */
    val from: Long,
    /**
     * @return maximal allowed value (inclusive)
     */
    val to: Long
)