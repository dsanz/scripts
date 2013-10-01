import com.liferay.portal.kernel.json.JSONObject

public interface CommandResultListener {
	// get listener results
	public JSONObject getResult();
	public String getResultAsString();

	// async way of know if we're done
	public boolean isDone();

	// sync way for result handlers to know that this listener finished receiving command result writes
	public void registerResultHandler(ResultHandler prh);
}