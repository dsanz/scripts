import com.liferay.portal.kernel.json.JSONObject

public interface CommandResultListener {
	// get listener results
	public JSONObject getResult();
	public String getResultAsString();

	// configures the number of notifications this listener expects
	public void setNumberOfNotifications(int numberOfNotifications);

	// tells this listener a new command result is available
	public void notifyValue(String key, String value);

	// async way of know if we're done
	public boolean isDone();

	// sync way for result handlers to know that this listener finished receiving command result writes
	public void registerResultHandler(ResultHandler prh);
}