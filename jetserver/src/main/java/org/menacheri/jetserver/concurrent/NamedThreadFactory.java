package org.menacheri.jetserver.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory
{
	private static AtomicInteger counter = new AtomicInteger(1); 
    private String        name = "Lane"; 
    private boolean       daemon; 
    private int           priority; 

    public NamedThreadFactory(String name) { 
        this(name, false, -1); 
    } 

    public NamedThreadFactory(String name, boolean daemon) { 
        this(name, daemon, -1); 
    } 

    public NamedThreadFactory(String name, boolean daemon, int priority) { 
        this.name = name; 
        this.daemon = daemon; 
        this.priority = priority; 
    } 

    @Override 
    public Thread newThread(Runnable r) { 
        Thread thread = new Thread(r, name + "[" + counter.getAndIncrement() + "]"); 
        thread.setDaemon(daemon); 
        if (priority != -1) { 
            thread.setPriority(priority); 
        } 
        return thread; 
    }
}
# Change 2 on 2019-05-30
# Change 0 on 2019-08-28
# Change 2 on 2019-09-25
# Change 1 on 2019-10-03
# Change 2 on 2019-11-23
# Change 3 on 2019-11-30
# Change 0 on 2019-12-12
# Change 3 on 2020-08-18
# Change 1 on 2021-02-02
