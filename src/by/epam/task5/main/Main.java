package by.epam.task5.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

import by.epam.task5.entity.port.Port;
import by.epam.task5.entity.ship.Container;
import by.epam.task5.entity.ship.Ship;

public class Main {
	static {
		new DOMConfigurator().doConfigure("log4j.xml",
				LogManager.getLoggerRepository());
	}

	public static void main(String[] args) {
		Container container = new Container();
		Port port = new Port(1);
		port.getStorage().add(container);
		Ship ship1 = new Ship("Ship1", 3, port);
		ship1.addContiner(container);
		ship1.addContiner(container);
		ship1.addContiner(container);
		Ship ship2 = new Ship("Ship2", 4, port);
		ship2.addContiner(container);
		ship2.addContiner(container);
		ship2.addContiner(container);
		Ship ship3 = new Ship("Ship3", 5, port);
		ship3.addContiner(container);
		ship3.addContiner(container);
		ship3.addContiner(container);
		ship3.addContiner(container);
		Ship ship4 = new Ship("Ship4", 5, port);
		ship4.addContiner(container);
		ship4.addContiner(container);
		Ship ship5 = new Ship("Ship5", 5, port);
		Ship ship6 = new Ship("Ship6", 5, port);
		ship6.addContiner(container);
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(6);
	
		
		executor.execute(ship5);
		executor.execute(ship6);
		executor.execute(ship2);
		executor.execute(ship3);
		executor.execute(ship1);
		executor.execute(ship4);
	
		executor.shutdown();

	}

}
