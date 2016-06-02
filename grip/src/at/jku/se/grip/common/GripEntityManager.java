package at.jku.se.grip.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton class to hold GripEntityManager.
 */
public class GripEntityManager {
	private static GripEntityManager singleton = new GripEntityManager();
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.DEFAULT_PERSISTENCE_IDENTIFIER);
	private final ThreadLocal<EntityManager> threadLocalEntityManager =  new ThreadLocal<EntityManager>();
	private final ThreadLocal<Integer> sessions = new ThreadLocal<Integer>();

	private GripEntityManager() {
		
	}
	
	/**
	 * @return the single instance of the GripEntityManager
	 */
	public static GripEntityManager getInstance() {
		if(singleton == null){
			singleton = new GripEntityManager();
		}
		return singleton;
	}
	
	/**
	 * Get the EntityManager for the default persistence {@value Constants#DEFAULT_PERSISTENCE_IDENTIFIER}}
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		EntityManager em = threadLocalEntityManager.get();
		if(em == null || !em.isOpen()) {
			em = factory.createEntityManager();
			threadLocalEntityManager.set(em);
			
			LogUtilities.log().info("thread " + Thread.currentThread().getId() + " created entity manager: " + em);
		}
		return em;
	}
	
	/**
	 * Increase the session counter for the current thread or start with count = 0.
	 */
	public void beginSession() {
		Integer count = sessions.get();
		if(count == null) {
			count = 0;
		}
		sessions.set(++count);
		
		LogUtilities.log().debug("thread " + Thread.currentThread().getId() + " began a new session, nesting count is now " + count);
	}
	
	/**
	 * Decrease session count for nested session.<br>
	 * Clear and close EntityManger for top level session.
	 */
	public void closeSession() {

		Integer count = sessions.get();
		if(count == null) {
			count = 1;
		}
		count --;

		boolean closedEm = false;
		if(count <= 0) {
			if (count < 0) {
				LogUtilities.log().warn("thread " + Thread.currentThread().getId() + " closed a session which was not opened, count is " + count);
			}
			EntityManager em = threadLocalEntityManager.get();
			if(em != null) {
				threadLocalEntityManager.remove();
				em.clear();
				em.close();
				LogUtilities.log().debug("thread " + Thread.currentThread().getId() + " closed entity manager: " + em + " because session nesting count was 0");
				closedEm = true;
			}
			sessions.remove();
		} else {
			sessions.set(count);
		}

		LogUtilities.log().debug("thread " + Thread.currentThread().getId() + " closed a session, nesting count is now " + count +
				(closedEm ? " (the entity manager was closed)" : ""));
	}
}