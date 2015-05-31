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

import rx.Observer
import rx.Subscriber
import spock.lang.Specification
import rx.Observable

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Learning test to exercise the Rx Java APIs.
 */
class RxLearningTest extends Specification {

    def observer = [onCompleted: { println 'onCompleted called!'},
                    onError: { Throwable t -> println "onError called: ${t.message}" },
                    onNext: { String s -> println "onNext called with ${s}"} ] as Observer<String>

    def 'exercise observer from list'() {
        given: 'stream of data'
        def data = ['a', 'b', 'c']

        and: 'an observable'
        def observable = Observable.from( data )

        when: 'the observer is attached to the observer'
        observable.subscribe( observer )

        then: 'the data stream is printed out'
    }

    def 'exercise observer from iterable'() {
        given: 'stream of data'
        def data = ['a', 'b', 'c'] as Iterable

        and: 'an observable'
        def observable = Observable.from( data )

        when: 'the observer is attached to the observer'
        observable.subscribe( observer )

        then: 'the data stream is printed out'
    }

    def 'exercise observer from future'() {
        given: 'a future'
        def future = [cancel: { println 'cancel called' ; true },
                      isCancelled: { println 'isCancelled called' ; true },
                      isDone: { println 'isDone called' ; true },
                      get: { long timeout, TimeUnit unit -> println 'get called' ; Thread.sleep( 1000 ) ; 'some string' }
        ] as Future<String>

        and: 'an observable'
        def observable = Observable.from( future, 500, TimeUnit.MILLISECONDS )

        when: 'the observer is attached to the observer'
        observable.subscribe( observer )

        then: 'the data stream is printed out'
    }

    def 'exercise synchronous observable'() {
        given: 'an observable'
        def observable = Observable.create { Subscriber<String> aSubscriber ->
            ('a'..'z').each { letter ->
                if ( !aSubscriber.unsubscribed ) {
                    aSubscriber.onNext( letter )
                }
            }
            if ( !aSubscriber.unsubscribed ) {
                aSubscriber.onCompleted()
            }
        }

        when: 'the observer is attached to the observer'
        observable.subscribe {  println( it ) } as Subscriber<String>

        then: 'the data stream is printed out'
    }

    def 'exercise asynchronous observable'() {
        given: 'an observable'
        def callback = { Subscriber<String> aSubscriber ->
            Thread.start {
                ('A'..'Z').each { letter ->
                    if (!aSubscriber.unsubscribed) {
                        aSubscriber.onNext(letter)
                    }
                }
                if (!aSubscriber.unsubscribed) {
                    aSubscriber.onCompleted()
                }
            }
        } as Observable.OnSubscribe<String>

        def observable = Observable.create( callback )

        when: 'the observer is attached to the observer'
        observable.subscribe {  println( it ) } as Subscriber<String>

        then: 'the data stream is printed out'
    }

    def 'exercise synchronous transformations'() {
        given: 'an observable'
        def observable = Observable.create { Subscriber<String> aSubscriber ->
            ('a'..'z').each { letter ->
                if ( !aSubscriber.unsubscribed ) {
                    aSubscriber.onNext( letter )
                }
            }
            if ( !aSubscriber.unsubscribed ) {
                aSubscriber.onCompleted()
            }
        }

        when: 'the observer is attached to the observer'
        observable.skip( 10 ).take( 5 ).map { it + '!' }.subscribe {  println( it ) } as Subscriber<String>

        then: 'the data stream is printed out'
    }

}
