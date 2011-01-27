package cg.oam.proxy;

import java.util.Date;

public interface AdaptationEngineProxy {

	/**
	 * Return the date when this object was loaded.
	 */
	public Date getLoadDate();

	/**
	 * Return the number of hits this object has handled.
	 */
	public int getAccessCount();

	public int getCurrentRequests();

	/**
	 * Return the number of milliseconds since this object was loaded.
	 */
	public long getUptimeMillis();

	/**
	 * Return the average response time achieved by this object.
	 */
	public int getAverageResponseTimeMillis();

	/**
	 * Return the best (lowest) response time achieved by this object.
	 */
	public int getBestResponseTimeMillis();

	/**
	 * Return the worst (slowest) response time achieved by this object.
	 */
	public int getWorstResponseTimeMillis();

	public int getErrorCount();

	public int getSuccessCount();

	public String getStatus();

	public long getTaskTimeout();

	public void setTaskTimeout(long timeout);

	public String getRequestCounts();

	public String getSummary();
	
	public boolean clearStatistics();

}