package by.epam.task5.entity.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import by.epam.task5.entity.port.Port;
import by.epam.task5.exeption.ShipExeption;

public class Ship extends Thread {

	private static final Logger LOG = Logger.getLogger(Ship.class);

	private Port port;
	private List<Container> containers;
	private int capacity;

	public Ship(String nameShip, int capacity, Port port) {
		super(nameShip);
		this.port = port;
		this.capacity = capacity;
		this.containers = new ArrayList<>(capacity);
	}

	public Ship() {
	}

	public void moor() {
		this.port.getLock().lock();
		LOG.info(getName() + " moor.");
	}

	public void salis() {
		LOG.info(getName() + " salis.");
		this.port.getLock().unlock();
	}


	public void addContiner(Container container) {
		containers.add(container);
		LOG.info("Container the "+getName()+" is added.");
	}
	
	
	public boolean getOccupancy() {
		boolean occupancy = false;
		if (this.capacity == this.containers.size()) {
			occupancy = true;
		}
		return occupancy;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public Container getContainer(int index) {
		LOG.info("Container from the " + getName()+ " unloaded.");
		return containers.get(index);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result
				+ ((containers == null) ? 0 : containers.hashCode());
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
		Ship other = (Ship) obj;
		if (capacity != other.capacity) {
			return false;
		}
		if (containers == null) {
			if (other.containers != null) {
				return false;
			}
		} else if (!containers.equals(other.containers)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Ship [name=" + getName() + ", containers="
				+ containers + ", capacity=" + capacity + "]");
		return stringBuilder.toString();
	}

	private boolean random() {
		boolean random = false;
		Random x = new Random();
		int y = x.nextInt(2);
		if (y == 0) {
			random = true;
		}
		return random;
	}
	
	private  synchronized void  awaitShip(){
		if(getOccupancy() && port.getOccupancy() || containers.size() == 0 && port.getStorage().size() == 0){
			LOG.info( getName() + " did not wait for the release of the port warehouse");
			salis();
		}else {
			working();
		}
		
	}
		private void working() {
			if (getOccupancy()) {// проверяем заполненость корабля - корабль заполнен
				if (port.getOccupancy()) {// проверяем заполненость порта - порт заполнен					
					try {//корабль ждет пока появиться место на складе в порту
						LOG.info( getName() + " wait when the free space on the storage port.");
						port.getIsFree().await(5,TimeUnit.SECONDS);
						awaitShip();
					} catch (InterruptedException e) {
						LOG.warn(new ShipExeption(e));
					}
				} else {// порт не заполнен
					port.addContainer((getContainer(0)));// кладем контейнер склад порта
				}
			} else {// проверяем заполненость корабля - корабль незаполнен
				if (port.getOccupancy()) {// проверяем заполненость порта - порт заполнен
					addContiner((port.getContainer()));// берем контейнер из склада в корабль
				} else {// порт не заполнен
					if (containers.size() == 0 && port.getStorage().size() == 0) {// если оба пусты
						try {//корабль ждет пока появиться контейнер на складе в порту
							LOG.info( getName() + " waits to appear on the container stock in the port.");
							port.getIsFree().await(7,TimeUnit.SECONDS);
							awaitShip();
						} catch (InterruptedException e) {
							LOG.warn(new ShipExeption(e));
						}
					}else{
					// рандомно происходит оперция выгрузки\загрузки контейнера
					if (random()) {
						addContiner((port.getContainer()));// берем контейнер из порта
					} else {
						port.addContainer((getContainer(0)));// кладем контейнер в порт
					}}
				}
			}
		}

	@Override
	public void run() {
		moor();
		working();
		salis();
	}

}
