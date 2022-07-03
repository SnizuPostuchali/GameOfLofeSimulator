package com.mycompany.gameoflifesimulator.gol.util.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {

    private EventBus eventBus;

    @BeforeEach
    void setup(){
        eventBus = new EventBus();
    }

    @Test
    void singleListenerCalled(){
        HelloEvent event = new HelloEvent();
        TestEventListener<HelloEvent> listener = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, listener);

        eventBus.emit(event);

        assertTrue(listener.listenerCalled);
    }

    @Test
    void multipleListenersCalled(){
        HelloEvent event = new HelloEvent();
        TestEventListener<HelloEvent> listener1 = new TestEventListener<>();
        TestEventListener<HelloEvent> listener2 = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, listener1);
        eventBus.listenFor(HelloEvent.class, listener2);

        eventBus.emit(event);

        assertTrue(listener1.listenerCalled);
        assertTrue(listener2.listenerCalled);
    }

    @Test
    void multipleEventTypes(){
        HelloEvent helloEvent = new HelloEvent();
        WorldEvent worldEvent = new WorldEvent();

        TestEventListener<HelloEvent> helloListener1 = new TestEventListener<>();
        TestEventListener<WorldEvent> worldListener1 = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, helloListener1);
        eventBus.listenFor(WorldEvent.class, worldListener1);

//        eventBus.emit(helloEvent);
        eventBus.emit(worldEvent);

        assertFalse(helloListener1.listenerCalled);
        assertTrue(worldListener1.listenerCalled);
    }

    private static class TestEventListener<T extends Event> implements EventListener<T>{

        boolean listenerCalled;

        @Override
        public void handle(T event) {
            listenerCalled = true;
        }
    }

    private static class HelloEvent implements Event{
        private String value = "Hello";
    }

    private static class WorldEvent implements Event{
        private String value = "World";
    }
}