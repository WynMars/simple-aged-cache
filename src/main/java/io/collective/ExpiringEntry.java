package io.collective;

import java.time.Clock;
import  java.time.Instant;

public class ExpiringEntry {

    Object key;
    Object value;
    ExpiringEntry next;
    Clock clock;

    Instant expire_at;

    ExpiringEntry(Object key, Object value, Clock clock, int retention) {
        this.key = key;
        this.value = value;
        this.clock = clock;
        this.expire_at = clock.instant().plusMillis(retention);
        this.next = null;
    }

    Object getKey() {return key;}
    Object getValue() {return value;}
    ExpiringEntry getNextEntry() {return next;}

    public void setNextEntry(ExpiringEntry next) {this.next = next;}

    boolean isExpired() {return clock.instant().isAfter(expire_at);}

}
