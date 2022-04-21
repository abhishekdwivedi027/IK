package algo.common.concurrency;

import common.Gender;
import common.Person;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

    /**
     * Concurrency => multithreading
     * synchronization => inter-thread communication (ex - mutex, volatile, sleep, join, wait/await, notify/signal)
     *
     * Deadlock -
     * Reasons: ALL of 1.mutex/lock on mutable shared state 2. nested locking 3. circular waiting 4. no preemption - can't force a thread to release lock
     * Preventions: ANY of 1. immutable state 2. release one before acquiring other 3. ordered locks 4. timed or interruptible locks
     * Recovery: No Way! Kill the process. Fix.
     *
     * Concepts:
     * 1. critical section - no concurrency - sandwiched between acquire and release locks - atomicity (all or none)
     * 2. reentrant lock - if a thread requesting lock on an object is same as the thread holding it, then let it through; implicit locks are reentrant
     * 3. concurrent modification - concurrency exception even when just ONE thread is working - use iterator, clone/copy of collections
     * 4. join() - hey calling thread, wait till I am done.
     * 5. wait() - I am a thread holding a lock (on object o) waiting on a condition to proceed. Since I am waiting, I will release this lock by calling wait(). Whosoever - please notify when this lock is available (when ANY condition is fulfilled; check spurious waking => NOT if(!condition) but while(!condition)).
     * 6. notifyAll() - I am a thread holding a lock (on object o), and I have changed a condition. I want to notify *all* threads who have been waiting for this lock (NOT on a particular condition) so that they can contend for this lock whenever I release it.
     * 7. await()/signal() - explicit conditions. more tha one conditions per lock.
     * 8. synchronizers - a. countdown latch b. semaphore c. cyclic barrier
     * 9. Executor Framework
     * 10. Context Switching, Lock Contention, Lock Striping, Compare & Swap
     * 11. Distributed Systems: Optimistic Locking (Less Collision/Contention), Transaction Isolation - 3 Phase Commit or Distributed Resource Manager
     *
     * Remember:
     * 1. no concurrency in class/static initialization (happens-before and happens-after relationships)
     * 2. synchronization doesn't work as expected unless both read and write ops are synchronized
     * 3. critical section should be minimized and must not contain 3rd party code invocation
     *
     */

    public static void main(String[] args) {
        Concurrency concurrency = new Concurrency();
        concurrency.mapReduce(10);
        // concurrency.unisexWashroom(3, 12);
    }

    public void mapReduce(int n) {
        CountDownLatch ready = new CountDownLatch(n);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(n);

        Runnable runningTask = () -> System.out.println("referee started the race. Running!");

        Thread referee = new Thread(() -> {
            try {
                System.out.println("waiting for runners to get ready.");
                ready.await();
                System.out.println("all runners ready. start the race!");
                start.countDown();
                System.out.println("waiting for runners to finish the race.");
                done.await();
                System.out.println("race done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        referee.start();

        ExecutorService runners = Executors.newFixedThreadPool(n);
        for (int i=0; i<n; i++) {
            runners.execute(() -> {
                ready.countDown();
                try {
                    System.out.println("me ready! waiting for referee to start the race");
                    start.await();
                    runningTask.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();
                }
            });
        }
    }

    public void unisexWashroom(int washroomCapacity, int people) {
        ExecutorService pool = Executors.newFixedThreadPool(people);
        UnisexWashroom washroom = new UnisexWashroom(washroomCapacity);
        Person person = null;
        int count = 0;
        while (count < people) {
            int random = (int) ((Math.random() * (people - count)) + count);
            if (count % 3 == 0) {
                person = new Person("Charles" + count, Gender.MALE, random * random);
            } else {
                person = new Person("Elizabeth" + count, Gender.FEMALE, random * random);
            }
            count++;
            UnisexRunnable runnable = new UnisexRunnable(washroom, person);
            pool.execute(runnable);
        }
    }

    class UnisexRunnable implements Runnable {

        UnisexWashroom washroom;
        Person person = null;

        UnisexRunnable(UnisexWashroom washroom, Person person) {
            this.washroom = washroom;
            this.person = person;
        }

        @Override
        public void run() {
            washroom.register(person);
        }
    }
}
