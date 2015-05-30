/*
 * Copyright (c) 2015 Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.library

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Example Spock unit test.
 */
@Unroll( '#description' )
class SampleWidgetUnitTest extends Specification {

    def 'exercise widget'() {
        given: 'subject under test'
        def sut = new SampleWidget()

        when: 'echo is called'
        def result = sut.echo( message )

        then: 'expected response is returned'
        expected == result

        where:
        message || expected
        'bob'   || 'BOB'
        'ted'   || 'TED'
        'devan' || 'DEVAN'
        'logan' || 'LOGAN'

        description = "Sent ${message}, expecting ${expected}"
    }
}
