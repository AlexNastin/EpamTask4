package by.epam.task5.entity.port;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import by.epam.task5.entity.ship.Container;

public class Port {
	private static final Logger LOG = Logger.getLogger(Port.class);

	private ArrayBlockingQueue<Container> storage;
	private int capacity;
	private Lock lock = new ReentrantLock();
	private Condition isFree = lock.newCondition();

	public Port() {
	}

	public Port(int capacity) {
		super();
		this.capacity = capacity;
		this.storage = new ArrayBlockingQueue<>(capacity);
	}

	public Condition getIsFree() {
		return isFree;
	}

	public void setIsFree(Condition isFree) {
		this.isFree = isFree;
	}

	public ArrayBlockingQueue<Container> getStorage() {
		return storage;
	}

	public void setStorage(ArrayBlockingQueue<Container> storage) {
		this.storage = storage;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;

	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public  void addContainer(Container container) {
		storage.add(container);
		LOG.info("Container the port is added.");
	}

	public Container getContainer() {
		Container container = storage.poll();
		LOG.info("Container from the port unloaded.");
		return container;
	}

	public boolean getOccupancy() {
		boolean occupancy = false;
		if (this.capacity == this.storage.size()) {
			occupancy = true;
		}
		return occupancy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((isFree == null) ? 0 : isFree.hashCode());
		result = prime * result + ((lock == null) ? 0 : lock.hashCode());
		result = prime * result + ((storage == null) ? 0 : storage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Port other = (Port) obj;
		if (capacity != other.capacity) {
			return false;
		}
		if (isFree == null) {
			if (other.isFree != null) {
				return false;
			}
		} else if (!isFree.equals(other.isFree)) {
			return false;
		}
		if (lock == null) {
			if (other.lock != null) {
				return false;
			}
		} else if (!lock.equals(other.lock)) {
			return false;
		}
		if (storage == null) {
			if (other.storage != null) {
				return false;
			}
		} else if (!storage.equals(other.storage)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Port [storage=" + storage + ", capacity="
				+ capacity + ", lock=" + lock + ", isFree=" + isFree + "]");
		return stringBuilder.toString();
	}

}
