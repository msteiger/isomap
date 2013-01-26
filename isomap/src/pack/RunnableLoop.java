/*
 * Copyright (C) 2012-2013 Martin Steiger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates a {@link Runnable} continuously
 * @author Martin Dummer
 */
public class RunnableLoop
{
	private final static Logger log = LoggerFactory.getLogger(RunnableLoop.class); 
			
	private volatile boolean shouldStop = false;
	private volatile boolean shouldSleep = true;

	private volatile long maxFPS = 40;
	
	private Runnable runnable;	// synchronized using a lock

	private final Object lock = new Object();

	private final Runnable loopingRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			log.info("Thread ID=" + Thread.currentThread().getId() + " started");

			while (!shouldStop)
			{
				if (shouldSleep)
				{
					synchronized (lock)
					{
						try
						{
							while (shouldSleep)
								lock.wait();

							if (shouldStop)
								break;
						}
						catch (InterruptedException e)
						{
							Thread.currentThread().interrupt();
						}
					}
				}

				long old = System.nanoTime();

				synchronized (lock)
				{
					if (runnable != null)
					{
						try
						{
							runnable.run();
						}
						catch (Exception e)
						{
							log.error(e.toString());
						}
					}
				}

				if (maxFPS > 0)
				{
					long interval = 1000 / maxFPS;
					long now = System.nanoTime();
					long dt = (now - old) / 1000000;

					try
					{
						while (dt < interval)
						{
							Thread.sleep(1);
							dt = (System.nanoTime() - old) / 1000000;
						}
					}
					catch (InterruptedException e)
					{
						log.warn("Update thread sleep() interrupted");
					}
				}
			}

			log.info("Thread ID=" + Thread.currentThread().getId() + " terminated");
		}
	};

	private Thread thread = new Thread(loopingRunnable, "Loop Thread");
	
	/**
	 * Default constructor without runnable
	 */
	public RunnableLoop()
	{
		// nothing to do
	}

	/**
	 * @param runnable the runnable that should be updated (can be null)
	 */
	public RunnableLoop(Runnable runnable)
	{
		setRunnable(runnable);
	}

	/**
	 * The thread will sleep until resume()
	 * or setLayout(runnable != null) is called
	 */
	public void sleep()
	{
		shouldSleep = true;
	}
	
	/**
	 * @return true if the thread is sleeping
	 */
	public boolean isSleeping()
	{
		return shouldSleep;
	}
	
	/**
	 * Causes the thread to wake up and continue
	 */
	public void resume()
	{
		if (!shouldSleep)
			return;
	
		shouldSleep = false;
		
		synchronized(lock)
		{
			lock.notifyAll();
		}
	}
	
	/**
	 * Causes the thread to terminate
	 */
	public void stop()
	{
		shouldStop = true;
		shouldSleep = false;	// wake up if sleeping

		synchronized(lock)
		{
			lock.notifyAll();
		}
	}

	/**
	 * Indicates whether the thread is about to terminate
	 * @return true if the thread is about to terminate
	 */
	public boolean isStopping()
	{
		return shouldStop;
	}

	/**
	 * @return the update interval in millisec.
	 */
	public long getMaxFps()
	{
		return maxFPS;
	}

	/**
	 * @param fps the maximum number of frames 
	 * per second or 0 if unlimited 
	 */
	public void setMaxFps(long fps)
	{
		this.maxFPS = fps;
	}

	/**
	 * @return the runnable
	 */
	public Runnable getRunnable()
	{
		return runnable;
	}

	/**
	 * @param runnable the runnable to set
	 */
	public void setRunnable(Runnable runnable)
	{
		if (!thread.isAlive())
			thread.start();
		
		synchronized (lock)
		{
			this.runnable = runnable;
		}

		shouldSleep = (runnable == null);
	}

	/**
	 * Waits for the loop thread to die.
	 */
	public void join()
	{
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			thread.interrupt();
		}
	}
}
