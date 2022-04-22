package algo.common.concurrency;

import common.Gender;
import common.Person;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * TODO how come is this a multithreading problem where order must be enforced?
 */
public class UnisexWashroom {

    private int capacity;
    private final Set<Person> occupying = new HashSet<>();
    private final Queue<Person> waitingMale = new LinkedList<>();
    private final Queue<Person> waitingFemale = new LinkedList<>();
    private final Queue<Gender> waitingGender = new LinkedList<>();
    private Gender occupyingGender = null;

    public UnisexWashroom(int capacity) {
        this.capacity = capacity;
    }

    public void register(Person person) {
        wait(person);
        while (!waitingGender.isEmpty()) {
            Gender gender = waitingGender.poll();
            if (gender == Gender.FEMALE) {
                useWashroom(waitingFemale.poll());
            }
            if (gender == Gender.MALE) {
                useWashroom(waitingMale.poll());
            }
        }
    }

    public void useWashroom(Person person) {
        while (isOccupied(person) || isNotAvailable(person)) {
            wait(person);
        }

        occupy(person);

        try {
            System.out.println(person.getName() + " started");
            Thread.sleep(1000);
            System.out.println(person.getName() + " finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        leave(person);
    }

    private boolean isOccupied(Person person) {
        return occupying.size() == capacity || (occupyingGender != null && occupyingGender != person.getGender());
    }

    private boolean isNotAvailable(Person person) {
        return !waitingGender.isEmpty() && waitingGender.peek() != person.getGender();
    }

    private void wait(Person person) {
        Gender gender = person.getGender();
        waitingGender.offer(gender);
        if (gender == Gender.FEMALE) {
            waitingFemale.offer(person);
        }
        if (gender == Gender.MALE) {
            waitingMale.offer(person);
        }
    }

    private void occupy(Person person) {
        if (occupying.isEmpty()) {
            occupyingGender = person.getGender();
        }

        occupying.add(person);
    }

    private void leave(Person person) {
        occupying.remove(person);

        if (occupying.isEmpty()) {
            occupyingGender = null;
        }
    }
}
