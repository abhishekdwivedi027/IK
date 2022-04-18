package algo.common.concurrency;

import java.util.HashMap;
import java.util.Map;

public class ReentrantReadWriteLock {

    private Map<Thread, Integer> readingThreads = new HashMap<>();
    private Thread writingThread = null;
    private int writeAccesses    = 0;
    private int writeRequests    = 0;

    public synchronized void lockRead() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(!canGrantReadAccess(callingThread)){
            wait();
        }

        readingThreads.put(callingThread, getReadAccessCount(callingThread) + 1);
    }

    public synchronized void unlockRead(){
        Thread callingThread = Thread.currentThread();

        // does this thread even have a lock?
        if(readingThreads.get(callingThread) == null) {
            throw new IllegalMonitorStateException("Calling Thread does not hold a read lock on this ReadWriteLock");
        }

        int readAccessCount = getReadAccessCount(callingThread);
        if (readAccessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, readAccessCount - 1);
        }

        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;

        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)){
            wait();
        }

        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        if(writingThread != callingThread) {
            throw new IllegalMonitorStateException("Calling Thread does not hold the write lock on this ReadWriteLock");
        }

        writeAccesses--;
        if(writeAccesses == 0){
            writingThread = null;
        }

        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread){

        // if this thread has write access - true
        if (callingThread == writingThread) {
            return true;
        }

        // if any other thread has write access - false
        if (writingThread != null) {
            return false;
        }

        // if this thread has read access - true
        if (readingThreads.get(callingThread) != null) {
            return true;
        }

        // if there are existing write requests - false
        if (writeRequests > 0) {
            return false;
        }

        return true;
    }

    private int getReadAccessCount(Thread callingThread){
        Integer readAccessCount = readingThreads.get(callingThread);
        if (readAccessCount == null) {
            return 0;
        } else {
            return readAccessCount.intValue();
        }
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        // if this thread has read access - true
        if (readingThreads.size() > 0 && readingThreads.get(callingThread) != null) {
            return true;
        }

        // if other threads have read access - false
        if (readingThreads.size() > 0) {
            return false;
        }

        // if no write access or calling thread has write access - true
        if (writingThread == null || callingThread == writingThread) {
            return true;
        }

        // if other thread has write access - false
        if (callingThread != writingThread) {
            return false;
        }

        return true;
    }
}
