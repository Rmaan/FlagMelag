package javachallenge.server;

/**
 * An interface for asynchronous play/pause/step message passing
 */
public class RemoteControl {
	private Boolean paused = false;
	private Object lock = new Object();

	public void play() {
		synchronized (paused) {
			if (paused) {
				paused = false;
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}
	}

	public void pause() {
		synchronized (paused) {
			if (!paused) {
				paused = true;
			}
		}
	}

	public void playPauseToggle() {
		synchronized (paused) {
			if (paused)
				play();
			else
				pause();
		}
	}

	public void step() {
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void waitForPlay() {
		synchronized (paused) {
			if (!paused)
				return;
		}

		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
