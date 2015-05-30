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
package org.kurron.rx

import rx.functions.Action1
import spock.lang.Specification
import rx.Observable

/**
 * Learning test to exercise the Rx Java APIs.
 */
class RxLearningTest extends Specification {

    def 'exercise widget'() {
        given: 'stream of data'
        def data = ['a', 'b', 'c']

        and: 'an observable'
        def observable = Observable.from( data )

        and: 'an observer'
        def observer = { it -> println it } as Action1<String>

        when: 'the observer is attached to the observer'
        observable.subscribe( observer )

        then: 'the data stream is printed out'

    }
}