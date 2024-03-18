package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    Clock clock;
    ExpiringEntry head;
    ExpiringEntry tail;

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.head = null;
        this.tail = this.head;
    }

    public SimpleAgedCache(){
        this.clock = Clock.systemUTC();
        this.head = null;
        this.tail = this.head;
    }

    public void put(Object key, Object value, int retention) {
        ExpiringEntry input = new ExpiringEntry(key, value, this.clock, retention);
        if (this.head == null) {
            this.head = input;
            this.tail = input;
            return;
        }
        this.tail.setNextEntry(input);
        this.tail = this.tail.getNextEntry();
    }

    public  boolean isEmpty() { return this.head == null;}

    public int size() {
        int len = 0;
        if (this.head == null) {
            return 0;
        }

        var curr = this.head;
        if(this.head.isExpired() == false) {
            len++;
        }
        while(curr.getNextEntry() != null) {
            curr = curr.getNextEntry();
            if (curr.isExpired()) {
                continue;
            }
            len++;
        }
        return len;
    }

    public Object get(Object key) {
        if(this.head == null) {
            return null;
        }
        var curr = this.head;
        while(curr.getKey() != key){
            curr = curr.getNextEntry();
            if(curr == null){
            return null;
            }
            if (curr.isExpired()){
                return null;
            }
        }
    return curr.getValue();
    }

}